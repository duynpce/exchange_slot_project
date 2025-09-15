package Main.DTO;

public class ResponseDTO {
    private String message;
    private String error;
    private boolean processSuccess;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setProcessSuccess(boolean processSuccess) {
        this.processSuccess = processSuccess;
    }

    public boolean isProcessSuccess() {
        return processSuccess;
    }
}
