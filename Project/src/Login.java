import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by Ankit on 7/24/2020.
 */
public class Login extends JPanel{
    private static Home home = new Home();

    public static void main(String[] args) {
        home.home();
    }

    private Repository repository;
    private ArrayList<String> stores;
    JComboBox nameField;

    private JLabel nameLabel = new JLabel("Store Name");
    private JLabel passLabel = new JLabel("Password");
    JPasswordField passField = new JPasswordField();
    private JButton loginButton =new JButton("Login");
    private JButton registerButton = new JButton("Register");
    Login(){
        repository = fetchRepo();

        stores = repository.getStoreNames();
        nameField = new AutoCompleteComboBox(stores.toArray());

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

        nameLabel.setBounds((getWidth()+200)/2,110,200,30);
        nameField.setBounds((getWidth()+200)/2,140,200,30);
        passLabel.setBounds((getWidth()+200)/2,180,200,30);
        passField.setBounds((getWidth()+200)/2,210,200,30);
        loginButton.setBounds((getWidth()+200)/2,250,150,40);
        registerButton.setBounds((getWidth()+200)/2,300,150,40);

        setBorder(new CompoundBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.BLACK), new EmptyBorder(5,5,5,5)));

        add(nameLabel);
        add(nameField);
        add(passLabel);
        add(passField);
        add(loginButton);
        add(registerButton);

        setLayout(null);
        setPreferredSize(new Dimension(800,270));
        setVisible(true);
    }
    private Repository fetchRepo(){
        return new Repository();
    }
}