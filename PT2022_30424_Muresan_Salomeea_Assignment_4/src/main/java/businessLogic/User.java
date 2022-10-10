package businessLogic;

public class User {
    private int userID;
    private String username;
    private String password;
    private AccessRights accessRights;

    public int getOrderCount() {
        return orderCount;
    }

    private int orderCount;

    public User() {
        orderCount = 0;
    }

    public String toString(){
        return this.userID + " " + this.username + " " + this.accessRights;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
