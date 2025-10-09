package Main.Exception;


import Main.DTO.ResponseDTO;
import jakarta.transaction.TransactionalException;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;


@ControllerAdvice
public class GlobalExceptionHandler {

    /// DB exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO<String>> handleDataIntegrityViolation(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("the data could be null, violate the constraint of database or already existed");
        response.setError("CONFLICT");
        response.setHttpStatus(HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseDTO<String>> handleEmptyResultDataAccessException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("no such existed data found");
        response.setError("NOT_FOUND");
        response.setHttpStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ResponseDTO<String>> handleTransactionSystemException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("can't commit data to the database please check out the data");
        response.setError("CONFLICT");
        response.setHttpStatus(HttpStatus.CONFLICT.value());
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
        response.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMediaTypeNotSupportedException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("do not support this type of file, support json only");
        response.setError("UNSUPPORTED MEDIA TYPE");
        response.setHttpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMediaTypeNotAcceptableException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("can't return the file's type of the client expected");
        response.setError("NOT ACCEPTABLE");
        response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.value());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMessageNotReadableException() {
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("cannot read the data from client");
        response.setError("NOT ACCEPTABLE");
        response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.value());
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
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("wrong parameter to call a method");
        response.setError("BAD REQUEST");
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseDTO<String>> handleMissingPathVariableException(){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage("need a value from the url to run the method properly");
        response.setError("BAD REQUEST");
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /// custom exception
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ResponseDTO<String>>handleAccountException(AccountException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError(e.getHttpStatus().getReasonPhrase());
        response.setHttpStatus(e.getHttpStatus().value());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(ExchangeClassRequestException.class)
    public ResponseEntity<ResponseDTO<String>>handleExchangeClassRequestException(ExchangeClassRequestException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError(e.getHttpStatus().getReasonPhrase());
        response.setHttpStatus(e.getHttpStatus().value());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(ExchangeSlotRequestException.class)
    public ResponseEntity<ResponseDTO<String>>handleExchangeSlotRequestException(ExchangeSlotRequestException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError(e.getHttpStatus().getReasonPhrase());
        response.setHttpStatus(e.getHttpStatus().value());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(URLException.class)
    public ResponseEntity<ResponseDTO<String>>handleURLException(URLException e){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setData(null);
        response.setProcessSuccess(false);
        response.setMessage(e.getMessage());
        response.setError(e.getHttpStatus().getReasonPhrase());
        response.setHttpStatus(e.getHttpStatus().value());
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
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }

}
