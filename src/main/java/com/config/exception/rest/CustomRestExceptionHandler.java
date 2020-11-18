/**
 * 
 */
package com.config.exception.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.share.api_response.ApiErrorsView;
import com.share.api_response.ApiFieldError;
import com.share.api_response.ApiGlobalError;

/**
 * @author Sanjeev
 *
 */

@ControllerAdvice("com.admin")

//@ControllerAdvice(value = "my.chosen.package")
//@ControllerAdvice(basePackages = "my.chosen.package")
//@ControllerAdvice(assignableTypes = CountryMasterController.class)
//@RequestMapping(produces = "application/vnd.error+json")
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log=LoggerFactory.getLogger(CustomRestExceptionHandler.class);
	// 400 : This error will occur when the method arguments are not valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		log.info("Rhrough Advice: handleMethodArgumentNotValid");
		// Set the field level error with all its details
		List<ApiFieldError> apiFieldErrorList = new ArrayList<ApiFieldError>();
		ApiFieldError apiFieldError;
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			apiFieldError = new ApiFieldError(error.getField(), error.getCode(), error.getRejectedValue(),
					error.getDefaultMessage());
			apiFieldErrorList.add(apiFieldError);
		}

		// Set the global error
		List<ApiGlobalError> apiGlobalErrorList = new ArrayList<ApiGlobalError>();
		ApiGlobalError apiGlobalError;

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			apiGlobalError = new ApiGlobalError(error.getObjectName(), error.getDefaultMessage());
			apiGlobalErrorList.add(apiGlobalError);
		}

		ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				apiFieldErrorList, apiGlobalErrorList);

		return handleExceptionInternal(ex, apiErrorsView, headers, apiErrorsView.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		logger.info(ex.getClass().getName());
		log.info("Rhrough Advice: handleBindException");
		// Set the field level error with all its details
		List<ApiFieldError> apiFieldErrorList = new ArrayList<ApiFieldError>();
		ApiFieldError apiFieldError;
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			apiFieldError = new ApiFieldError(error.getField(), error.getCode(), error.getRejectedValue(),
					error.getDefaultMessage());
			apiFieldErrorList.add(apiFieldError);
		}

		// Set the global error
		List<ApiGlobalError> apiGlobalErrorList = new ArrayList<ApiGlobalError>();
		ApiGlobalError apiGlobalError;
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			apiGlobalError = new ApiGlobalError(error.getObjectName(), error.getDefaultMessage());
			apiGlobalErrorList.add(apiGlobalError);
		}

		ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
				apiFieldErrorList, apiGlobalErrorList);

		return handleExceptionInternal(ex, apiErrorsView, headers, apiErrorsView.getStatus(), request);

	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		log.info("Rhrough Advice: handleTypeMismatch");
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info("Rhrough Advice: handleMissingServletRequestPart");
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());

	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info("Rhrough Advice: handleMissingServletRequestParameter");
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getParameterName() + " parameter is missing";
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

	//

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		log.info("Rhrough Advice: handleMethodArgumentTypeMismatch");
		logger.info(ex.getClass().getName());
		log.info("handleMethodArgumentTypeMismatch");
		//
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		log.info("Rhrough Advice: handleConstraintViolation");
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info("Rhrough Advice: handleNoHandlerFoundException");
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());

	}

	// 405

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		log.info("Rhrough Advice: handleHttpRequestMethodNotSupported");
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		List<String> errors = new ArrayList<String>();
		errors.add(builder.toString());
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
				errors);

		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.info("Rhrough Advice: handleHttpMediaTypeNotSupported");
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		List<String> errors = new ArrayList<String>();
		errors.add(builder.substring(0, builder.length() - 2));

		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				ex.getLocalizedMessage(), errors);

		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

	// 500

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		log.info("Rhrough Advice: handleAll");
		logger.info(ex.getClass().getName());
		logger.error("error", ex);

		//
		List<String> errors = new ArrayList<String>();
		errors.add("error occurred");
		final ApiErrorsView apiErrorsView = new ApiErrorsView(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiErrorsView, new HttpHeaders(), apiErrorsView.getStatus());
	}

} 