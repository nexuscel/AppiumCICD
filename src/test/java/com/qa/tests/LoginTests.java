package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
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

public class LoginTests extends BaseTest{
    LoginPage loginPage;
    ProductsPage productsPage;
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
        }
        
        @AfterMethod
        public void afterMethod() {
        }
          
        @Test
        public void invalidUserName() {
            	
    	 loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
             loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
             loginPage.pressLoginButton();
             
             String actualErrorText = loginPage.getErrorText();
             String expectedErrorText =getStrings().get("err_invalid_username_or_password");
             TestUtils.log("Actual error text: " + actualErrorText + "\nExpected error test: "+ expectedErrorText);
             
             Assert.assertEquals(actualErrorText, expectedErrorText);
               
        }
        
        @Test
        public void invalidPassword() {
           
            loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
            loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
            loginPage.pressLoginButton();
           
            String actualErrorText = loginPage.getErrorText();
            String expectedErrorText = getStrings().get("err_invalid_username_or_password");
            TestUtils.log("Actual error text: " + actualErrorText + "\nExpected error test: "+ expectedErrorText);
            
            Assert.assertEquals(actualErrorText, expectedErrorText);
                  
        }
        
        @Test
        public void successfulLogin() {
           
            loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
            loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
            productsPage = loginPage.pressLoginButton();

            String actualproductTitleText= productsPage.getTitle();
            String expectedProductTitleText =getStrings().get("product_title");
            
            TestUtils.log("Actual Product Title: " + actualproductTitleText + "\nExpected Product Title: "+ expectedProductTitleText);
          
            Assert.assertEquals(actualproductTitleText, expectedProductTitleText);
 
        }
  
}
