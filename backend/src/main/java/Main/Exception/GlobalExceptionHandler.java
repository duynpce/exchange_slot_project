package Main.Exception;


import Main.DTO.Common.ResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /// DB exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO<String>> handleDataIntegrityViolation(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("the data could be null, violate the constraint of database or already existed");
        response.setError("CONFLICT");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseDTO<String>> handleEmptyResultDataAccessException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("no such existed data found");
        response.setError("NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ResponseDTO<String>> handleTransactionSystemException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("can't commit data to the database please check out the data");
        response.setError("CONFLICT");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /// http exception
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpRequestMethodNotSupportedException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("no such method existed in this endpoint");
        response.setError("METHOD_NOT_ALLOWED");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMediaTypeNotSupportedException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("do not support this type of file, support json only");
        response.setError("UNSUPPORTED MEDIA TYPE");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMediaTypeNotAcceptableException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("can't return the file's type of the client expected");
        response.setError("NOT ACCEPTABLE");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMessageNotReadableException() {
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("cannot read the data from client");
        response.setError("NOT ACCEPTABLE");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

        //general exception
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDTO<String>> handleNullPointerException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("null pointer from the required data");
        response.setError("BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("wrong parameter to call a method");
        response.setError("BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseDTO<String>> handleMissingPathVariableException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("need a value from the url to run the method properly");
        response.setError("BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDTO<String>> handleExpiredJwtException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("access token expired");
        response.setError("UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /// custom exception

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseDTO<String>>handleBaseException(BaseException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError(e.getHttpStatus().getReasonPhrase());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    //config exception
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<ResponseDTO<String>>handleNoSuchAlgorithmException(NoSuchAlgorithmException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("no algorithms to hash");
        response.setError("NoSuchAlgorithms");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }

}
