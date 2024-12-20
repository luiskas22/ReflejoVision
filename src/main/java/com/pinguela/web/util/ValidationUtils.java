package com.pinguela.web.util;

import org.apache.logging.log4j.util.Strings;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidationUtils {

	public static void validateNombreUsuario(String nombre, Errors errors) {
		if (nombre == null || Strings.isBlank(nombre)) {
			errors.addFieldError(Attributes.NOMBRE, ErrorCodes.MANDATORY_FIELD);
		} else {
			nombre = nombre.trim();
			if (nombre.length() < 2 || nombre.length() > 15 || !nombre.matches("^[a-zA-Z\\s]+$")) {
				errors.addFieldError(Attributes.NOMBRE, ErrorCodes.INVALID_NAME_FORMAT);
			}
		}
	}

	public static void validateUsername(String username, Errors errors) {
		if (username == null || Strings.isBlank(username)) {
			errors.addFieldError(Attributes.USERNAME, ErrorCodes.MANDATORY_FIELD);
		} else {
			username = username.trim();
			if (username.length() < 4 || username.length() > 15) {
				errors.addFieldError(Attributes.USERNAME, ErrorCodes.INVALID_USERNAME_LENGTH);
			}
		}
	}

	public static void validateUsernameNotMandatory(String username, Errors errors) {
		username = username.trim();
		if (username.length() < 4 || username.length() > 15) {
			errors.addFieldError(Attributes.USERNAME, ErrorCodes.INVALID_USERNAME_LENGTH);
		}
	}

	public static void validatePassword(String password, Errors errors) {
		if (password == null || Strings.isBlank(password)) {
			errors.addFieldError(Attributes.PASSWORD, ErrorCodes.MANDATORY_FIELD);
		} else {
			password = password.trim();
			if (password.length() < 6 || password.length() > 12) {
				errors.addFieldError(Attributes.PASSWORD, ErrorCodes.INVALID_PASSWORD_LENGTH);
			}
		}
	}

	public static void validateRol(String rolStr, Errors errors) {
		if (rolStr == null || Strings.isBlank(rolStr)) {
			errors.addFieldError(Attributes.ROL, ErrorCodes.MANDATORY_FIELD);
		} else {
			try {
				Long rol = Long.valueOf(rolStr);
				if (rol != 1 && rol != 2) {
					errors.addFieldError(Attributes.ROL, ErrorCodes.UNKNOWN_ROLE);
				}
			} catch (NumberFormatException nfe) {
				errors.addFieldError(Attributes.ROL, ErrorCodes.UNKNOWN_ROLE);
			}
		}
	}

	public static void validateRolNotMandatory(String rolStr, Errors errors) {
		try {
			Long rol = Long.valueOf(rolStr);
			if (rol != 1 && rol != 2) {
				errors.addFieldError(Attributes.ROL, ErrorCodes.UNKNOWN_ROLE);
			}
		} catch (NumberFormatException nfe) {
			errors.addFieldError(Attributes.ROL, ErrorCodes.UNKNOWN_ROLE);
		}
	}

	public static void validateCorreo(String correo, Errors errors) {
		if (correo == null || Strings.isBlank(correo)) {
			errors.addFieldError(Attributes.CORREO, ErrorCodes.MANDATORY_FIELD);
		} else {
			EmailValidator validator = EmailValidator.getInstance();
			if (!validator.isValid(correo)) {
				errors.addFieldError(Attributes.CORREO, ErrorCodes.INVALID_EMAIL);
			}
		}
	}

	public static void validateCorreoNotMandatory(String correo, Errors errors) {
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(correo)) {
			errors.addFieldError(Attributes.CORREO, ErrorCodes.INVALID_EMAIL);
		}

	}

//	public static void validateId(String idStr, Errors errors) {
//		if (idStr == null || Strings.isBlank(idStr)) {
//			errors.addFieldError(Attributes.ID, ErrorCodes.MANDATORY_FIELD);
//		} else {
//			try {
//				Long.valueOf(idStr);
//			} catch (NumberFormatException nfe) {
//				errors.addFieldError(Attributes.ID, ErrorCodes.INVALID_ID);
//			}
//		}
//	}

	public static void validateNombre(String nombre, Errors errors) {
		if (nombre == null || Strings.isBlank(nombre)) {
			errors.addFieldError(Attributes.NOMBRE, ErrorCodes.MANDATORY_FIELD);
		} else {
			nombre = nombre.trim();
			if (nombre.length() < 2 || nombre.length() > 30) {
				errors.addFieldError(Attributes.NOMBRE, ErrorCodes.INVALID_NAME_LENGTH);
			}
			if (!nombre.matches("^[a-zA-Z0-9\\s]*$")) {
				errors.addFieldError(Attributes.NOMBRE, ErrorCodes.INVALID_NAME_CHARACTERS);
			}
		}
	}

	public static void validateNombreNotMandatory(String nombre, Errors errors) {

		nombre = nombre.trim();
		if (nombre.length() < 2 || nombre.length() > 30) {
			errors.addFieldError(Attributes.NOMBRE, ErrorCodes.INVALID_NAME_LENGTH);
		}
		if (!nombre.matches("^[a-zA-Z0-9\\s]*$")) {
			errors.addFieldError(Attributes.NOMBRE, ErrorCodes.INVALID_NAME_CHARACTERS);
		}

	}

	public static void validateUnidadMedida(String unidadMedidaStr, Errors errors) {
		if (unidadMedidaStr == null || Strings.isBlank(unidadMedidaStr)) {
			errors.addFieldError(Attributes.UNIDADMEDIDA, ErrorCodes.MANDATORY_FIELD);
		} else {
			try {
				Long unidadMedida = Long.valueOf(unidadMedidaStr);
				if (unidadMedida != 1 && unidadMedida != 2 && unidadMedida != 3) {
					errors.addFieldError(Attributes.UNIDADMEDIDA, ErrorCodes.UNKNOWN_UNIDADMEDIDA);
				}
			} catch (NumberFormatException nfe) {
				errors.addFieldError(Attributes.UNIDADMEDIDA, ErrorCodes.UNKNOWN_UNIDADMEDIDA);
			}
		}
	}

	public static void validatePrize(String precioStr, Errors errors) {
		if (precioStr == null || Strings.isBlank(precioStr)) {
			errors.addFieldError(Attributes.PRECIO, ErrorCodes.MANDATORY_FIELD);
		} else {
			try {
				Double.valueOf(precioStr);
			} catch (NumberFormatException nfe) {
				errors.addFieldError(Attributes.PRECIO, ErrorCodes.INVALID_NUMERIC_FORMAT);
			}
		}
	}

	public static void validateNumericField(String valueStr, String attribute, String errorCode, Errors errors) {
		if (valueStr == null || Strings.isBlank(valueStr)) {
			errors.addFieldError(attribute, ErrorCodes.MANDATORY_FIELD);
		} else {
			try {
				Long.valueOf(valueStr);
			} catch (NumberFormatException nfe) {
				errors.addFieldError(attribute, errorCode);
			}
		}
	}

	public static void validateNumericFieldNotMandatory(String valueStr, String attribute, String errorCode,
			Errors errors) {
		try {
			Long.valueOf(valueStr);
		} catch (NumberFormatException nfe) {
			errors.addFieldError(attribute, errorCode);
		}
	}

}
