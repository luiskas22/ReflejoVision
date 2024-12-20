package com.pinguela.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class URLUtils {
    public static String buildBaseURL(HttpServletRequest request) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(request.getRequestURI().substring(request.getContextPath().length()));
        
        Map<String, String[]> parametersMap = request.getParameterMap();
        Set<String> parameterNames = parametersMap.keySet();
        
        if (parameterNames.size()>0) urlBuilder.append("?");
        
        for (String parameterName : parameterNames) {
            if (!Parameters.CALLBACK_URL.equalsIgnoreCase(parameterName)) {
                
            	urlBuilder.append(parameterName).append("=").append(request.getParameter(parameterName)).append("&");
            }
        }
        String url = urlBuilder.toString();
        // url = encoding ...
        return url;
    }
} 