package com.yeolsimee.moneysaving.util;

import com.yeolsimee.moneysaving.app.common.exception.BaseException;
import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;

import java.util.HashMap;

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
}
