package appium;

import base.BaseUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;




/*
 * >>>>>>>>>>>>PRE REQUISITE<<<<<<<<<<<<<<
 * APPIUM SERVER SHOULD BE UP AND RUNNING ON - http://127.0.0.1:4723
 * ANDROID EMULATOR SHOULD BE UP AND RUNNING
 * */
public class FirstAppiumTest extends BaseUtils {


    @Test
    public void testSearch(){
         String actualText = androidDriver.findElement(By.id("com.android.settings:id/search_action_bar_title")).getText();
         Assert.assertEquals(actualText, "Search settings", "Wrong text");
         this.scrollDown();
         this.scrollUp();
    }

    @Test
    public void testUiScroll() throws InterruptedException {
        String actualText = androidDriver.findElement(By.id("com.android.settings:id/search_action_bar_title")).getText();
        Assert.assertEquals(actualText, "Search settings", "Wrong text");
        scrollAndClick("System");
        Thread.sleep(3000);

        retryingFindClick("Date & time");
        Thread.sleep(3000);
    }

    @Test
    public void testWifiOff() throws InterruptedException {
        setWifiOff();
        Thread.sleep(3000);
    }

    @Test
    public void testWifiOn() throws InterruptedException {
        setWifiOn();
        Thread.sleep(3000);
    }

    @Test
        public void testOpenNotification() throws InterruptedException {
        openNotification();
        Thread.sleep(3000);
    }



// scroll down and click on system > advance option > developer option > Icon shape > then verify 5 radio button options

}
