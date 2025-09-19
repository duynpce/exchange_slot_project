package Main.Exception;

import Main.DTO.ErrorResponse;

import Main.DTO.ResponseDTO;
import jakarta.transaction.TransactionalException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    /// DB exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO<String>> handleDataIntegrityViolation(DataIntegrityViolationException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("violated constraint or existed data");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseDTO<String>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("not found data");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ResponseDTO<String>> handleTransactionSystemException(TransactionSystemException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("can't commit data");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /// http exception
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("Wrong method called");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("unsupported file's type");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("can't return the type as client return");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    //general exception
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDTO<String>> handleNullPointerException(NullPointerException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError(" having a Null pointer");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("wrong parameter");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseDTO<String>> handleMissingPathVariableException(MissingPathVariableException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError("lack of Path variable (variable from the url)");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}
