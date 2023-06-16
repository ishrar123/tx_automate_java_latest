package pageobjects;

import org.openqa.selenium.By;

public class ACS_locators {

	public static By sign_in = By.xpath("//a[text()='Sign in']");
	public static By enter_username = By.xpath("//input[@placeholder='Enter Username']");
	public static By enter_password = By.xpath("//input[@placeholder='Enter Password']");

	public static By sign_up = By.xpath("//strong[text()='Sign in']");

	public static By admin_name = By.xpath("//span[text()=' Hi sahil kapoor ']");

	public static By templates = By.xpath("//a[text()=' Templates ']");

	public static By enter_tempalte_name = By.xpath("//input[@placeholder='Enter template name']");

	public static By enter_project_Name = By.xpath("//input[@placeholder='Enter project name']");

	public static By add_tempalte_button = By.xpath("//button[text()=' Add template ']");

	public static By search_template = By.xpath("//input[@placeholder='Search template']");

	public static By search_project = By.xpath("//input[@placeholder='Enter a project name']");

	public static By third_radio_button = By.xpath("(//input[@type='radio'])[3]");

	public static By button_by_text(String buttonname) {
		return By.xpath("//button[text()=' " + buttonname + " ']");
	}

	public static By dropdown_buttons(String buttonname) {
		return By.xpath("//a[text()=' " + buttonname + " ']");
	}

	public static By error_message = By.xpath("//div[@role='alertdialog']");

	public static By search_button = By.xpath("//button[@title='search_btn_status']");

	public static By Manage_button = By.xpath("//button[@class='btn btn btn-view']");

	public static By Edit_Template_heading = By.xpath("//h1[text()='Edit Template']");

	public static By Manage_dropdowns(String dropdownname) {
		return By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[text()='" + dropdownname + "']");
	}

	public static By checkboxActivityTemplate = By
			.xpath("//input[@type='checkbox' and contains(@class,'dirty')]//parent::div");

	public static By checkboxActivityTemplate(int index) {

		return By.xpath("(//input[@type='checkbox' and contains(@class,'dirty')]//parent::div)[" + index + "]");

	}

	public static By displayParticipantLabel = By.xpath("//label[text()='Display participant list without search']");

	public static By activityCardInstruction = By.xpath("//input[@id='activity_card_instruction']");

	public static By mobileACSReport = By.xpath("//input[@id='mobile_acs_instruction']");

	public static By remoteACSReport = By.xpath("//input[@id='remote_acs_instruction']");

	public static By updateBtn = By.xpath("//button[text()=' Update Details ']");

	public static By activityCardOptions(String activityradioBtnText) {
		return By.xpath("//span[text()='" + activityradioBtnText + "']//ancestor::label//span[@class='cr']");
	}

	public static By nextBtn = By.xpath("//a[text()=' Next ']");

	public static By view_details = By.xpath("//a[text()='View details']");

	public static By Edit_Project_heading = By.xpath("//h1[text()='Edit Project']");

	public static By checkbox_project_doing_with_others = By
			.xpath("//input[@type='checkbox' and @id='project_doing_with_others']//parent::div");

	public static By select_project_template_setting = By.xpath("//select[@id='setting_template_id ']");

	public static By select_template_name(String templatename) {
		return By.xpath("//select[@id='setting_template_id ']/option[text()=' " + templatename + " ']");
	}

	public static By update_projectBtn = By.xpath("//button[text()='Update Project']");

	public static By search_results=By.xpath("//tr[@class='ng-star-inserted']//td[@class='semi-bold']");
}
