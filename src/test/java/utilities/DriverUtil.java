package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.OperatingSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import step_definitions.Hooks;


public class DriverUtil {

	public static int row;
	public static String NDevice_Name = null;
	public static String ODevice_Name;
	public static String username;

	public static final String IE = "IE";
	public static final String REMOTE = "BrowserStack";
	public static final String EDGE = "edge";
	public static final String CHROME = "Chrome";
	public static final String FIREFOX = "Firefox";
	public static final String SAFARI = "Safari";
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();

	private static HashMap<String, String> checkLogin = new HashMap<String, String>();
	public static DesiredCapabilities capabilities = new DesiredCapabilities();

	public static XSSFWorkbook wb;
	public static XSSFSheet sheet1;

	public static final String USER_NAME = "testmailtestingx_94YFr3";

	public static final String ACCESS_KEY = "GA13qkaCFPPzpV9mn5gi";

	public static String deviceName = null;
	public static String osVersion = null;

	
	private DriverUtil() {

	}
	public static WebDriver invokeLocalBrowserWeb(String osName, String browserName) {
		WebDriver browser = null;
		OperatingSystem operatingSystem;
		try {
			osName = osName.toUpperCase();
			browserName = browserName.toUpperCase();

			switch (osName) {
				case "MAC":
					operatingSystem = OperatingSystem.MAC;
					break;
				case "WINDOWS":
					operatingSystem = OperatingSystem.WIN;
					break;
				case "LINUX":
					operatingSystem = OperatingSystem.LINUX;
					break;
				default:
					throw new Exception("Invalid OS Name - " + osName);
			}

			browser = drivers.get(browserName);
			if (browser == null)
				switch (browserName) {
					case "CHROME":
						WebDriverManager.chromedriver().operatingSystem(operatingSystem).setup();
						browser = new ChromeDriver();
						break;
					case "FIREFOX":
						WebDriverManager.firefoxdriver().operatingSystem(operatingSystem).setup();
						browser = new FirefoxDriver();
						break;
					default:
						throw new Exception("Invalid Browser Name - " + browserName);
				}
			drivers.put(browserName, browser);
			browser.manage().window().maximize();
			browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			LogUtil.errorLog(DriverUtil.class, "Browser not launched. Please check the configuration ", e);
			e.printStackTrace();
		}
		return browser;
	}





	
	public static WebDriver getBrowser(String exeEnv, String browserName) {
		WebDriver browser = null;
		try {
			String URL = null;

			MutableCapabilities capabilities = new MutableCapabilities();

			capabilities.setCapability("browserstack.debug", "true");
			capabilities.setCapability("build", GlobalUtil.getCommonSettings().getBuildNumber());

			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();

			browserstackOptions.put("os", GlobalUtil.getCommonSettings().getRemoteOS().split("_")[0]);
			browserstackOptions.put("osVersion", GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);
			browserstackOptions.put("browserVersion", "latest");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("projectName", GlobalUtil.getCommonSettings().getProjectName());
			browserstackOptions.put("buildName", GlobalUtil.getCommonSettings().getBuildNumber());
			browserstackOptions.put("sessionName", Hooks.testCaseDescription);

			if (exeEnv.equals("REMOTE")) {

				if (GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("WINDOWS")) {
					if (browserName.equalsIgnoreCase(CHROME)) {
						capabilities.setCapability("browserName", CHROME);
					} else if (browserName.equalsIgnoreCase(EDGE)) {
						capabilities.setCapability("browserName", EDGE);
					} else if (browserName.equalsIgnoreCase(FIREFOX)) {
						capabilities.setCapability("browserName", FIREFOX);
					} else {
						throw new Exception("Browser name provided in automation control sheet is not valid. Please change it.");
					}
				} else if (GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("OS X")) {
					if (browserName.equalsIgnoreCase(SAFARI)) {
						capabilities.setCapability("browserName", SAFARI);
					} else if (browserName.equalsIgnoreCase(CHROME)) {
						capabilities.setCapability("browserName", CHROME);
					} else if (browserName.equalsIgnoreCase(EDGE)) {
						capabilities.setCapability("browserName", EDGE);
					} else if (browserName.equalsIgnoreCase(FIREFOX)) {
						capabilities.setCapability("browserName", FIREFOX);
					} else {
						throw new Exception("Browser name provided in automation control sheet is not valid. Please change it.");
					}
				} else {
					throw new Exception("Remote OS name provided in automation control sheet is not valid. Please change it.");
				}

				try {
					capabilities.setCapability("bstack:options", browserstackOptions);

					URL = "https://" + GlobalUtil.getCommonSettings().getHostName() + ":" + GlobalUtil.getCommonSettings().getKey() + "@hub-cloud.browserstack.com/wd/hub";

					browser = new RemoteWebDriver(new URL(URL), capabilities);
				} catch (Exception e) {
					e.printStackTrace();
				}
				drivers.put(browserName, browser);

			} else {
				browser = drivers.get(browserName);
				if (browserName.equalsIgnoreCase(CHROME)) {
					if (browser == null) {
						WebDriverManager.chromedriver().setup();
						ChromeOptions chromeOptions = new ChromeOptions();
						chromeOptions.addArguments("--remote-allow-origins=*");
						browser = new ChromeDriver(chromeOptions);
						drivers.put("Chrome", browser);
					}
				}

				else if (browserName.equalsIgnoreCase(IE)) {
					if (browser == null) {
						WebDriverManager.iedriver().arch32().setup();
						InternetExplorerOptions ieO = new InternetExplorerOptions();
						ieO.setCapability("ie.ensureCleanSession", true);
						ieO.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
						browser = new InternetExplorerDriver(ieO);
						drivers.put("IE", browser);
					}
				}

				else if (browserName.equalsIgnoreCase(FIREFOX)) {
					if (browser == null) {
						WebDriverManager.firefoxdriver().setup();
						browser = new FirefoxDriver();
						drivers.put("Firefox", browser);
					}
				}

				else if (browserName.equalsIgnoreCase(EDGE)) {
					if (browser == null) {
						WebDriverManager.edgedriver().arch32().setup();
						browser = new EdgeDriver();
						drivers.put("Edge", browser);
					}
				}
			}
			browser.manage().window().maximize();
			LogUtil.infoLog(DriverUtil.class, GlobalUtil.getCommonSettings().getBrowser() + " : Browser Launched and Maximized.");
		} catch (Exception e) {
			LogUtil.errorLog(DriverUtil.class, "Browser not launched. Please check the configuration ", e);
			e.printStackTrace();
		}
		return browser;
	}

	
	public static String getBrowserAndVersion(WebDriver browser, DesiredCapabilities cap) {
		String browserversion;
		String windows = "Windows";
		String browsername = cap.getBrowserName();
		if ("IE".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) browser).executeScript("return navigator.userAgent;");
			LogUtil.infoLog(DriverUtil.class, uAgent);
			if (uAgent.contains("MSIE") && uAgent.contains(windows)) {
				browserversion = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf(windows) - 2);
			} else if (uAgent.contains("Trident/7.0")) {
				browserversion = "11.0";
			} else {
				browserversion = "0.0";
			}
		} else {
						browserversion = cap.getVersion();
		}
		String browserversion1 = browserversion.substring(0, browserversion.indexOf('.'));
		return browsername + " " + browserversion1;
	}

	
	public static void closeAllDriver() {

		drivers.entrySet().forEach(key -> {
			key.getValue().quit();
			key.setValue(null);
		});

		LogUtil.infoLog(DriverUtil.class, "Closing Browsers");
	}

	public static String getImgRef(String imgFile) {
		return new DriverUtil().getRefImage(imgFile);
	}

	private String getRefImage(String imgFile) {
		String openCVImgsFolder = "OpenCVImages/";
		URL refImgUrl = getClass().getClassLoader().getResource(openCVImgsFolder + imgFile + ".png");
		File refImgFile;
		try {
			refImgFile = Paths.get(refImgUrl.toURI()).toFile();
			System.out.println("File Found : " + refImgFile.exists());
			return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static HashMap<String, String> getCheckLogin() {
		return checkLogin;
	}

	public static void setCheckLogin(HashMap<String, String> checkLogin) {
		DriverUtil.checkLogin = checkLogin;
	}
}

