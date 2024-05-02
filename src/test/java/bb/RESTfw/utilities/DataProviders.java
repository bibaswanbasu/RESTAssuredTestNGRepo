package bb.RESTfw.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "userData")
	public Object[][] allDataProvider() throws IOException {

		Object[][] allData = ExcelReader.getUserData("CreateUser");

		// Assuming the first row contains column headers, skip it
		int rowCount = allData.length - 1;
		Object[][] userData = new Object[rowCount][allData[0].length]; // Assuming headers are in row 0

		for (int i = 1; i < allData.length; i++) {
			System.arraycopy(allData[i], 0, userData[i - 1], 0, allData[i].length);
		}

		return userData;

	}

	@DataProvider(name = "userNameData")
	public String[] UserNameDataProvider() throws IOException {

		String[] allData = ExcelReader.getColumnData("CreateUser", "userName");

		return allData;

	}

}
