package Main.DTO;

public class ResponseDTO<T>{
    private boolean processSuccess;
    private String error;
    private String message;
    private T data;
    private int httpStatus;

    public ResponseDTO() {
    }

    public ResponseDTO(boolean processSuccess, String error, String message, T data) {
        this.processSuccess = processSuccess;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public boolean isProcessSuccess() {
        return processSuccess;
    }

    public void setProcessSuccess(boolean processSuccess) {
        this.processSuccess = processSuccess;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}