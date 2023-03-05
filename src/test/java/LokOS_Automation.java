import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LokOS_Automation {
    static AndroidDriver<MobileElement> driver;
    static WebDriverWait wait;
    public static void main(String[] args) throws InterruptedException {
        openLokOS();
        navigateSHG();
        fillInfo();
    }
    public static MobileElement scrollToText(AndroidDriver<MobileElement> driver, String text) {
        return (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable("
                + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().text(\"" + text + "\"));");
    }

    public static MobileElement scrollToId(AndroidDriver<MobileElement> driver, String id) {

        return (MobileElement) driver.findElementByAndroidUIAutomator(
                "new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().resourceIdMatches(\"" + id + "\"));");
    }

    public static void fillInfo(){
        WebElement SHGName = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/et_groupname")));
        SHGName.sendKeys("Test SHG");
        WebElement date = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/et_formationDate")));
        date.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/button1"))).click();
        System.out.println("Date Selected");

        WebElement promotedBy = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/spin_promotedby")));
        promotedBy.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='NRLM']"))).click();
        // Meeting Frequency
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/spin_frequency"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Weekly']"))).click();

        // saving frequency
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/spin_savingfrequency"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Weekly']"))).click();

        // saving value
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/et_saving"))).sendKeys("10");

        // primary activity
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/spin_primaryActivity"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Agriculture Activities']"))).click();

        // primary activity
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/spin_secondaryActivity"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Fishery Activities']"))).click();

        // tertiary activity
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/spin_tertiaryActivity"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Horticulture Activities']"))).click();

        // tenure of office bearers
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.microware.cdfi.training:id/et_electiontenure"))).sendKeys("12");



    }

    public static void navigateSHG(){
        WebElement choose = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.microware.cdfi.training:id/tbl_shg")));
        choose.click();
        System.out.println("SHG selected");

        WebElement create = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.microware.cdfi.training:id/IvAdd")));
        create.click();
    }
    public static void openLokOS(){

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Note 10 Pro");
        capabilities.setCapability("189557ef", "RZ8M31X1Z9C");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "12");
        capabilities.setCapability("appPackage", "com.microware.cdfi.training");
        capabilities.setCapability("appActivity", "com.microware.cdfi.activity.SplashScreenActivity");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", 20);


        URL url;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
            driver=new AndroidDriver<MobileElement>(url,capabilities);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error in URL");
        }



        System.out.println("App Opened");
        wait = new WebDriverWait(driver, 300);
        // enter password on login screen
        WebElement enterPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.microware.cdfi.training:id/otp_view")));
        enterPassword.sendKeys("1234");


        // wait for some time

        System.out.println("Password entered");


    }

}
