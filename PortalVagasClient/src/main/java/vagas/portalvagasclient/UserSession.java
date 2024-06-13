package vagas.portalvagasclient;
public class UserSession {
    private static UserSession instance;

    private String token;
    private String email;

    private UserSession(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public static UserSession getInstance( String email, String token) {
        if(instance == null) {
            instance = new UserSession(email, token);
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public void cleanUserSession() {
        email = "";
        token = "";
        instance = null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "email='" + email + '\'' +
                '}';
    }
}