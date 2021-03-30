import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Ankit on 3/8/2021.
 */
public class UserHomePage extends JPanel {
    private final String path = "D:\\Programming\\Java\\InventoryManagementSoftware\\Project\\images\\";
    private final Home home;
    private final String userName;
    private JPanel userInfoPanel = new JPanel();
    private JPanel wishListPanel = new JPanel();
    private JPanel userBuyListPanel = new JPanel();

    private DefaultTableModel wishListTableModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){return false;}
    };
    private DefaultTableModel buyListTableModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){return false;}
    };

    private final int WISHLIST_ROW_COUNT = 6;
    private final int BUYLIST_ROW_COUNT = 8;

    private Object wishListRow[] = new Object[WISHLIST_ROW_COUNT];
    private Object buyListRow[] = new Object[BUYLIST_ROW_COUNT];

    private Repository repository = new Repository();

    private JTable wishListTable = new JTable();
    private JTable buyListTable = new JTable();

    UserHomePage(Home home){
        this.userName = Home.getLoggedInUser();
        this.home = home;
        buildUserInfoPanel();
        buildWishListPanel();
        buildUserBuyListPanel();

        add(userBuyListPanel,BorderLayout.NORTH);
        add(userInfoPanel,BorderLayout.SOUTH);
        add(wishListPanel,BorderLayout.SOUTH);

        setBackground(Color.orange);
        setLayout(new BorderLayout());
        setVisible(true);

    }

    private void buildWishListPanel() {

        JLabel wishListLabel = new JLabel("WISH LIST");
        JScrollPane wishListPane = new JScrollPane();

        wishListPane.getViewport().add(wishListTable);

        Object wishListColumns[] ={"SN", "Item", "In Stock", "Price", "Type","Purchase Place"};
        wishListTableModel.setColumnIdentifiers(wishListColumns);

        wishListTable.setModel(wishListTableModel);

        buildTableUI(wishListTable,WISHLIST_ROW_COUNT);

        wishListPanel.setBackground(Color.red);

        wishListLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));



        wishListLabel.setBounds(450,1,200,40);
        wishListPane.setBounds(1,40,1098,360);

        wishListPanel.add(wishListLabel);
        wishListPanel.add(wishListPane);
        wishListPanel.setVisible(true);
        wishListPanel.setSize(new Dimension(1100,400));
        wishListPanel.setLayout(null);
        wishListPanel.setBorder(new EtchedBorder());
    }

    private void buildTableUI(JTable table, int rowCount) {
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setSelectionBackground(new Color(232,57,95));
        table.setShowVerticalLines(false);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int x=0;x<rowCount;x++){
            table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
    }

    private void buildUserBuyListPanel() {

        JLabel buyListLabel = new JLabel("BUY LIST");
        JScrollPane buyListPane = new JScrollPane();
        buyListPane.getViewport().add(buyListTable);
        Object []buyListColumns = {"Sn","Item","Quantity","Price","Type","Purchase Place","Purchase Date","Delivered"};
        buyListTableModel.setColumnIdentifiers(buyListColumns);

        buyListTable.setModel(buyListTableModel);
        buildTableUI(buyListTable,BUYLIST_ROW_COUNT);

        userBuyListPanel.setBackground(Color.red);
        buyListLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));

        buyListLabel.setBounds(450,1,200,40);
        buyListPane.setBounds(1,40,1098,360);

        userBuyListPanel.add(buyListLabel);
        userBuyListPanel.add(buyListPane);

        userBuyListPanel.setVisible(true);
        userBuyListPanel.setBounds(0,410,1100,480);
        userBuyListPanel.setLayout(null);
        userBuyListPanel.setBorder(new EtchedBorder());
    }

    private void buildUserInfoPanel() {
        UserModel userModel = repository.getParticularUserData(userName);
        JLabel profilePictureIconLabel = new JLabel();
        ImageIcon profilePictureIcon = new ImageIcon(path+"empty_profile_picture.png");
        profilePictureIconLabel.setIcon(new ImageIcon(profilePictureIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));

        JLabel logOutIconLabel = new JLabel();
        ImageIcon logOutIcon = new ImageIcon(path+"logout.png");
        logOutIconLabel.setIcon(new ImageIcon(logOutIcon.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT)));
        logOutIconLabel.setToolTipText("<html><h3>Logout</h3></html>");

        JLabel userNameLabel = new JLabel("Username: " + userModel.getUserName());
        JLabel addressLabel = new JLabel("Address: " + userModel.getAddress());
        JLabel phoneNumberLabel = new JLabel("Phone Number: " + userModel.getPhoneNumber());

        profilePictureIconLabel.setBounds(125,30,120,100);
        logOutIconLabel.setBounds(300,25,40,40);
        userNameLabel.setBounds(100,160,200,30);
        addressLabel.setBounds(100,190,200,30);
        phoneNumberLabel.setBounds(100,220,200,30);

        userInfoPanel.add(profilePictureIconLabel);
        userInfoPanel.add(logOutIconLabel);
        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(addressLabel);
        userInfoPanel.add(phoneNumberLabel);

        userInfoPanel.setLayout(null);
        userInfoPanel.setBackground(Color.green);
        userInfoPanel.setVisible(true);
        userInfoPanel.setBounds(1110,0,350,840);
        userInfoPanel.setBorder(new EtchedBorder());

        logOutIconLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Home.setLoggedInUser("");
                home.removeUserHomePageFromView();
                home.buildUserLogin();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    void clearWishListTable(){
        wishListTableModel.setRowCount(0);
    }
    void clearBuyListTable(){
        buyListTableModel.setRowCount(0);
    }

    void addBuyListTableRow(UserReportModel userReportModel, boolean delivered){
        buyListRow[0] = buyListTable.getRowCount() + 1;
        buyListRow[1] = userReportModel.getItem();
        buyListRow[2] = userReportModel.getQuantity();
        buyListRow[3] = userReportModel.getPrice();
        buyListRow[4] = userReportModel.getType();
        buyListRow[5] = userReportModel.getStoreName();
        buyListRow[6] = String.valueOf(userReportModel.getTimestamp()).substring(0,19);
        buyListRow[7] = (delivered)?"Yes":"No";
        buyListTableModel.addRow(buyListRow);
    }

    void addWishListTableRow(ItemModel itemModel){
        wishListRow[0] = wishListTable.getRowCount() + 1;
        wishListRow[1] = itemModel.getItem();
        wishListRow[2] = itemModel.getQuantity();
        wishListRow[3] = itemModel.getPrice();
        wishListRow[4] = itemModel.getType();
        wishListRow[5] = itemModel.getStoreName();
        wishListTableModel.addRow(wishListRow);
    }
}