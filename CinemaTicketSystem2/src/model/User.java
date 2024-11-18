package model;

public class User {

    private int userId;
    private String userName;
    private String userPassword;
    private String userGender;
    private String userPhone;
    private String userEmail;

    public User(int userId, String userName, String userPassword, String userGender, String userPhone, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }
}