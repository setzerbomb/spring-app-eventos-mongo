package com.eventos.app.controller.resources.common;

import com.eventos.app.common.components.MessageByLocaleService;
import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.common.exceptions.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Log4j2
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageByLocaleService messageByLocaleService;

	@Autowired
	public ResourceExceptionHandler(MessageByLocaleService messageByLocaleService) {
		this.messageByLocaleService = messageByLocaleService;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		log.info("ENTROU: ResponseEntity<Object> handleMethodArgumentNotValid");
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		StandardError apiError =
				new StandardError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(
				ex, apiError, headers, apiError.getStatus(), request);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<String> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		log.info("ENTROU: ResponseEntity<String> objectNotFound(ObjectNotFoundException e, HttpServletRequest request)");
		String message = messageByLocaleService.getMessage(e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataException.class)
	public ResponseEntity<String> dataException(DataException e, HttpServletRequest request) {
		log.info("ENTROU: ResponseEntity<String> dataException(DataException e, HttpServletRequest request) ");
		String message = messageByLocaleService.getMessage(e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<String> validationException(ValidationException e, HttpServletRequest request) {
		log.info("ENTROU: ResponseEntity<String> validationException(ValidationException e, HttpServletRequest request)");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, WebRequest request) {
		log.info("ENTROU:  ResponseEntity<Object> handleConstraintViolation");
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " +
					violation.getPropertyPath() + ": " + violation.getMessage());
		}

		StandardError apiError =
				new StandardError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		log.info("ENTROU: ResponseEntity<Object> handleMethodArgumentTypeMismatch");
		String error =
				ex.getName() + " should be of type " + ex.getRequiredType().getName();

		StandardError apiError =
				new StandardError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), Arrays.asList(error));
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.info("ENTROU: ResponseEntity<Object> handleMissingServletRequestParameter");
		String error = ex.getParameterName() + " parameter is missing";

		StandardError apiError =
				new StandardError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), Arrays.asList(error));
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}


}