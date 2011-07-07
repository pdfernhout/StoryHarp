package com.kurtz_fernhout.storyharp.testing;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;


public class Test_Application {
	@Test public void test() {
		Calendar calendar = GregorianCalendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
        if (year >= 2000) {
        	System.out.println("after 2000: " + year);
        }
	}
}
