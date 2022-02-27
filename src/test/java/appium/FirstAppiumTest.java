package appium;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


/*
 * >>>>>>>>>>>>PRE REQUISITE<<<<<<<<<<<<<<
 * APPIUM SERVER SHOULD BE UP AND RUNNING ON - http://127.0.0.1:4723
 * ANDROID EMULATOR SHOULD BE UP AND RUNNING
 * */
public class FirstAppiumTest {
    private AndroidDriver<MobileElement> androidDriver;

    @BeforeMethod
    public void appSetup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "30");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554"); // your device id
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", "com.android.settings.Settings");

        androidDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        androidDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }


    @Test
    public void testSearch(){
         String actualText = androidDriver.findElement(By.id("com.android.settings:id/search_action_bar_title")).getText();
         Assert.assertEquals(actualText, "Search settings", "Wrong text");
    }

    // Homework: Please add at least 5 or more tests with multiple assertions on android setting app


    @AfterMethod
    public void tearDown(){
           // driver closed
            androidDriver.closeApp();
            androidDriver.quit();
    }

}
