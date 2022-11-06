package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ProductTests extends BaseTest{
    LoginPage loginPage;
    ProductsPage productsPage;
    SettingsPage settingsPage;
    ProductDetailsPage  productDetailsPage;
    InputStream dataIS;
    JSONObject loginUsers;
    
        @BeforeClass
        public void beforeClass() throws IOException {
            try {
            String dataFileName = "data/loginUsers.json";
            dataIS = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(dataIS);
            loginUsers = new JSONObject(tokener);
            }catch(Exception e) {
        	e.printStackTrace();
        	throw e;
            }finally {
        	if(dataIS != null) {
        	    dataIS.close();
        	}
            }
            closeApp();
            launchApp();
         }
        
        @AfterClass
        public void afterClass() {
        }
            
        @BeforeMethod
        public void beforeMethod(Method m) {
           loginPage = new LoginPage(getDriver());
           TestUtils.log("\n" + "********* Starting Test: " + m.getName() + "\n");
           
           productsPage =  loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
			loginUsers.getJSONObject("validUser").getString("password"));
           
        }
        
        @AfterMethod
        public void afterMethod() {
            settingsPage = productsPage.pressSettingsButton();
            loginPage = settingsPage.pressLogoutButton();
        }
          
        @Test
        public void validateProductOnProductsPage() {
            	SoftAssert sa = new SoftAssert();
        	
        	
        	String SLBTitle = productsPage.getSLBTitle();
                sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));
                
                String SLBPrice = productsPage.getSLBPrice();
                sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));
                           
                sa.assertAll();
               
        }
        
        @Test
        public void validateProductOnProductDetailsPage() {
            	SoftAssert sa = new SoftAssert();
        	
        	productDetailsPage  = productsPage.pressSLBTitle();
        	
        	String SLBTitle = productDetailsPage.getSLBTitle();
                sa.assertEquals(SLBTitle, getStrings().get("product_detials_page_slb_title"));
                
                String SLBPrice = productDetailsPage.getSLBText();
                sa.assertEquals(SLBPrice, getStrings().get("products_details_page_slb_text"));
              
                productsPage = productDetailsPage.pressBackToProductsButton();
                
                sa.assertAll();
               
        }
        
  
}
