package com.qa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class BaseTest {
    @SuppressWarnings("rawtypes")
    protected static ThreadLocal <AppiumDriver>driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal <HashMap<String, String>>();
    protected static ThreadLocal <String> platform = new ThreadLocal<String>();
    protected static ThreadLocal <String> device = new ThreadLocal<String>();
    protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
    TestUtils utils;
    static Logger log = LogManager.getLogger(BaseTest.class.getName());
   private static AppiumDriverLocalService server;
   
   
    public BaseTest() {
    }
    
    @SuppressWarnings("rawtypes")
    @Parameters({"platformName","platformVersion", "udid", "deviceName", "emulator", "systemPort", "chromeDriverPort"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String udid, String deviceName, String emulator, String systemPort, String chromeDriverPort) throws IOException {
	utils = new TestUtils();
	
	String strFile = "logs" + File.separator + platformName + "_" + deviceName;
	File logFile = new File(strFile);
	if (!logFile.exists()) {
		logFile.mkdirs();
	}
	//route logs to separate file for each thread
	ThreadContext.put("ROUTINGKEY", strFile);
	utils.log().info("log path: " + strFile);
	
	log.info("Before Test method started...");
	log.error("this is the error message.");

	 setDateTime(utils.dateTime());
	  setPlatform(platformName);
	  setDeviceName(deviceName);
	  AppiumDriver driver;
	  Properties props = new Properties();
	  InputStream inputStream = null;
	InputStream stringsIS = null;
	 URL url;
	 
	 
	 
	 
	try {
	    	
	    	String propFileName = "config.properties";
	    	String xmlFileName = "strings/strings.xml";
	    	
	    	inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	    	props.load(inputStream);
	    	
	    	stringsIS = getClass().getClassLoader().getResourceAsStream(xmlFileName);
	    	
	    	 setStrings(utils.parseStringXML(stringsIS));
	    	
	    	DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
	    	desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
	    	desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
	    	desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
	    	desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
	    	desiredCapabilities.setCapability("udid", udid);
	    	desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
	    	desiredCapabilities.setCapability("appActivity",props.getProperty("androidAppActivity"));
	    	desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);
	    	desiredCapabilities.setCapability("systemPort", systemPort);
		//URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
		String appUrl = System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test" + 
						File.separator + "resources" + File.separator + "app" + File.separator + 
						"Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
		
		desiredCapabilities.setCapability("app", appUrl);
		TestUtils.log(appUrl);
		url = new URL(props.getProperty("appiumUrl"));

		driver = new AndroidDriver(url, desiredCapabilities);
		
		setDriver(driver);
	}catch(Exception e) {
	    e.printStackTrace();
	
	}finally {
	    if(inputStream != null) {
		inputStream.close();
	    }
	    if(stringsIS != null) {
		stringsIS.close();
	    }
	}
	
    }
    
    @BeforeSuite
    public void beforeSuite() {
	server = getAppiumServerDefault();
	server.start();
	System.out.println("Appium server started...");
    }
    
    @AfterSuite
    public void afterSuite() {
	server.stop();
	System.out.println("Appiums erver stopped.");
	
    }
    
    public AppiumDriverLocalService getAppiumServerDefault() {
	return AppiumDriverLocalService.buildDefaultService();
    }
    
    @BeforeMethod
	public void beforeMethod() {
		((CanRecordScreen) getDriver()).startRecordingScreen();
	}
    
    
    
  //stop video capturing and create *.mp4 file
  	@AfterMethod
  	public synchronized void afterMethod(ITestResult result) throws Exception {
  		String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
  		
  		Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();		
  		String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") 
  		+ File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
  		
  		File videoDir = new File(dirPath);
  		
  		synchronized(videoDir){
  			if(!videoDir.exists()) {
  				videoDir.mkdirs();
  			}	
  		}
  		FileOutputStream stream = null;
  		try {
  			stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
  			stream.write(Base64.decodeBase64(media));
  			stream.close();
  			utils.log().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
  		} catch (Exception e) {
  			utils.log().error("error during video capture" + e.toString());
  		} finally {
  			if(stream != null) {
  				stream.close();
  			}
  		}		
  	}
  	
    
    
    public void closeApp() {
	((InteractsWithApps) getDriver()).closeApp();
    }
    
    public void launchApp() {
	((InteractsWithApps) getDriver()).launchApp();
    }
    
    public AppiumDriver getDriver() {
	  return driver.get();
}

public void setDriver(AppiumDriver driver2) {
	  driver.set(driver2);
}

public Properties getProps() {
	  return props.get();
}

public void setProps(Properties props2) {
	  props.set(props2);
}
    
public HashMap<String, String> getStrings() {
	  return strings.get();
}

public void setStrings(HashMap<String, String> strings2) {
	  strings.set(strings2);
}

public String getPlatform() {
	  return platform.get();
}

public void setPlatform(String platform2) {
	  platform.set(platform2);
}

public String getDateTime() {
	  return dateTime.get();
}

public void setDateTime(String dateTime2) {
	  dateTime.set(dateTime2);
}

public String getDeviceName() {
	  return device.get();
}

public void setDeviceName(String deviceName2) {
	  device.set(deviceName2);
}
    
    @AfterTest
    public void afterTest() {
	getDriver().quit();
    }
    
    
}
