package io.learn.tests.api;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.learn.api.ApiClient;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PetApiTest {

    private static final String PET_ID = "6454";

    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    static {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports-api.html");
        extentReports.attachReporter(sparkReporter);
    }

    @Test
    public void testGetPet(ITestContext context) {
        ExtentTest test = extentReports.createTest("testGetPet");
        extentTest.set(test);
        ExtentTest extentTest1 = extentTest.get();
        extentTest1.log(Status.INFO, "Sending GET request to retrieve pet with ID: " + PET_ID);
        Response response = ApiClient.get("/" + PET_ID);

        // Store the request and response in the context for failure handling
        context.setAttribute("apiRequest", "/pets/" + PET_ID);
        context.setAttribute("apiResponse", response);

        extentTest1.log(Status.INFO, "Response received with status code: " + response.getStatusCode());

        // Assertions
        try {
            assertEquals(response.getStatusCode(), 200);
            assertEquals(response.jsonPath().getString("name"), "Tillman");
            extentTest1.log(Status.PASS, "Pet retrieved successfully with ID: " + PET_ID);
        } catch (AssertionError e) {
            extentTest1.log(Status.FAIL, "Test failed due to assertion error: " + e.getMessage());
            throw e;  // Re-throw the exception to mark the test as failed
        }
    }
}
