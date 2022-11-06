package com.qa.pages;




import org.openqa.selenium.By;

import com.qa.MenuPage;
import com.qa.Actions.Actions;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;


public class ProductsPage {
    
       @SuppressWarnings("rawtypes")
    AppiumDriver driver;
    @SuppressWarnings("rawtypes")
    public ProductsPage(AppiumDriver driver) {
	this.driver = driver;
    }
    //@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") private MobileElement productTitleText;
    private final By productTitleText = MobileBy.xpath("//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView");
    private final By SLBTitle= MobileBy.xpath("//android.widget.TextView[@content-desc=\"test-Item title\"][1]");
    private final By SLBPrice= MobileBy.xpath("//android.widget.TextView[@content-desc=\"test-Price\"][1]");
    
    public String getTitle() {
	return Actions.getAttibute(driver, productTitleText, "text");
    }
    public String getSLBTitle() {
	return Actions.getAttibute(driver, SLBTitle, "text");
    }
    public String getSLBPrice() {
	return Actions.getAttibute(driver, SLBPrice, "text");
    }
    
    public ProductDetailsPage pressSLBTitle() {
	Actions.click(driver, SLBTitle);
	TestUtils.log("Pressed SLB Title.");
	return new ProductDetailsPage(driver);
    }
    public SettingsPage pressSettingsButton() {
	MenuPage mp = new MenuPage(driver);
	return mp.pressSettingsButton();
    }
    
    
}
