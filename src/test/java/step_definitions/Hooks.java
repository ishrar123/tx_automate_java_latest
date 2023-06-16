package step_definitions;

import com.Buffer.BufferUtilSuiteLevel;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import mantisutil.MantisReport;
import testlink.api.java.client.TestLinkAPIResults;
import utilities.ConfigReader;
import utilities.DriverUtil;
import utilities.ExtentUtil;
import utilities.GlobalUtil;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class Hooks {

	String imagePath;
	String pathForLogger;
	public static String testCaseDescription;
	public static String executingTagName;

	public static String imagePath1;
	public static String concatt = ".";

	@Before("@Web")
	public void beforeMethodAmazon(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		ExtentUtil.startTestInit(testCaseDescription);

		LogUtil.infoLog(getClass(), "\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());
		LogUtil.infoLog(getClass(), "Test Started with tag : " + scenario.getSourceTagNames());
		executingTagName = scenario.getSourceTagNames().toArray()[0].toString();
		LogUtil.infoLog(getClass(), "Test Started with tag : " + executingTagName);
		LogUtil.infoLog(Hooks.class, "Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog(Hooks.class, "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());

		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(),
				GlobalUtil.getCommonSettings().getBrowser()));

	}




	@After("@Web")
	public void afterMethodSmoke(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				ExtentUtil.attachScreenshotToReportOnFailure(scenario);

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
					bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.errorMsg, "General",
							"Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser() + " and Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(),
							BufferUtilSuiteLevel.screenshotFilePath);
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi
							.reporIssue(
									scenario.getName(), "Automated on OS: " + osName + ",\n Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser()
											+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n" + GlobalUtil.errorMsg,
									BufferUtilSuiteLevel.screenshotFilePath);
				}

				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi.updateTestLinkResult(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
							TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in " + GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

				LogUtil.infoLog(getClass(), "Trying to get Bug ID: " + bugID);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog(Hooks.class, "Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed", TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		DriverUtil.closeAllDriver();
	}


}