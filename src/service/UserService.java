//Authentication and user management
package service;

public class UserService {
    private static UserService instance;
    private String currentUserEmail;
    private String currentUserName;
    private boolean isLoggedIn;

    private UserService() {
        this.isLoggedIn = false;
        this.currentUserEmail = null;
        this.currentUserName = null;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void login(String email, String name) {
        this.currentUserEmail = email;
        this.currentUserName = name;
        this.isLoggedIn = true;
    }

    public void logout() {
        this.currentUserEmail = null;
        this.currentUserName = null;
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }
}
