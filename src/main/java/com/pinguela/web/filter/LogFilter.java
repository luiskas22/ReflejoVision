package com.pinguela.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Filtro de autenticación.
 */
public class LogFilter extends HttpFilter implements Filter {
	private static Logger logger = LogManager.getLogger(LogFilter.class);

	public LogFilter() {
		super();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StringBuffer url = new StringBuffer();
		 url.append(httpRequest.getScheme());
		// url.append("://");
		// url.append(httpRequest.getLocalName());
		// url.append(":");
		// url.append(httpRequest.getLocalPort());
		// url.append(httpRequest.getContextPath());
		url.append(httpRequest.getRequestURL());
		logger.info("--> Request " + url + " from " + httpRequest.getRemoteHost());

//		Map<String, String[]> parameters = httpRequest.getParameterMap();
		// TODO: Logger parametros...
		logger.info("--> Request" + url + " from " + httpRequest.getRemoteHost());
		if (httpRequest.getCookies() != null) {
			for (Cookie c : httpRequest.getCookies()) {
				logger.info("Cookie [" + c.getPath() + "] " + c.getName() + "=" + c.getValue() + " (ttl:"
						+ c.getMaxAge() + ")");
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);

		logger.info("Request " + url + " from " + httpRequest.getRemoteAddr() + " --> ");
	}

	public void destroy() {
	}

}
