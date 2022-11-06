package com.qa.pages;


import org.openqa.selenium.By;

import com.qa.Actions.Actions;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;


public class LoginPage  {
    @SuppressWarnings("rawtypes")
     AppiumDriver driver;
    
    @SuppressWarnings("rawtypes")
    public LoginPage(AppiumDriver driver2) {
	this.driver = driver2;
    }
   
    private final By usernameTextField = MobileBy.AccessibilityId("test-Username");
    private final By passwordTextField = MobileBy.AccessibilityId("test-Password");
    private final By loginButton = MobileBy.AccessibilityId("test-LOGIN");
    private final By errorText = MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView");
    
    public LoginPage enterUserName(String username) {
	Actions.clear(driver, usernameTextField);
	 Actions.sendKeys(driver, usernameTextField, username);
	 TestUtils.log("Login with username: " + username);
	return this;
	}
    
    public LoginPage enterPassword(String password) {
	Actions.clear(driver, passwordTextField);
	 Actions.sendKeys(driver, passwordTextField, password);
	 TestUtils.log("Login with password: " + password);
	return this;
	}
    
    public ProductsPage  pressLoginButton() {
	Actions.click(driver, loginButton);
	TestUtils.log("Pressed Login Button.");
	return new ProductsPage(driver);
    }
    
    public String getErrorText() {
	return Actions.getAttibute(driver, errorText, "text");
    }
    
    public ProductsPage login(String username, String password) {
	enterUserName(username);
	enterPassword(password);
	return pressLoginButton();
    }
    
    
}



