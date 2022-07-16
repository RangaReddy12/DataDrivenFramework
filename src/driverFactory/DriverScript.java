package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import constant.AppUtil;
import utilites.ExcelFileUtil;

public class DriverScript extends AppUtil{
	String inputpath ="D:\\Selenium_9oclockBatch\\DDT_FrameWork\\TestInput\\LoginData.xlsx";
	String outputpath ="D:\\Selenium_9oclockBatch\\DDT_FrameWork\\TestOutPut\\DataDrivenResults.xlsx";
	ExtentReports report;
	ExtentTest test;
@Test
public void startTest()throws Throwable
{
	//define path for html
	report= new ExtentReports("./Reports/DataDriven.html");
	//create object for excel file util class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count no of rows in a sheet
	int rc =xl.rowCount("Login");
	//count no of cells in row
	int cc =xl.cellCount("Login");
	Reporter.log(rc+"       "+cc,true);
	for(int i=1;i<=rc;i++)
	{
		test=report.startTest("Validate Login");
		String user =xl.getCellData("Login", i, 0);
		String pass = xl.getCellData("Login", i, 1);
		//call login method from function library
		boolean res =FunctionLibrary.verifyLogin(user, pass);
		if(res)
		{
			//write as login success into results cell
			xl.setCellData("Login", i, 2, "Login Success", outputpath);
			//write as pass into status cell
			xl.setCellData("Login", i, 3, "Pass", outputpath);
			test.log(LogStatus.PASS, "Login success");
		}
		else
		{
			//take screen shot
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screens/Iteration/"+"  "+i+"Loginpage.png"));
			//write as login success into results cell
			xl.setCellData("Login", i, 2, "Login Fail", outputpath);
			//write as pass into status cell
			xl.setCellData("Login", i, 3, "Fail", outputpath);
			test.log(LogStatus.FAIL, "Login success");
		}
		report.endTest(test);
		report.flush();
	}
}

}














