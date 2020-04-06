package com.vam.auto;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import junit.framework.Assert;

public class ShareTestTest {

	@Test
	public void accessExcelFilePositive() throws IOException {
		ShareTest st = new ShareTest();
		st.accessExcelFile("F:\\ECLIPSE WORKSPACE\\AutoProject\\src\\main\\resources\\ShareMarketDetails.xlsx",
				"ShareMarket");
		Assert.assertTrue(true);
	}
	
	@Test(expected = IOException.class)
	public void accessExcelFileInvalidPath() throws IOException {
		ShareTest st = new ShareTest();
		st.accessExcelFile("F:\\WORKSPACE\\AutoProject\\src\\main\\resources\\ShareMarketDetails.xlsx",
				"ShareMarket");
	}
	
	@Test(expected = IOException.class)
	public void accessExcelFileInvalidSheetName() throws IOException {
		ShareTest st = new ShareTest();
		st.accessExcelFile("F:\\ECLIPSE WORKSPACE\\AutoProject\\src\\main\\resources\\ShareMarketDetails.xlsx",
				"ShareMarket1");
	}
	

}
