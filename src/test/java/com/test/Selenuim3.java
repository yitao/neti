package com.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Created by yitao on 2017/3/15.
 */
public class Selenuim3 {
    public static void main(String[] args) {
        int count = 0;
        String targetUrl = "http://www.baidu.com";
        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);
        driver.get(targetUrl);
        String title = driver.getTitle();
        System.out.println("title=" + title);

        WebElement kw = driver.findElement(By.id("kw"));
        kw.clear();
        kw.sendKeys("cheese");
        kw.submit();


//        WebElement btn = driver.findElement(By.id("su"));
//        btn.click();//点击搜索



        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());

        driver.quit();
    }

    @Test
    public void test() throws IOException {
        int count = 0;
        String targetUrl = "http://www.baidu.com";
        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);
        driver.get(targetUrl);
        String curWinHandler = driver.getWindowHandle();


        WebElement kw = driver.findElement(By.id("kw"));
        kw.clear();
        kw.sendKeys("cheese");
        kw.submit();


//        WebElement btn = driver.findElement(By.id("su"));
//        btn.click();//点击搜索
//        driver.close();

        System.out.println("Page title is: " + driver.getTitle());
        sleep(6000);
        Set<String> ss = driver.getWindowHandles();
        for(String s: ss){
            System.out.println("s="+s);
            if(!StringUtils.equals(s,curWinHandler)){
                WebDriver d2 = driver.switchTo().window(s);
                System.out.printf("d2.title="+d2.getTitle());
                d2.close();
            }
        }


        /*(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });*/

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());

//        driver.quit();
//        String res = driver.getPageSource();
//        System.out.println("=================");
//        System.out.println(res);
//        System.out.println("=================");
//        //WebElement w1= driver.findElement(By.className("c-row"));
//        while (true) {
//            sleep(1);
//        }

    }

    public void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        String targetUrl = "http://www.baidu.com";
        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // And now use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());

        //Close the browser
        driver.quit();
    }

}
