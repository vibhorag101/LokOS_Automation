import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
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

    public static MobileElement scrollToText(AndroidDriver<MobileElement> driver, String text) {
        return (MobileElement) driver.findElementByAndroidUIAutomator("new UiScrollable("
                + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().text(\"" + text + "\"));");
    }

    public static MobileElement scrollToId(String id) {

        return (MobileElement) driver.findElementByAndroidUIAutomator(
                "new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().resourceIdMatches(\"" + id + "\"));");
    }

    public static MobileElement selectDropdownText(String text) {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='" + text + "']")));
        return (MobileElement) dropdown;
    }
//    public static MobileElement selectDropdownTextAbsolute(String text){
//        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(text)));
//        return (MobileElement) dropdown;
//    }

    public static MobileElement selectElement(String id) {
        WebElement chosenElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return (MobileElement) chosenElement;
    }

    public static MobileElement selectElementXPath(String myText) {
        WebElement chosenElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'" + myText + "')]")));
        return (MobileElement) chosenElement;
    }
    public static  MobileElement selectRadio(String myText){
        WebElement chosenElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.RadioButton[contains(@text,'" + myText + "')]")));
        return (MobileElement) chosenElement;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        openLokOS();
        navigateToSHG();
        createSHG();
    }

    public static void openLokOS() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Note 10 Pro");
        capabilities.setCapability("udid", "189557ef");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "12");
        capabilities.setCapability("appPackage", "com.microware.cdfi.training");
        capabilities.setCapability("appActivity", "com.microware.cdfi.activity.SplashScreenActivity");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", 10000);


        URL url;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AndroidDriver<MobileElement>(url, capabilities);
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

    public static void navigateToSHG() {
        WebElement choose = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.microware.cdfi.training:id/tbl_shg")));
        choose.click();
        System.out.println("SHG selected");
    }
    public static void createSHG() throws IOException, InterruptedException{
//        fillInfo();
        createMembers();
    }
    public static void clickImage(){
        //Camera Image Capture Working
        // wait for camera to load
        selectElement("com.android.camera:id/top_tip_layout");

        // press camera button
        driver.pressKey(new KeyEvent().withKey(AndroidKey.CAMERA));
        selectElement("com.android.camera:id/done_button").click();
        selectElement("com.microware.cdfi.training:id/crop_image_menu_crop").click();
    }


    public static void fillInfo() throws IOException, InterruptedException {
        // press the create SHG Button
        WebElement create = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.microware.cdfi.training:id/IvAdd")));
        create.click();

        System.out.println("Filling Info");
        // SHG Name
        MobileElement SHGName = scrollToId("com.microware.cdfi.training:id/et_groupname");
        SHGName.sendKeys("Test SHG");

        // SHG Date
        scrollToId("com.microware.cdfi.training:id/et_formationDate").click();
        selectElement("android:id/button1").click();

        // Promoted By
        scrollToId("com.microware.cdfi.training:id/spin_promotedby").click();
        selectDropdownText("NRLM").click();

        // Meeting Frequency
        scrollToId("com.microware.cdfi.training:id/spin_frequency").click();
        selectDropdownText("Weekly").click();

        // Meeting Day
        scrollToId("com.microware.cdfi.training:id/spin_weekday").click();
        selectDropdownText("Monday").click();

        // saving frequency
        scrollToId("com.microware.cdfi.training:id/spin_savingfrequency").click();
        selectDropdownText("Weekly").click();

        // saving value
        scrollToId("com.microware.cdfi.training:id/et_saving").sendKeys("10");

        // primary activity
        scrollToId("com.microware.cdfi.training:id/spin_primaryActivity").click();
        selectDropdownText("Agriculture Activities").click();

        // secondary activity
        scrollToId("com.microware.cdfi.training:id/spin_secondaryActivity").click();
        selectDropdownText("Fishery Activities").click();

        // tertiary activity
        scrollToId("com.microware.cdfi.training:id/spin_tertiaryActivity").click();
        selectDropdownText("Horticulture Activities").click();

        // tenure of office bearers
        scrollToId("com.microware.cdfi.training:id/et_electiontenure").sendKeys("12");

        // Resolution File
        scrollToId("com.microware.cdfi.training:id/Imgmember").click();
        selectElement("com.microware.cdfi.training:id/tvUploadData").click();

        // push a file to the device using appium
        String filePath = "src/test/java/SHGCopy.pdf";
        driver.pushFile("/storage/emulated/0/Download/SHGCopy.pdf", new File(filePath));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        selectElementXPath("SHGCopy").click();

        // click on submit
        selectElement("com.microware.cdfi.training:id/btn_save").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();


        System.out.println("Switching to Location Page");
        // switch to page 3
        selectElement("com.microware.cdfi.training:id/Ivloc").click();
        selectElement("com.microware.cdfi.training:id/addAddress").click();
        selectElement("com.microware.cdfi.training:id/et_address1").sendKeys("Test Address");
        selectElement("com.microware.cdfi.training:id/et_pincode").sendKeys("222129");
        selectElement("com.microware.cdfi.training:id/btn_add").click();

        // clicking ok after submit
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        System.out.println("Switching to Bank Page");
        // switch to page 4
        selectElement("com.microware.cdfi.training:id/IvBank").click();
        selectElement("com.microware.cdfi.training:id/addBank").click();

        // bank name
        selectElementXPath("Please Select Bank").click();

        //selecting the first bank
        selectDropdownText("AXIS BANK").click();

        // there is a branch BIJNOR in the list selected from above
        selectElementXPath("Select Branch Name").click();
        selectElementXPath("BIJNOR").click();

        scrollToId("com.microware.cdfi.training:id/et_Accountno").sendKeys("22345678901");
        scrollToId("com.microware.cdfi.training:id/et_retype_Accountno").sendKeys("22345678901");

        // account opening date
        scrollToId("com.microware.cdfi.training:id/et_opdate").click();
        selectElement("android:id/button1").click();

        //Camera Image Capture Working
        scrollToId("com.microware.cdfi.training:id/ImgFrntpage");
        selectElement("com.microware.cdfi.training:id/ImgFrntpage").click();
        clickImage();

        // submit bank details
        selectElement("com.microware.cdfi.training:id/btn_add").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

//        // switch to page 5
//        selectElement("com.microware.cdfi.training:id/IvKyc").click();

        // go to main page
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        selectElement("com.microware.cdfi.training:id/ivBack").click();

    }

    public static void createMembers(){
        selectElement("com.microware.cdfi.training:id/tv_count").click();
        selectElement("com.microware.cdfi.training:id/tbl_add").click();

        // fill member details
        selectElement("com.microware.cdfi.training:id/et_name").sendKeys("Test Member");

        // selection the layout group and the get the radio child and select the No one
        MobileElement parentRadio = scrollToId("com.microware.cdfi.training:id/rgisDobavailable");
        parentRadio.findElements(By.className("android.widget.RadioButton")).get(1).click();

        scrollToId("com.microware.cdfi.training:id/et_age").sendKeys("40");

        // change the marital status
        scrollToId("com.microware.cdfi.training:id/spin_martial").click();
        selectDropdownText("Married").click();

        scrollToId("com.microware.cdfi.training:id/et_mother_father").sendKeys("Test Spouse");

        // social category
        scrollToId("com.microware.cdfi.training:id/spin_socialCategory").click();
        selectDropdownText("General").click();

        // Religion
        scrollToId("com.microware.cdfi.training:id/spin_religion").click();
        selectDropdownText("Hindu").click();

        // Highest Education
        scrollToId("com.microware.cdfi.training:id/spin_education").click();
        selectDropdownText("Diploma").click();

        // No to insurance
        scrollToId("com.microware.cdfi.training:id/rgInsurance");
        MobileElement insuranceRadio = scrollToId("com.microware.cdfi.training:id/rgInsurance");
        insuranceRadio.findElements(By.className("android.widget.RadioButton")).get(1).click();

        // Primary Livelihood
        scrollToId("com.microware.cdfi.training:id/spin_occupation").click();
        selectDropdownText("Agriculture Activities").click();

        // Secondary Livelihood
        scrollToId("com.microware.cdfi.training:id/spin_secondaryoccu").click();
        selectDropdownText("Horticulture Activities").click();

        // Tertiary Livelihood
        scrollToId("com.microware.cdfi.training:id/spin_tertiaryoccu").click();
        selectDropdownText("Fishery Activities").click();

        // Member Image
        scrollToId("com.microware.cdfi.training:id/Imgmember").click();
        clickImage();

        // Aadhar Form
        scrollToId("com.microware.cdfi.training:id/ImgmemberConsent").click();
        clickImage();

        scrollToId("com.microware.cdfi.training:id/btn_save").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();


        // Add Phone Number Page
        selectElement("com.microware.cdfi.training:id/addphone").click();
        selectElement("com.microware.cdfi.training:id/et_phoneno").sendKeys("9999999999");
        selectElement("com.microware.cdfi.training:id/spin_ownership").click();
        selectDropdownText("Self").click();
        selectElement("com.microware.cdfi.training:id/btn_save").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        // Add Address Page
        selectElement("com.microware.cdfi.training:id/Ivloc").click();
        selectElement("com.microware.cdfi.training:id/addAddress").click();

        selectElement("com.microware.cdfi.training:id/spin_addresstype").click();
        selectDropdownText("Primary").click();

        scrollToId("com.microware.cdfi.training:id/et_address1").sendKeys("Test Address 1");
        scrollToId("com.microware.cdfi.training:id/et_pincode").sendKeys("222129");

        scrollToId("com.microware.cdfi.training:id/btn_add").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        // Add Bank Details
        scrollToId("com.microware.cdfi.training:id/IvBank").click();
        selectElement("com.microware.cdfi.training:id/addBank").click();

        // Bank Name
        selectElementXPath("Please Select Bank").click();

        //selecting the first bank
        selectDropdownText("AXIS BANK").click();

        // there is a branch BIJNOR in the list selected from above
        selectElementXPath("Select Branch Name").click();
        selectElementXPath("BIJNOR").click();

        scrollToId("com.microware.cdfi.training:id/et_Accountno").sendKeys("22345604901");
        scrollToId("com.microware.cdfi.training:id/et_retype_Accountno").sendKeys("22345604901");

        // account opening date
        scrollToId("com.microware.cdfi.training:id/et_opdate").click();
        selectElement("android:id/button1").click();

        //Camera Image Capture Working
        scrollToId("com.microware.cdfi.training:id/ImgFrntpage");
        selectElement("com.microware.cdfi.training:id/ImgFrntpage").click();

        // wait for camera to load
        selectElement("com.android.camera:id/top_tip_layout");
        clickImage();

        scrollToId("com.microware.cdfi.training:id/btn_add").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        // Do KYC Page
        selectElement("com.microware.cdfi.training:id/IvKyc").click();
        selectElement("com.microware.cdfi.training:id/addKyc").click();

        selectElement("com.microware.cdfi.training:id/spin_kyctype").click();
        selectDropdownText("Voter ID").click();

        selectElement("com.microware.cdfi.training:id/et_kycno").sendKeys("WHY1234567");

        scrollToId("com.microware.cdfi.training:id/IvFrntUpload").click();
        clickImage();

        selectElement("com.microware.cdfi.training:id/IvrearUpload").click();
        clickImage();

        selectElement("com.microware.cdfi.training:id/btn_kyc").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        selectElement("com.microware.cdfi.training:id/ivBack").click();














    }






}
