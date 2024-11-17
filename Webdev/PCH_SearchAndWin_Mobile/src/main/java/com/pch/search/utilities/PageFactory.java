package com.pch.search.utilities;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PageFactory {

	protected static Map<String, BrowserDriverImpl> browserNDriverMap = null;

	public static Map<String, BrowserDriverImpl> getBrowserNDriverMap() {
		return browserNDriverMap;
	}

	protected static <T> T getPageInstance(Class<T> klass) throws Exception {
		T pageObjectInstance = null;
		try {
			if (browserNDriverMap == null)
				browserNDriverMap = new ConcurrentHashMap<String, BrowserDriverImpl>();
			pageObjectInstance = klass.newInstance();
			Class<?> klassObj = null;
			for (klassObj = pageObjectInstance.getClass(); klassObj.getName() != Action.class
					.getName(); klassObj = klassObj.getSuperclass())
				;

			Field[] fields = klassObj.getDeclaredFields();
			for (Field field : fields) {
				if (field.getType().equals(BrowserDriver.class)) {
					field.setAccessible(true);
					String browserKey = Environment.getBrowserType() + Thread.currentThread().getId();
					if (!browserNDriverMap.containsKey(browserKey)) {
						CustomLogger.log(String.format(
								"No instance of %s browser found, so loading %s class with new webdriver instance.",
								browserKey, klass.getName()));
						browserNDriverMap.put(browserKey, Browser.getBrowserInstance());
					}
					field.set(pageObjectInstance, browserNDriverMap.get(browserKey));

					break;
				}

			}
		} catch (InstantiationException e) {
			// e.printStackTrace();
			CustomLogger.logException(e);
			throw new Error(e.getMessage());
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			CustomLogger.logException(e);
			throw new Error(e.getMessage());
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			CustomLogger.logException(e);
			throw new Error(e.getMessage());
		}
		return pageObjectInstance;
	}

}
