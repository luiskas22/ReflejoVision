package com.pinguela.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.reflejovision.PinguelaException;
import com.luis.reflejovision.model.Results;
import com.luis.reflejovision.model.Usuario;
import com.luis.reflejovision.model.UsuarioCriteria;
import com.luis.reflejovision.service.FileService;
import com.luis.reflejovision.service.ServiceException;
import com.luis.reflejovision.service.UsuarioService;
import com.luis.reflejovision.service.impl.FileServiceImpl;
import com.luis.reflejovision.service.impl.UsuarioServiceImpl;
import com.pinguela.web.util.Actions;
import com.pinguela.web.util.Attributes;
import com.pinguela.web.util.ErrorCodes;
import com.pinguela.web.util.Errors;
import com.pinguela.web.util.RouterUtils;
import com.pinguela.web.util.SessionManager;
import com.pinguela.web.util.ValidationUtils;
import com.pinguela.web.util.Views;

/**
 * Controller para peticiones sobre Usuario.
 */
@WebServlet("/private/UsuarioServlet")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class PrivateUsuarioServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(PrivateUsuarioServlet.class);

	private UsuarioService usuarioService = null;
	private FileService fileService = null;

	public PrivateUsuarioServlet() {
		super();
		usuarioService = new UsuarioServiceImpl();
		fileService = new FileServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);

		String action = request.getParameter(Actions.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;

		if (Actions.SEARCH.equalsIgnoreCase(action)) {
			UsuarioCriteria criteria = new UsuarioCriteria();

			// Validación del username
			String username = request.getParameter(Attributes.USERNAME);
			if (username == null || username.isEmpty()) {
				criteria.setUsername(null);
			} else {
				ValidationUtils.validateUsernameNotMandatory(username, errors);
				if (!errors.hasErrors()) {
					criteria.setUsername(username.trim());
				}
			}

			// Validación del ID
			String idStr = request.getParameter(Attributes.ID);
			if (idStr == null || idStr.isEmpty()) {
				criteria.setId(null);
			} else {
				ValidationUtils.validateNumericFieldNotMandatory(idStr, Attributes.ID,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				if (!errors.hasErrors()) {
					criteria.setId(Long.valueOf(idStr));
				}
			}

			// Validación del nombre
			String nombre = request.getParameter(Attributes.NOMBRE);
			if (nombre == null || nombre.isEmpty()) {
				criteria.setNombre(null);
			} else {
				ValidationUtils.validateNombreNotMandatory(nombre, errors);
				if (!errors.hasErrors()) {
					criteria.setNombre(nombre.trim());
				}
			}

			// Validación del rol
			String rolStr = request.getParameter(Attributes.ROL);
			if (rolStr == null || rolStr.isEmpty()) {
				criteria.setRol(null);
			} else {
				ValidationUtils.validateNumericFieldNotMandatory(rolStr, Attributes.ROL,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				if (!errors.hasErrors()) {
					criteria.setRol(Long.valueOf(rolStr));
				}
			}

			// Validación del correo
			String correo = request.getParameter(Attributes.CORREO);
			if (correo == null || correo.isEmpty()) {
				criteria.setCorreo(null);
			} else {
				ValidationUtils.validateCorreoNotMandatory(correo, errors);
				if (!errors.hasErrors()) {
					criteria.setCorreo(correo.trim());
				}
			}

			// Si hay errores, envíalos a la vista
			if (errors.hasErrors()) {
				request.setAttribute("errors", errors);
				targetView = Views.USER_SEARCH;
				forwardOrRedirect = true;
			} else {
				// Ejecutar la búsqueda
				try {
					Results<Usuario> resultados = usuarioService.findBy(criteria, 1, 15);
					request.setAttribute(Attributes.RESULTADOS, resultados.getPage());
				} catch (PinguelaException pe) {
					logger.error(pe.getMessage(), pe);
				}
				targetView = Views.USER_SEARCH;
				forwardOrRedirect = true;
			}
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {
			try {
				String idStr = request.getParameter(Attributes.ID);
				Long id = Long.valueOf(idStr);

				// Buscar el usuario por ID
				Usuario u = usuarioService.findById(id);
				request.setAttribute(Attributes.USUARIO, u);

				targetView = Views.USER_DETAIL;
				forwardOrRedirect = true;
			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		} else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.removeAttribute(request, Attributes.USUARIO_AUTENTICADO);
			targetView = Views.HOME;
			forwardOrRedirect = false;

		} else if (Actions.INGRESAR.equalsIgnoreCase(action)) {
			try {
				Usuario usuario = new Usuario();

				// Validaciones de campos
				String nombre = request.getParameter(Attributes.NOMBRE);
				ValidationUtils.validateNombreUsuario(nombre, errors);

				String username = request.getParameter(Attributes.USERNAME);
				ValidationUtils.validateUsername(username, errors);

				String correo = request.getParameter(Attributes.CORREO);
				ValidationUtils.validateCorreo(correo, errors);

				String password = request.getParameter(Attributes.PASSWORD);
				ValidationUtils.validatePassword(password, errors);

				String rolStr = request.getParameter(Attributes.ROL);
				ValidationUtils.validateRol(rolStr, errors);

				// Si no hay errores, registrar al usuario
				if (!errors.hasErrors()) {
					usuario.setNombre(nombre);
					usuario.setUsername(username);
					usuario.setCorreo(correo);
					usuario.setContrasena(password);
					usuario.setRol(Long.valueOf(rolStr));

					try {
						Long idUsuario = usuarioService.registrar(usuario);

						Part filePart = request.getPart("file"); 
						if (filePart != null && filePart.getSize() > 0) {
							byte[] fileBytes = new byte[(int) filePart.getSize()];
							try (InputStream inputStream = filePart.getInputStream()) {
								inputStream.read(fileBytes); 
								fileService.uploadUserProfileImage(idUsuario, fileBytes); 
							} catch (IOException e) {
								logger.error("Error al leer el archivo", e);
							}
						}

						targetView = Views.LOGIN;
						forwardOrRedirect = false;

					} catch (Exception e) {
						logger.error("Error al registrar el usuario o al manejar la imagen de perfil", e);
						targetView = Views.USER_INSERT;
						forwardOrRedirect = true;
					}

				} else {
					request.setAttribute(Attributes.ERRORS, errors);
					forwardOrRedirect = true;
					targetView = Views.USER_INSERT;
				}
			} catch (Exception e) {
				logger.error("Error procesando la acción INGRESAR: ", e);
			}

		} else if (Actions.DELETE.equalsIgnoreCase(action)) {
			try {
				String idStr = request.getParameter(Attributes.ID);
				Long id = Long.valueOf(idStr);
				usuarioService.delete(id);
				targetView = Views.USER_SEARCH;
				forwardOrRedirect = true;
			} catch (PinguelaException | ServiceException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}else if (Actions.UPLOAD_IMAGE.equalsIgnoreCase(action)) {
		    try {
		        Long usuarioId = Long.valueOf(request.getParameter(Attributes.ID));
		        Part filePart = request.getPart("file");

		        if (filePart != null && filePart.getSize() > 0) {
		            byte[] fileBytes = new byte[(int) filePart.getSize()];
		            try (InputStream inputStream = filePart.getInputStream()) {
		                inputStream.read(fileBytes);
		            }

		            // Guardar la imagen usando el servicio
		            fileService.uploadUserProfileImage(usuarioId, fileBytes);
		            logger.info("Imagen de perfil subida correctamente para el usuario con ID: {}", usuarioId);
		        }

		        // Recuperar información actualizada del usuario
		        Usuario u = usuarioService.findById(usuarioId);
		        request.setAttribute(Attributes.USUARIO, u);

		        targetView = Views.USER_DETAIL;
		        forwardOrRedirect = true;

		    } catch (Exception e) {
		        logger.error("Error al subir la imagen de perfil", e);
		    }
		}


		RouterUtils.route(request, response, forwardOrRedirect, targetView);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
