package com.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
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
import java.util.concurrent.TimeUnit;


/**
 * Created by yitao on 2017/3/15.
 */
public class Selenuim4 {
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
        //存为png格式
        ImageIO.write(dest, "jpg", screen);
        return screen;
    }

    @Test
    public void test() throws IOException {
        int count = 0;
        String targetUrl = "http://www.gsxt.gov.cn/corp-query-homepage.html";
        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);
        driver.get(targetUrl);
        String title = driver.getTitle();
        System.out.println("title=" + title);

        WebElement kw = driver.findElement(By.id("keyword"));
        kw.clear();
        kw.sendKeys("微位");

//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);//隐式等待

        long i = Calendar.getInstance().getTimeInMillis();
        System.out.println(i);

        WebDriverWait wait = new WebDriverWait(driver, 30);//显示等待
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("gt_holder")));
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        sleep(1000);

        WebElement gt_holder = driver.findElement(By.cssSelector(".gt_holder"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity = 1;arguments[0].style.display = 'block';", gt_holder);

        WebElement fullBg = driver.findElement(By.className("gt_fullbg"));//原图
        File fullBgFile = captureElement(fullBg);
        FileUtils.copyFile(fullBgFile, new File("d:/temp/" + i + "_fullBg.jpg"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"none\"", fullBg);

        //隐藏拖动的box
        //$(".gt_slice").css("display","none");
        WebElement gt_slice = driver.findElement(By.className("gt_slice"));//活动box
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"none\"", gt_slice);

        WebElement gt_bg = driver.findElement(By.className("gt_bg"));//处理后的图
        File cutFullBgFile = captureElement(gt_bg);
        FileUtils.copyFile(cutFullBgFile, new File("d:/temp/" + i + "_fullBgCut.jpg"));

        int offsetX = calcOffsetX(fullBgFile, cutFullBgFile, i);
        System.out.println("offsetX=" + offsetX);

        sleep(1000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"inline-block\"", gt_slice);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity = 1;arguments[0].style.display = 'none';", gt_holder);
        sleep(800);

        WebElement btn = driver.findElement(By.id("btn_query"));
        btn.click();//点击搜索

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"inline-block\"", gt_slice);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity = 1;arguments[0].style.display = 'block';", gt_holder);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


        //失败刷新机制
        while(true) {

            offsetX -= 6;

            WebElement knob = driver.findElement(By.className("gt_slider_knob"));

            int t = 0;//
            int s = 0;//
            int ts = 0;
            int it = 5;
            int a = 2;
            int v0 = 0;
//        a = new Random(System.currentTimeMillis()+112645).nextInt(3)+1;//加速度 px/ms
//        v0 = new Random(System.currentTimeMillis()+9730).nextInt(3);
            int ul = new Random(System.currentTimeMillis() + 7730).nextInt(3) + 7;
            int bl = new Random(System.currentTimeMillis() + 74730).nextInt(3) + 2;
            System.out.println("it=" + it + ",a=" + a + ",v0=" + v0 + ",ul=" + ul + ",bl=" + bl);
//        while(offsetX<50?ts<offsetX/6*ul:ts<offsetX/6*bl){
            while (ts < ((offsetX-20)<0?offsetX+20:offsetX-20) ) {
                s = v0 * it + it * it * a / 2;
//            s += new Random(System.currentTimeMillis()+930).nextInt(3)+2;
//            s -= new Random(System.currentTimeMillis()+7435).nextInt(3)+2;
                t += it;
                System.out.println("s=" + s+",ts="+ts);
                ts += s;
                action.clickAndHold(knob).moveByOffset(s, 0).pause(Duration.ofMillis(it)).perform();
            }
            sleep(500);
            v0 = a * t;
            int ls = offsetX - ts;
            a=-v0/2;
            it=2;
            ts=0;
            while (ts < ls ) {
                s = v0 * it + it * it * a / 2;
//            s += new Random(System.currentTimeMillis()+930).nextInt(3)+2;
//            s -= new Random(System.currentTimeMillis()+7435).nextInt(3)+2;
                //t += it;
                //v0 += a * t;
                System.out.println("222   s=" + s+",ts="+ts);
                ts += s;
                action.clickAndHold(knob).moveByOffset(s, 0).pause(Duration.ofMillis(it)).perform();
            }
            sleep(500);
            ls = ls - ts;
            System.out.println("333 ts=" + ts + ",ls=" + ls);
            action.clickAndHold(knob).moveByOffset(ls, 0).pause(Duration.ofMillis(300)).perform();
            action.click(knob).perform();


//        int d = offsetX/2;
//        int d2 = offsetX/3;
//        int d3 = offsetX/6;
//        action.clickAndHold(knob).moveByOffset(d, 0).pause(Duration.ofMillis(200)).perform();
//        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
//        action.clickAndHold(knob).moveByOffset(d2, 0).pause(Duration.ofMillis(300)).perform();
//        driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
//        action.clickAndHold(knob).moveByOffset(d3, 0).pause(Duration.ofMillis(500)).perform();
//        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
//        action.click(knob).perform();

            title = driver.getTitle();
            System.out.println("new title=" + title);
            String url = driver.getCurrentUrl();
            System.out.println("url="+url);
            if(targetUrl.equals(url) && count<=5){
                count++;
                //重新截图，重新计算offsetX
                //continue;
            }
            while (true) {
                sleep(1);
            }
        }

    }

    public void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int calcOffsetX(File f1, File f2, long timestamp) throws IOException {
        String img1 = "d:/temp/fullBgFile2.jpg";
        String img2 = "d:/temp/cutFullBgFile2.jpg";
        String out = "d:/temp/" + timestamp + "_fullBgOut.jpg";
        BufferedImage bi1 = ImageIO.read(f1);
        BufferedImage bi2 = ImageIO.read(f2);
        int width = bi1.getWidth();
        int height = bi1.getHeight();
        int x = 0;
        int y = 0;
        int count = 0;
        int minX = width + 1;
        int ominX = minX;
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
