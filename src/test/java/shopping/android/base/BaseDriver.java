package shopping.android.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import java.io.FileReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @FunctionDesc
 * @Author bjyfxuxiaojun
 * @CreateDate 2017/8/10
 * @Reviewer kongxiangyun
 * @ReviewDate 2017/8/10
 */
public class BaseDriver {
    protected AndroidDriver<AndroidElement> driver;

    private static final String configFilePath = "src/test/resources/config.ini";
    private static final String COOLPAD = "Coolpad";
    private static final String MATE8 = "Mate8";
    private static Properties sConfig;
    private static Map<String, String[]> deviceDetail;

    static {
        sConfig = new Properties();
        deviceDetail = new HashMap<>();
        deviceDetail.put(COOLPAD, new String[]{"9323779f", "4.2.2"});
        deviceDetail.put(MATE8, new String[]{"5LM0216117013394", "7.0"});
    }



    @BeforeTest

    public void setUp() throws Exception {
        sConfig.load(new FileReader(configFilePath));
        String currentPhone = sConfig.getProperty("currentPhone", COOLPAD);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
//        capabilities.setCapability(MobileCapabilityType.NO_RESET,"true");

        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD,"true");
        setDeviceCaps(capabilities, currentPhone, deviceDetail.get(currentPhone));


        setAppPackageActivity(capabilities, "com.jingdong.app.mall", ".main.MainActivity");
//        setAppPackageActivity(capabilities, "com.android.calculator2", ".Calculator");
//        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "5");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void setDeviceCaps(DesiredCapabilities capabilities, String deviceName, String[] details) throws Exception {
        validateDevice(deviceName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability("uuid", details[0]);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, details[1]);
    }

    private void validateDevice(String deviceName) throws Exception {
        if (!deviceDetail.keySet().contains(deviceName)) {
            throw new Exception("deviceName.invalid");
        }
    }

    private void setAppPackageActivity(DesiredCapabilities capabilities, String appPackage, String appActivity) {
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
    }

    protected void swipeToUp(AppiumDriver<AndroidElement> dr, int mills) throws WebDriverException{
        int width = dr.manage().window().getSize().width;
        int height = dr.manage().window().getSize().height;
        dr.swipe(width/2, height*4/5, width/2, height/5, mills);
    }
}
