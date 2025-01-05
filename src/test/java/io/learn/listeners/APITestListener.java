package io.learn.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class APITestListener implements ITestListener {

    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    static {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports-api.html");
        extentReports.attachReporter(sparkReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        test.info("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (extentTest.get() != null) {
            extentTest.get().pass("Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (extentTest.get() != null) {
            extentTest.get().fail("Test failed: " + result.getThrowable());

            // Retrieve API request/response from the context
            String apiRequest = (String)result.getTestContext().getAttribute("apiRequest");
            Response apiResponse = (Response)result.getTestContext().getAttribute("apiResponse");

            if (apiRequest != null && apiResponse != null) {
                // Log API request and response details
                extentTest.get().info("API Request: " + apiRequest);
                extentTest.get().info("API Response: " + apiResponse.asString());

                extentTest.get().info("Response Status: " + apiResponse.getStatusCode());
                extentTest.get().info("Response Body: " + apiResponse.getBody());
            } else {
                extentTest.get().info("API request/response data is not available.");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (extentTest.get() != null) {
            extentTest.get().skip("Test skipped: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

    @Override
    public void onStart(ITestContext context) {
        // No implementation needed
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
