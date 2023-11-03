package com.tsui_base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Functions {
    private WebDriver driver;

    public Functions(WebDriver driver) {
        this.driver = driver;
    }

    public void ZoomIn() {
        WebElement ZoomIn = driver.findElement(By.xpath("//i[contains(@class,'fa-solid fa-magnifying-glass-plus')]"));
        for (int i = 0; i < 5; i++) {
            ZoomIn.click();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
        }
    }

    public void ZoomOut() {
        WebElement Zoomout = driver.findElement(By.xpath("//i[contains(@class,'fa-solid fa-magnifying-glass-minus')]"));
        for (int i = 1; i < 5; i++) {
            Zoomout.click();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
        }
    }

    public void Horizontol() {
        WebElement smallmap = driver.findElement(By.xpath("//*[@id=\"map\"]/div[4]/div[1]/div"));
        Actions builder = new Actions(driver);
        for (int i = 0; i < 4; i++) {
            int xOffset = -30;
            int yOffset = 0;
            builder.dragAndDropBy(smallmap, xOffset + -30, yOffset + 0).perform();
        }
    }
}