package org.j2se.date;

import java.text.ParseException;

import org.junit.Test;

public class GetBetweenDaysTest {
	@Test
	public void test() throws ParseException {
		int t = GetBetweenDays.getBetweenDays("1911-01-03", "1921-03-03");
		System.out.println(t);
	}
}
