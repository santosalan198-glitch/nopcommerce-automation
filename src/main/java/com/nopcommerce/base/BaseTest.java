package com.nopcommerce.base;

import com.nopcommerce.utils.ConfigReader;
import com.nopcommerce.utils.DriverFactory;
import com.nopcommerce.utils.DriverManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = System.getProperty("browser", ConfigReader.get("browser"));
        DriverManager.setDriver(DriverFactory.createDriver(browser));
        DriverManager.getDriver().get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        DriverManager.quitDriver();
    }
}