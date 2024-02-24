package com.nopcommerce.account;

import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.admin.AdminDashboardPageObject;
import pageObjects.admin.AdminLoginPageObject;
import pageObjects.user.*;

public class Level_11_Global_Constants extends BaseTest {
    private WebDriver driver;
    //Khoonng nên khơi tạo đối tượng ngoài phạm vi của class. vi pham nguyên tác khởi tạo
    //nên che giấu
    private HomePageObject homePage;
    private RegisterPageObject registerPage;
    private UserLoginPageObject userLoginPage;
    private AdminLoginPageObject adminLoginPage;
    private AdminDashboardPageObject adminDashboardPage;
    private CustomerPageObject customerPage;
    private AddressPageObject addressPage;
    private OrderPageObject orderPage;
    private RewardPointPageObject rewardPointPage;
    private String emailAddress = getEmailAddress();

    private String adminUrl = GlobalConstants.DEV_ADMIN_URL;
    private String userUrl = GlobalConstants.DEV_USER_URL;


    @Parameters({"browser"})
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = getBrowserDriver(browserName, userUrl);
        homePage = PageGeneratorManager.getHomePage(driver);
    }

    @Test
    public void User_01_User_To_Admin() {
        // registerPage.clickToNopCommerceLogo();

        registerPage = homePage.clickToRegisterLink();

        registerPage.enterToFirstNameTextbox("John");
        registerPage.enterToLastNameTextbox("Kennedy");
        registerPage.enterToEmailTextbox(emailAddress);
        registerPage.enterToPasswordTextbox("123456");
        registerPage.enterToConfirmPasswordTextbox("123456");
        registerPage.clickToRegisterButton();
        Assert.assertEquals(registerPage.getRegisterSuccessMessageText(), "Your registration completed");
        homePage = registerPage.clickToNopCommerceLogo();
        userLoginPage = homePage.clickToLoginLink();

        userLoginPage.loginToUser(emailAddress, "123456");
        //log out
        homePage.clickToLogoutLink();

        //Home page(user) -> open Login page(admin)
        homePage.openPageUrl(driver, adminUrl);
        adminLoginPage = PageGeneratorManager.getAdminLoginPage(driver);
        adminDashboardPage = adminLoginPage.loginToAdmin(GlobalConstants.DEV_ADMIN_USERNAME, GlobalConstants.DEV_ADMIN_PASSWORD);
        Assert.assertTrue(adminDashboardPage.isPageLoadedSuccess(driver));

    }

    @Test
    public void User_02_Admin_To_User() {
        //registerPage.clickToNopCommerceLogo();

       adminLoginPage=adminDashboardPage.clickToLogoutLink();
       adminLoginPage.openPageUrl(driver, userUrl);
       homePage = PageGeneratorManager.getHomePage(driver);

       userLoginPage = homePage.clickToLoginLink();
       homePage=userLoginPage.loginToUser(emailAddress,"123456");

    }



    @AfterClass
    public void afterClass() {
        closeBrowser();
    }


}

