package Main.DTO;

public class LoginDTO {
    private String refreshToken;
    private String accessToken;
    private String message;

    public LoginDTO(){}
    public LoginDTO(String refreshToken, String accessToken, String message){
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.message = message;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
