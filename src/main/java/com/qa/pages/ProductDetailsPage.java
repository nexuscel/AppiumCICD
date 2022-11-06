package com.qa.pages;

import org.openqa.selenium.By;

import com.qa.Actions.Actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class ProductDetailsPage {

    AppiumDriver driver;
    @SuppressWarnings("rawtypes")
    public ProductDetailsPage(AppiumDriver driver) {
	this.driver = driver;
    }
    
    private final By SLBTitle= MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]");
    private final By SLBText= MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]");
    private final By backToProductsButton = MobileBy.AccessibilityId("test-BACK TO PRODUCTS");

    public String getSLBTitle() {
	return Actions.getAttibute(driver, SLBTitle, "text");
    }
    public String getSLBText() {
	return Actions.getAttibute(driver, SLBText, "text");
    }
    
    public ProductsPage pressBackToProductsButton() {
	Actions.click(driver, backToProductsButton);
	return new ProductsPage(driver);
    }
    
}
