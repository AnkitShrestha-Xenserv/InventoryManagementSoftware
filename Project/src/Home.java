import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Home {

    private Repository repository = new Repository();
    private List<ItemModel> list = repository.getAllStoreData();
    private List<ItemModel> filteredList = new ArrayList<>();
    private List<ItemModel> filteredListV2 = new ArrayList<>();
    private JPanel panels;

    private JFrame frame = new JFrame();
    private JPanel topPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private Login ownerLogin;
    private UserLogin userLogin;
    private UserHomePage userHomePage;
    private Cart cart = new Cart();
    private AddStock stock;
    private JComboBox<String> priceSort = new JComboBox<>();
    private JTextField search = new JTextField();
    private JButton searchButton = new JButton("Search");

    private final String path = "D:\\Programming\\Java\\InventoryManagementSoftware\\Project\\images\\";
    private static String loggedInUser = "";

    private JList<String> jList = new JList<>(getAllTypes(list));

    private JScrollPane pane = new JScrollPane();
    private ImageIcon homeIcon = new ImageIcon(path + "home.png");
    private ImageIcon shoppingCartIcon = new ImageIcon(path + "shopping-cart.png");
    private ImageIcon notesIcon = new ImageIcon(path + "book.png");
    private ImageIcon quitIcon = new ImageIcon(path + "quit.png");
    private ImageIcon minimizeIcon = new ImageIcon(path + "minimize.png");

    //NEW
    private ImageIcon userIcon = new ImageIcon(path + "user.png");
    private ImageIcon storeIcon = new ImageIcon(path + "store.png");

    //NEW
    private JLabel storeIconLabel = new JLabel();

    private JLabel homeIconLabel = new JLabel();
    private JLabel shoppingCartIconLabel = new JLabel();
    private JLabel userIconLabel = new JLabel();
    private JLabel notesIconLabel = new JLabel();
    private JLabel quitIconLabel = new JLabel();
    private JLabel minimizeIconLabel = new JLabel();

    void home(){
        //System.out.println(screenSize.getWidth());
        //System.out.println(screenSize.getHeight());
        buildTopPanel();
        buildMiddlePanel();
        buildLeftPanel();

        // ADD ALL THE LISTENERS
        addListeners();

        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(leftPanel,BorderLayout.WEST);
        frame.add(middlePanel,BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setSize(new Dimension(1900,1000));
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private JPanel buildPanel(List<ItemModel> items){
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.orange);
        int row = 0, column = 0;
        GridBagConstraints gbc = new GridBagConstraints();
        itemsPanel.setLayout(new GridBagLayout());
        for(ItemModel item : items) {
            gbc.gridx = row;
            gbc.gridy = column;
            itemsPanel.add(new IndividualItem(item, cart), gbc);
            row++;
            if(row == 5){
                row = 0 ;
                column++;
            }
        }
        return itemsPanel;
    }

    private void buildTopPanel(){
        priceSort.addItem("");
        priceSort.addItem("Low to High");
        priceSort.addItem("High to Low");

        ToolTipManager.sharedInstance().setInitialDelay(0);

        homeIconLabel.setIcon(new ImageIcon(homeIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        shoppingCartIconLabel.setIcon(new ImageIcon(shoppingCartIcon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT)));
        userIconLabel.setIcon(new ImageIcon(userIcon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT)));
        notesIconLabel.setIcon(new ImageIcon(notesIcon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT)));
        quitIconLabel.setIcon(new ImageIcon(quitIcon.getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        minimizeIconLabel.setIcon(new ImageIcon(minimizeIcon.getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));

        //NEW
        storeIconLabel.setIcon(new ImageIcon(storeIcon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT)));

        homeIconLabel.setToolTipText("<html><h2>HOME</h2></html>");
        shoppingCartIconLabel.setToolTipText("<html><h2>Your Cart</h2></html>");
        notesIconLabel.setToolTipText("<html><h2>Tips</h2></html>");
        quitIconLabel.setToolTipText("<html><h3>Quit</h3></html>");
        minimizeIconLabel.setToolTipText("<html><h3>Minimize</h3></html>");
        //NEW
        userIconLabel.setToolTipText("<html><h2>User Login</h2></html>");
        storeIconLabel.setToolTipText("<html><h3>Setup Shop</h3></html>");

        homeIconLabel.setBounds(20,40,100,100);
        shoppingCartIconLabel.setBounds(200, 40, 100, 100);
        userIconLabel.setBounds(1500, 40, 100, 100);
        notesIconLabel.setBounds(1700,40,100,100);
        quitIconLabel.setBounds(1850,10,40,40);
        minimizeIconLabel.setBounds(1800,10,40,40);

        //NEW
        storeIconLabel.setBounds(380,40,100,100);

        priceSort.setBounds(850,140,140,50);
        search.setBounds(700,80,500,50);
        searchButton.setBounds(1250,80,140,50);

        search.setFont(new Font("Serif",Font.LAYOUT_LEFT_TO_RIGHT,20));

        topPanel.setBackground(Color.green);

        topPanel.add(homeIconLabel);
        topPanel.add(shoppingCartIconLabel);
        topPanel.add(userIconLabel);
        topPanel.add(notesIconLabel);
        topPanel.add(quitIconLabel);
        topPanel.add(minimizeIconLabel);
        topPanel.add(priceSort);
        topPanel.add(search);
        topPanel.add(searchButton);

        //NEW
        topPanel.add(storeIconLabel);

        topPanel.setBounds(10,10,1900,200);
        topPanel.setLayout(null);
        topPanel.setBorder(new EtchedBorder());

    }

    void removeUserHomePageFromView(){
        userHomePage.setVisible(false);
    }

    void reFetchData(){
        middlePanel.remove(pane);
        repository = new Repository();
        list = repository.getAllStoreData();
        filteredList = new ArrayList<>();
        filteredListV2 = new ArrayList<>();

        pane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        pane.getVerticalScrollBar().setUnitIncrement(5);
        pane.setViewportView(buildPanel(list));
        pane.setBounds(10,10,1460,830);
        pane.setBorder(new EmptyBorder(2,2,2,2));
        middlePanel.add(pane);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    private void buildMiddlePanel(){

        pane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        pane.getVerticalScrollBar().setUnitIncrement(5);
        pane.setViewportView(buildPanel(list));
        pane.setBounds(10,10,1460,830);
        pane.setBorder(new EmptyBorder(2,2,2,2));

        ownerLogin = new Login();
        ownerLogin.setBounds(10,10,1460,830);
        ownerLogin.setVisible(false);

        //New
        userLogin = new UserLogin(this);
        userLogin.setBounds(10,10,1460,830);
        userLogin.setVisible(false);

        cart.setBounds(10,10,1460,830);
        cart.setVisible(false);

        middlePanel.add(cart);
        middlePanel.add(ownerLogin);
        //NEW
        middlePanel.add(userLogin);

        middlePanel.setBackground(Color.orange);

        middlePanel.add(pane);
        middlePanel.setBounds(430,220,1480,850);
        middlePanel.setLayout(null);
        middlePanel.setBorder(new EtchedBorder());
    }

    private void buildLeftPanel(){
        jList.setCellRenderer(new ListRenderer());
        jList.setBounds(20,20,350,810);
        jList.setBackground(Color.orange);
        leftPanel.setBackground(Color.orange);

        leftPanel.add(jList);
        leftPanel.setBounds(10,220,400,850);
        leftPanel.setLayout(null);
        leftPanel.setBorder(new EtchedBorder());
    }

    private DefaultListModel<String> getAllTypes(List<ItemModel> list){
        DefaultListModel<String> model = new DefaultListModel<>();
        List<String> types = new ArrayList<>();
        List<String> uniqueTypes;
        for(ItemModel items : list){
            types.add(items.getType());
        }
        uniqueTypes = types.stream().distinct().collect(Collectors.toList());

        model.addElement("SELECT TYPES");
        for(String type : uniqueTypes){

            model.addElement(type);
        }
        return model;
    }

    void buildLogin(){
        middlePanel.remove(ownerLogin);
        ownerLogin = new Login();
        middlePanel.add(ownerLogin);
        ownerLogin.setBounds(10,10,1460,830);
        ownerLogin.setVisible(true);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    void buildUserLogin(){
        middlePanel.remove(userLogin);
        userLogin = new UserLogin(this);
        middlePanel.add(userLogin);
        userLogin.setBounds(10,10,1460,830);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    void logInUser(String userName){
        loggedInUser = userName;
        userHomePage = new UserHomePage(this);
        userHomePage.setBounds(10,10,1460,830);
        userLogin.setVisible(false);
        userHomePage.setVisible(true);
        middlePanel.add(userHomePage);
        repository.deliverItemsToUser(userName);
        if(!loggedInUser.equals("")) {
            userHomePage.clearWishListTable();
            userHomePage.clearBuyListTable();
            ArrayList<ItemModel> wishList = repository.getUserWishListData(loggedInUser);
            wishList.forEach(userHomePage::addWishListTableRow);
            ArrayList<UserReportModel> buyList = repository.getUserBuyListData(repository.getUserBuyListName(loggedInUser));
            ArrayList<UserReportModel> deliveredList = repository.getUserBuyListData(repository.getUserDeliveredListName(loggedInUser));
            buyList.forEach(value -> {
                userHomePage.addBuyListTableRow(value,false);
                }
            );
            deliveredList.forEach(value -> {
                userHomePage.addBuyListTableRow(value,true);
            });
        }
    }

     void logInStoreUser(String name){
        ownerLogin.setVisible(false);
        stock = new AddStock(name);
        stock.setVisible(true);
        stock.setBounds(10,10,1460,830);
        middlePanel.add(stock);

    }

    private void addListeners(){

         quitIconLabel.addMouseListener(new MouseListener() {
             @Override
             public void mouseReleased(MouseEvent e) {
                 frame.dispose();
             }
             @Override
             public void mouseClicked(MouseEvent e) {}
             @Override
             public void mousePressed(MouseEvent e) {}
             @Override
             public void mouseEntered(MouseEvent e) {}
             @Override
             public void mouseExited(MouseEvent e) {}
         });

        minimizeIconLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                frame.setState(Frame.ICONIFIED);
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        notesIconLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                String note = "<html><body width='1%s'><h2>Tips for using the Software</h2><br>"
                        + "<p> 1.  Please Login to purchase"
                        + "<p> 2.  Quantity for Vegetables is taken in KG"
                        + "<p> 3.  Price mentioned is for a single Item"
                        + "<p> 4.  Press the User Icon to register your own shop"
                        + "<p> 5.  Currency Used is Rupees";
                JOptionPane.showMessageDialog(frame, note);
            }

            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        homeIconLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                reFetchData();
                cart.setVisible(false);
                userLogin.setVisible(false);
                if(userHomePage != null) {
                    userHomePage.setVisible(false);
                }
                ownerLogin.setVisible(false);
                ownerLogin.nameField.setSelectedItem("");
                ownerLogin.passField.setText("");
                if(stock != null) {
                    stock.setVisible(false);
                }
                jList.setEnabled(true);
                search.setEnabled(true);
                searchButton.setEnabled(true);
                priceSort.setEnabled(true);
                pane.setVisible(true);
            }

            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        shoppingCartIconLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                cart.setVisible(true);
                pane.setVisible(false);
                jList.setEnabled(false);
                search.setEnabled(false);
                searchButton.setEnabled(false);
                priceSort.setEnabled(false);
                if(stock != null) {
                    stock.setVisible(false);
                }
                userLogin.setVisible(false);
                if(userHomePage != null) {
                    userHomePage.setVisible(false);
                }
                ownerLogin.setVisible(false);
                ownerLogin.nameField.setSelectedItem("");
                ownerLogin.passField.setText("");

            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        //NEW
        userIconLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cart.setVisible(false);
                pane.setVisible(false);
                jList.setEnabled(false);
                search.setEnabled(false);
                searchButton.setEnabled(false);
                priceSort.setEnabled(false);
                if(stock != null) {
                    stock.setVisible(false);
                }
                ownerLogin.setVisible(false);
                ownerLogin.nameField.setSelectedItem("");
                ownerLogin.passField.setText("");

                //logInUser("Shreeya");
                if(!loggedInUser.equals("")) {
                    repository.deliverItemsToUser(loggedInUser);
                    userHomePage.clearWishListTable();
                    ArrayList<ItemModel> list = repository.getUserWishListData(loggedInUser);
                    list.forEach(userHomePage::addWishListTableRow);
                    ArrayList<UserReportModel> buyList = repository.getUserBuyListData(repository.getUserBuyListName(loggedInUser));
                    ArrayList<UserReportModel> deliveredList = repository.getUserBuyListData(repository.getUserDeliveredListName(loggedInUser));
                    buyList.forEach(value -> {
                                userHomePage.addBuyListTableRow(value,false);
                            }
                    );
                    deliveredList.forEach(value -> {
                        userHomePage.addBuyListTableRow(value,true);
                    });
                    userHomePage.setVisible(true);
                }else{
                    userLogin.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //NEW
        storeIconLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                cart.setVisible(false);
                pane.setVisible(false);
                jList.setEnabled(false);
                search.setEnabled(false);
                searchButton.setEnabled(false);
                priceSort.setEnabled(false);
                if (stock != null) {
                    stock.setVisible(false);
                }
                if(userHomePage != null) {
                    userHomePage.setVisible(false);
                }
                userLogin.setVisible(false);
                ownerLogin.nameField.setSelectedItem("");
                ownerLogin.passField.setText("");
                ownerLogin.setVisible(true);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        jList.setSelectionModel(new DefaultListSelectionModel() {
            public void setSelectionInterval(int i, int j) {
                if (i != 0 || j != 0) {
                    if (i == 0) i = 1;
                    if (j == 0) j = 1;
                    super.setSelectionInterval(i, j);
                }
            }
            public void addSelectionInterval(int i, int j) {
                if (i != 0 || j != 0) {
                    if (i == 0) i = 1;
                    if (j == 0) j = 1;
                    super.addSelectionInterval(i, j);
                }
            }
            public void setLeadSelectionIndex(int i) {
                if (i != 0) super.setLeadSelectionIndex(i);
            }
            public void setAnchorSelectionIndex(int i) {
                if (i != 0) super.setAnchorSelectionIndex(i);
            }
        });

        jList.addListSelectionListener((ListSelectionEvent e) -> {
                try {
                    filteredListV2.clear();
                    String selectedValue = jList.getSelectedValue();
                    priceSort.setSelectedItem("");
                    if(filteredList.size() == 0 && search.getText().equals("")) {
                        list.forEach(value -> {
                            if (value.getType().toLowerCase().equals(selectedValue.toLowerCase())) {
                                filteredListV2.add(value);
                            }
                        });

                    }else{
                        filteredList.forEach(value -> {
                            if (value.getType().toLowerCase().equals(selectedValue.toLowerCase())) {
                                filteredListV2.add(value);
                            }
                        });
                    }
                    panels = buildPanel(filteredListV2);
                    pane.setViewportView(panels);
                }catch(Exception exception){
                    //exception.printStackTrace();
                }
            }
        );

        searchButton.addActionListener((ActionEvent e) -> {
                List<ItemModel> filteredListForSearch = new ArrayList<>();
                priceSort.setSelectedItem("");
                jList.clearSelection();
                filteredList.clear();
                filteredListV2.clear();

                if(search.getText().equals("")){
                    panels = buildPanel(list);
                    pane.setViewportView(panels);
                }else{
                    String searchData = search.getText();
                    list.forEach(value -> {
                        if(value.getItem().toLowerCase().contains(searchData.toLowerCase())){
                            filteredListForSearch.add(value);
                        }
                    });
                    filteredList = filteredListForSearch;
                    panels = buildPanel(filteredList);
                    pane.setViewportView(panels);
                }
            }
        );

        priceSort.addItemListener((ItemEvent e) -> {
                String selectedItem = priceSort.getSelectedItem().toString();
                List<ItemModel> listPicker = filteredListV2.size() !=  0? filteredListV2 : (filteredList.size() != 0? filteredList : list);
                if(selectedItem.equals("Low to High")) {
                    Collections.sort(listPicker, ItemModel.PriceComparatorL2H);
                    panels = buildPanel(listPicker);
                    pane.setViewportView(panels);
                }else if(selectedItem.equals("High to Low")){
                    Collections.sort(listPicker, ItemModel.PriceComparatorH2L);
                    panels = buildPanel(listPicker);
                    pane.setViewportView(panels);
                }
            }
        );
    }

    static String getLoggedInUser(){
        return loggedInUser;
    }
    static void setLoggedInUser(String loggedInUser) { Home.loggedInUser = loggedInUser;}
}