package com.helloB.inter;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
public class LoginTest {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("browser") String browser) {

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/firefox");
                driver = new ChromeDriver();
                break;

            default:
                System.out.println("Do not hoe to start " + browser + ", starting chrome instead");
                driver = new ChromeDriver();
                break;
        }
        //      Create friver


        // maximize browser
        driver.manage().window().maximize();
        sleep(3000);

        //setup implicit wait
        //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Parameters({"url", "username", "password", "expectedMessage"})
    @Test(priority = 2, groups = {"negativeLoginTest"})

    public void negativeLoginTest(String url, String username, String pwd, String excpectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(url);

        //enter usr Name
        WebElement usernameRlment = driver.findElement(By.id("userName"));
        usernameRlment.sendKeys(username);

        //enter password
        WebElement passwordElement = driver.findElement(By.id("password"));
        passwordElement.sendKeys(pwd);

        // enter Login button
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();


        //    verifications

        //    new url is matched
        String exceptedUrl = "https://demoqa.com/login";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, exceptedUrl, "Actual page url is not the same excepted");


        //   login button is visable
        WebElement loginButton2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
        Assert.assertTrue(loginButton2.isDisplayed(), "Login button is still visible");


        //    invalid login message
        WebElement successMessage = driver.findElement(By.id("name"));
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(excpectedMessage), "The banner message is different, actual vs excepected ");

    }


    @Parameters({"url", "username", "password", "expectedMessage"})
    @Test(priority = 1, groups = {"positiveTest", "smokeTest"})
    public void positiveLoginTest(String url, String username, String pwd, String excpectedMessage) {

        //    open page and
        driver.get(url);

        //enter usr Name
        WebElement usernameRlment = driver.findElement(By.id("userName"));
        usernameRlment.sendKeys(username);

        //enter password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(pwd);

        //wait variable.
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // enter Login button
        WebElement loginButton = driver.findElement(By.id("login"));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();

        //    verifications
        //    new url is matched
        String exceptedUrl = "https://exceptedURL";
        String actualUrl = driver.getCurrentUrl();

        //Here we need to know what is the expected url page once we log successfully
        Assert.assertEquals(actualUrl, exceptedUrl, "Actual page url is not the same excepted");

        //  I'd say there should be a logout button when we enter. That's why I put it
        WebElement logoutButton = driver.findElement(By.id("logoutButton"));
        Assert.assertTrue(logoutButton.isDisplayed(), "LogOut button is not visible");

        // Also maybe we have a sucessfull message when We enter
        WebElement successMessage = driver.findElement(By.xpath("//path_to_the_successfully_message"));
        String actualMessage = successMessage.getText();
        Assert.assertTrue(actualMessage.contains(excpectedMessage), "Actual meesage does not contains Excepted message ");
    }



    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        //Close Broser
        driver.quit();
    }

}
