package com.yeolsimee.roumo.util;

import com.yeolsimee.roumo.app.common.exception.BaseException;
import com.yeolsimee.roumo.app.common.response.ResponseMessage;
import com.yeolsimee.roumo.app.routine.entity.WeekType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeUtils {
	public static HashMap<String, String> convertAlarmTimeToHourMinute(String time) {
		if (time.length() != 4) {
			throw new BaseException(ResponseMessage.ROUTINE_DAY_TIME_LENGTH_NOT_FOUR);
		}
		HashMap<String, String> hashMap = new HashMap<>();
		String timeHour = time.substring(0, 2);
		String timeMinute = time.substring(2, 4);
		hashMap.put("timeHour", timeHour);
		hashMap.put("timeMinute", timeMinute);
		return hashMap;
	}

	public static List<String> makeDateList(String startDateStr, String endDateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		List<String> dateList = new ArrayList<>();
		LocalDate currentDate = startDate;

		while (!currentDate.isAfter(endDate)) {
			dateList.add(currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			currentDate = currentDate.plusDays(1);
		}
		return dateList;
	}
	public static WeekType convertDayToWeekType(String datStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = format.parse(datStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		WeekType dayOfWeekString = null;

		switch (dayOfWeek) {
			case Calendar.SUNDAY:
				dayOfWeekString = WeekType.SUNDAY;
				break;
			case Calendar.MONDAY:
				dayOfWeekString = WeekType.MONDAY;
				break;
			case Calendar.TUESDAY:
				dayOfWeekString = WeekType.TUESDAY;
				break;
			case Calendar.WEDNESDAY:
				dayOfWeekString = WeekType.WEDNESDAY;
				break;
			case Calendar.THURSDAY:
				dayOfWeekString = WeekType.THURSDAY;
				break;
			case Calendar.FRIDAY:
				dayOfWeekString = WeekType.FRIDAY;
				break;
			case Calendar.SATURDAY:
				dayOfWeekString = WeekType.SATURDAY;
				break;
		}
		return dayOfWeekString;
	}
}
