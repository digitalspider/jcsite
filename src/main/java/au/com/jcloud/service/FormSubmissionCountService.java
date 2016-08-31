package au.com.jcloud.service;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import java.util.Map;

import au.com.jcloud.enums.FormType;

/**
 * Created by david.vittor on 31/08/16.
 */

public class FormSubmissionCountService {

	// counts expire every 1min
	private static final Map<String, Integer> countMap = new PassiveExpiringMap<String, Integer>(1 * 60 * 1000);

	private String getCountKey(String ipAddress, FormType type) {
		return ipAddress.toLowerCase()+"|"+type.name().toLowerCase();
	}

	public void incrementCount(String ipAddress, FormType type) {
		String key = getCountKey(ipAddress, type);
		Integer count = countMap.get(key);
		if (count==null) {
			count = 1;
		}
		countMap.put(key,++count);
	}

	public int getCount(String ipAddress, FormType type) {
		String key = getCountKey(ipAddress, type);
		return countMap.get(key);
	}

	public void allow(String ipAddress, FormType type) throws Exception {
		int value = getCount(ipAddress, type);
		if (value > type.getMaxAttempts()) {
			throw new Exception("Too many " + type.name().toLowerCase() + " attempts from ip=" + ipAddress);
		}
	}
}
