package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SalesEntry {
	JFrame frame4=new JFrame();
	double rate=93.79;
	void salesEntry(){
		frame4.setTitle("Sales Entry Form");
		JLabel l1=new JLabel("Sales Entry");
		JLabel l2=new JLabel("Customer Information");
		JLabel l3=new JLabel("Bill no:");
		JLabel payment=new JLabel("Payment");
		JLabel vatNo=new JLabel("Vat no.");
		JLabel phone=new JLabel("Phone:");
		JTextField vatNoField=new JTextField(20);
		JTextField phoneField=new JTextField(20);
		
		Object []mat= {"Iron","Cement"};
		Object []pay= {"Cash","Cheque"};
		@SuppressWarnings("unchecked")
		JComboBox<String> box=new JComboBox(mat);
		JComboBox<String> boxPayment=new JComboBox(pay);
		JLabel category=new JLabel("Category");
		JTextField t1=new JTextField(10);
		LocalDate date=LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		JLabel l4=new JLabel("Date :          "+date.format(formatter));
		JLabel l5=new JLabel("Item Details:");
		JLabel l6=new JLabel("Item");
		JLabel l7=new JLabel("Bundle");
		JLabel l8=new JLabel("Quantity");
		
		JLabel total=new JLabel("Total");
		JLabel discount=new JLabel("Discount | 0.59%");
		JLabel subTotal=new JLabel("Sub Total");
		JLabel exciseDuty=new JLabel("Excise Duty");
		JLabel taxableAmt=new JLabel("Taxable Amount");
		JLabel vat=new JLabel("VAT");
		JLabel totalAmount=new JLabel("Total Amount");
		
		JTextField totalField=new JTextField(20);
		JTextField discountField=new JTextField(20);
		JTextField subTotalField=new JTextField(20);
		JTextField exciseDutyField=new JTextField(20);
		JTextField taxableAmtField=new JTextField(20);
		JTextField vatField=new JTextField(20);
		JTextField totalAmountField=new JTextField(20);
		
		total.setBounds(360,380,100,50);
		totalField.setBounds(470,395,100,20);
		discount.setBounds(360,400,100,50);
		discountField.setBounds(470,415,100,20);
		subTotal.setBounds(360,420,100,50);
		subTotalField.setBounds(470,435,100,20);
		exciseDuty.setBounds(360,440,100,50);
		exciseDutyField.setBounds(470,455,100,20);
		taxableAmt.setBounds(360,460,100,50);
		taxableAmtField.setBounds(470,475,100,20);
		vat.setBounds(360,480,100,50);
		vatField.setBounds(470,495,100,20);
		totalAmount.setBounds(360,500,100,50);
		totalAmountField.setBounds(470,515,100,20);
		
		frame4.add(total);
		frame4.add(totalField);
		frame4.add(discount);
		frame4.add(subTotal);
		frame4.add(exciseDuty);
		frame4.add(taxableAmt);
		frame4.add(vat);
		frame4.add(totalAmount);
		
		frame4.add(discountField);
		frame4.add(subTotalField);
		frame4.add(exciseDutyField);
		frame4.add(taxableAmtField);
		frame4.add(vatField);
		frame4.add(totalAmountField);
		
		
		JTextField item=new JTextField(35);
		JTextField bundle=new JTextField(35);
		JTextField quantity=new JTextField(35);
		JButton add=new JButton("Add");
		JButton delete=new JButton("Delete");
		JButton next=new JButton("Next");
		JButton clear=new JButton("Clear");
		JButton close=new JButton("Close");
		
		add.setBounds(30,600,100,20);
		delete.setBounds(130,600,100,20);
		clear.setBounds(280,600,100,20);
		next.setBounds(380,600,100,20);
		close.setBounds(520,600,100,20);
		
		category.setBounds(250,60,100,50);
		box.setBounds(310,75,100,20);
		payment.setBounds(250,80,200,50);
		boxPayment.setBounds(310,95,100,20);
		
		vatNo.setBounds(450,60,100,50);
		vatNoField.setBounds(500,80,100,20);
		phone.setBounds(450,80,100,50);
		phoneField.setBounds(500,100,100,20);
		
		l4.setBounds(80,80,200,50);
		
		l1.setBounds(50,20,100,50);
		l2.setBounds(70,40,200,50);
		l3.setBounds(80,60,100,50);
		
		l5.setBounds(50,180,100,50);
		
		l6.setBounds(120,120,150,50);
		item.setBounds(50,160,165,20);
		l7.setBounds(280,120,150,50);
		bundle.setBounds(215,160,170,20);
		l8.setBounds(450,120,150,50);
		quantity.setBounds(385,160,165,20);
		
		frame4.add(add);
		frame4.add(delete);
		frame4.add(clear);
		frame4.add(next);
		frame4.add(close);
		frame4.add(l1);
		frame4.add(l2);
		frame4.add(l3);
		frame4.add(t1);
		frame4.add(category);
		frame4.add(box);
		frame4.add(payment);
		frame4.add(boxPayment);
		frame4.add(vatNo);
		frame4.add(vatNoField);
		frame4.add(phone);
		frame4.add(phoneField);
		frame4.add(l4);
		frame4.add(l5);
		frame4.add(l6);
		frame4.add(l7);
		frame4.add(l8);
		frame4.add(item);
		frame4.add(bundle);
		frame4.add(quantity);
		
		JTable table=new JTable();
		JScrollPane pane=new JScrollPane();
		pane.setBounds(50,220,550,150);
		pane.getViewport().add(table);
		DefaultTableModel model=new DefaultTableModel();
		Object columns[]= {"Item","Bundle","Quantity","Rate","Amount","Excise Amount"};
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		Object []row=new Object[6];
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(item.getText().equals("") || bundle.getText().equals("") || quantity.getText().equals("")) {
				
				}else {
					row[0]=item.getText();
					row[1]=bundle.getText();
					row[2]=quantity.getText();
					row[3]=rate;
					row[4]=Float.parseFloat(quantity.getText())*rate;
					row[5]=Float.parseFloat(quantity.getText())*1.5;
					model.addRow(row);
					item.setText("");
					bundle.setText("");
					quantity.setText("");
					item.requestFocus();
					String total2=Double.toString(Float.parseFloat(quantity.getText())*rate);
					totalField.setText(total2);
				}
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
				frame4.dispose();
				
			}
		});
		
		frame4.getRootPane().setDefaultButton(add);
		frame4.add(pane);
		frame4.setSize(650, 700);
		frame4.setLocationRelativeTo(null);
		frame4.setLayout(null);
		frame4.setVisible(true);
	}
}
