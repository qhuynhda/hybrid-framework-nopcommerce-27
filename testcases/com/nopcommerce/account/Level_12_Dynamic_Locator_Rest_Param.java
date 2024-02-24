package com.nopcommerce.account;

import commons.BaseTest;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.user.*;

public class Level_12_Dynamic_Locator_Rest_Param extends BaseTest {
    private WebDriver driver;
    //Khoonng nên khơi tạo đối tượng ngoài phạm vi của class. vi pham nguyên tác khởi tạo
    //nên che giấu
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private UserLoginPageObject loginPage;
    private CustomerPageObject customerPage;
    private AddressPageObject addressPage;
    private OrderPageObject orderPage;
    private RewardPointPageObject rewardPointPage;

    private String emailAddress = getEmailAddress();

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = getBrowserDriver(browserName);
        homePage = PageGeneratorManager.getHomePage(driver);
    }


    @Test
    public void User_01_Register_Success() {
       // registerPage.clickToNopCommerceLogo();

        registerPage = homePage.clickToRegisterLink();

        registerPage.enterToFirstNameTextbox("John");
        registerPage.enterToLastNameTextbox("Kennedy");
        registerPage.enterToEmailTextbox(emailAddress);
        registerPage.enterToPasswordTextbox("123456");
        registerPage.enterToConfirmPasswordTextbox("123456");
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getRegisterSuccessMessageText(), "Your registration completed");

    }

    @Test
    public void User_02_Login_Success() {
        //registerPage.clickToNopCommerceLogo();

        loginPage = homePage.clickToLoginLink();

        loginPage.enterToEmailTextbox(emailAddress);
        loginPage.enterToPasswordTextbox("123456");

        homePage = loginPage.clickLoginButton();

        customerPage = homePage.clickToMyAccountLink();


        Assert.assertEquals(customerPage.getFirstNameAttributeValue(), "John");
        Assert.assertEquals(customerPage.getLastNameAttributeValue(), "Kennedy");
        Assert.assertEquals(customerPage.getEmailAddressTextboxAttributeValue(), emailAddress);

    }
    @Test
    public void User_03_Page_Navigate()
    {
        addressPage = (AddressPageObject) customerPage.openDynamicSideBarPage("Addresses");
        orderPage = (OrderPageObject) addressPage.openDynamicSideBarPage("Orders");
        customerPage = (CustomerPageObject) orderPage.openDynamicSideBarPage("Customer info");
        orderPage= (OrderPageObject) customerPage.openDynamicSideBarPage("Orders");
        addressPage= (AddressPageObject) orderPage.openDynamicSideBarPage("Addresses");
        rewardPointPage= (RewardPointPageObject) addressPage.openDynamicSideBarPage("Reward points");
        customerPage = (CustomerPageObject) rewardPointPage.openDynamicSideBarPage("Customer info");
        rewardPointPage= (RewardPointPageObject) customerPage.openDynamicSideBarPage("Reward points");
    }
    @Test
    public void User_04_Page_Navigate()
    {
        rewardPointPage.openDynamicSideBarPageByName("Reward points");
        customerPage=PageGeneratorManager.getCustomerPage(driver);

        customerPage.openDynamicSideBarPageByName("Addresses");
        addressPage=PageGeneratorManager.getAddressPage(driver);
    }
    @AfterClass
    public void afterClass() {
        closeBrowser();
    }


}

