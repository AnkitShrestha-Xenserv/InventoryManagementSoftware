/**
 * Created by Ankit on 7/24/2020.
 */
public class StoreModel {

    final String storeName;
    final String storeOwnerName;
    final String phoneNumber;
    final String address;
    final String password;

    StoreModel(String storeName,String storeOwnerName, String phoneNumber, String address, String password){
        this.storeName = storeName;
        this.storeOwnerName = storeOwnerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }
}