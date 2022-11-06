package com.qa.pages;

import org.openqa.selenium.By;

import com.qa.Actions.Actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

public class SettingsPage {
    	
    AppiumDriver driver;
    
    public SettingsPage(AppiumDriver driver) {
	this.driver = driver;
    }
    
    private final By logoutButton = MobileBy.AccessibilityId("test-LOGOUT");
    
    public LoginPage pressLogoutButton() {
	Actions.click(driver, logoutButton);
	return new LoginPage(driver);
    }
    
}
