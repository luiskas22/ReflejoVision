package com.pinguela.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luis.reflejovision.service.FileService;
import com.luis.reflejovision.service.impl.FileServiceImpl;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {

	private FileService fileService;

	public ImageServlet() {
		super();
		fileService = new FileServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		FileInputStream fileInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		
		if ("profileImage".equalsIgnoreCase(action)) {

			String imageName = request.getParameter("imageName");

			String usuarioIdStr = request.getParameter("usuarioId");
			
			if (usuarioIdStr=="") {
				return;
			}
			
			Long usuarioId = Long.valueOf(usuarioIdStr);
			List<File> images = fileService.getProfileImageByUsuarioId(usuarioId);

			if (images.isEmpty()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "NO image found for the given usuarioId");
			} else {

				response.setContentType("image/jpg");
				response.setHeader("Content-Disposition", "inline; filename=hola.png");

				try (OutputStream out = response.getOutputStream()) {
					if (images.get(0).getName().equalsIgnoreCase(imageName)) {
						FileInputStream fis = new FileInputStream(images.get(0));
							
						byteArrayOutputStream = new ByteArrayOutputStream();

						int byteRead;
						while ((byteRead = fis.read()) != -1) {
							byteArrayOutputStream.write(byteRead);
						}

						byte[] byteArray = byteArrayOutputStream.toByteArray();

						out.write(byteArray);
						out.flush();
					}

				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
