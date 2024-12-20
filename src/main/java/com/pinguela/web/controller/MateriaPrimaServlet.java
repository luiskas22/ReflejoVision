package com.pinguela.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.reflejovision.PinguelaException;
import com.luis.reflejovision.model.MateriaPrimaCriteria;
import com.luis.reflejovision.model.MateriaPrimaDTO;
import com.luis.reflejovision.model.MateriaPrimaIdioma;
import com.luis.reflejovision.model.Results;
import com.luis.reflejovision.service.MateriaPrimaService;
import com.luis.reflejovision.service.impl.MateriaPrimaServiceImpl;
import com.pinguela.web.util.Actions;
import com.pinguela.web.util.Attributes;
import com.pinguela.web.util.ErrorCodes;
import com.pinguela.web.util.Errors;
import com.pinguela.web.util.LocaleUtils;
import com.pinguela.web.util.RouterUtils;
import com.pinguela.web.util.SessionManager;
import com.pinguela.web.util.ValidationUtils;
import com.pinguela.web.util.Views;

/**
 * Controller para peticiones sobre materias primas.
 */
@WebServlet("/private/MateriaPrimaServlet")
public class MateriaPrimaServlet extends HttpServlet {
	private Logger logger = LogManager.getLogger(MateriaPrimaServlet.class);

	private MateriaPrimaService materiaPrimaService = null;

	public MateriaPrimaServlet() {
		super();
		materiaPrimaService = new MateriaPrimaServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);

		String action = request.getParameter(Actions.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = true;
		String defaultLocale = LocaleUtils
				.validLocale(SessionManager.getAttribute(request, Attributes.LOCALE).toString()).toString();

		if (Actions.SEARCH.equalsIgnoreCase(action)) {
			MateriaPrimaCriteria criteria = new MateriaPrimaCriteria();

			criteria.setLocale(defaultLocale);
			// Validación del nombre
			String nombre = request.getParameter(Attributes.NOMBRE);
			if (nombre == null || nombre.isEmpty()) {
				criteria.setNombre(null);
			} else {
				ValidationUtils.validateNombreNotMandatory(nombre, errors);
				if (!errors.hasErrors()) {
					criteria.setNombre(nombre);
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

			// Validación del precioDesde
			String precioDesdeStr = request.getParameter(Attributes.PRECIODESDE);
			if (precioDesdeStr == null || precioDesdeStr.isEmpty()) {
				criteria.setPrecioDesde(null);
			} else {
				ValidationUtils.validateNumericFieldNotMandatory(precioDesdeStr, Attributes.PRECIODESDE,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				if (!errors.hasErrors()) {
					criteria.setPrecioDesde(Double.valueOf(precioDesdeStr));
				}
			}

			// Validación del precioHasta
			String precioHastaStr = request.getParameter(Attributes.PRECIOHASTA);
			if (precioHastaStr == null || precioHastaStr.isEmpty()) {
				criteria.setPrecioHasta(null);
			} else {
				ValidationUtils.validateNumericFieldNotMandatory(precioHastaStr, Attributes.PRECIOHASTA,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				if (!errors.hasErrors()) {
					criteria.setPrecioHasta(Double.valueOf(precioHastaStr));
				}
			}

			// Validación de unidadesDesde
			String unidadesDesdeStr = request.getParameter(Attributes.UNIDADESDESDE);
			if (unidadesDesdeStr == null || unidadesDesdeStr.isEmpty()) {
				criteria.setUnidadesDesde(null);
			} else {
				ValidationUtils.validateNumericFieldNotMandatory(unidadesDesdeStr, Attributes.UNIDADESDESDE,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				if (!errors.hasErrors()) {
					criteria.setUnidadesDesde(Integer.valueOf(unidadesDesdeStr));
				}
			}

			// Validación de unidadesHasta
			String unidadesHastaStr = request.getParameter(Attributes.UNIDADESHASTA);
			if (unidadesHastaStr == null || unidadesHastaStr.isEmpty()) {
				criteria.setUnidadesHasta(null);
			} else {
				ValidationUtils.validateNumericFieldNotMandatory(unidadesHastaStr, Attributes.UNIDADESHASTA,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				if (!errors.hasErrors()) {
					criteria.setUnidadesHasta(Integer.valueOf(unidadesHastaStr));
				}
			}

			// Si hay errores, envíalos a la vista
			if (errors.hasErrors()) {
				request.setAttribute("errors", errors);
				targetView = Views.MATERIAPRIMA_SEARCH;
				forwardOrRedirect = true;
			} else {
				// Ejecutar la búsqueda
				try {
					Results<MateriaPrimaDTO> resultados = materiaPrimaService.findBy(criteria, 1, 15);
					logger.info("Encontrados " + resultados.getTotal() + " materias primas");
					if (resultados.getPage().size() > 0) {
						request.setAttribute("resultados", resultados.getPage());
					}
				} catch (PinguelaException pe) {
					logger.error(pe.getMessage(), pe);
				}
				targetView = Views.MATERIAPRIMA_SEARCH;
				forwardOrRedirect = true;
			}
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {
			try {
				String idStr = request.getParameter(Attributes.ID);
				Long id = Long.valueOf(idStr);

				MateriaPrimaDTO mp = materiaPrimaService.findbyId(id, defaultLocale);
				if (mp == null) {
					throw new PinguelaException("Materia prima no encontrada con ID: " + id);
				}

				request.setAttribute("mp", mp);

				// Obtener las traducciones (puede ser una lista vacía)
				List<MateriaPrimaIdioma> traducciones = mp.getTraducciones();
				if (traducciones == null) {
					traducciones = new ArrayList<>(); // Aseguramos que no sea null
				}
				request.setAttribute("traducciones", traducciones);

				targetView = Views.MATERIAPRIMA_DETAIL;
				forwardOrRedirect = true;

			} catch (NumberFormatException nfe) {
				logger.error("Error de formato en el ID de la materia prima", nfe);
			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			} catch (Exception e) {
				logger.error("Error inesperado", e);
			}
		} else if (Actions.COMPRAR.equalsIgnoreCase(action)) {
			MateriaPrimaDTO mp = new MateriaPrimaDTO();
			List<MateriaPrimaIdioma> traducciones = new ArrayList<>();

			// Capture names for different languages
			String nombre_es = request.getParameter(Attributes.NOMBRE_ES);
			String nombre_en = request.getParameter(Attributes.NOMBRE_EN);
			String nombre_gl = request.getParameter(Attributes.NOMBRE_GL);

			ValidationUtils.validateNombre(nombre_es, errors);
			ValidationUtils.validateNombre(nombre_en, errors);
			ValidationUtils.validateNombre(nombre_gl, errors);

			String precioStr = request.getParameter(Attributes.PRECIO);
			ValidationUtils.validateNumericField(precioStr, Attributes.PRECIO, ErrorCodes.INVALID_NUMERIC_FORMAT,
					errors);

			String unidadesStr = request.getParameter(Attributes.UNIDADES);
			ValidationUtils.validateNumericField(unidadesStr, Attributes.UNIDADES, ErrorCodes.INVALID_NUMERIC_FORMAT,
					errors);

			String unidadMedidaStr = request.getParameter(Attributes.UNIDADMEDIDA);
			ValidationUtils.validateUnidadMedida(unidadMedidaStr, errors);

			if (!errors.hasErrors()) {
				mp.setNombre(nombre_en); 
				mp.setPrecio(Double.valueOf(precioStr));
				mp.setUnidades(Integer.valueOf(unidadesStr));
				mp.setIdUnidadMedida(Long.valueOf(unidadMedidaStr));

				// Add translations
				MateriaPrimaIdioma traduccionEs = new MateriaPrimaIdioma();
				traduccionEs.setLocale("es");
				traduccionEs.setNombre(nombre_es);
				traduccionEs.setIdMateriaPrima(mp.getId());

				MateriaPrimaIdioma traduccionEn = new MateriaPrimaIdioma();
				traduccionEn.setLocale("en");
				traduccionEn.setNombre(nombre_en);
				traduccionEn.setIdMateriaPrima(mp.getId());

				MateriaPrimaIdioma traduccionGl = new MateriaPrimaIdioma();
				traduccionGl.setLocale("gl_ES");
				traduccionGl.setNombre(nombre_gl);
				traduccionGl.setIdMateriaPrima(mp.getId());

				traducciones.add(traduccionEs);
				traducciones.add(traduccionEn);
				traducciones.add(traduccionGl);

				mp.setTraducciones(traducciones);

				try {
					Long id = materiaPrimaService.create(mp);
					logger.info("Materia Prima created with ID: " + id);
				} catch (PinguelaException pe) {
					logger.error(pe.getMessage(), pe);
				}
				targetView = Views.MATERIAPRIMA_SEARCH;
				forwardOrRedirect = false;
			} else {
				request.setAttribute(Attributes.ERRORS, errors);
				forwardOrRedirect = true;
				targetView = Views.MATERIAPRIMA_INSERT;
			}
		} else if (Actions.DELETE.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Attributes.ID);
				Long id = Long.valueOf(idStr);
				materiaPrimaService.delete(id);
				targetView = Views.MATERIAPRIMA_SEARCH;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		} else if (Actions.UPDATE.equalsIgnoreCase(action)) {
			MateriaPrimaDTO mp = new MateriaPrimaDTO();
			List<MateriaPrimaIdioma> traducciones = new ArrayList<>();

			try {
				// Validación del ID
				String idStr = request.getParameter(Attributes.ID);
				ValidationUtils.validateNumericField(idStr, Attributes.ID, ErrorCodes.INVALID_NUMERIC_FORMAT, errors);

				// Validación del nombre en diferentes idiomas
				String nombre_es = request.getParameter(Attributes.NOMBRE_ES);
				ValidationUtils.validateNombre(nombre_es, errors);

				String nombre_en = request.getParameter(Attributes.NOMBRE_EN);
				ValidationUtils.validateNombre(nombre_en, errors);

				String nombre_gl = request.getParameter(Attributes.NOMBRE_GL);
				ValidationUtils.validateNombre(nombre_gl, errors);

				// Validación del precio
				String precioStr = request.getParameter(Attributes.PRECIO);
				ValidationUtils.validatePrize(precioStr, errors);

				// Validación de las unidades
				String unidadesStr = request.getParameter(Attributes.UNIDADES);
				ValidationUtils.validateNumericField(unidadesStr, Attributes.UNIDADES,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);

				// Validación de la unidad de medida
				String unidadMedidaStr = request.getParameter(Attributes.UNIDADMEDIDA);
				ValidationUtils.validateUnidadMedida(unidadMedidaStr, errors);

				// Si no hay errores, procesar la actualización
				if (!errors.hasErrors()) {
					mp.setId(Long.parseLong(idStr));
					mp.setPrecio(Double.parseDouble(precioStr));
					mp.setUnidades(Integer.parseInt(unidadesStr));
					mp.setIdUnidadMedida(Long.parseLong(unidadMedidaStr));

					// Definir el nombre según el idioma por defecto
					if ("es".equals(defaultLocale)) {
						mp.setNombre(nombre_es);
					} else if ("en".equals(defaultLocale)) {
						mp.setNombre(nombre_en);
					} else if ("gl_ES".equals(defaultLocale)) {
						mp.setNombre(nombre_gl);
					}

					// Agregar traducciones usando setters
					MateriaPrimaIdioma traduccionEs = new MateriaPrimaIdioma();
					traduccionEs.setLocale("es");
					traduccionEs.setNombre(nombre_es);
					traduccionEs.setIdMateriaPrima(mp.getId());

					MateriaPrimaIdioma traduccionEn = new MateriaPrimaIdioma();
					traduccionEn.setLocale("en");
					traduccionEn.setNombre(nombre_en);
					traduccionEn.setIdMateriaPrima(mp.getId());

					MateriaPrimaIdioma traduccionGl = new MateriaPrimaIdioma();
					traduccionGl.setLocale("gl_ES");
					traduccionGl.setNombre(nombre_gl);
					traduccionGl.setIdMateriaPrima(mp.getId());

					traducciones.add(traduccionEs);
					traducciones.add(traduccionEn);
					traducciones.add(traduccionGl);

					mp.setTraducciones(traducciones);

					try {
						boolean updated = materiaPrimaService.update(mp);
						logger.info("Materia prima " + mp.getId() + (updated ? " actualizada." : " NO actualizada."));
					} catch (PinguelaException pe) {
						logger.error(pe.getMessage() + ": nombre = " + mp.getNombre(), pe);
					}

					targetView = Views.MATERIAPRIMA_SEARCH;
					forwardOrRedirect = true;
					request.setAttribute("mp", mp);
				} else {
					targetView = Views.MATERIAPRIMA_DETAIL;
					forwardOrRedirect = true;
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				request.setAttribute(Attributes.ERRORS, errors);
			}
		}

		RouterUtils.route(request, response, forwardOrRedirect, targetView);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
