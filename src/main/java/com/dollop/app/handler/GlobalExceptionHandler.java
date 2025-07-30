package com.dollop.app.handler;

import java.nio.file.AccessDeniedException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.dollop.app.exceptions.AccessDenied;
import com.dollop.app.exceptions.BadRequestException;
import com.dollop.app.exceptions.ExpiredTokenException;
import com.dollop.app.exceptions.InvalideEmailFormateException;
import com.dollop.app.exceptions.InvalidePasswordException;
import com.dollop.app.exceptions.OtpExpiredException;
import com.dollop.app.exceptions.OtpNotVerifiedException;
import com.dollop.app.exceptions.ResourceNotFound;
import com.dollop.app.exceptions.UnAuthorizedUser;
import com.dollop.app.exceptions.UserAlreadyExist;
import com.dollop.app.response.ApiResponse;
import com.dollop.app.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
//	  @InitBinder
//	    public void initBinder(WebDataBinder binder) {
//	        System.out.println("=====> Global InitBinder called");
//	        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//	    }
	
	
	@ExceptionHandler(InvalideEmailFormateException.class)
	public ResponseEntity<ApiResponse> showInvalideEmailFormateException(InvalideEmailFormateException ex)
	{
		return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("BAD_REQUEST").build(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidePasswordException.class)
	public ResponseEntity<ApiResponse> showInvalidePasswordException(InvalidePasswordException ex)
	{
		return new ResponseEntity<ApiResponse>(ApiResponse.builder().message(ex.getMessage()).response("BAD_REQUEST").build(),HttpStatus.BAD_REQUEST);
     }
	
	
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ErrorResponse> showResourseNotFound(ResourceNotFound ex)
	{
		return new ResponseEntity<ErrorResponse>( ErrorResponse.builder().message(ex.getMessage()).details("BAD_REQUEST").status(400).build(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAlreadyExist.class)
	public ResponseEntity<ErrorResponse> showUserAlreadyExistException(UserAlreadyExist ex)
	{
		return new ResponseEntity<ErrorResponse>( ErrorResponse.builder().message(ex.getMessage()).details("BAD_REQUEST").status(400).build(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnAuthorizedUser.class)
	public ResponseEntity<ApiResponse> showUnAuthorizedUserException(UnAuthorizedUser ex)
	{
		return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("UNAUTHORIZED").build(),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccessDenied.class)
	public ResponseEntity<ApiResponse> showUserAccessDeniedException(AccessDenied ex)
	{
		return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("FORBIDDEN").build(),HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(OtpNotVerifiedException.class)
	public ResponseEntity<ApiResponse> showOtpNotVerifiedException(OtpNotVerifiedException ex)
	{
		return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("FORBIDDEN").build(),HttpStatus.FORBIDDEN);
     }

	@ExceptionHandler(OtpExpiredException.class)
	public ResponseEntity<ApiResponse> showOtpExpiredException(OtpExpiredException ex)
	{
		return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("FORBIDDEN").build(),HttpStatus.FORBIDDEN);
    }

	@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        
        Map<String, String> message = new HashMap<>();
        
        if (ex.getCause() instanceof JsonParseException) {
        	message.put("error", "Invalid JSON format");
        } else if (ex.getCause() instanceof JsonMappingException) {
        	message.put("error", "JSON structure is incorrect");
        } else {
        	message.put("error", "Invalid request body");
        }
 
        return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(message).response("BAD_REQUEST").build(), HttpStatus.BAD_REQUEST);
    }

	// Handle forbidden access (authorization failures)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> message = new HashMap<>();
        message.put("error", "Access Denied");
        message.put("message", "You do not have permission to perform this action");

        return new ResponseEntity<ApiResponse>(ApiResponse.builder().message(message).response("FORBIDDEN").build(), HttpStatus.FORBIDDEN);
       }
	
    // Handle unauthorized access (authentication failures)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> message = new HashMap<>();
        message.put("error", "Unauthorized access");
        message.put("message", ex.getMessage());

        return new ResponseEntity<ApiResponse>(ApiResponse.builder().message(message).response("UNAUTHORIZED").build(), HttpStatus.UNAUTHORIZED);
           }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleCustomBadRequest(BadRequestException ex) {
       
       return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("BAD_REQUEST").build(),HttpStatus.BAD_REQUEST);
        
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<ApiResponse>( ApiResponse.builder().message(ex.getMessage()).response("BAD_REQUEST").build(),HttpStatus.BAD_REQUEST);
    }
    
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> showMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		  Map<String, String> errors = new HashMap<>();

	        ex.getBindingResult().getAllErrors().forEach(error -> {
	            String fieldName = "message";
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);  // Custom message from DTO
	        });

	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	  }
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
	        MissingServletRequestParameterException ex) {
	    String paramName = ex.getParameterName();

	    ErrorResponse response = ErrorResponse.builder()
	            .status(400)
	            .message("Required request parameter '" + paramName + "' is missing.")
	            .details("parameter required")
	            .build();

	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
	    String path = ex.getRequestURL();

	    ErrorResponse response = ErrorResponse.builder()
	            .status(404)
	            .message("The requested URL '" + path + "' was not found on the server.")
	            .details("Correct Path Required")
	            .build();

	    return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
       
    	  ErrorResponse response = ErrorResponse.builder()
  	            .status(404)
  	            .message("Invalid role provided: ")
  	            .details(ex.getMessage())
  	            .build();
    	return new ResponseEntity<>(response  , HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException(ExpiredTokenException ex) {
       
    	  ErrorResponse response = ErrorResponse.builder()
  	            .status(404)
  	            .message("JWT Token has expired:=====> ")
  	            .details(ex.getMessage())
  	            .build();
    	return new ResponseEntity<>(response  , HttpStatus.BAD_REQUEST);
    }
    
    
}
