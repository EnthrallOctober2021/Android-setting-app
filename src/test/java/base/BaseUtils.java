package base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BaseUtils {

    protected static AndroidDriver<MobileElement> androidDriver;

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
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        // driver closed
        androidDriver.closeApp();
        androidDriver.quit();
    }

    //************************** Base functions *****************************************//

    // Android only, will not work for ios
    public void scrollAndClick(String visibleText) {
        androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))").click();
    }

    public void scrollDown() {

        TouchAction action = new TouchAction(androidDriver);
        Dimension size = androidDriver.manage().window().getSize();
        int width = size.width;
        int height = size.height;
        int middleOfX = width / 2;
        int startYCoordinate = (int) (height * .7);
        int endYCoordinate = (int) (height * .2);

        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
    }

    public void scrollUp() {

        TouchAction action = new TouchAction(androidDriver);
        Dimension size = androidDriver.manage().window().getSize();
        int width = size.width;
        int height = size.height;
        int middleOfX = width / 2;
        int startYCoordinate = (int) (height * .2);
        int endYCoordinate = (int) (height * .7);

        action.press(PointOption.point(middleOfX, startYCoordinate))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(middleOfX, endYCoordinate)).release().perform();
    }

    public void tapWithText(String text) {
        List<MobileElement> elements = androidDriver.findElementsById("android:id/title");
        for (MobileElement s : elements) {
            if (s.getText().equals(text)) {
                s.click();
            }
        }
    }

    public String getAllElementText() {
        String s = "";
        List<MobileElement> elements = androidDriver.findElementsById("android:id/text1");
        for (MobileElement el : elements) {
            s = el.getText();
            System.out.println(s);
        }
        return s;
    }

    public List<String> getAllElementTextInList() {
        List<String> list = new ArrayList<>();
        List<MobileElement> elements = androidDriver.findElementsById("android:id/text1");
        for (MobileElement el : elements) {
            list.add(el.getText());
        }
        System.out.println(list);
        return list;
    }

    public boolean retryingFindClick(String text) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                List<MobileElement> elements = androidDriver.findElementsById("android:id/title");
                for (MobileElement s : elements) {
                    if (s.getText().equals(text)) {
                        s.click();
                    }
                }
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public void setWifiOff() {
        if (androidDriver.getConnection().isWiFiEnabled()) {
            androidDriver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());
        }
    }

    public void setWifiOn() {
        if (!androidDriver.getConnection().isWiFiEnabled()) {
            androidDriver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
        }

    }

    public void openNotification(){
        androidDriver.openNotifications();
    }

    public void waitForElement(By by){
        WebDriverWait wait = new WebDriverWait(androidDriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }


}
