import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class IndividualItem extends JPanel {
    private static int ADD_NEW_ROW = 1;
    private String storeName;
    private String item;
    private int quantity;
    private double price;
    private String type;
    private Cart cart;
    private static final int STATE_UP = 1;
    private static final int STATE_DOWN = 0;
    private int qty = 0;
    private int state = STATE_UP;
    final String path = "D:\\Programming\\Java\\InventoryManagementSoftware\\Project\\";
    private JButton buyBtn = new JButton("BUY");
    private JButton addToCartBtn = new JButton("Add to Cart");
    private JLabel itemLabel = new JLabel();
    private JLabel priceLabel = new JLabel();
    private ImageIcon wishlistIcon = new ImageIcon(path + "images\\heart.png");
    private JLabel wishlistLabel = new JLabel();
    private JTextField qtyField = new JTextField();
    private JButton addQtyField = new JButton("+");
    private JButton minusQtyField = new JButton("-");
    private JLabel qtyLabel = new JLabel("Quantity");
    private JLabel totalQtyLabel = new JLabel();
    private JLabel storeNameLabel = new JLabel();
    private final Repository repository = new Repository();

    IndividualItem(ItemModel itemModel, Cart cart) {
        this.storeName = itemModel.getStoreName();
        this.item = itemModel.getItem();
        this.quantity = (int)Math.round(itemModel.getQuantity());
        this.price = itemModel.getPrice();
        this.type = itemModel.getType();
        this.cart = cart;

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
              // DON'T ALLOW USERS TO INTERACT WITHOUT LOGGING IN
              if(!Home.getLoggedInUser().equals("")) {
                  flip();
                  repaint();
              }else{
                  JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Please Login to Continue");
              }
            }
        };
        // SETUP SCREEN
        setPreferredSize(new Dimension(280, 270));
        setBackground(Color.white);
        setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(4, 4, 4, 4, Color.BLACK),
                new EmptyBorder(10, 10, 10, 10)));
        setLayout(null);

        // ADD LISTENER
        addMouseListener(mouseAdapter);

        itemLabel.setText(item);
        priceLabel.setText("Price: " + price);
        qtyField.setText(String.valueOf(qty));
        totalQtyLabel.setText("In Stock: " + quantity);
        storeNameLabel.setText(storeName);
        wishlistLabel.setIcon(new ImageIcon(wishlistIcon.getImage().getScaledInstance(50, 80, Image.SCALE_DEFAULT)));

        // MANAGE POSITIONS
        wishlistLabel.setBounds(10, 10, 50, 80);

        itemLabel.setBounds(0, 20, 280, 40);
        priceLabel.setBounds(0, 40, 280, 40);

        minusQtyField.setBounds(60, 100, 50, 30);
        qtyField.setBounds(115, 100, 50, 30);
        addQtyField.setBounds(170, 100, 50, 30);
        qtyLabel.setBounds(0, 70, 280, 30);
        totalQtyLabel.setBounds(0, 140, 280, 40);
        storeNameLabel.setBounds(0, 160, 280, 40);

        buyBtn.setBounds((getWidth() + 180) / 2, 150, 100, 40);
        addToCartBtn.setBounds((getWidth() + 140) / 2, 200, 150, 40);

        // CENTER TEXT
        itemLabel.setHorizontalAlignment(JLabel.CENTER);
        priceLabel.setHorizontalAlignment(JLabel.CENTER);
        qtyLabel.setHorizontalAlignment(JLabel.CENTER);
        totalQtyLabel.setHorizontalAlignment(JLabel.CENTER);
        storeNameLabel.setHorizontalAlignment(JLabel.CENTER);
        wishlistLabel.setHorizontalAlignment(JLabel.CENTER);

        // ADD WIDGETS TO SCREEN
        add(wishlistLabel);
        add(itemLabel);
        add(priceLabel);
        add(minusQtyField);
        add(qtyField);
        add(addQtyField);
        add(qtyLabel);
        add(totalQtyLabel);
        add(storeNameLabel);
        add(addToCartBtn);

        // ADD LISTENERS
        addListeners(itemModel, cart);
    }

   // HELPER METHODS

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == STATE_UP) {
            wishlistLabel.setVisible(false);
            buyBtn.setVisible(false);
            addToCartBtn.setVisible(false);
            itemLabel.setVisible(false);
            priceLabel.setVisible(false);
            minusQtyField.setVisible(false);
            qtyField.setVisible(false);
            addQtyField.setVisible(false);
            qtyLabel.setVisible(false);
            totalQtyLabel.setVisible(false);
            storeNameLabel.setVisible(false);
            int y = getHeight();
            try {
                g.setFont(Font.createFont(
                        Font.TRUETYPE_FONT,
                        new FileInputStream(
                                new File(path + "\\static\\Recursive-Black-CASL=0-CRSV=0.5-MONO=0-slnt=0.ttf")))
                        .deriveFont(Font.PLAIN, 18)
                );
                BufferedImage img = imagePicker(item);
                if (img == null) img = imagePicker("imagenotfound");
                g.drawImage(img, (getWidth() - 150) / 2, (getHeight() - 220) / 2, 150, 150, null);
                g.drawString("In Stock: " + quantity, (getWidth() - g.getFontMetrics().stringWidth("In Stock: " + quantity)) / 2, y - 30);
                g.drawString(item, (getWidth() - g.getFontMetrics().stringWidth(item)) / 2, y - 70);
                g.drawString("Rs " + price, (getWidth() - g.getFontMetrics().stringWidth("Rs " + price)) / 2, y - 50);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        } else {
            wishlistLabel.setVisible(true);
            buyBtn.setVisible(true);
            addToCartBtn.setVisible(true);
            itemLabel.setVisible(true);
            priceLabel.setVisible(true);
            minusQtyField.setVisible(true);
            qtyField.setVisible(true);
            addQtyField.setVisible(true);
            qtyLabel.setVisible(true);
            totalQtyLabel.setVisible(true);
            storeNameLabel.setVisible(true);
        }
    }

    private void flip() {
        if (state == STATE_UP) {
            state = STATE_DOWN;
        } else {
            state = STATE_UP;
        }
    }

    private BufferedImage imagePicker(String item) {
        String path = "D:\\Programming\\Java\\InventoryManagementSoftware\\Project\\images";
        try {
            File folder = new File(path);
            File[] files = folder.listFiles();
            String itemName = item.toLowerCase() + ".jpg";
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().equals(itemName.replaceAll(" ", ""))) {
                    return ImageIO.read(new File(path + "\\" + itemName.replaceAll(" ", "")));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    // LISTENERS
    private void addListeners(final ItemModel itemModel, Cart cart) {
        wishlistLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (repository.checkDuplicateItemInWishList(itemModel, Home.getLoggedInUser())) {
                    JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Item Already in Wishlist");
                    return;
                }
                if (repository.addItemToWishList(itemModel, Home.getLoggedInUser()) == 1) {

                    JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Added to wishlist");
                } else {
                    JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Failed");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addToCartBtn.addActionListener((ActionEvent e) -> {
            try {
                qty = Integer.parseInt(qtyField.getText());
                if (qty <= 0) {
                    String msg = "<html><body width='1%s'><h2>Please select 1 or more Quantity</h2><br>";
                    JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), msg);
                }
                if (qty > quantity) {
                    String msg = "<html><body width='1%s'><h2>Value exceeds available Quantity</h2><br>"
                            + "<p>Quantity Available : " + quantity;
                    JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), msg);
                    return;
                }
                TableModel model = cart.table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 1).equals(item) && model.getValueAt(i, 5).equals(storeName)) {
                        if ((Double.parseDouble(model.getValueAt(i, 2).toString()) + qty) > quantity) {
                            String msg = "<html><body width='1%s'><h2>Value exceeds available Quantity</h2><br>"
                                    + "<p>Quantity Available : " + quantity;
                            JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), msg);
                        } else {
                            double qtyNow = qty + Double.parseDouble(model.getValueAt(i, 2).toString());
                            model.setValueAt(qtyNow, i, 2);
                            model.setValueAt(qtyNow*price,i,6);
                        }
                        ADD_NEW_ROW = 0;
                        break;
                    }
                }
                if (ADD_NEW_ROW == 1) {
                    cart.addTableRow(item, String.valueOf(qty), String.valueOf(price), type, storeName, String.valueOf(quantity));
                    JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Added To Cart");
                }
                ADD_NEW_ROW = 1;
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Value must Be an Integer");
            }

        });

        addQtyField.addActionListener((ActionEvent e) -> {
                    try {
                        qty = Integer.parseInt(qtyField.getText());
                        if (qty >= quantity) {
                            String msg = "<html><body width='1%s'><h2>Value exceeds available Quantity</h2><br>"
                                    + "<p>Quantity Available : " + quantity;
                            JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), msg);
                        } else {
                            qty++;
                            qtyField.setText(String.valueOf(qty));
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Value must Be an Integer");
                    }
                }
        );

        minusQtyField.addActionListener((ActionEvent e) -> {
                    try {
                        qty = Integer.parseInt(qtyField.getText());
                        if (qty > 0) {
                            qty--;
                            qtyField.setText(String.valueOf(qty));
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(IndividualItem.super.getComponent(1), "Value must Be an Integer");
                    }
                }
        );
    }
}