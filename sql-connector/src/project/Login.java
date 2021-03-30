package project;

import javax.swing.*;
import java.awt.event.*;

public class Login {
	public static void main(String[]args) {
		JFrame frame1=new JFrame();
		JLabel title=new JLabel("STAR HARDWARE");
		JLabel user=new JLabel("UserName");
		JLabel pass=new JLabel("Password");
		JTextField userText=new JTextField(10);
		JPasswordField passText=new JPasswordField(10);
		JButton login=new JButton("Log In");
		
		title.setBounds(90,10,200,20);
		user.setBounds(70,50,80,15);
		userText.setBounds(150,50,80,15);
		pass.setBounds(70,80,80,15);
		passText.setBounds(150,80,80,15);
		login.setBounds(100,110,80,20);
		
		
		frame1.setTitle("Log In");
		frame1.add(title);
		frame1.add(user);
		frame1.add(userText);
		frame1.add(pass);
		frame1.add(passText);
		frame1.add(login);
		frame1.getRootPane().setDefaultButton(login);
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home h=new Home();
				h.home();
				frame1.dispose();
			}
			
		});
		
		frame1.setLayout(null);;
		frame1.setSize(280,180);
		frame1.setVisible(true);
		frame1.setLocationRelativeTo(null);
	}
}
