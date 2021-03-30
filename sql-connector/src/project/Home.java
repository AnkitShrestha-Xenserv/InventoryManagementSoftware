package project;

import javax.swing.*;

import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;

public class Home {
	JFrame frame2=new JFrame();
	JMenuBar menuBar=new JMenuBar();
	void home(){
		
		JMenu entry=new JMenu("Entry");
		JMenuItem stockEntry=new JMenuItem("Stock Entry");
		JMenuItem salesEntry=new JMenuItem("Sales Entry");
		JMenu report=new JMenu("Report");
		JMenu admin=new JMenu("Admin");
		JMenu help=new JMenu("Help");
		JMenu exit=new JMenu("Exit");
		JMenuItem exitToLogin=new JMenuItem("Exit To Login Screen");
		JMenuItem exitToDesktop=new JMenuItem("Exit To Desktop");
		
		menuBar.add(entry);
		menuBar.add(report);
		menuBar.add(admin);
		menuBar.add(help);
		menuBar.add(exit);
		entry.add(stockEntry);
		entry.add(salesEntry);
		exit.add(exitToLogin);
		exit.add(exitToDesktop);
		frame2.setTitle("Home");
		
		JLabel head=new JLabel("INVENTORY AND BILLING SYSTEM");
		JLabel side=new JLabel("STAR");
		JLabel side2=new JLabel("HARDWARE");
		Font font=new Font("Times New Roman",Font.BOLD,30);
		Font font2=new Font("Time New Roman",Font.BOLD,20);
		head.setBounds(200,6,1000,100);
		side.setBounds(150,130,300,100);
		side2.setBounds(120,160,500,100);
		head.setFont(font);
		side.setFont(font2);
		side2.setFont(font2);
		head.setForeground(Color.green);
		frame2.add(head);
		frame2.add(side);
		frame2.add(side2);
		
		JTable table=new JTable();
		JScrollPane pane=new JScrollPane();
		pane.getViewport().add(table);
		pane.setBounds(450,100,400,300);
		frame2.add(pane);
		
		Font font3=new Font("Segoe UI",Font.BOLD,15);
		@SuppressWarnings("serial")
		DefaultTableModel model=new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		Object columns[]= {"Item","Bundle","Quantity"};
		
		
		table.getTableHeader().setReorderingAllowed(false);
		
		table.getTableHeader().setFont(font3);;
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Color.cyan);
		table.setRowHeight(25);
		table.setShowVerticalLines(false);
		table.setFocusable(false);
		table.setSelectionBackground(Color.yellow);
		model.setColumnIdentifiers(columns);
		
		table.setModel(model);		
		
		try{
			String DB_url="jdbc:mysql://localhost/StarHardwareDB";
			String sql;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(DB_url, "root", "");
			sql="SELECT * from stock";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			Object row[]=new Object[3];
			while(rs.next()) {
				row[0]=rs.getString("Item");
				row[1]=rs.getInt("Bundle");
				row[2]=rs.getInt("Quantity");
				model.addRow(row);
			}
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		frame2.setLayout(null);
		frame2.setJMenuBar(menuBar);
		frame2.setSize(900, 500);
		frame2.setVisible(true);
		frame2.setLocationRelativeTo(null);
		frame2.setFocusable(true);
        frame2.setFocusTraversalKeysEnabled(false);
		
		exitToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] args = null;
				Login.main(args);
				frame2.dispose();
			}
		});
		
		exitToDesktop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame2.dispose();
			}
		});
		
		salesEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalesEntry salesEntry=new SalesEntry();
				salesEntry.salesEntry();
			}
		});
		
		stockEntry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StockEntry sales=new StockEntry();
				sales.stockEntry();
			}
			
		});
		
	}

}
