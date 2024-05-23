package api;

public class RegisterData {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RegisterData(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
