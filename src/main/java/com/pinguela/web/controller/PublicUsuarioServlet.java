package com.pinguela.web.controller;

import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.reflejovision.PinguelaException;
import com.luis.reflejovision.model.Usuario;
import com.luis.reflejovision.service.UsuarioService;
import com.luis.reflejovision.service.impl.UsuarioServiceImpl;
import com.pinguela.web.util.Actions;
import com.pinguela.web.util.Attributes;
import com.pinguela.web.util.CookieManager;
import com.pinguela.web.util.ErrorCodes;
import com.pinguela.web.util.Errors;
import com.pinguela.web.util.LocaleUtils;
import com.pinguela.web.util.Parameters;
import com.pinguela.web.util.RouterUtils;
import com.pinguela.web.util.SessionManager;
import com.pinguela.web.util.ValidationUtils;
import com.pinguela.web.util.Views;

@WebServlet("/public/UsuarioServlet")
public class PublicUsuarioServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(PublicUsuarioServlet.class);

    private UsuarioService usuarioService = null;

    public PublicUsuarioServlet() {
        super();
        usuarioService = new UsuarioServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(Actions.ACTION);
        String targetView = null;
        boolean forwardOrRedirect = false;
        
        Errors errors = new Errors();
        request.setAttribute(Attributes.ERRORS, errors);
        
//		Locale locale = LocaleUtils.validLocale(SessionManager.getAttribute(request, Attributes.LOCALE).toString()); 

        if (Actions.LOGIN.equalsIgnoreCase(action)) {
            try {
                String username = request.getParameter(Attributes.USERNAME);
                String password = request.getParameter(Attributes.PASSWORD);

                if (!errors.hasErrors()) {
                    Usuario usuario = usuarioService.autenticar(username, password);

                    if (usuario != null) {
                        SessionManager.setAttribute(request, Attributes.USUARIO_AUTENTICADO, usuario);
                        String rememberMeStr = request.getParameter(Attributes.REMEMBERUSER);
                        Boolean rememberMe = rememberMeStr != null;

                        if (rememberMe) {
                            CookieManager.setCookie(response, request.getContextPath(), Attributes.USER, usuario.getUsername(),
                                    30 * 24 * 60 * 60);
                        } else {
                            CookieManager.removeCookie(response, request.getContextPath(), Attributes.USER);
                        }

                        targetView = Views.HOME;
                        forwardOrRedirect = false;
                    } else {
                        logger.warn("Authentication failed: user = {} ", username);
                        errors.addGlobal(ErrorCodes.AUTHENTICATION_FAILED);
                        forwardOrRedirect = true;
                        targetView = Views.LOGIN;
                    }
                } else {
                    forwardOrRedirect = false;
                    targetView = Views.LOGIN;
                }
            } catch (Exception e) {
                logger.error("Error processing login: ", e);
            }
//        } else if (Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) {
//            String[] newLocaleStr = request.getParameter(Attributes.LOCALE).split("_");
//
//            // El locale que nos piden está soportado???
//            // Si lo está, y si no el de por defecto
//            Locale newLocale = new Locale("en"); // ConfigurationParametrs.... dfeult;
//
//            if (newLocaleStr.length == 1) {
//                newLocale = new Locale(newLocaleStr[0]);
//            } else if (newLocaleStr.length == 2) {
//                newLocale = new Locale(newLocaleStr[0], newLocaleStr[1]);
//            }
//
//            SessionManager.setAttribute(request, "locale", newLocale);
//            // Se guarda en una cookie
//            // CookieManager.setCookie...
//            targetView = Views.HOME;
//            forwardOrRedirect = false;
//        }
        }else if (Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) {
    			Locale localeSeleted = LocaleUtils.validLocale(LocaleUtils.findSupported(request.getParameter(Parameters.LOCALE))) ;
    			logger.info("localeSeleted: " + localeSeleted);
    			
    			SessionManager.setAttribute(request, Attributes.LOCALE, localeSeleted);
    			CookieManager.setCookie(response, request.getContextPath(), Attributes.LOCALE, localeSeleted.toString(), 30*24*60*60);
    			
    			targetView = request.getParameter(Parameters.CALLBACK_URL);			
    			forwardOrRedirect = false;
    		}

        RouterUtils.route(request, response, forwardOrRedirect, targetView);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

