package icu.pymiliblog.teachingmanagementplatform.pojo.user;

/**
 * 用户请求POJO
 * @author PYmili
 */
public class UserRequestPojo {
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private String qqId;

    public UserRequestPojo() {}

    public UserRequestPojo(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    @Override
    public String toString() {
        return "UserRequestPojo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", qqId='" + qqId + '\'' +
                '}';
    }
}
