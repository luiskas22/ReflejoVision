package com.pinguela.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.reflejovision.PinguelaException;
import com.luis.reflejovision.dao.DataException;
import com.luis.reflejovision.model.ConsumoDTO;
import com.luis.reflejovision.model.Producto;
import com.luis.reflejovision.model.ProductoCriteria;
import com.luis.reflejovision.model.Results;
import com.luis.reflejovision.service.ConsumoService;
import com.luis.reflejovision.service.MateriaPrimaService;
import com.luis.reflejovision.service.ProductoService;
import com.luis.reflejovision.service.StockException;
import com.luis.reflejovision.service.impl.ConsumoServiceImpl;
import com.luis.reflejovision.service.impl.MateriaPrimaServiceImpl;
import com.luis.reflejovision.service.impl.ProductoServiceImpl;
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
 * Servlet implementation class ProductoServlet
 */
@WebServlet("/private/ProductoServlet")
public class ProductoServlet extends HttpServlet {
	private Logger logger = LogManager.getLogger(ProductoServlet.class);

	private ProductoService productoService = null;
	private ConsumoService consumoService = null;
	private MateriaPrimaService materiaPrimaService = null;

	public ProductoServlet() {
		super();
		materiaPrimaService = new MateriaPrimaServiceImpl();
		productoService = new ProductoServiceImpl();
		consumoService = new ConsumoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);

		String action = request.getParameter(Actions.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;
		String defaultLocale = LocaleUtils
				.validLocale(SessionManager.getAttribute(request, Attributes.LOCALE).toString()).toString();

		if (Actions.SEARCH.equalsIgnoreCase(action)) {
			ProductoCriteria criteria = new ProductoCriteria();

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
				targetView = Views.PRODUCTO_SEARCH;
				forwardOrRedirect = true;
			} else {
				// Ejecutar la búsqueda
				try {
					Results<Producto> resultados = productoService.findBy(criteria, 1, 20);
					logger.info("Encontrados " + resultados.getTotal() + " productos");
					if (resultados.getPage().size() > 0) {
						request.setAttribute(Attributes.RESULTADOS, resultados.getPage());
					}
				} catch (PinguelaException pe) {
					logger.error(pe.getMessage(), pe);
				}
				targetView = Views.PRODUCTO_SEARCH;
				forwardOrRedirect = true;
			}
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Attributes.ID);

				Long id = Long.valueOf(idStr);
				Producto p = productoService.findById(id);
				request.setAttribute(Attributes.PRODUCTO, p);
				SessionManager.setAttribute(request, Attributes.CONSUMOS, p.getConsumos());

				targetView = Views.PRODUCTO_DETAIL;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}

		} else if (Actions.GENERAR.equalsIgnoreCase(action)) {

			Producto p = new Producto();
			ConsumoDTO c = null;

			String nombre = request.getParameter(Attributes.NOMBRE);
			ValidationUtils.validateNombre(nombre, errors);
			String precioStr = request.getParameter(Attributes.PRECIO);
			ValidationUtils.validateNumericField(precioStr, Attributes.PRECIO, ErrorCodes.INVALID_NUMERIC_FORMAT,
					errors);

			// Primer consumo
			String idStr1 = request.getParameter(Attributes.ID_MATERIAPRIMA1);
			String unidadesMpStr1 = request.getParameter(Attributes.UNIDADES1);

			if (idStr1 != null && !idStr1.isEmpty() && unidadesMpStr1 != null && !unidadesMpStr1.isEmpty()) {
				Long idMp1 = Long.valueOf(idStr1);
				Double unidadesMp1 = Double.valueOf(unidadesMpStr1);

				c = new ConsumoDTO();
				c.setIdMateriaPrima(idMp1);
				c.setUnidades(unidadesMp1);
				p.getConsumos().add(c);
			}

			// Segundo consumo
			String idStr2 = request.getParameter(Attributes.ID_MATERIAPRIMA2);
			String unidadesMpStr2 = request.getParameter(Attributes.UNIDADES2);

			if (idStr2 != null && !idStr2.isEmpty() && unidadesMpStr2 != null && !unidadesMpStr2.isEmpty()) {
				Long idMp2 = Long.valueOf(idStr2);
				Double unidadesMp2 = Double.valueOf(unidadesMpStr2);

				c = new ConsumoDTO();
				c.setIdMateriaPrima(idMp2);
				c.setUnidades(unidadesMp2);
				p.getConsumos().add(c);
			}

			// Tercer consumo
			String idStr3 = request.getParameter(Attributes.ID_MATERIAPRIMA3);
			String unidadesMpStr3 = request.getParameter(Attributes.UNIDADES3);

			if (idStr3 != null && !idStr3.isEmpty() && unidadesMpStr3 != null && !unidadesMpStr3.isEmpty()) {
				Long idMp3 = Long.valueOf(idStr3);
				Double unidadesMp3 = Double.valueOf(unidadesMpStr3);

				c = new ConsumoDTO();
				c.setIdMateriaPrima(idMp3);
				c.setUnidades(unidadesMp3);
				p.getConsumos().add(c);
			}
			if (!errors.hasErrors()) {
				p.setNombre(nombre);
				p.setPrecio(Double.valueOf(precioStr));
				p.setUnidades(0);
				try {
					Long id = productoService.create(p);

				} catch (PinguelaException pe) {
					logger.error(pe.getMessage() + ": nombre = " + nombre, pe);
				}
				targetView = Views.PRODUCTO_CREAR;
			} else {
				request.setAttribute(Attributes.ERRORS, errors);
				forwardOrRedirect = true;
				targetView = Views.PRODUCTO_GENERAR;
			}

		} else if (Actions.CREAR.equalsIgnoreCase(action)) {
			try {
				Producto producto = new Producto();
				Boolean autoActualizacionMateriasPrimas = true;

				String nombre = request.getParameter(Attributes.NOMBRE);
				ValidationUtils.validateNombre(nombre, errors);

				String idStr = request.getParameter(Attributes.ID);
				ValidationUtils.validateNumericField(idStr, Attributes.ID, ErrorCodes.INVALID_NUMERIC_FORMAT, errors);

				String unidadesStr = request.getParameter(Attributes.UNIDADES);
				ValidationUtils.validateNumericField(unidadesStr, Attributes.UNIDADES,
						ErrorCodes.INVALID_NUMERIC_FORMAT, errors);
				
				if (!errors.hasErrors()) {
					Long id = Long.valueOf(idStr); 
					Integer unidades = Integer.valueOf(unidadesStr); 

					try {
						productoService.updateStock(id, unidades, autoActualizacionMateriasPrimas, defaultLocale);
					} catch (PinguelaException pe) {
						logger.error(pe.getMessage(), pe);

					}

					targetView = Views.PRODUCTO_SEARCH;
					forwardOrRedirect = true;
				} else {
					targetView = Views.PRODUCTO_CREAR;
					forwardOrRedirect = true;
				}

			} catch (Exception e) {
				logger.error("Unexpected error occurred", e);
				forwardOrRedirect = true;
			}
		} else if (Actions.DELETE.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Attributes.ID);
				Long id = Long.valueOf(idStr);
				productoService.delete(id);
				targetView = Views.PRODUCTO_SEARCH;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		} else if (Actions.DELETE_CONSUMO.equalsIgnoreCase(action)) {

		    try {
		        // Obtener parámetros de la solicitud
		        String idProductoStr = request.getParameter("idProducto");
		        String idMateriaPrimaStr = request.getParameter("idMateriaPrima");

		        Long idProducto = Long.valueOf(idProductoStr);
		        Long idMateriaPrima = Long.valueOf(idMateriaPrimaStr);

		        consumoService.delete(idProducto, idMateriaPrima);
		        // Obtener el producto actualizado
		        Producto producto = productoService.findById(idProducto);
		        List<ConsumoDTO> consumos = consumoService.findByProducto(idProducto);

		        // Asegurarse de que la información del producto y consumos se pasen a la vista
		        request.setAttribute("p", producto); 
		        request.setAttribute("consumos", consumos); 
		        SessionManager.setAttribute(request, "idProducto", idProducto);

		        targetView = Views.PRODUCTO_DETAIL;
		        forwardOrRedirect = true;

		    } catch (PinguelaException pe) {
		        logger.error("Error al eliminar consumo", pe);
		        
		    }
		}


		RouterUtils.route(request, response, forwardOrRedirect, targetView);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
