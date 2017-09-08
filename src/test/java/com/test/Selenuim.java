package com.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by yitao on 2017/3/15.
 */
public class Selenuim {

    public static void main(String[] args) throws IOException {
        String targetUrl = "http://www.gsxt.gov.cn/index.html";
//        String targetUrl = "http://www.baidu.com";
        String kw = "keyword";
        String su = "btn_query";
        String keyword = "微位";
        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);
        String root = "d:/temp2/";
        long i = Calendar.getInstance().getTimeInMillis();
        int failCount = 0;
        int ts = 0;
        driver.get(targetUrl);

        WebDriverWait wait = new WebDriverWait(driver, 30);//显示等待
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("gt_holder")));

        //等待图片加载完
        sleep(1000);
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);//隐式等待

        WebElement kwWE = driver.findElement(By.id(kw));//输入框
        WebElement suWE = driver.findElement(By.id(su));//搜索按钮

        WebElement gt_holder = driver.findElement(By.className("gt_holder"));
        WebElement gt_slice = driver.findElement(By.className("gt_slice"));//活动box
        WebElement gt_fullbg = driver.findElement(By.className("gt_fullbg"));//原图
        WebElement gt_bg = driver.findElement(By.className("gt_bg"));//处理后的图
        WebElement html = driver.findElement(By.tagName("html"));
        action.moveToElement(suWE);

        //显示gt_holder
        ((JavascriptExecutor) driver).executeScript("arguments[0].className=arguments[0].className+\" gt_show\"", gt_holder);
        //截取原图
        sleep(500);
        //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);//隐式等待
        File fullBgFile = captureElement(gt_fullbg);
        FileUtils.copyFile(fullBgFile, new File(root + i + "_fullBg.jpg"));
        //获取被裁减的图，隐藏原图
        ((JavascriptExecutor) driver).executeScript("arguments[0].className=arguments[0].className.replace(\" gt_show\",\" gt_hide\")", gt_fullbg);
        //隐藏活动的box
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"none\"", gt_slice);
        //截取获取被裁减的图
        sleep(500);
        //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);//隐式等待
        File cutFullBgFile = captureElement(gt_bg);
        FileUtils.copyFile(cutFullBgFile, new File(root + i + "_fullBgCut.jpg"));

        //计算偏移
        String out = root + i + "_fullBgOut.jpg";
        int offsetX = calcOffsetX(fullBgFile, cutFullBgFile, out);
        ts = offsetX - 6;
        System.out.println("偏移量=" + offsetX);

        //还原隐藏活动的box
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"inline-block\"", gt_slice);
        //还原隐藏原图
        ((JavascriptExecutor) driver).executeScript("arguments[0].className=arguments[0].className.replace(\" gt_hide\",\" gt_show\")", gt_fullbg);
        //还原显示gt_holder
        ((JavascriptExecutor) driver).executeScript("arguments[0].className=arguments[0].className.replace(\" gt_show\",\" gt_hide\")", gt_holder);

        //操作页面-搜索
        kwWE.clear();
        kwWE.sendKeys(keyword);
        //suWE.click();

        //根据偏移制定模拟滑动策略
        WebElement gt_slider_knob = driver.findElement(By.className("gt_slider_knob"));
        sleep(1000);

        action.moveToElement(gt_slider_knob,50,50);
        int s = 0;
        int ds = 1;
        action.click(suWE);
        while(s<ts) {
            action.clickAndHold(gt_slider_knob).moveByOffset(ds, 0).perform();
            s+=ds;
            ds+=1;
            //sleep(1);
        }
        //sleep(500);
        int ls = ts-s;

        action.clickAndHold(gt_slider_knob).moveByOffset(ls, 0).perform();
        action.click(gt_slider_knob).perform();
//        action.clickAndHold(gt_slider_knob).moveByOffset(1, 0).pause(Duration.ofMillis(1)).perform();

        //等待6s,确认页面是否跳转，如果跳转了，则抓取页面中的所有数据链接。否则进行截图重试。如果截图重试失败5次，则重新开始

        //抓取完页面中的链接后，关闭驱动

    }

    //页面元素截图
    public static File captureElement(WebElement element) throws IOException {
        WrapsDriver wrapsDriver = (WrapsDriver) element;
        // 截图整个页面
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        Point p = element.getLocation();
        int x = p.getX();
        int y = p.getY();

        BufferedImage dest = img.getSubimage(x, y, width, height);
        //存为jpg格式
        ImageIO.write(dest, "jpg", screen);
        return screen;
    }


    public static void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int calcOffsetX(File f1, File f2, String out) throws IOException {
        BufferedImage bi1 = ImageIO.read(f1);
        BufferedImage bi2 = ImageIO.read(f2);
        int width = bi1.getWidth();
        int height = bi1.getHeight();
        int x = 0;
        int y = 0;
        int count = 0;
        int minX = width + 1;
        int lastX=0;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        for (y = 0; y < height; y++) {
            for (x = 0; x < width; x++) {
                int pixel = bi1.getRGB(x, y);
                int r = ColorModel.getRGBdefault().getRed(pixel);
                int g = ColorModel.getRGBdefault().getGreen(pixel);
                int b = ColorModel.getRGBdefault().getBlue(pixel);
                int pixel2 = bi2.getRGB(x, y);
                int r2 = ColorModel.getRGBdefault().getRed(pixel2);
                int g2 = ColorModel.getRGBdefault().getGreen(pixel2);
                int b2 = ColorModel.getRGBdefault().getBlue(pixel2);
//                System.out.print("("+pixel+")");
//                System.out.print("("+r+","+g+","+b+")");
                int delta = -50;//差值忽略
                int dr = Math.abs(r2 - r) + delta;
                int dg = Math.abs(g2 - g) + delta;
                int db = Math.abs(b2 - b) + delta;
                dr = dr < 0 ? 0 : dr;
                dg = dg < 0 ? 0 : dg;
                db = db < 0 ? 0 : db;
                dr = dr > 255 ? 255 : dr;
                dg = dg > 255 ? 255 : dg;
                db = db > 255 ? 255 : db;
//                int dr =r2-r;
//                int dg = g2-g;
//                int db =b2-b;
                int p = (dr << 16) | (dg << 8) | (db);
//                System.out.print("(" + p + ")");
                if (p != 0) {
                    if(lastX+1==x){
                        count++;
                    }
                    if(count==5){
                        minX = minX < x ? minX : x;
                    }
                    lastX = x;
                }
                image.setRGB(x, y, p);
            }
            count = 0;
//            System.out.println();
        }
        //System.out.println();
        ImageIO.write(image, "jpg", new File(out));
//        System.out.println("end");
        System.out.println(minX-5);
        return minX-5;
    }
}
