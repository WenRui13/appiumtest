package shopping.android.execute;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import shopping.android.base.BaseDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @FunctionDesc
 * @Author bjyfxuxiaojun
 * @CreateDate 2017/8/10
 * @Reviewer kongxiangyun
 * @ReviewDate 2017/8/10
 */
public class DeleteCouponsOnDevice extends BaseDriver {
    @Test
    public void testLoopDeleteCoupons() throws Exception {
        driver.findElementByName("优惠券").click();
        List<AndroidElement> list = driver.findElementsByClassName("android.widget.TextView");
        System.out.println(list.get(1).getText());
        while (!list.get(1).getText().contains("未使用 (0)")) {
            driver.findElementsByClassName("android.widget.ImageView").get(1).click();
            driver.findElementByName("批量删除").click();
            driver.findElementByName("全选").click();

            while (!list.get(list.size() - 1).getText().contains("批量删除(90)")) {
                try {
                    swipeToUp(driver, 300);
                } catch (WebDriverException e) {
                    break;
                } finally {
                    list = driver.findElementsByClassName("android.widget.TextView");
                }
//                Thread.sleep(1000);
//                list = driver.findElementsByClassName("android.widget.TextView");
            }
            list.get(list.size() - 1).click();
            driver.findElementsByClassName("android.widget.Button").get(1).click();
            list = driver.findElementsByClassName("android.widget.TextView");
        }
    }

    @BeforeTest
    public void logout() throws Exception {
        Assert.assertTrue(driver.findElementByAccessibilityId("我的").isDisplayed());
        driver.findElementByAccessibilityId("我的").click();
        Assert.assertTrue(driver.findElementByAccessibilityId("账户管理").isDisplayed());

        String currentUser = driver.findElementsByClassName("android.widget.TextView").get(0).getText();
        System.out.println(currentUser);
        if (!currentUser.contains("登录/注册")) {
            driver.findElementByAccessibilityId("账户管理").click();
            swipeToUp(driver, 500);

            driver.findElementByName("退出登录").click();
            driver.findElementByName("确定").click();
            Assert.assertTrue(driver.findElementByName("登录/注册").isDisplayed());


        }
        driver.findElementByName("登录/注册").click();

        driver.findElementsByClassName("android.widget.EditText").get(0).click();
        driver.findElementByClassName("android.widget.ImageView").click();

        driver.findElementsByClassName("android.widget.EditText").get(0).sendKeys("追求完美的猫");
        driver.findElementsByClassName("android.widget.EditText").get(1).clear();
        driver.findElementsByClassName("android.widget.EditText").get(1).sendKeys("xzh123");

        driver.findElementByName("登录").click();

//        driver.findElementByName("QQ登录").click();
//
//        Assert.assertTrue(driver.findElementByName("（2447029216）").isDisplayed());
//        driver.findElementByName("登录").click();

    }
}
