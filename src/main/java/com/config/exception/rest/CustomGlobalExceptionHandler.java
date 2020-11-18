/**
 * 
 */
package com.config.exception.rest;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;

/**
 * @author Sanjeev
 *
 */

@ControllerAdvice
@ResponseBody
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

	@Autowired
	public CustomGlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CustomErrorResponse processValidationError(MethodArgumentNotValidException ex, HttpServletRequest request) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		LocalDateTime timestamp = LocalDateTime.now();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String message = "Input parameters are invalid";
		String path = request.getRequestURI();
		List<FieldErrorDTO> fieldErrorsBox = new ArrayList<FieldErrorDTO>();
		List<FieldErrorDTO> fieldErrorsPrepared = processFieldErrors(fieldErrors, fieldErrorsBox);

		CustomErrorResponse error = new CustomErrorResponse(timestamp, status, message, fieldErrorsPrepared, path);

		return error;
	}

	private List<FieldErrorDTO> processFieldErrors(List<FieldError> fieldErrors, List<FieldErrorDTO> fieldErrorsBox) {

		for (FieldError fieldError : fieldErrors) {
			/// String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			fieldErrorsBox.add(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
			// dto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}
		return fieldErrorsBox;
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		// If the message was not found, return the most accurate field error code
		// instead.
		// You can remove this check if you prefer to get the default error message.
		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			String[] fieldErrorCodes = fieldError.getCodes();
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
	}

	// ResponseEntity<CustomErrorResponse>
	@ExceptionHandler(ConstraintViolationException.class)
	public final String handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request,
			HttpServletResponse response) {
		LocalDateTime timestamp = LocalDateTime.now();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String message = "Input parameters are invalid";

		List<FieldErrorDTO> fieldErrors = ex.getConstraintViolations().parallelStream()
				.map(e -> new FieldErrorDTO(e.getPropertyPath(), e.getMessage())).collect(Collectors.toList());
		String path = request.getRequestURI();
		CustomErrorResponse error = new CustomErrorResponse(timestamp, status, message, fieldErrors, path);

		// return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		String acceptHeader = request.getHeader("Accept");

		// If Accept header exists, check if it expects a response of type json,
		// otherwise just return text/html
		// Use apache commons lang3 to escape json values
		if (acceptHeader.contains("application/json")) {

			return new Gson().toJson(error);
		} else {
			// return as HTML
			response.setContentType("text/html");
			return new Gson().toJson(error);
		}

		//
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> handleNodataFoundException(NoDataFoundException ex, HttpServletRequest request) {
		LocalDateTime timestamp = LocalDateTime.now();
		HttpStatus status = HttpStatus.NOT_FOUND;
		String message = "Record not found.";
		List<String> details = null;
		String path = request.getRequestURI();
		CustomErrorResponse error = new CustomErrorResponse(timestamp, status, message, null, path);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}// CustomGlobalExceptionHandler
