package com.nopcommerce.account;

import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.user.CustomerPageObject;
import pageObjects.user.HomePageObject;
import pageObjects.user.UserLoginPageObject;
import pageObjects.user.RegisterPageObject;

public class Level_07_Page_Manager_2 extends BaseTest {
    private WebDriver driver;
    //Khoonng nên khơi tạo đối tượng ngoài phạm vi của class. vi pham nguyên tác khởi tạo
    //nên che giấu
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private UserLoginPageObject loginPage;
    private CustomerPageObject customerPage;
    private String emailAddress = getEmailAddress();

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = getBrowserDriver(browserName);
        homePage = new HomePageObject(driver);
    }

    @Test
    public void Register_01_Empty_Data() {
        homePage.clickToRegisterLink();
        // Mở page mới phải kởi tạo page đó lên
        registerPage = new RegisterPageObject(driver);
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getFirstNameErrorMsgText(), "First name is required.");
        Assert.assertEquals(registerPage.getLastNameErrorMsgText(), "Last name is required.");
        Assert.assertEquals(registerPage.getEmailErrorMsgText(), "Email is required.");
        Assert.assertEquals(registerPage.getPasswordErrorMsgText(), "Password is required.");
        Assert.assertEquals(registerPage.getConfirmPasswordErrorMsgText(), "Password is required.");
    }


    @Test
    public void Register_02_Invalid_Email() {
        registerPage.clickToNopCommerceLogo();
        homePage = new HomePageObject(driver);
        homePage.clickToRegisterLink();
        registerPage = new RegisterPageObject(driver);
        registerPage.enterToFirstNameTextbox("John");
        registerPage.enterToLastNameTextbox("Kennedy");
        registerPage.enterToEmailTextbox("John@kennedy@us");
        registerPage.enterToPasswordTextbox("123456");
        registerPage.enterToConfirmPasswordTextbox("123456");
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getEmailErrorMsgText(), "Wrong email");
    }

    @Test
    public void Register_03_Invalid_Password() {
        registerPage.clickToNopCommerceLogo();
        homePage = new HomePageObject(driver);

        homePage.clickToRegisterLink();
        registerPage = new RegisterPageObject(driver);
        registerPage.enterToFirstNameTextbox("John");
        registerPage.enterToLastNameTextbox("Kennedy");
        registerPage.enterToEmailTextbox("John@kennedy@us");
        registerPage.enterToPasswordTextbox("123");
        registerPage.enterToConfirmPasswordTextbox("123");
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getPasswordErrorMsgText(), "Password must meet the following rules:\nmust have at least 6 characters");
    }

    @Test
    public void Register_04_Incorrect_Confirm_Password() {
        registerPage.clickToNopCommerceLogo();
        homePage = new HomePageObject(driver);
        homePage.clickToRegisterLink();
        registerPage = new RegisterPageObject(driver);
        registerPage.enterToFirstNameTextbox("John");
        registerPage.enterToLastNameTextbox("Kennedy");
        registerPage.enterToEmailTextbox("John@kennedy@us");
        registerPage.enterToPasswordTextbox("123456");
        registerPage.enterToConfirmPasswordTextbox("123");
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getConfirmPasswordErrorMsgText(), "The password and confirmation password do not match.");
    }

    @Test
    public void Register_05_Success() {
        registerPage.clickToNopCommerceLogo();
        homePage = new HomePageObject(driver);
        homePage.clickToRegisterLink();
        registerPage = new RegisterPageObject(driver);
        registerPage.enterToFirstNameTextbox("John");
        registerPage.enterToLastNameTextbox("Kennedy");
        registerPage.enterToEmailTextbox(emailAddress);
        registerPage.enterToPasswordTextbox("123456");
        registerPage.enterToConfirmPasswordTextbox("123456");
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getRegisterSuccessMessageText(), "Your registration completed");

    }

    @Test
    public void User_06_Login_Success() {
        registerPage.clickToNopCommerceLogo();
        homePage = new HomePageObject(driver);
        homePage.clickToLoginLink();
        loginPage = new UserLoginPageObject(driver);
        loginPage.enterToEmailTextbox(emailAddress);
        loginPage.enterToPasswordTextbox("123456");
        loginPage.clickLoginButton();
        homePage = new HomePageObject(driver);
        homePage.clickToMyAccountLink();
        customerPage = new CustomerPageObject(driver);
        Assert.assertEquals(customerPage.getFirstNameAttributeValue(), "John");
        Assert.assertEquals(customerPage.getLastNameAttributeValue(), "Kennedy");
        Assert.assertEquals(customerPage.getEmailAddressTextboxAttributeValue(), emailAddress);

    }

    @AfterClass
    public void afterClass() {
        closeBrowser();
    }


}

