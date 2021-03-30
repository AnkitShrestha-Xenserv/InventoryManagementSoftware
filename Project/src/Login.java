import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Created On: 7/24/2020.

public class Login extends JPanel{

    // CREATE ONE AND ONLY OBJECT OF HOME CLASS
    private static Home home = new Home();

    public static void main(String[] args) {
        home.home();
    }

    private Repository repository;
    JComboBox nameField;

    JPasswordField passField = new JPasswordField();

    Login(){
        ArrayList<String> stores;

        JLabel nameLabel = new JLabel("Store Name");
        JLabel passLabel = new JLabel("Password");

        JButton loginButton =new JButton("Login");
        JButton registerButton = new JButton("Register");

        repository = fetchRepo();

        stores = repository.getStoreNames();
        // ADD BLANK STRING SO THAT INITIAL SELECTION IS EMPTY
        stores.add(0,"");
        nameField = new AutoCompleteComboBox(stores.toArray());

        // ADD LISTENERS
        loginButton.addActionListener((ActionEvent e) -> {
                if(repository.checkStoreValidity(nameField.getSelectedItem().toString(), String.valueOf(passField.getPassword()))) {
                    home.logInStoreUser(nameField.getSelectedItem().toString());
                }else{
                    JOptionPane.showMessageDialog(Login.super.getComponent(1),"Incorrect Credentials");
                }
            }
        );
        registerButton.addActionListener((ActionEvent e) -> {
                Register register = new Register();
                register.register(home);
            }
        );

        // MANAGE POSITIONS
        nameLabel.setBounds((getWidth()+200)/2,110,200,30);
        nameField.setBounds((getWidth()+200)/2,140,200,30);
        passLabel.setBounds((getWidth()+200)/2,180,200,30);
        passField.setBounds((getWidth()+200)/2,210,200,30);
        loginButton.setBounds((getWidth()+200)/2,250,150,40);
        registerButton.setBounds((getWidth()+200)/2,300,150,40);


        // ADD WIDGETS TO SCREEN
        add(nameLabel);
        add(nameField);
        add(passLabel);
        add(passField);
        add(loginButton);
        add(registerButton);

        // SETUP THE PANEL
        setBorder(new CompoundBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.BLACK), new EmptyBorder(5,5,5,5)));
        setLayout(null);
        setPreferredSize(new Dimension(800,270));
        setVisible(true);
    }
    private Repository fetchRepo(){
        return new Repository();
    }
}