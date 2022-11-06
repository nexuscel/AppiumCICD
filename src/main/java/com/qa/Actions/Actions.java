package com.qa.Actions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Actions {
    
    @SuppressWarnings("rawtypes")
    public static void waitForVisibility(AppiumDriver driver,By element) {
   	WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
   	wait.until(ExpectedConditions .visibilityOfElementLocated(element));
   	
       }
       
    @SuppressWarnings("rawtypes")
    public static void clear(AppiumDriver driver, By element) {
	waitForVisibility(driver, element);
	driver.findElement(element).clear();
    }
    
       @SuppressWarnings("rawtypes")
    public static void click(AppiumDriver driver, By element) {
   	waitForVisibility(driver, element);
   	driver.findElement(element).click();
       }
       
       @SuppressWarnings("rawtypes")
    public static void sendKeys(AppiumDriver driver, By element, String text) {
   	waitForVisibility(driver, element);
   	driver.findElement(element).sendKeys(text);
       }
       
       @SuppressWarnings("rawtypes")
    public static String getAttibute(AppiumDriver driver, By element, String attribute) {
   	waitForVisibility(driver, element);
   	return driver.findElement(element).getAttribute(attribute);
       }
       
     
       
       public static void scrollUsingTouchAction(AppiumDriver driver, String direction) {
	   Dimension dim = driver.manage().window().getSize();
	   int x = dim.getWidth()/2;
	   int startY = 0;
	   int endY = 0;
	   
	   switch(direction) {
	   case "up":
	       startY = (int) (dim.getHeight() * 0.8);
	       endY = (int) (dim.getHeight() * 0.2);
	       break;
	   case "down":
	       startY = (int) (dim.getHeight() * 0.2);
	       endY = (int) (dim.getHeight() * 0.8);
	       break;
	   }
	   
	   TouchAction ta = new TouchAction(driver);
	   ta.press(PointOption.point(x, startY))
	   	.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
	   	.moveTo(PointOption.point(x,endY))
	   	.release()
	   	.perform();
	   
       }
       
       public void scrollToElement(AppiumDriver driver) {
	   ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
		   "new UiScrollable(newUiSelector()" + ".description(\"<parent_locator>\")).scrollIntoView("
			   +"new UiSelector().description(\"<child_locator>\"));");
       }
       
       
}
