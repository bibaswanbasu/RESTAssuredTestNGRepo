package bb.RESTfw.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListener implements ITestListener {

	private ExtentSparkReporter htmlReporter;
	private static ExtentReports reports;
	private ExtentTest test;

	public void configureReport() {
		if (reports == null) {
			String timestamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());

			String reportName = "PetStoreAPIReport" + timestamp + ".html";
			htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//test-output//" + reportName);

			reports = new ExtentReports();
			reports.attachReporter(htmlReporter);

			// ADD SYSTEM INFO
			reports.setSystemInfo("Machine", "testPC");
			reports.setSystemInfo("os", "Windows 10");
			reports.setSystemInfo("User Name", "Bibaswan Basu");

			// UPDATE LOOK OF THE REPORT
			htmlReporter.config().setDocumentTitle("Extent Listener Report");
			htmlReporter.config().setReportName("First API Report");
			htmlReporter.config().setTheme(Theme.DARK);
		}

	}

	public void onStart(ITestContext context) {
		configureReport();

	}

	public void onTestStart(ITestResult result) {
		// not implemented
	}

	public void onTestSuccess(ITestResult Result) {
		test = reports.createTest(Result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel("Name Of the Pass TC- " + Result.getName(), ExtentColor.GREEN));
	}

	public void onTestFailure(ITestResult Result) {

		test = reports.createTest(Result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel("Name Of the Failed TC- " + Result.getName(), ExtentColor.RED));
	}

	public void onTestSkipped(ITestResult Result) {
		test = reports.createTest(Result.getName());
		test.log(Status.SKIP,
				MarkupHelper.createLabel("Name Of the Skipped TC- " + Result.getName(), ExtentColor.YELLOW));
	}

	public void onFinish(ITestContext context) {
		flushReport();

	}

	public static void flushReport() {
		if (reports != null) {
			reports.flush();
		}
	}
}
