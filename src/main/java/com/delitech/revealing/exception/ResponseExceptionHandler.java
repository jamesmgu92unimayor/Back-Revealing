package com.delitech.revealing.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.delitech.revealing.commons.Constants.TIME_ZONE;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private final LocalDateTime ldt=LocalDateTime.now(ZoneId.of(TIME_ZONE));
	private final Logger loggerMsg =Logger.getLogger(ResponseExceptionHandler.class.getName());

	@ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<StandardError> authenticationExceptionHandler(BadCredentialsException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error= new StandardError(ldt, HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), "Invalid Login Credentials", request.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<StandardError> authenticationExceptionHandler(AccessDeniedException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error= new StandardError(ldt, HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Access Denied", request.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
	}


	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<StandardError> modelNotFoundExceptionHandler(ConstraintViolationException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error= new StandardError(ldt, HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ModelNotFoundException.class)
	public final ResponseEntity<StandardError> modelNotFoundExceptionHandler(ModelNotFoundException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error= new StandardError(ldt, HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BusinessLogicException.class)
	public final ResponseEntity<StandardError> businessLogicExceptionHandler (BusinessLogicException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt,HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	public final ResponseEntity<StandardError> nullPointerExceptionHandler (NullPointerException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "NullPointerException", request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ArithmeticException.class)
	public final ResponseEntity<StandardError> arithmeticExceptionHandler (ArithmeticException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<StandardError>exceptionHandler (Exception ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IOException.class)
	public final ResponseEntity<StandardError>exceptionHandler (IOException ex, WebRequest request){
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"El Json no tiene la estructura adecuada",request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt,HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(),  "Tipo de información no soportado",request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StringBuilder errorResults= new StringBuilder();
		for (ObjectError objectError:ex.getBindingResult().getAllErrors()) {
			errorResults.append(objectError.getDefaultMessage()+"\n");
		}
		StandardError error = new StandardError(ldt, HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),errorResults.toString(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.METHOD_NOT_ALLOWED.value(),HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		loggerMsg.log(Level.SEVERE, () -> ex.getClass().getName() + " - " + ex.getMessage());
		StandardError error = new StandardError(ldt, HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), "No encontrado", request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
