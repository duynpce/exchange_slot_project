package Main.DTO;

public class ExchangeClassRequestDTO {
    private String error;
    private String message;
    private boolean exchangeSuccessfully;

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }


    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public boolean getExchangeSuccessfully() {
        return exchangeSuccessfully;
    }
    public void setExchangeSuccessfully(boolean exchangeSuccessfully) {
        this.exchangeSuccessfully = exchangeSuccessfully;
    }
}
