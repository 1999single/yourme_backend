package com;

import com.single.yourme.YourMeApplication;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-12-04
 */
@SpringBootTest(classes = YourMeApplication.class)
@RunWith(SpringRunner.class)
public class SpringbootSelenium {

    private static ChromeDriver browser;

    @Test
    public void addBookToEmptyList() throws InterruptedException {
//        // 要测试的网址
//        String baseUrl = "http://localhost:" + 5950 + "/TEST" ;
//        browser.get(baseUrl);
//        // 对网页表单元素进行赋值操作并提交表单
//        assertEquals("You have no books in your book list",
//                browser.findElementByTagName("div").getText());
//        browser.findElementByName("title")
//                .sendKeys("BOOK TITLE");
//        browser.findElementByName("author")
//                .sendKeys("BOOK AUTHOR");
//        browser.findElementByName("isbn")
//                .sendKeys("1234567890");
//        browser.findElementByName("description")
//                .sendKeys("DESCRIPTION");
//        browser.findElementByTagName("form")
//                .submit();
//        // 测试运行结果是否符合预期
//        WebElement dl =
//                browser.findElementByCssSelector("dt.bookHeadline");
//        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)",
//                dl.getText());
//        WebElement dt =
//                browser.findElementByCssSelector("dd.bookDescription");
//        assertEquals("DESCRIPTION", dt.getText());
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Administrator\\Desktop\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://baidu.com/");
        driver.close();
    }
}
