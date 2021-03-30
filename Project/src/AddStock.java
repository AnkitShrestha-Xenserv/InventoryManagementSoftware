import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 7/24/2020.
 */
class AddStock extends JPanel {
    private static int SUCCESS = 1;
    private static int FLAG = 0;
    private final String storeName;
    private JTable table = new JTable();
    private JTable table2 = new JTable();
    private Object []row2 = new Object[5];
    private DefaultTableModel model2 = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){return false;}
    };
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private Repository repository = new Repository();
    private JPanel mainPanel = new JPanel();

    AddStock(String storeName){
        this.storeName = storeName;

        mainPanel.setVisible(true);

        buildFirstPanel();
        buildSecondPanel();

        mainPanel.add(panel1);
        mainPanel.add(panel2);

        mainPanel.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));
        mainPanel.setBounds(10,10,1440,810);
        mainPanel.setLayout(null);

        add(mainPanel);

        setLayout(null);
        setPreferredSize(new Dimension(1460,800));
    }

    private void buildSecondPanel(){
        panel2.setVisible(true);
        Repository repository = new Repository();

        List<ItemModel> list = repository.getParticularStoreData(storeName);

        JLabel availableStockLabel = new JLabel("Available stock");
        availableStockLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JButton salesReportButton = new JButton("Sales Report");

        JScrollPane pane = new JScrollPane();

        table2.getTableHeader().setReorderingAllowed(false);
        Object []columns = {"No.", "Item", "Quantity", "Price", "Type"};
        model2.setColumnIdentifiers(columns);

        table2.setModel(model2);
        table2.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 25));
        table2.getTableHeader().setOpaque(true);
        table2.getTableHeader().setBackground(new Color(32, 136, 203));
        table2.getTableHeader().setForeground(new Color(255,255,255));
        table2.setRowHeight(25);
        table2.setFocusable(false);
        table2.setIntercellSpacing(new Dimension(0,0));
        table2.setSelectionBackground(new Color(232,57,95));
        table2.setShowVerticalLines(false);

        list.forEach(value -> {
            row2[0] = table2.getRowCount() + 1;
            row2[1] = value.getItem();
            row2[2] = value.getQuantity();
            row2[3] = value.getPrice();
            row2[4] = value.getType();
            model2.addRow(row2);
        });

        pane.getViewport().add(table2);

        pane.setBounds(30, 90, 650, 500);
        availableStockLabel.setBounds(260,20,200,50);
        salesReportButton.setBounds(300,650,200,50);

        salesReportButton.addActionListener((ActionListener)->{
            new StoreSalesReport(storeName);
        });

        panel2.add(salesReportButton);
        panel2.add(availableStockLabel);
        panel2.add(pane);
        panel2.setBounds(720,0,710,810);
        panel2.setLayout(null);
        panel2.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));

    }

    private void buildFirstPanel(){
        JLabel addStockLabel = new JLabel("Add Stock");

        JLabel itemLabel = new JLabel("Item");
        JLabel quantityLabel = new JLabel("Quantity");
        JLabel priceLabel = new JLabel("Price");
        JLabel typeLabel = new JLabel("Type");

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton resetButton = new JButton("Reset");
        JButton confirmButton = new JButton("Confirm");

        JTextField itemField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        List<String> types = new ArrayList<>();

        types.add("");
        List<ItemModel> list = repository.getAllStoreData();
        for(ItemModel model : list){
            types.add(model.getType());
        }
        AutoCompleteComboBox typeField = new AutoCompleteComboBox(types.stream().distinct().toArray());

        itemField.setFont(new Font("Serif", Font.BOLD, 16));
        priceField.setFont(new Font("Serif", Font.BOLD, 16));
        quantityField.setFont(new Font("Serif", Font.BOLD, 16));

        addStockLabel.setFont(new Font("Serif", Font.BOLD, 30));

        itemLabel.setFont(new Font("Serif", Font.BOLD, 16));
        quantityLabel.setFont(new Font("Serif", Font.BOLD, 16));
        priceLabel.setFont(new Font("Serif", Font.BOLD, 16));
        typeLabel.setFont(new Font("Serif", Font.BOLD, 16));

        addButton.setFont(new Font("Serif", Font.BOLD, 16));
        deleteButton.setFont(new Font("Serif", Font.BOLD, 16));
        resetButton.setFont(new Font("Serif", Font.BOLD, 16));
        confirmButton.setFont(new Font("Serif", Font.BOLD, 16));

        JScrollPane pane = new JScrollPane();

        pane.getViewport().add(table);

        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column){return false;}
        };
        table.getTableHeader().setReorderingAllowed(false);
        Object columns[] ={"No.", "Items", "Quantity", "Price", "Type"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        Object row[] = new Object[5];

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setSelectionBackground(new Color(232,57,95));
        table.setShowVerticalLines(false);

        addButton.addActionListener((ActionEvent e) -> {
                    if(!(itemField.getText().equals("") ||
                            quantityField.getText().equals("") ||
                            priceField.getText().equals("") ||
                            typeField.getSelectedItem().equals(""))){
                        if(repository.checkDuplicateItemWithDifferentPrice(
                                storeName,
                                itemField.getText(),
                                priceField.getText()) ||
                                checkDuplicateInTable(
                                        itemField.getText(),
                                        priceField.getText())
                                ) {
                            JOptionPane.showMessageDialog(AddStock.super.getComponent(0), "Same Items cannot have Different Prices");
                        }else if(repository.checkDuplicateItemWithDifferentType(
                                storeName,
                                itemField.getText(),
                                typeField.getSelectedItem().toString()) ||
                                checkDuplicateTypeInTable(itemField.getText(),
                                        typeField.getSelectedItem().toString())){
                            JOptionPane.showMessageDialog(AddStock.super.getComponent(0), "Same Items cannot have Different Types");

                        }else if(checkDuplicateItemInTableAndAdd(itemField.getText()) > -1){
                            int dup = checkDuplicateItemInTableAndAdd(itemField.getText());
                            model.setValueAt(Double.parseDouble(model.getValueAt(dup,2).toString()) + Double.parseDouble(quantityField.getText()),dup,2);
                        }else{
                            row[0] = table.getRowCount() + 1;
                            row[1] = itemField.getText();
                            row[2] = quantityField.getText();
                            row[3] = priceField.getText();
                            row[4] = typeField.getSelectedItem();
                            model.addRow(row);
                            itemField.setText("");
                            quantityField.setText("");
                            priceField.setText("");
                            itemField.grabFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(AddStock.super.getComponent(0), "Please Complete the Form");
                    }
                }
        );

        deleteButton.addActionListener((ActionEvent e) -> {
                    int i = table.getSelectedRow();
                    if(i >= 0){
                        model.removeRow(i);
                    }
                }
        );

        resetButton.addActionListener((ActionEvent e) -> model.setRowCount(0));

        confirmButton.addActionListener((ActionEvent e) -> {

                    for(int i=0;i<table.getModel().getRowCount();i++){
                        ItemModel itemModel = new ItemModel(
                                storeName,
                                table.getModel().getValueAt(i,1).toString(),
                                Double.parseDouble(table.getModel().getValueAt(i,2).toString()),
                                Double.parseDouble(table.getModel().getValueAt(i,3).toString()),
                                String.valueOf(table.getModel().getValueAt(i,4)));
                        if(repository.addStoreItems(itemModel)==0){
                            SUCCESS = 0;
                        }
                    }

                    if(SUCCESS ==0){
                        JOptionPane.showMessageDialog(AddStock.super.getComponent(1), "Data Insertion Failed");
                        SUCCESS = 1;
                    }else {
                        JOptionPane.showMessageDialog(AddStock.super.getComponent(0), "Data Insertion Complete");
                        for(int i= 0 ; i<model.getRowCount(); i++){
                            for(int j = 0; j < model2.getRowCount(); j++){
                                if(model.getValueAt(i,1).toString().toLowerCase().equals(model2.getValueAt(j,1).toString().toLowerCase())){
                                    model2.setValueAt(Double.parseDouble(model.getValueAt(i,2).toString()) + Double.parseDouble(model2.getValueAt(j,2).toString()),j,2);
                                    FLAG = 1;
                                    break;
                                }
                            }
                            if(FLAG == 0) {
                                row2[0] = model2.getRowCount() + 1;
                                row2[1] = model.getValueAt(i, 1);
                                row2[2] = model.getValueAt(i, 2);
                                row2[3] = model.getValueAt(i, 3);
                                row2[4] = model.getValueAt(i, 4);
                                model2.addRow(row2);
                            }
                            FLAG = 0;
                        }
                        model.setRowCount(0);
                    }
                }
        );

        panel1.setVisible(true);

        panel1.add(addStockLabel);
        panel1.add(itemLabel);
        panel1.add(quantityLabel);
        panel1.add(priceLabel);
        panel1.add(itemField);
        panel1.add(quantityField);
        panel1.add(priceField);
        panel1.add(typeLabel);
        panel1.add(typeField);
        panel1.add(addButton);

        addStockLabel.setBounds(260,20,200,50);
        itemLabel.setBounds(40,80,200,40);
        itemField.setBounds(220,80,200,40);
        quantityLabel.setBounds(40,130,200,40);
        quantityField.setBounds(220,130,200,40);
        priceLabel.setBounds(40,180,200,40);
        priceField.setBounds(220,180,200,40);
        typeLabel.setBounds(40,230,200,40);
        typeField.setBounds(220,230,200,40);

        addButton.setBounds(500,230,150,50);

        pane.setBounds(40,300,660,200);
        deleteButton.setBounds(300, 520, 150, 50);
        resetButton.setBounds(50,520,150,50);
        confirmButton.setBounds(550,520,150,50);


        panel1.add(deleteButton);
        panel1.add(resetButton);
        panel1.add(confirmButton);
        panel1.add(pane);

        panel1.setBounds(10,0,720,810);
        panel1.setLayout(null);
        panel1.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));

    }

    private boolean checkDuplicateTypeInTable(String item, String type){
        for(int i = 0 ; i<table.getModel().getRowCount(); i++){
            if(table.getModel().getValueAt(i,1).toString().toLowerCase().equals(item.toLowerCase()) && !table.getModel().getValueAt(i,4).toString().toLowerCase().equals(type.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private boolean checkDuplicateInTable(String item, String price){
        for(int i=0;i<table.getModel().getRowCount();i++){
            if(table.getModel().getValueAt(i,1).toString().toLowerCase().equals(item.toLowerCase()) &&
                    !(table.getModel().getValueAt(i,3).equals(price))){
                return true;
            }
        }
        return false;
    }
    private int checkDuplicateItemInTableAndAdd(String item){
        for(int i=0;i<table.getModel().getRowCount();i++){
            if(table.getModel().getValueAt(i,1).toString().toLowerCase().equals(item.toLowerCase())){
                return i;
            }
        }
        return -1;
    }
}