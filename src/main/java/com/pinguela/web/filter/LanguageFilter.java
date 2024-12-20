package com.pinguela.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.pinguela.web.util.Attributes;
import com.pinguela.web.util.CookieManager;
import com.pinguela.web.util.LocaleUtils;
import com.pinguela.web.util.SessionManager;




public class LanguageFilter extends HttpFilter implements Filter {
	
	private static Logger logger = LogManager.getLogger(LanguageFilter.class);
	

	public LanguageFilter() {
		super();
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
		
		Locale languageSelected = null;
		String cookie = null;
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
	
		cookie = CookieManager.getValue(httpRequest, Attributes.LOCALE);
		if(Strings.isEmpty(cookie) || cookie==null) {
			Enumeration<String> lenguajesHeader = httpRequest.getHeaders("Accept-Language");
			languageSelected = LocaleUtils.getFirstSupported(lenguajesHeader.nextElement().split(","));
		} else {
			languageSelected = LocaleUtils.validLocale(LocaleUtils.findSupported(cookie));
		} 
		SessionManager.setAttribute(httpRequest, Attributes.LOCALE, languageSelected);
		chain.doFilter(request, response);
	}
	
	public void destroy() {
		
	}
}