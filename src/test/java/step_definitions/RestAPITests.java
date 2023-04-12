package step_definitions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.CreatedUserResponse;
import pojo.CreatedUserResponseWithWrongData;
import restutil.Payloads;
import restutil.RestUtils;
import utilities.ExtentUtil;
import utilities.GlobalUtil;
import utilities.HTMLReportUtil;

public class RestAPITests {

	public static String jsonObjectString = null;
	public static Response response = null;
	public String createUserPayload = null;
	static CreatedUserResponseWithWrongData json = null;
	static CreatedUserResponse json1 = null;

	@Given("^Read \"([^\"]*)\" from Testdata file required to create a user$")
	public void read_from_Testdata_file_and_convert_to_json_Object(String arg1) {
		createUserPayload = Payloads.createUser(arg1);
		if (createUserPayload.isEmpty())
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("Failed to load test data from excel"));
		else
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Test data sucessfully loaded from excel"));
	}

	@Given("^I set the content type and body$")
	public void i_set_the_content_type_and_body() {
		try {
			RestUtils.setBaseURI(GlobalUtil.getCommonSettings().getRestURL(), "URI has been set.");
			RestUtils.setContentBodyType(ContentType.JSON, GlobalUtil.getCommonSettings().getRestAccessToken(), createUserPayload,
					"Content Type set to JSON and set the Authorization token");
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("URI, Authorization token and Content Type not set."));
			Assert.fail(e.getMessage());
		}
	}

	@When("^I post the create user data to the api$")
	public void i_post_the_json() {
		try {
			response = RestUtils.PostResponse("", "POST request sent.");
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("POST request failed."));
			Assert.fail(e.getMessage());
		}
	}

	@Then("^I verify the status as \"([^\"]*)\"$")
	public void i_verify_the_status_as(int arg1) {
		try {
			Assert.assertEquals(response.getStatusCode(), arg1, "Status Check Failed!");
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Status code matched."));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("Status code did not match."));
			Assert.fail(e.getMessage());

		}
	}

	@Then("^I verify the json response have an Id$")
	public void i_verify_the_json_response() {
		json1 = response.as(CreatedUserResponse.class);
		try {
			assertFalse(json1.equals(null));
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Got the response body"));
			int id = json1.getData().getId();
			if (id != 0) {
				ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Response body have an Id " + id));
			} else {
				ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("Response body does not have an Id"));
			}
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("Response body is empty"));
			Assert.fail(e.getMessage());
		}

	}

	@Then("^I verify the response body have code \"([^\"]*)\"$")
	public void i_verify_the_response_body_have_code_something(int expectedCode) {
		json = response.as(CreatedUserResponseWithWrongData.class);
		int code = json.getCode();
		try {
			assertEquals(code, expectedCode, "Code did not match");
			ExtentUtil.logger.get().log(Status.PASS, HTMLReportUtil.passStringGreenColor("Code has been matched. " + code));
		} catch (Throwable e) {
			GlobalUtil.errorMsg = e.getMessage();
			ExtentUtil.logger.get().log(Status.FAIL, HTMLReportUtil.failStringRedColor("Code did not match. Expected [" + expectedCode + "] but found [" + code + "]"));
			Assert.fail(e.getMessage());

		}

	}

}
