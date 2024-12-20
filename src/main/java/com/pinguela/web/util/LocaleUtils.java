package com.pinguela.web.util;

import java.util.Locale;

import com.luis.reflejovision.conf.ConfigurationParametersManager;

public class LocaleUtils {

	private static final String DEFAULTLOCALE = ConfigurationParametersManager.getParameterValue("locale.default");
	private static final String[] SUPPORTEDLOCALE = ConfigurationParametersManager.getParameterValue("locale.supported").split(","); 

	public static Locale getFirstSupported(String[] headerLanguage) {
		if(headerLanguage == null || headerLanguage.length == 0) {
			return validLocale(DEFAULTLOCALE);
		}
		
		String localeSelected = getFirstHeader(headerLanguage);
		
		Locale locale = validLocale(localeSelected);
		
		return locale;
	}

	public static String getFirstHeader(String[] headerLanguage) {
		for(String len: headerLanguage) {
			String localeStr = len.replace("-", "_").split(";")[0];
			return findSupported(localeStr);
		}
		return DEFAULTLOCALE;
	}
	
	public static String findSupported(String localeHeader) {
		String[] part = localeHeader.split("_");
		for(String supported: SUPPORTEDLOCALE){
			if(localeHeader.equalsIgnoreCase(supported) || part[0].equals(supported)) {
				return supported;
			}
		}
		return DEFAULTLOCALE;
	}
	
	public static Locale validLocale(String localeSelected) {
		String[] localeFinal = localeSelected.split("_");
		return localeFinal.length > 1? new Locale(localeFinal[0],localeFinal[1]) : new Locale(localeFinal[0]);
	}
}