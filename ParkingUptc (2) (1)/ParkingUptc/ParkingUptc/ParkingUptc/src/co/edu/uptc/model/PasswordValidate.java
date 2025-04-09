package co.edu.uptc.model;

public class PasswordValidate {

    public boolean isValidPassword(String pass) {
        return pass.matches("\\d{8}");
    }

    public boolean arePasswordsEqual(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

}
