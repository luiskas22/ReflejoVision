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
import javax.servlet.http.HttpServletResponse;

import com.pinguela.web.util.RouterUtils;
import com.pinguela.web.util.SessionManager;
import com.pinguela.web.util.Views;



public class AuthenticationFilter extends HttpFilter implements Filter {
    public AuthenticationFilter() {
        super();
    }
    
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (SessionManager.getAttribute(httpRequest, "usuario")==null) {
			RouterUtils.route(httpRequest, (HttpServletResponse) response, false, Views.LOGIN);
			
	} else {			
			// 	pass the request along the filter chain
			chain.doFilter(request, response);

		}
		

	}

	
	public void destroy() {
	}


}
