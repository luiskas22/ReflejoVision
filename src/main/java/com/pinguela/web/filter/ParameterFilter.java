package com.pinguela.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.pinguela.web.util.Parameters;
import com.pinguela.web.util.URLUtils;

/**
 * Servlet Filter implementation class ParameterFilter
 */
//@WebFilter("/ParameterFilter")
public class ParameterFilter extends HttpFilter implements Filter {
       
    public ParameterFilter() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String callbackURL = URLUtils.buildBaseURL((httpRequest));
		httpRequest.setAttribute(Parameters.CALLBACK_URL, callbackURL);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
