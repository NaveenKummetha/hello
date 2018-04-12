package com.sirius.onesource.runtime.spi;

import java.net.URL;

import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.sirius.onesource.commons.beans.TestStepBean;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class AppiumSafariClearCache {

	private static final String CLASS_NAME = AppiumSafariClearCache.class.getName();

	public static void clearCache(String parrameter, String port, String deviceType) {
		AppiumDriver driver = null;
		try {
			// TestStepBean stepBean = getTestStepBean();

			long startTime = System.currentTimeMillis();
			String[] parameterValues = parrameter.split(":");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ipad");
			capabilities.setCapability(MobileCapabilityType.APP, "Settings");
			capabilities.setCapability("automationName", "XCUITest");

			driver = new IOSDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
			
			((AppiumDriver<?>) driver).context("NATIVE_APP");
			if (deviceType.toLowerCase().contains("ipad")) {
				ipadLogic(parameterValues, driver);
			} else if (deviceType.toLowerCase().contains("iphone")) {
				iphoneLogic(parameterValues, driver);
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);

			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	public static void scrollDown(int loopTimes, RemoteWebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<>();
		scrollObject.put("direction", "down");
		for (int i = 0; i < loopTimes; i++) {
			js.executeScript("mobile:scroll", scrollObject);
		}
	}

	public static void swipeUp(int loopTimes, RemoteWebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<>();
		scrollObject.put("direction", "up");
		for (int i = 0; i < loopTimes; i++) {
			js.executeScript("mobile:swipe", scrollObject);
		}
	}

	public static void ipadLogic(String[] parameterValues, RemoteWebDriver driver) {
		scrollDown(Integer.parseInt(parameterValues[0]), driver);
		driver.findElementByName("Safari").click();
		swipeUp(Integer.parseInt(parameterValues[1]), driver);
		driver.findElementByName("Clear History and Website Data").click();
		driver.findElementByName("Clear").click();
	}

	public static void iphoneLogic(String[] parameterValues, RemoteWebDriver driver) {
		scrollDown(Integer.parseInt(parameterValues[0]), driver);
		driver.findElementByName("Safari").click();
		scrollDown(Integer.parseInt(parameterValues[1]), driver);
		driver.findElementByName("Clear History and Website Data").click();
		driver.findElementByName("Clear History and Data").click();
	}

	public static void main(String[] args) {
		try {
//			public static void clearCache(String parrameter, String port, String deviceType) {
			new AppiumSafariClearCache().clearCache("2:1", "42022", "iphone");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
