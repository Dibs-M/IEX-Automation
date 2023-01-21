package org.example;





import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


import java.io.File;


import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {
  static WebDriver driver;
  static WebElement object;
  static Logger logger;
  public static void main(String[] args) {
    String iexUrl = "https://www.iexindia.com/marketdata/areavolume.aspx";
    launch(iexUrl);
  }


    public static void launch(String iexUrl) {
      try {
        File chromedriver = new File("chromedriver");
        System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {
          driver.get(iexUrl);
          driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          WebElement timeSelectorDropdown = driver.findElement(By.xpath("//*[@id='ctl00_InnerContent_ddlPeriod']"));
          if (timeSelectorDropdown != null && timeSelectorDropdown.isDisplayed()) {
            Select dropdown = new Select(timeSelectorDropdown);
           // selectDateAndDownload("Today", dropdown, driver);
            selectDateAndDownload("Tomorrow", dropdown, driver);
          } else {
            //TODO replace with logging
            System.out.println("null data in timeSelectorDropdown");
          }
        } catch (Exception e) {
          System.out.println(e.getLocalizedMessage());
          driver.get("https://www.iexindia.com/marketdata/areavolume.aspx");
        }

        //dropdown.selectByValue("0");
        //selectDateAndDownload("Tomorrow", dropdown, driver);

        //TODO assert condition
        driver.close();
        System.out.println("Done!");


      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    private static void selectDateAndDownload(String day, Select dropdown, WebDriver driver) throws InterruptedException {
      System.out.println(" Selecting For : "+ day);
      dropdown.selectByVisibleText(day);
      WebElement updateReportButton = driver.findElement(By.id("ctl00_InnerContent_btnUpdateReport"));
      if(updateReportButton!= null && updateReportButton.isDisplayed()) {
        try {
          Thread.sleep(5000);
          driver.switchTo().defaultContent();
          updateReportButton.click();
        } catch (WebDriverException e) {
          updateReportButton.click();
        }
      }
      else {
        System.out.println("null data in updateReportButton");
      }
      Thread.sleep(5000);
      WebElement reportDropdown = driver.findElement(By.xpath("//*[@id='ctl00_InnerContent_reportViewer_ctl05_ctl04_ctl00_ButtonImg']"));
      if(reportDropdown != null && reportDropdown.isDisplayed()) {
        reportDropdown.click();
        WebElement excel = driver.findElement(By.xpath("//*[text()='Excel']"));
        if(excel != null && excel.isDisplayed()) {
          excel.click();
        }else {
          System.out.println("FAILED");
        }
      }else {
        System.out.println("FAILED");
      }
    }

}