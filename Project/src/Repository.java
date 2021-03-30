import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

class Repository {
    private final String Db_url = "jdbc:mysql://localhost/inventory_management?useLegacyDatetimeCode=false&serverTimezone=Asia/Kathmandu";

    // GET DATA FROM THE DATABASE

    //GET ITEM DATA FROM DATABASE

    // GET ALL THE INFORMATION OF A PARTICULAR ITEM FROM A PARTICULAR STORE
    ItemModel getParticularItemData(String storeName, String itemName){
        try{
            String sql = "SELECT * FROM " + changeNameToDBFormat(storeName);
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                if(reformItemName(rs.getString("Item")).equals(itemName)){
                    return new ItemModel(
                            storeName,
                            itemName,
                            rs.getDouble("Quantity"),
                            rs.getDouble("Price"),
                            reformItemName(rs.getString("Type")));
                }
            }

        }catch(Exception exception){
            exception.printStackTrace();
        }
        return new ItemModel(
                storeName,
                itemName,
                0.0,
                0.0,
                ""
        );
    }

    // GET USER DATA FROM DATABASE

    // GET NAMES OF ALL EXISTING USERS
    ArrayList<String> getUserNames(){
        ArrayList<String> users = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "SELECT Username FROM user_info";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                users.add(rs.getString("Username"));
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return users;
    }

    // GET ALL THE INFORMATION OF A PARTICULAR USER
    UserModel getParticularUserData(String userName) {
        try {
            String sql = "SELECT * from  user_info";
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("Username").equals(userName)) {
                    return new UserModel(userName, rs.getString("PhoneNumber"), rs.getString("Address"), rs.getString("Password"));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    // GET LIST OF ALL WISH LIST ITEMS OF USER
    ArrayList<ItemModel> getUserWishListData(String userName){
        try{
            ArrayList<ItemModel> list = new ArrayList<>();
            Connection con = DriverManager.getConnection(Db_url,"root", "");
            String sql = "SELECT * from " + getWishListName(userName);
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String sql2 = "SELECT * from " + changeNameToDBFormat(rs.getString("StoreName"));
                PreparedStatement st2 = con.prepareStatement(sql2);
                ResultSet rs2 = st2.executeQuery();
                while (rs2.next()) {
                    if(rs2.getString("Item").equals(rs.getString("Item"))){
                        list.add(new ItemModel(rs.getString("StoreName"),reformItemName(rs.getString("Item")),rs2.getDouble("Quantity"),rs2.getDouble("Price"),reformItemName(rs2.getString("Type"))));
                        break;
                    }
                }
            }
            return list;
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    // GET ALL THE DATA OF USER TRANSACTIONS
    // PASS getUserBuyListName() METHOD FOR NOT DELIVERED ITEMS LIST
    // PASS getUserDeliveredListName() DELIVERED ITEMS LIST
    ArrayList<UserReportModel> getUserBuyListData(String userName){
        try{
            ArrayList<UserReportModel> list = new ArrayList<>();
            Connection con = DriverManager.getConnection(Db_url,"root", "");
            String sql = "SELECT * from " + userName;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                list.add(new UserReportModel(
                        reformItemName(rs.getString("Item")),
                        rs.getDouble("Quantity"),
                        rs.getDouble("Price"),
                        rs.getString("Type"),
                        rs.getString("StoreName"),
                        rs.getTimestamp("BuyDate"))
                );
            }
            return list;
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    // GET STORE DATA FROM DATABASE

    // GET NAMES OF ALL EXISTING STORES
    ArrayList<String> getStoreNames(){
        ArrayList<String> stores = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "SELECT StoreName FROM stores_info";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                stores.add(rs.getString("StoreName"));
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return stores;
    }

    // GET ALL THE INFORMATION OF A PARTICULAR STORE
    List<ItemModel> getParticularStoreData(String storeName){
        try{
            List<ItemModel> list = new ArrayList<>();
            String sql = "SELECT * from " + changeNameToDBFormat(storeName);
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ItemModel item;
            while(rs.next()){
                item = new ItemModel(
                        storeName,
                        reformItemName(rs.getString("Item")),
                        rs.getDouble("Quantity"),
                        rs.getDouble("Price"),
                        reformItemName(rs.getString("Type"))
                );
                list.add(item);
            }
            return list;
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    // GET ALL THE INFORMATION OF ALL EXISTING STORES
    List<ItemModel> getAllStoreData(){
        ArrayList<String> stores = getStoreNames();

        List<ItemModel> items = new ArrayList<>();
        ItemModel item;
        try{
            Connection con = DriverManager.getConnection(Db_url, "root","");
            for(String store: stores){
                String sql = "SELECT * FROM " + changeNameToDBFormat(store);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    item = new ItemModel(
                            store,
                            reformItemName(rs.getString("Item")),
                            rs.getDouble("Quantity"),
                            rs.getDouble("Price"),
                            reformItemName(rs.getString("Type"))
                    );
                    items.add(item);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return items;
    }

    // GET SALES REPORT LIST OF A PARTICULAR STORE
    ArrayList<StoreReportModel> getStoreReportData(String storeName){
        try{
            ArrayList<StoreReportModel> list = new ArrayList<>();
            Connection con = DriverManager.getConnection(Db_url,"root", "");
            String sql = "SELECT * from " + getStoreReportListName(storeName);
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                list.add(new StoreReportModel(
                        reformItemName(rs.getString("Item")),
                        rs.getDouble("Quantity"),
                        rs.getDouble("Price"),
                        rs.getString("Type"),
                        rs.getTimestamp("SalesDate"),
                        rs.getString("BuyerName"),
                        rs.getString("BuyerAddress"),
                        rs.getString("BuyerPhoneNumber"))
                );
            }
            return list;
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    // ADD DATA TO THE DATABASE

    // ADD DATA TO USER DATABASE

    // ADD INFORMATION OF NEW USER
    int addUserData(UserModel model){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "INSERT into user_info (UserName, PhoneNumber, Address, Password)" + "VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,model.getUserName());
            stmt.setString(2,model.getPhoneNumber());
            stmt.setString(3,model.getAddress());
            stmt.setString(4,model.getPassword());
            return stmt.executeUpdate();
        }catch(Exception exception){
            exception.printStackTrace();
            return 0;
        }
    }

    // ADD ITEM TO USER WISH LIST
    int addItemToWishList(ItemModel itemModel,String userName){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "INSERT into " + getWishListName(userName) + " (Item,StoreName)" + " VALUES(?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,itemModel.getItem().toLowerCase());
            stmt.setString(2,itemModel.getStoreName());
            return stmt.executeUpdate();
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return 0;
    }

    private final int DELIVERY_TIME = 10; // Delivery Time in Minutes
    // TO CHANGE DELIVERY TIME, CHANGE THE {DELIVERY_TIME} CONSTANT ABOVE
    // CHECK WHETHER THE ITEMS HAVE BEEN DELIVERED TO THE USER
    // AND CALL METHODS TO UPDATE THE TABLES RESPECTIVELY
    void deliverItemsToUser(String userName){
        try{
            Connection con = DriverManager.getConnection(Db_url,"root","");
            String sql = "SELECT * from " + getUserBuyListName(userName);
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                if((DateStuff.getIntegerTime(rs.getTimestamp("BuyDate").toString()) + DELIVERY_TIME) <
                        DateStuff.getIntegerTime((new Timestamp((new Date()).getTime())).toString().substring(0,19))){
                    addBoughtItemToUserDeliveredList(rs,userName,con);
                    removeBoughtItemFromUserBuyList(rs.getInt("Id"),userName,con);
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // ADD ITEM TO userBuyList WHEN USER BUYS ITEMS
    void addBoughtItemToUserBuyList(double quantity,ItemModel itemModel, UserModel userModel){
        try{
            Connection con = DriverManager.getConnection(Db_url,"root","");
            String sql = "INSERT into " + getUserBuyListName(userModel.getUserName()) +
                    " (Item, Quantity, Price, `Type`, StoreName, BuyDate)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,itemModel.getItem());
            st.setDouble(2,quantity);
            st.setDouble(3,itemModel.getPrice());
            st.setString(4,itemModel.getType());
            st.setString(5,itemModel.getStoreName());
            st.setObject(6,new Timestamp((new Date()).getTime()));
            st.execute();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    // ADD ITEM TO THE USER DELIVERED LIST FROM userBuyList AFTER DELIVERY TIME HAS PASSED
    void addBoughtItemToUserDeliveredList(ResultSet rs, String userName,Connection con){
        try{
            String sql = "INSERT into " + getUserDeliveredListName(userName) +
                    " (Item, Quantity, Price, `Type`, StoreName, BuyDate)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,rs.getString("Item"));
            st.setDouble(2,rs.getDouble("Quantity"));
            st.setDouble(3,rs.getDouble("Price"));
            st.setString(4,rs.getString("Type"));
            st.setString(5,rs.getString("StoreName"));
            st.setObject(6,rs.getTimestamp("BuyDate"));
            st.execute();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }



    // ADD DATA TO STORE DATABASE

    // ADD INFORMATION OF NEW STORE
    int addStoreData(StoreModel model){
        try {
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "INSERT into stores_info (StoreName, StoreOwnerName, PhoneNumber, Address, Password)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,model.storeName);
            stmt.setString(2,model.storeOwnerName);
            stmt.setString(3,model.phoneNumber);
            stmt.setString(4,model.address);
            stmt.setString(5,model.password);
            return stmt.executeUpdate();
        }catch(Exception exception){
            exception.printStackTrace();
            return 0;
        }
    }

    // ADD NEW STOCK ITEMS TO STORE DATABASE
    int addStoreItems(ItemModel model){
        try{
            String sqlExtract = "SELECT * FROM " + changeNameToDBFormat(model.getStoreName());
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sqlExtract);
            while(rs.next()){
                if(rs.getString("Item").toLowerCase().equals(model.getItem().toLowerCase())){
                    String sql = "UPDATE " + changeNameToDBFormat(model.getStoreName()) +
                            " SET Quantity = ? , Price = ?"+
                            " WHERE Item = ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setDouble(1,rs.getDouble("Quantity")+model.getQuantity());
                    stmt.setDouble(2, model.getPrice());
                    stmt.setString(3, model.getItem().toLowerCase());
                    stmt.executeUpdate();
                    return 1;
                }
            }
            String sqlInsert = "INSERT INTO " + changeNameToDBFormat(model.getStoreName()) +
                    "(Item, Quantity, Price, Type)" + "VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setString(1, model.getItem().toLowerCase());
            stmt.setDouble(2,model.getQuantity());
            stmt.setDouble(3,model.getPrice());
            stmt.setString(4, model.getType().toLowerCase());
            stmt.executeUpdate();
            return 2;
        }catch (Exception exception){
            exception.printStackTrace();
        }
      return 0;
    }

    // ADD ITEM TO storeReportList WHEN A USER BUYS AN ITEM FROM THE STORE
    void addSoldItemToReportList(double quantity,ItemModel itemModel, UserModel userModel){
        try{

            Connection con = DriverManager.getConnection(Db_url,"root","");
            String sql = "INSERT into " + getStoreReportListName(itemModel.getStoreName()) +
                    " (Item, Quantity, Price, `Type`, SalesDate, BuyerName, BuyerAddress, BuyerPhoneNumber) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1,itemModel.getItem());
            st.setDouble(2,quantity);
            st.setDouble(3,itemModel.getPrice());
            st.setString(4,itemModel.getType());
            st.setObject(5,new Timestamp((new Date()).getTime()));
            st.setString(6,userModel.getUserName());
            st.setString(7,userModel.getAddress());
            st.setString(8,userModel.getPhoneNumber());
            st.execute();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // REMOVE ITEMS FROM DATABASE

    // REMOVE ITEMS FROM USER BUY LIST AFTER DELIVERY TIME HAS PASSED
    void removeBoughtItemFromUserBuyList(int id, String userName,Connection con){
        try{
            String sql = "DELETE FROM " + getUserBuyListName(userName) +
                    " WHERE id = " + id;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // REDUCE STOCK OF THE STORE FROM WHERE THE ITEMS ARE BOUGHT AND
    // CALL METHODS TO ADD THE BOUGHT ITEM TO USER AND STORE REPORT LIST
    int transact(String item, String storeName, Double quantity, Double totalQuantity){
        try {
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "UPDATE " + changeNameToDBFormat(storeName)
                    + " SET Quantity = ? "
                    + "WHERE Item = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, totalQuantity - quantity);
            stmt.setString(2, item);
            stmt.executeUpdate();
            String userName = Home.getLoggedInUser();
            ItemModel itemModel = getParticularItemData(storeName,item);
            UserModel userModel = getParticularUserData(userName);
            addSoldItemToReportList(quantity,itemModel,userModel);
            addBoughtItemToUserBuyList(quantity,itemModel,userModel);
            return 1;
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return 0;
    }



    // NEW ACCOUNT TABLE CREATIONS

    // CREATE ALL NECESSARY TABLES FOR USER ACCOUNT
    void createUserAccount(String userName){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "CREATE TABLE " +
                    "user_"+ changeNameToDBFormat(userName) +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Item VARCHAR(100)," +
                    " Quantity DOUBLE," +
                    " Price DOUBLE," +
                    " Type TEXT," +
                    " Store VARCHAR(100)," +
                    " PurchaseDate VARCHAR(100)," +
                    " Delivered INT)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
            String sql2 = "CREATE TABLE " +
                    getWishListName(userName) +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Item VARCHAR(100)," +
                    " StoreName VARCHAR(100))";
            PreparedStatement stmt2 = con.prepareStatement(sql2);
            stmt2.executeUpdate();
            String sql3 = "CREATE TABLE " +
                    getUserBuyListName(userName) +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Item VARCHAR(100)," +
                    " Quantity DOUBLE," +
                    " Price DOUBLE," +
                    " Type TEXT," +
                    " StoreName VARCHAR(100)," +
                    " BuyDate DATETIME)";
            PreparedStatement stmt3 = con.prepareStatement(sql3);
            stmt3.executeUpdate();
            String sql4 = "CREATE TABLE " +
                    getUserDeliveredListName(userName) +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Item VARCHAR(100)," +
                    " Quantity DOUBLE," +
                    " Price DOUBLE," +
                    " Type TEXT," +
                    " StoreName VARCHAR(100)," +
                    " BuyDate DATETIME)";
            PreparedStatement stmt4 = con.prepareStatement(sql4);
            stmt4.executeUpdate();

        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // CREATE ALL NECESSARY TABLES FOR STORE ACCOUNT
    void createStoreAccount(String storeName){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "CREATE TABLE " +
                    changeNameToDBFormat(storeName) +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Item VARCHAR(100)," +
                    " Quantity DOUBLE," +
                    " Price DOUBLE," +
                    " Type TEXT)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
            String sql2 = "CREATE TABLE " +
                    getStoreReportListName(storeName) +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Item VARCHAR(100)," +
                    " Quantity DOUBLE," +
                    " Price DOUBLE," +
                    " Type TEXT," +
                    " SalesDate DATETIME," +
                    " BuyerName VARCHAR(100)," +
                    " BuyerAddress VARCHAR(100)," +
                    " BuyerPhoneNumber TEXT)";
            PreparedStatement stmt2 = con.prepareStatement(sql2);
            stmt2.executeUpdate();

        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    // CHECK DUPLICATES

    // CHECK IF THE GIVEN ITEM ALREADY EXISTS IN THE USER WISH LIST
    boolean checkDuplicateItemInWishList(ItemModel itemModel,String userName){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "SELECT * from " + getWishListName(userName);
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                if(itemModel.getItem().toLowerCase().equals(rs.getString("Item").toLowerCase()) && itemModel.getStoreName().toLowerCase().equals(rs.getString("StoreName").toLowerCase())){
                    return true;
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    // CHECK IF THE GIVEN STORE NAME ALREADY EXISTS
    boolean checkDuplicateStoreName(String name){
        String []stores = getStoreNames().toArray(new String[0]);

        for(String store: stores){
            if(name.equals(store)){
                return false;
            }
        }
        return true;
    }

    // CHECK IF THE GIVEN USER NAME ALREADY EXISTS
    boolean checkDuplicateUserName(String name){
        String []users = getUserNames().toArray(new String[0]);

        for(String user: users){
            if(name.equals(user)){
                return false;
            }
        }
        return true;
    }

    // CHECK IF THE SAME ITEM IS PROVIDED WITH DIFFERENT TYPE
    boolean checkDuplicateItemWithDifferentType(String storeName, String item, String type){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "SELECT * from " + changeNameToDBFormat(storeName);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("Item").equals(item.toLowerCase()) && !rs.getString("Type").equals(type.toLowerCase())){
                    return true;
                }
            }
            return false;
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    // CHECK IF THE SAME ITEM IS PROVIDED WITH DIFFERENT PRICE
    boolean checkDuplicateItemWithDifferentPrice(String storeName, String item, String price){
        try{
            Connection con = DriverManager.getConnection(Db_url,"root","");
            String sql = "SELECT * FROM " + changeNameToDBFormat(storeName);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(item.toLowerCase().equals(rs.getString(2).toLowerCase()) &&
                        !(Double.parseDouble(price) == (rs.getDouble(4)))){
                    return true;
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    // HELPER FUNCTIONS

    // RETURN DATABASE NAME FOR STORE REPORT LIST
    String getStoreReportListName(String storeName){
        return changeNameToDBFormat(storeName)+"_report";
    }


    // RETURN DATABASE NAME FOR USER DELIVERED LIST
    String getUserDeliveredListName(String userName){
        return "user_" + changeNameToDBFormat(userName) + "_delivered_report";
    }

    // RETURN DATABASE NAME FOR USER WISH LIST
    private String getWishListName(String userName) {
        return "user_" + changeNameToDBFormat(userName) + "_wishlist";
    }

    // RETURN DATABASE NAME FOR USER BUY LIST REPORT
    String getUserBuyListName(String userName){
        return "user_"+ changeNameToDBFormat(userName)+"_report";
    }

    // CHANGE NAME TO DATABASE FORMAT
    private String changeNameToDBFormat(String storeName){
        return storeName.toLowerCase().replaceAll(" ","_");
    }

    // REFORM ITEM NAME TO DISPLAY FORMAT (FIRST LETTER OF EACH WORD CAPITAL)
    private String reformItemName(String item) {
        if(item.equals("")) return item;
        item = (item.substring(0,1).toUpperCase() + item.substring(1)).trim();
        for(int i=0;i<item.length();i++){
            if(item.substring(i,i+1).equals(" ")){
                item = item.substring(0,i+1) + item.substring(i+1,i+2).toUpperCase() + item.substring(i+2);
            }
        }
        return item;
    }

    // VALIDATION

    // VALIDATE STORE LOGIN
    boolean checkStoreValidity(String storeName, String password){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "SELECT * FROM stores_info";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                if (storeName.equals(rs.getString(2)) && password.equals(rs.getString(6))) {
                    return true;
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    // VALIDATE USER LOGIN
    boolean checkUserValidity(String Username, String password){
        try{
            Connection con = DriverManager.getConnection(Db_url, "root", "");
            String sql = "SELECT * FROM user_info";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                if (Username.equals(rs.getString(2)) && password.equals(rs.getString(5))) {
                    return true;
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return false;
    }
}