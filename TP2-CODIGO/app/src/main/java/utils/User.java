package utils;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String token;
    private String tokenRefresh;

    public User(String email, String token, String tokenRefresh) {
        this.email = email;
        this.token = token;
        this.tokenRefresh = tokenRefresh;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getTokenRefresh() {
        return tokenRefresh;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setTokenRefresh(String tokenRefresh){
        this.tokenRefresh = tokenRefresh;
    }
}
