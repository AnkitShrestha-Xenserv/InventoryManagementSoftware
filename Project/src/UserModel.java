/**
 * Created by Ankit on 3/2/2021.
 */
public class UserModel {
    final private String userName;
    final private String phoneNumber;
    final private String address;
    final private String password;

    UserModel(String userName, String phoneNumber, String address, String password){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}