import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LokOS_Automation {
    static AndroidDriver<MobileElement> driver;
    static WebDriverWait wait;
    static AppiumServer appiumServer;

    public static MobileElement scrollToText(String text) {
        return driver.findElementByAndroidUIAutomator("new UiScrollable("
                + "new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().text(\"" + text + "\"));");
    }

    public static MobileElement scrollToId(String id) {

        return driver.findElementByAndroidUIAutomator(
                "new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().resourceIdMatches(\"" + id + "\"));");
    }

    public static MobileElement selectSHG(String SHGName) {
        // scroll the container with the shg elements
        driver.findElementByAndroidUIAutomator(
                "new UiScrollable(" + "new UiSelector().resourceIdMatches(\"com.microware.cdfi.training:id/rvShgList\")).scrollIntoView("
                        + "new UiSelector().text(\"" + SHGName + "\"));");

        // once the required element in view select all the linearlayout and check if it contains the SHGName
        // if it does return the tv_count from that element
        for (MobileElement m : driver.findElementsByClassName("android.widget.LinearLayout")) {
            if (m.findElementsByXPath("//android.widget.TextView[contains(@text,'" + SHGName + "')]").size() != 0) {
                return (m.findElementById("com.microware.cdfi.training:id/tv_count"));
            }

        }
        return null;

    }

    public static MobileElement selectDropdownText(String text) {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='" + text + "']")));
        return (MobileElement) dropdown;
    }

    public static MobileElement selectElement(String id) {
        WebElement chosenElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return (MobileElement) chosenElement;
    }

    public static MobileElement selectElementXPath(String myText) {
        WebElement chosenElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'" + myText + "')]")));
        return (MobileElement) chosenElement;
    }

    public static MobileElement selectRadio(String myText) {
        WebElement chosenElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.RadioButton[contains(@text,'" + myText + "')]")));
        return (MobileElement) chosenElement;
    }

    static Scanner scan = new Scanner(System.in);
    static String shg_number = "";
    static String shg_addressNumber = "";
    static String shg_accountNumber = "";
    static String mobileNumber = "";
    static String addressNumber = "";
    static String member_accountNumber = "";

    public static void startAppiumServer(){
        appiumServer = new AppiumServer();
        int port = 4723;
        if(!appiumServer.checkIfServerIsRunnning(port)) {
            appiumServer.startServer();
        } else {
            System.out.println("Appium Server already running on Port - " + port);
        }
    }
    public static void stopAppiumServer(){
        appiumServer.stopServer();
    }
    public static void main(String[] args) throws InterruptedException{
        DataGenerator.main(new String[] {"Tho Chaliye Shuru Karte Hain!"});
        startAppiumServer();
        openLokOS();
        navigateToSHG();




        File file = new File("src/test/resources/SHG-Data/SHG_Data.csv");
        try {
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);

            if (scanner.hasNextLine())scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                createSHG(fields[0], fields[1], fields[2]);
                shg_number = fields[0].substring(4);
                shg_addressNumber = fields[1].substring(11);
                shg_accountNumber = fields[2];
            }
            scanner.close();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in reading the CSV File!");
        }

        System.out.println("SHG Creation result?");
        System.out.println("1. Positive");
        System.out.println("2. Negative");
        System.out.println("Type: 'P/p' --> Positive || 'N/n' --> Negative: ");
        String input = scan.nextLine();
        if(input.equals("P") || input.equals("p")) updateCounter();
        else System.out.println("DataCounter Unchanged!");
        stopAppiumServer();
    }

    public static void updateCounter(){
        String fileName = "src/test/resources/DataCounter.csv";
        try {
            FileWriter writer = new FileWriter(fileName);
            /* Column Headers */
            writer.append("shg_number,");
            writer.append("shg_address,");
            writer.append("shg_accountNumber,");
            writer.append("mobileNumber,");
            writer.append("address,");
            writer.append("accountNumber\n");

            writer.append(shg_number + ",");
            writer.append(shg_addressNumber + ",");
            writer.append(shg_accountNumber + ",");
            writer.append(mobileNumber + ",");
            writer.append(addressNumber + ",");
            writer.append(member_accountNumber + "\n");

            writer.flush();
            writer.close();
        }
        catch (IOException e){
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
        System.out.println("Data Counter Updated!");
    }



    public static void openLokOS() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("deviceName", "Redmi Note 10 Pro");
//        capabilities.setCapability("udid", "189557ef");
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
//        System.out.println("SHG selected");
    }

    public static void createSHG(String name, String address, String accountNumber) throws IOException {
        fillInfo(name, address, accountNumber);
        String member_FileNumber = name.split(" ")[1];
        File file = new File("src/test/resources/SHG-Data/SHG_MemberData-" + member_FileNumber + ".csv");
        MobileElement buttonSelected = selectSHG(name);
        if (buttonSelected == null) {
            System.out.println("SHG Not exist");
            return;
        }
        buttonSelected.click();
        try {
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);

            if (scanner.hasNextLine())scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                createMembers(name, fields[0], fields[1], fields[2], fields[3], fields[4]);
                mobileNumber = fields[1];
                addressNumber = fields[2];
                member_accountNumber = fields[3];
            }
            scanner.close();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in reading the CSV File!");
        }
//        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        selectElement("com.microware.cdfi.training:id/icBack").click();

    }

    public static void clickImage() {
        //Camera Image Capture Working
        // wait for camera to load
        selectElement("com.android.camera:id/top_tip_layout");
//        selectElement("com.sec.android.app.camera:id/camera_preview");
        // press camera button
        driver.pressKey(new KeyEvent().withKey(AndroidKey.CAMERA));
        selectElement("com.android.camera:id/done_button").click();
//        selectElement("com.sec.android.app.camera:id/okay").click();
        selectElement("com.microware.cdfi.training:id/crop_image_menu_crop").click();
    }


    public static void fillInfo(String name, String address, String accountNumber) throws IOException{
        // press the create SHG Button
        WebElement create = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.microware.cdfi.training:id/IvAdd")));
        create.click();

//        System.out.println("Filling Info");
        // SHG Name
        MobileElement SHGName = scrollToId("com.microware.cdfi.training:id/et_groupname");
        SHGName.sendKeys(name);

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
        String filePath = "src/test/resources/SHGCopy.pdf";
        driver.pushFile("/storage/emulated/0/Download/SHGCopy.pdf", new File(filePath));
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        selectElementXPath("SHGCopy").click();

        // click on submit
        selectElement("com.microware.cdfi.training:id/btn_save").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();


//        System.out.println("Switching to Location Page");
        // switch to page 3
        selectElement("com.microware.cdfi.training:id/Ivloc").click();
        selectElement("com.microware.cdfi.training:id/addAddress").click();
        selectElement("com.microware.cdfi.training:id/et_address1").sendKeys(address);
        selectElement("com.microware.cdfi.training:id/et_pincode").sendKeys("222129");
        selectElement("com.microware.cdfi.training:id/btn_add").click();

        // clicking ok after submit
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

//        System.out.println("Switching to Bank Page");
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

        scrollToId("com.microware.cdfi.training:id/et_Accountno").sendKeys(accountNumber);
        scrollToId("com.microware.cdfi.training:id/et_retype_Accountno").sendKeys(accountNumber);

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
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        selectElement("com.microware.cdfi.training:id/ivBack").click();

    }


    public static void createMembers(String shg_name, String name, String mobileNumber, String address, String accountNumber, String aadharNumber) {
//        MobileElement buttonSelected = selectSHG(shg_name);
//        if (buttonSelected == null) {
//            System.out.println("SHG Not exist");
//            return;
//        }
//        buttonSelected.click();

        selectElement("com.microware.cdfi.training:id/tbl_add").click();

        // fill member details
        selectElement("com.microware.cdfi.training:id/et_name").sendKeys(name);

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
        selectElement("com.microware.cdfi.training:id/et_phoneno").sendKeys(mobileNumber);
        selectElement("com.microware.cdfi.training:id/spin_ownership").click();
        selectDropdownText("Self").click();
        selectElement("com.microware.cdfi.training:id/btn_save").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        // Add Address Page
        selectElement("com.microware.cdfi.training:id/Ivloc").click();
        selectElement("com.microware.cdfi.training:id/addAddress").click();

        selectElement("com.microware.cdfi.training:id/spin_addresstype").click();
        selectDropdownText("Primary").click();

        scrollToId("com.microware.cdfi.training:id/et_address1").sendKeys(address);
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

        scrollToId("com.microware.cdfi.training:id/et_Accountno").sendKeys(accountNumber);
        scrollToId("com.microware.cdfi.training:id/et_retype_Accountno").sendKeys(accountNumber);

        // account opening date

        scrollToId("com.microware.cdfi.training:id/et_opdate").click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        selectElement("android:id/button1").click();

        //Camera Image Capture Working
        scrollToId("com.microware.cdfi.training:id/ImgFrntpage");
        selectElement("com.microware.cdfi.training:id/ImgFrntpage").click();

        // wait for camera to load
        clickImage();

        scrollToId("com.microware.cdfi.training:id/btn_add").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        // Do KYC Page
        selectElement("com.microware.cdfi.training:id/IvKyc").click();
        selectElement("com.microware.cdfi.training:id/addKyc").click();

        selectElement("com.microware.cdfi.training:id/spin_kyctype").click();
        selectDropdownText("Aadhaar").click();

        selectElement("com.microware.cdfi.training:id/et_kycno").sendKeys(aadharNumber);

        scrollToId("com.microware.cdfi.training:id/IvFrntUpload").click();
        clickImage();

        selectElement("com.microware.cdfi.training:id/IvrearUpload").click();
        clickImage();

        selectElement("com.microware.cdfi.training:id/btn_kyc").click();
        selectElement("com.microware.cdfi.training:id/btn_ok").click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        selectElement("com.microware.cdfi.training:id/ivBack").click();
    }
}
