package com.nopcommerce.account;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

public class Level_02_BasePage_03_Inheritance extends BasePage{
    WebDriver driver;
    //Khoonng nên khơi tạo đối tượng ngoài phạm vi của class. vi pham nguyên tác khởi tạo
    //nên che giấu

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();


        driver.manage().window().maximize();
    }

    @Test
    public void Register_01_Empty_Data() {
        openPageUrl(driver, "https://demo.nopcommerce.com/");
        clickToElement(driver, "//a[@class='ico-register']");
        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='LastName-error']"), "Last name is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Email is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='Password-error']"), "Password is required.");
        Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"), "Password is required.");
    }

    @Test
    public void Register_02_Invalid_Email() {
        openPageUrl(driver, "https://demo.nopcommerce.com/");
        clickToElement(driver, "//a[@class='ico-register']");

        sendkeyToElement(driver, "//input[@id='FirstName']", "John");
        sendkeyToElement(driver, "//input[@id='LastName']", "Kennedy");
        sendkeyToElement(driver, "//input[@id='Email']", "John@kennedy@us");
        sendkeyToElement(driver, "//input[@id='Password']", "123456");
        sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");

        clickToElement(driver, "//button[@id='register-button']");
        Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "Wrong email");


    }

    @Test
    public void Register_03_Invalid_Password() {
        openPageUrl(driver, "https://demo.nopcommerce.com/");
        clickToElement(driver, "//a[@class='ico-register']");

        sendkeyToElement(driver, "//input[@id='FirstName']", "John");
        sendkeyToElement(driver, "//input[@id='LastName']", "Kennedy");
        sendkeyToElement(driver, "//input[@id='Email']", "John@kennedy@us");
        sendkeyToElement(driver, "//input[@id='Password']", "123");
        sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "123");

        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//span[@id='Password-error']"), "Password must meet the following rules:\nmust have at least 6 characters");
    }

    @Test
    public void Register_04_Incorrect_Confirm_Password() {
        openPageUrl(driver, "https://demo.nopcommerce.com/");
        clickToElement(driver, "//a[@class='ico-register']");

        sendkeyToElement(driver, "//input[@id='FirstName']", "John");
        sendkeyToElement(driver, "//input[@id='LastName']", "Kennedy");
        sendkeyToElement(driver, "//input[@id='Email']", "John@kennedy@us");
        sendkeyToElement(driver, "//input[@id='Password']", "123456");
        sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "654321");

        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"), "The password and confirmation password do not match.");

    }

    @Test
    public void Register_05_Success() {
        openPageUrl(driver, "https://demo.nopcommerce.com/");
        clickToElement(driver, "//a[@class='ico-register']");

        sendkeyToElement(driver, "//input[@id='FirstName']", "John");
        sendkeyToElement(driver, "//input[@id='LastName']", "Kennedy");
        sendkeyToElement(driver, "//input[@id='Email']", getEmailAddress());
        sendkeyToElement(driver, "//input[@id='Password']", "123456");
        sendkeyToElement(driver, "//input[@id='ConfirmPassword']", "123456");

        clickToElement(driver, "//button[@id='register-button']");

        Assert.assertEquals(getElementText(driver, "//span[@class='result'"), "Your registration completed");

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public String getEmailAddress() {
        Random rand = new Random();
        return "kevinlap" + rand.nextInt(99999) + "@gmail.net";
    }
}

