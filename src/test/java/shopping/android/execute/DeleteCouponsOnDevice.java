package shopping.android.execute;

import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import shopping.android.base.BaseDriver;

import java.util.List;

/**
 * @FunctionDesc
 * @Author bjyfxuxiaojun
 * @CreateDate 2017/8/10
 * @Reviewer kongxiangyun
 * @ReviewDate 2017/8/10
 */
public class DeleteCouponsOnDevice extends BaseDriver {
    @DataProvider(name = "qq")
    public static Object[][] qq() {
        return new Object[][]{
                {"2491841795"},
                {"1795942062"},
                {"3454608466"},
                {"2447029216"},
                {"454803299"},
                {"872954518"},
                {"追求完美的猫"},
        };
    }

    @Test(dataProvider = "qq")
    public void testLoopDeleteCoupons(String qq) throws Exception {
        login(qq);
        String couponRecord = driver.findElementByXPath(".//*[@text='优惠券']/preceding-sibling::*").getText();
        if (Integer.valueOf(couponRecord) == 0) {
            System.out.println("优惠券数量为0，无须删除！");
            return;
        }
        driver.findElementByName("优惠券").click();
        List<AndroidElement> list = driver.findElementsByClassName("android.widget.TextView");
        System.out.println(list.get(1).getText());
        while (!list.get(1).getText().contains("未使用 (0)")) {
            driver.findElementsByClassName("android.widget.ImageView").get(1).click();
            driver.findElementByName("批量删除").click();
            driver.findElementByName("全选").click();

            while (!list.get(list.size() - 1).getText().contains("批量删除(90+)")) {
                try {
                    swipeToUp(driver, 200);
                } catch (WebDriverException e) {
                    break;
                } finally {
                    list = driver.findElementsByClassName("android.widget.TextView");
                }
            }
            list.get(list.size() - 1).click();
            driver.findElementsByClassName("android.widget.Button").get(1).click();
            list = driver.findElementsByClassName("android.widget.TextView");
        }

        driver.navigate().back();
        Assert.assertTrue(driver.findElementByAccessibilityId("账户管理").isDisplayed());
    }

    public void login(String qq) throws Exception {
        Assert.assertTrue(driver.findElementByAccessibilityId("我的").isDisplayed());
        driver.findElementByAccessibilityId("我的").click();
        Assert.assertTrue(driver.findElementByAccessibilityId("账户管理").isDisplayed());

        String currentUser = driver.findElementsByClassName("android.widget.TextView").get(0).getText();
        System.out.println(currentUser);
        if (!currentUser.contains("登录/注册")) {
            logout();
        }
        driver.findElementByName("登录/注册").click();
        if (qq.equals("追求完美的猫")) {
            driver.findElementsByClassName("android.widget.EditText").get(0).click();
            driver.findElementByClassName("android.widget.ImageView").click();

            driver.findElementsByClassName("android.widget.EditText").get(0).sendKeys("追求完美的猫");
            driver.findElementsByClassName("android.widget.EditText").get(1).clear();
            driver.findElementsByClassName("android.widget.EditText").get(1).sendKeys("xzh123");

            driver.findElementByName("登录").click();
            return;
        }

        driver.findElementByXPath(".//*[@text='忘记密码']/following::android.widget.TextView[1]").click();
        String QQ = driver.findElementByXPath(".//*[contains(@text,'（')]").getText().trim();
        String realQQ = QQ.substring(1, QQ.lastIndexOf("）"));
//        System.out.println(realQQ);
        if (!qq.equals(realQQ)) {       // can replace with   !driver.getPageSource().contains(qq)
            driver.findElementByName("切换帐号").click();
            driver.findElementByName(qq).click();
            return;
        }

        driver.findElementByName("登录").click();

    }

    @AfterMethod
    public void logout() throws Exception {
        driver.findElementByAccessibilityId("账户管理").click();

//        AndroidElement accountSettingView = driver.findElementByClassName("android.widget.ScrollView");
//        accountSettingView.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"退出登录\"));").click();

        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                ".scrollIntoView(new UiSelector().text(\"退出登录\"));").click();


//        swipeToUp(driver, 500);
//        driver.findElementByName("退出登录").click();
        driver.findElementByName("确定").click();
        Assert.assertTrue(driver.findElementByName("登录/注册").isDisplayed());
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.closeApp();
        driver.quit();
    }
}
