package com.qa;

import org.openqa.selenium.By;

import com.qa.Actions.Actions;
import com.qa.pages.SettingsPage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class MenuPage {

    AppiumDriver driver;
    
    public MenuPage(AppiumDriver driver) {
	this.driver = driver;
    }
    private final By settingsButton = MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By cartButton = MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");

    public SettingsPage pressSettingsButton() {
	Actions.click(driver, settingsButton);
	return new SettingsPage(driver);
    }
    
    public void pressCartButton() {
	Actions.click(driver, cartButton);
    }
    
    
}
