package mobileutil;

import java.util.List;

import org.openqa.selenium.remote.RemoteWebElement;

import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import utilities.ExtentUtil;
import utilities.GlobalUtil;

public class RedDoorzHelpers {

	public static void clickDismisspopup() {

		if (MobileKeywords.isWebElementPresent(RedDoorzConstants.id_popup_dismiss, AndriodConstants.Common.type_id)) {
			MobileKeywords.click(RedDoorzConstants.id_popup_dismiss, AndriodConstants.Common.type_id, "click on dismiss popup");
		}
	}

	public static void clickBookNow() throws InterruptedException {
		Thread.sleep(5000);
		if (MobileKeywords.isWebElementPresent(RedDoorzConstants.id_book_now, AndriodConstants.Common.type_id)) {
			MobileKeywords.click(RedDoorzConstants.id_book_now, AndriodConstants.Common.type_id, "click on book now");
		} else if (MobileKeywords.isWebElementPresent(RedDoorzConstants.id_continue_with_RC, AndriodConstants.Common.type_id)) {
			MobileKeywords.click(RedDoorzConstants.id_redclub_price, AndriodConstants.Common.type_id, "click on club price");
		} else {
			clickByText("pm_book_now", "BOOK Now Clicked");
		}

	}

	public static void clickDismissBtn() throws InterruptedException {
		Thread.sleep(5000);
		if (MobileKeywords.isWebElementPresent(RedDoorzConstants.id_btn_dismiss, AndriodConstants.Common.type_id)) {
			MobileKeywords.click(RedDoorzConstants.id_btn_dismiss, AndriodConstants.Common.type_id, "click on button dismiss");
		}

	}

	public static void clickByText(String text, String logstep) throws InterruptedException {
		Thread.sleep(12000);

		try {
			List<?> ListElemnts = GlobalUtil.getMDriver().findElements(MobileBy.xpath("//android.widget.TextView"));

			for (int i = 1; i <= ListElemnts.size(); i++) {
				if (((RemoteWebElement) ListElemnts.get(i)).getText().contains(text)) {
					System.out.println(text);
					((RemoteWebElement) ListElemnts.get(i)).click();
					ExtentUtil.logger.get().log(Status.PASS, logstep);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void enterByText(String text, String data) throws InterruptedException {
		Thread.sleep(12000);
		try {
			List<?> ListElemnts = GlobalUtil.getMDriver().findElements(MobileBy.xpath("//android.widget.EditText"));

			for (int i = 1; i <= ListElemnts.size(); i++) {
				System.out.println("Element=" + i + "=" + ((RemoteWebElement) ListElemnts.get(i)).getText());
				if (((RemoteWebElement) ListElemnts.get(i)).getText().contains(text)) {
					System.out.println(text);
					((RemoteWebElement) ListElemnts.get(i)).sendKeys(data);
					break;
				}

			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
