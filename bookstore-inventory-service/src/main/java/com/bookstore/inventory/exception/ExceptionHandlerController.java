package com.bookstore.inventory.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bookstore.inventory.utils.Constants;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(HttpServletRequest req,
			MethodArgumentNotValidException ex) {
		final List<String> errors = new ArrayList<>();
		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			errors.add(fieldErrors.get(0).getField() + ": " + fieldErrors.get(0).getDefaultMessage());
		}
		if (errors.isEmpty()) {
			List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors();
			if (!objectErrors.isEmpty()) {
				errors.add(objectErrors.get(0).getDefaultMessage());
			}
		}
		String description = String.join(",", errors);
		return makeResponse(req, ex, ReturnCode.INTERNAL_ERROR_BAD_REQUEST, description);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiErrorResponse> handleAuthenticationException(HttpServletRequest req,
			AuthenticationException ex) {
		Throwable cause = ex;

		while (cause.getCause() != null) {
			cause = cause.getCause();
		}

		String message = cause.getMessage();
		ReturnCode returnCode;
		if (message.contains(Constants.RESPONSE_MSG_ACCESS_TOKEN_EXPIRED)) {
			message = Constants.RESPONSE_MSG_ACCESS_TOKEN_EXPIRED;
			returnCode = ReturnCode.ACCESS_TOKEN_EXPIRED;
		} else {
			message = Constants.RESPONSE_MSG_INVALID_TOKEN;
			returnCode = ReturnCode.INVALID_TOKEN;
		}

		return makeResponse(req, ex, returnCode, message);
	}

	@ExceptionHandler(GenericBusinessException.class)
	protected ResponseEntity<ApiErrorResponse> handleGenericNotFoundException(HttpServletRequest req,
			GenericBusinessException ex) {
		return makeResponse(req, ex, ex.getCode(), ex.getDescription());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiErrorResponse> handleRuntimeException(HttpServletRequest req, Exception ex) {
		return makeResponse(req, ex, ReturnCode.INTERNAL_ERROR, ex.toString());
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ApiErrorResponse> handleDataAccessException(HttpServletRequest req, DataAccessException ex) {
		return makeResponse(req, ex, ReturnCode.INTERNAL_ERROR_DB, Constants.RESPONSE_MSG_DB_EXCEPTION);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ApiErrorResponse> handleBindException(HttpServletRequest req, BindException ex) {
		final List<String> errors = new ArrayList<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			errors.add(fieldErrors.get(0).getField() + ": " + fieldErrors.get(0).getDefaultMessage());
		}
		if (errors.isEmpty()) {
			List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors();
			if (!objectErrors.isEmpty()) {
				errors.add(objectErrors.get(0).getDefaultMessage());
			}
		}
		String description = String.join(",", errors);
		return makeResponse(req, ex, ReturnCode.INTERNAL_ERROR_BAD_REQUEST, description);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleBadProvisioningDaoException(HttpServletRequest req,
			ConstraintViolationException ex) {
		return makeResponse(req, ex, ReturnCode.INTERNAL_ERROR_BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ApiErrorResponse> handleAccessDeniedException(HttpServletRequest req,
			AccessDeniedException ex) {
		Throwable cause = ex;
		while (cause.getCause() != null) {
			cause = cause.getCause();
		}
		return makeResponse(req, ex, ReturnCode.ACCESS_DENIED, cause.getMessage());
	}

	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<ApiErrorResponse> handleTypeMismatchException(HttpServletRequest req, Exception ex) {
		Throwable cause = ex;
		while (cause.getCause() != null) {
			cause = cause.getCause();
		}
		return makeResponse(req, ex, ReturnCode.INVALID_TYPE_PARAMETER_VALUE, cause.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiErrorResponse> handleMissingMandatoryServletRequestParameter(HttpServletRequest req,
			Exception ex) {
		return makeResponse(req, ex, ReturnCode.MISSING_QUERYSTRING_PARAMETER, ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpServletRequest req,
			Exception ex) {
		return makeResponse(req, ex, ReturnCode.MISSING_BODY, Constants.RESPONSE_MSG_INVALID_REQUEST_BODY);
	}

	private ResponseEntity<ApiErrorResponse> makeResponse(HttpServletRequest req, Exception ex, ReturnCode returnCode,
			String description) {
		ResponseEntity<ApiErrorResponse> responseBody = getErrorResponse(returnCode, description);
		req.setAttribute("isExceptionHandled", true);
		req.setAttribute("exceptionHandled", ex);
		req.setAttribute("errorResponseBody", responseBody.toString());
		return responseBody;
	}

	private ResponseEntity<ApiErrorResponse> getErrorResponse(ReturnCode returnCode, String description) {
		final ApiErrorResponse apiErrorResponse = new ApiErrorResponse(returnCode, description);
		apiErrorResponse.setMessage("Exception Occured");
		return new ResponseEntity<>(apiErrorResponse, returnCode.getHttpStatus());
	}
}