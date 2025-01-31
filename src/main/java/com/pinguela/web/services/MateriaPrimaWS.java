package com.pinguela.web.services;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.luis.reflejovision.PinguelaException;
import com.luis.reflejovision.model.MateriaPrimaCriteria;
import com.luis.reflejovision.model.MateriaPrimaDTO;
import com.luis.reflejovision.model.Results;
import com.luis.reflejovision.service.MateriaPrimaService;
import com.luis.reflejovision.service.impl.MateriaPrimaServiceImpl;

/**
 * Servlet implementation class MateriaPrimaWS
 */
@WebServlet("/materiaprima")
public class MateriaPrimaWS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Gson gson = new Gson();
	private MateriaPrimaService materiaPrimaService = null;
	private Logger logger = LogManager.getLogger(MateriaPrimaWS.class);

	public MateriaPrimaWS() {
		super();
		materiaPrimaService = new MateriaPrimaServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String methodStr = request.getParameter("method");
			String nombreStr = request.getParameter("nombre");

			MateriaPrimaCriteria criteria = new MateriaPrimaCriteria();
			criteria.setNombre(nombreStr);
			criteria.setLocale("es");
			Results<MateriaPrimaDTO> results = materiaPrimaService.findBy(criteria, 1, 10);

			String resultsJSON = gson.toJson(results);

			
			response.setContentType("application/json");
			response.getOutputStream().write(resultsJSON.getBytes());
			response.getOutputStream().flush();
			
		} catch (PinguelaException pe) {
			logger.error(pe.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
