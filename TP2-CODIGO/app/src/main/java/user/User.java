package user;

public class User {
    private String email;
    private String token;
    private String tokenRefresh;

    public User(String email, String token, String tokenRefresh){
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
}