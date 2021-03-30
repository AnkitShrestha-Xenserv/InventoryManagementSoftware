import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Ankit on 7/24/2020.
 */
class Register {

    private Repository repository = new Repository();
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JLabel storeName = new JLabel("Store Name");
    private JLabel storeOwnerName = new JLabel("Store Owner Name");
    private JLabel phoneNumber = new JLabel("Phone Number");
    private JLabel address = new JLabel("Address");
    private JLabel password = new JLabel("Password");
    private JLabel retypePassword = new JLabel("Retype Password");

    private JTextField storeNameField = new JTextField();
    private JTextField storeOwnerNameField = new JTextField();
    private JTextField phoneNumberField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField passwordField = new JTextField();
    private JTextField retypePasswordField = new JTextField();

    void register(Home home){

        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener((ActionEvent e) -> {
                if(checkEmpty()){
                    JOptionPane.showMessageDialog(frame,"Please Fill out all The Fields");
                }else{
                    if(!repository.checkDuplicateStoreName(storeNameField.getText())){
                        JOptionPane.showMessageDialog(frame,"Store Name Already Exists");
                    }else{
                        if(phoneNumberField.getText().length()<7 || phoneNumberField.getText().length()>10){
                            JOptionPane.showMessageDialog(frame,"Invalid Phone Number");
                        }else{
                            if(!passwordField.getText().equals(retypePasswordField.getText())){
                                JOptionPane.showMessageDialog(frame,"Passwords do not Match");
                            }else{
                                if(!(repository.addStoreData(new StoreModel(
                                        storeNameField.getText(),
                                        storeOwnerNameField.getText(),
                                        phoneNumberField.getText(),
                                        addressField.getText(),
                                        passwordField.getText()))>0)){
                                    JOptionPane.showMessageDialog(frame, "Data Insertion Failed");
                                }else {
                                        repository.createStoreAccount(storeNameField.getText());
                                        home.buildLogin();
                                        frame.dispose();
                                }
                            }
                        }
                    }
                }

            }
        );

        panel.add(storeName);
        panel.add(storeNameField);
        panel.add(storeOwnerName);
        panel.add(storeOwnerNameField);
        panel.add(phoneNumber);
        panel.add(phoneNumberField);
        panel.add(address);
        panel.add(addressField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(retypePassword);
        panel.add(retypePasswordField);
        panel.add(confirmButton);

        panel.setBounds(10,10,400,500);
        panel.setLayout(new GridLayout(13,1,1,5));
        panel.setVisible(true);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.BLACK),new EmptyBorder(10,10,10,10)));

        frame.setLayout(null);
        frame.add(panel);
        frame.setSize(450,580);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private boolean checkEmpty(){
        return(storeNameField.getText().equals("") ||
                storeOwnerNameField.getText().equals("") ||
                phoneNumberField.getText().equals("") ||
                addressField.getText().equals("") ||
                passwordField.getText().equals("") ||
                retypePasswordField.getText().equals(""));
    }
}