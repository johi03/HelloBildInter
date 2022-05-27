package com.helloB.inter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class TextBox {

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
    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Parameters({"url","fullName", "email", "currentAddress", "permanentAddress"})
    @Test(priority = 1, groups = {"smokeTest"})

    public void textBoxPositive(String url, String fullName, String email, String currentAddress, String permanentAddress) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(url);

        //enter usr Full Name
        WebElement fullNameElment = driver.findElement(By.id("userName"));
        fullNameElment.sendKeys(fullName);
        WebElement fullNameElmentLab = driver.findElement(By.id("userName-label"));

        //enter email
        WebElement emailElement = driver.findElement(By.id("userEmail"));
        emailElement.sendKeys(email);
        WebElement emailElementLab = driver.findElement(By.id("userEmail-label"));

        //enter currentAddress
        WebElement cAddressElement = driver.findElement(By.xpath("//textarea[@id=\"currentAddress\"]"));
        cAddressElement.sendKeys(currentAddress);
        WebElement currentAddresstLab = driver.findElement(By.id("currentAddress-label"));

        //enter permanentAddress
        WebElement pAddressElement = driver.findElement(By.xpath("//textarea[@id=\"permanentAddress\"]"));
        pAddressElement.sendKeys(permanentAddress);
        WebElement pAddressElementLab = driver.findElement(By.id("permanentAddress-label"));

        // submit
        WebElement loginButton = driver.findElement(By.id("submit"));
        loginButton.click();


        // Here we have all the information processed
        WebElement currentNameD = driver.findElement(By.id("name"));
        WebElement currentEmailD = driver.findElement(By.id("email"));
        WebElement currentAddressD = driver.findElement(By.xpath("//p[@id=\"currentAddress\"]"));
        WebElement permanentAddressD = driver.findElement(By.xpath("//p[@id=\"permanentAddress\"]"));

        System.out.println(currentNameD.getText());

        //asserts

        Assert.assertEquals(emailElementLab.getText() + ":"+ email,currentEmailD.getText() );
        Assert.assertEquals(currentAddresstLab.getText() +" :"+ currentAddress,currentAddressD.getText() );
        Assert.assertEquals(pAddressElementLab.getText() + " :"+permanentAddress, permanentAddressD.getText() );
        Assert.assertEquals(fullNameElmentLab.getText() + ":"+fullName,  currentNameD.getText() );

                /*
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
*/
    }



    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        //Close Broser
        driver.quit();
    }



}
