import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

// Created on: 3/2/2021
public class UserLogin extends JPanel {
    Repository repository = new Repository();

    private JLabel nameLabel = new JLabel("Username");
    private JTextField nameField = new JTextField();
    private JLabel passLabel = new JLabel("Password");
    private JPasswordField passField = new JPasswordField();
    private JButton loginButton =new JButton("Login");
    private JButton registerButton = new JButton("Register");

    UserLogin(Home home){
        repository = fetchRepo();

        // ADD LISTENERS
        addListener(home);

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

        // SETUP SCREEN
        setBorder(new CompoundBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.BLACK), new EmptyBorder(5,5,5,5)));
        setLayout(null);
        setPreferredSize(new Dimension(800,270));
        setVisible(true);
    }

    // HELPER METHODS

    private void addListener(Home home) {
        loginButton.addActionListener((ActionEvent e) -> {
                    if(repository.checkUserValidity(nameField.getText().toString(), String.valueOf(passField.getPassword()))) {
                        home.logInUser(nameField.getText().toString());
                        JOptionPane.showMessageDialog(UserLogin.super.getComponent(1),"Successfully Logged In");
                    }else{
                        JOptionPane.showMessageDialog(UserLogin.super.getComponent(1),"Incorrect Credentials");
                    }
                }
        );
        registerButton.addActionListener((ActionEvent e) -> {
                    UserRegister register = new UserRegister();
                    register.register(home);
                }
        );
    }

    private Repository fetchRepo(){
        return new Repository();
    }
}