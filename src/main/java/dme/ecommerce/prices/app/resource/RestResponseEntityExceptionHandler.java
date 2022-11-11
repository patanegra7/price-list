package dme.ecommerce.prices.app.resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dme.ecommerce.prices.app.exception.PriceNotFoundException;
import dme.ecommerce.prices.app.exception.RequiredArgumentsException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler
 */
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	private final static String PRICE_NOT_FOUND_MSG = "Price not found.";
	private final static String REQUIRED_PARAMS_NOT_FOUND_MSG = "Required params not found.";
	private final static String UNEXPECTED_API_MSG = "Unexpected API error.";
			
	
	@ExceptionHandler(value = {PriceNotFoundException.class})
	protected ResponseEntity<Object> handleEntityNotFoundException(final PriceNotFoundException ex, final WebRequest request) {
		RestResponseEntityExceptionHandler.log.error(RestResponseEntityExceptionHandler.PRICE_NOT_FOUND_MSG, ex);
		return this.handleExceptionInternal(ex, RestResponseEntityExceptionHandler.PRICE_NOT_FOUND_MSG, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = {RequiredArgumentsException.class})
	protected ResponseEntity<Object> handleEntityNotFoundException(final RequiredArgumentsException ex, final WebRequest request) {
		RestResponseEntityExceptionHandler.log.error(RestResponseEntityExceptionHandler.REQUIRED_PARAMS_NOT_FOUND_MSG, ex);
		return this.handleExceptionInternal(ex, RestResponseEntityExceptionHandler.REQUIRED_PARAMS_NOT_FOUND_MSG, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<Object> handleGenericException(final Exception ex, final WebRequest request) {
		RestResponseEntityExceptionHandler.log.error(RestResponseEntityExceptionHandler.UNEXPECTED_API_MSG, ex);
		return this.handleExceptionInternal(ex, RestResponseEntityExceptionHandler.UNEXPECTED_API_MSG, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	


}
