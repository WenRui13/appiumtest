package shopping.android.execute;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import shopping.android.base.BaseDriver;

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

        Assert.assertTrue(driver.findElement(By.xpath(".//*[@text()='开始学习']")).isDisplayed());

    }
}
