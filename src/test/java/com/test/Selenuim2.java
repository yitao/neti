package com.test;

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
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * Created by yitao on 2017/3/15.
 */
public class Selenuim2 {
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

        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);
        driver.get("http://www.gsxt.gov.cn/corp-query-homepage.html");
        String title = driver.getTitle();
        System.out.println("title=" + title);

//        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);//隐式等待

//        WebDriverWait wait = new WebDriverWait(driver, 20);//显示等待
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".red_box")));

        //WebElement element = driver.findElement(By.cssSelector(".red_box"));
        long i = Calendar.getInstance().getTimeInMillis();
//        System.out.println(System.currentTimeMillis());
        WebDriverWait wait = new WebDriverWait(driver, 30);//显示等待
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("gt_holder")));
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//        System.out.println(System.currentTimeMillis());

        WebElement gt_holder = driver.findElement(By.cssSelector(".gt_holder"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity = 1;arguments[0].style.display = 'block';", gt_holder);

        WebElement fullBg = driver.findElement(By.className("gt_fullbg"));//原图
//        File fullBgFile = captureElement(fullBg);
//        FileUtils.copyFile(fullBgFile, new File("d:/temp/" + i + "_fullBg.jpg"));

        //driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
//        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"none\"", fullBg);

        //隐藏拖动的box
        //$(".gt_slice").css("display","none");
        WebElement gt_slice = driver.findElement(By.className("gt_slice"));//活动box
//        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"none\"", gt_slice);

        WebElement gt_bg = driver.findElement(By.className("gt_bg"));//处理后的图
//        File cutFullBgFile = captureElement(gt_bg);
//        FileUtils.copyFile(cutFullBgFile, new File("d:/temp/" + i + "_fullBgCut.jpg"));

//        int offsetX = calcOffsetX(fullBgFile, cutFullBgFile, i);
//        System.out.println("offsetX=" + offsetX);

        WebElement kw = driver.findElement(By.id("keyword"));
        kw.sendKeys("微位");

        //((JavascriptExecutor)driver).executeScript("arguments[0].style.border = \"5px double red\"",kw);
        sleep(800);

//        action.click(kw).perform();


//        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = \"block\"", gt_slice);
//        ((JavascriptExecutor) driver).executeScript("arguments[0].style.opacity = 1;arguments[0].style.display = 'none';", gt_holder);
        sleep(800);

        WebElement btn = driver.findElement(By.id("btn_query"));
        btn.click();//点击搜索

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

//        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(srcFile, new File("d:/temp/"+i+".jpg"));


        WebElement knob = driver.findElement(By.className("gt_slider_knob"));
        action.clickAndHold(knob).moveByOffset(10, 0);//.perform();

        //driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);

            /*Scanner scanner = new Scanner(System.in);
            System.out.print("continue?(y/n):");
            String s = scanner.next();
            if ("n".equals(s)){
                break;
            }*/
        while (true) {
            sleep(1);
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
        int minX = width + 1;
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
                //System.out.print("(" + p + ")");
                if (p != 0) {
                    minX = minX < x ? minX : x;
                }
                image.setRGB(x, y, p);
            }
            //System.out.println();
        }
        //System.out.println();
        ImageIO.write(image, "jpg", new File(out));
        System.out.println("end");
        System.out.println(minX);
        return minX;
    }

    @Test
    public void testOffsetX() throws IOException{
        String img1 = "d:/temp/1489636101439_fullBg.jpg";
        String img2 = "d:/temp/1489636101439_fullBgCut.jpg";
        String out = "d:/temp/out.jpg";
        BufferedImage bi1 = ImageIO.read(new File(img1));
        BufferedImage bi2 = ImageIO.read(new File(img2));
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
                System.out.print("(" + p + ")");
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
            System.out.println();
        }
        //System.out.println();
        ImageIO.write(image, "jpg", new File(out));
        System.out.println("end");
        System.out.println(minX-5);
    }
}
