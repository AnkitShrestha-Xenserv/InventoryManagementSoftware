package project;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.*;

public class StockEntry {
	
	JFrame frame3=new JFrame();
	void stockEntry()  {
		frame3.setTitle("Stock Entry Form");
		JLabel l1=new JLabel("Stock Entry");
		JLabel l2=new JLabel("Stock Information");
		JLabel l3=new JLabel("No:");
		
		Object []msg= {"Iron","Copper","Aluminium","Tungsten"};
		@SuppressWarnings("unchecked")
		JComboBox<String> box=new JComboBox(msg);
		JLabel category=new JLabel("Category");
		JTextField t1=new JTextField(10);
		LocalDate date=LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		JLabel l4=new JLabel("Date :          "+date.format(formatter));
		JLabel l5=new JLabel("Item Details:");
		JLabel l6=new JLabel("Item");
		JLabel l7=new JLabel("Bundle");
		JLabel l8=new JLabel("Quantity");
		JTextField item=new JTextField(35);
		JTextField bundle=new JTextField(35);
		JTextField quantity=new JTextField(35);
		JButton add=new JButton("Add");
		JButton delete=new JButton("Delete");
		JButton next=new JButton("Next");
		JButton clear=new JButton("Clear");
		JButton close=new JButton("Close");
		
		add.setBounds(30,400,100,20);
		delete.setBounds(130,400,100,20);
		clear.setBounds(280,400,100,20);
		next.setBounds(380,400,100,20);
		close.setBounds(520,400,100,20);
		
		box.setBounds(510,75,100,20);
		category.setBounds(450,60,100,50);
		l4.setBounds(450,80,200,50);
		
		l1.setBounds(50,20,100,50);
		l2.setBounds(50,40,200,50);
		l3.setBounds(50,60,100,50);
		t1.setBounds(70,80,100,15);
		
		l5.setBounds(50,180,100,50);
		
		l6.setBounds(120,120,150,50);
		item.setBounds(50,160,165,20);
		l7.setBounds(280,120,150,50);
		bundle.setBounds(215,160,170,20);
		l8.setBounds(450,120,150,50);
		quantity.setBounds(385,160,165,20);
		
		frame3.add(add);
		frame3.add(delete);
		frame3.add(clear);
		frame3.add(next);
		frame3.add(close);
		frame3.add(l1);
		frame3.add(l2);
		frame3.add(l3);
		frame3.add(t1);
		frame3.add(category);
		frame3.add(box);
		frame3.add(l4);
		frame3.add(l5);
		frame3.add(l6);
		frame3.add(l7);
		frame3.add(l8);
		frame3.add(item);
		frame3.add(bundle);
		frame3.add(quantity);
		
		JTable table=new JTable();
		JScrollPane pane=new JScrollPane();
		pane.setBounds(50,220,550,150);
		pane.getViewport().add(table);
		DefaultTableModel model=new DefaultTableModel();
		Object columns[]= {"Item","Bundle","Quantity"};
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		Object []row=new Object[3];
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item.getText().equals("") || bundle.getText().equals("") || quantity.getText().equals("")) {
				
				}else {
					row[0]=item.getText();
					row[1]=bundle.getText();
					row[2]=quantity.getText();
					model.addRow(row);
					item.setText("");
					bundle.setText("");
					quantity.setText("");
					item.requestFocus();
				}
			}
		});

		next.addActionListener(new ActionListener() {
			String DB_url="jdbc:mysql://localhost/StarHardwareDB";
			public void actionPerformed(ActionEvent e) {
				try {
					 
					 int bundle,bundlee;
					 int quantity,quantityy;
					 String sql;
					 PreparedStatement stmt;
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection(DB_url, "root", "");
					String sql2="SELECT * from stock";
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(sql2);
					
					while (rs.next()) {
						 int i=0;
					
					for(i=0;i<table.getModel().getRowCount();i++) {
						
					if(table.getModel().getValueAt(i, 0).equals("8mm") && rs.getString("Item").equals("8mm")) {
						bundle=rs.getInt("bundle");
						quantity=rs.getInt("quantity");
						sql="update stock"
								+ " set bundle=? , quantity=?"
								+ " where item=?";
					 stmt=con.prepareStatement(sql);
						bundlee=bundle+Integer.parseInt((String)table.getModel().getValueAt(i, 1));
						quantityy=quantity+Integer.parseInt((String)table.getModel().getValueAt(i, 2));
					
						stmt.setInt(1,bundlee);
						stmt.setInt(2, quantityy);
						stmt.setString(3, "8mm");
						stmt.executeUpdate();
					}
					
				if(table.getModel().getValueAt(i, 0).equals("12mm") && rs.getString("Item").equals("12mm")) {
						bundle=rs.getInt("bundle");
						quantity=rs.getInt("quantity");
						sql="update stock"
							+ " set bundle=? , quantity=?"
							+ " where item=?";
						stmt=con.prepareStatement(sql);
						bundlee=bundle+Integer.parseInt((String)table.getModel().getValueAt(i, 1));
				 		quantityy=quantity+Integer.parseInt((String)table.getModel().getValueAt(i, 2));
			
				 		stmt.setInt(1,bundlee);
				 		stmt.setInt(2, quantityy);
						stmt.setString(3, "12mm");
						stmt.executeUpdate();
					}
				
				if(table.getModel().getValueAt(i, 0).equals("16mm") && rs.getString("Item").equals("16mm")) {
						bundle=rs.getInt("bundle");
						quantity=rs.getInt("quantity");
						sql="update stock"
							+ " set bundle=? , quantity=?"
							+ " where item=?";
						stmt=con.prepareStatement(sql);
						bundlee=bundle+Integer.parseInt((String)table.getModel().getValueAt(i, 1));
						quantityy=quantity+Integer.parseInt((String)table.getModel().getValueAt(i, 2));
				
						stmt.setInt(1,bundlee);
						stmt.setInt(2, quantityy);
						stmt.setString(3, "16mm");
						stmt.executeUpdate();
				}
			}
		}} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
		}
				frame3.dispose();
	}
	});
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
			}
		});
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame3.dispose();
				
			}
		});
		
		frame3.getRootPane().setDefaultButton(add);
		frame3.add(pane);
		frame3.setSize(650, 500);
		frame3.setLocationRelativeTo(null);
		frame3.setLayout(null);
		frame3.setVisible(true);
	}

}
