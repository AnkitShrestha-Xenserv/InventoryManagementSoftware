import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Ankit on 8/12/2020.
 */
class Cart extends JPanel {

    private static int TRANSACTION = 1;
    JTable table = new JTable();
    private Repository repository = new Repository();
    private Object row[] = new Object[8];
    private DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){return false;}
    };
    Cart(){
        JButton deleteButton = new JButton("Delete");
        JButton resetButton = new JButton("Reset");
        JButton confirmButton = new JButton("Confirm");

        deleteButton.setFont(new Font("Serif", Font.BOLD, 16));
        resetButton.setFont(new Font("Serif", Font.BOLD, 16));
        confirmButton.setFont(new Font("Serif", Font.BOLD, 16));
        JScrollPane pane = new JScrollPane();

        pane.setBounds(40,180,1070,300);
        pane.getViewport().add(table);

        table.getTableHeader().setReorderingAllowed(false);

        Object columns[] ={"No.", "Items", "Quantity", "Price(per)", "Type", "Store Name", "Total", "Total Quantity"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.removeColumn(table.getColumnModel().getColumn(7));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setSelectionBackground(new Color(232,57,95));
        table.setShowVerticalLines(false);

        deleteButton.addActionListener((ActionEvent e) -> {
                    int i = table.getSelectedRow();
                    if(i >= 0){
                        model.removeRow(i);
                    }
                }
        );

        resetButton.addActionListener((ActionEvent e) -> model.setRowCount(0));

        confirmButton.addActionListener((ActionEvent e) -> {
            for(int i=0 ; i<model.getRowCount(); i++){
                String item = model.getValueAt(i,1).toString();
                String storeName = model.getValueAt(i,5).toString();
                Double quantity = Double.parseDouble(model.getValueAt(i,2).toString());
                Double totalQuantity = Double.parseDouble(model.getValueAt(i, 7).toString());
                int success = repository.transact(item,storeName,quantity,totalQuantity);
                if(success == 0){
                    TRANSACTION = 0;
                }
            }
            if(TRANSACTION == 1){
                JOptionPane.showMessageDialog(Cart.super.getComponent(0), "Transaction Successful");
                model.setRowCount(0);
            }else{
                JOptionPane.showMessageDialog(Cart.super.getComponent(0), "Transaction Failed");
            }
        }
        );

        deleteButton.setBounds(400, 500, 150, 50);
        resetButton.setBounds(50,500,150,50);
        confirmButton.setBounds(650,500,150,50);

        add(pane);
        add(deleteButton);
        add(resetButton);
        add(confirmButton);

        setVisible(true);
        setLayout(null);
    }

    void addTableRow(String itemName, String quantity, String price, String type, String storeName, String totalQuantity){
        row[0] = table.getRowCount() + 1;
        row[1] = itemName;
        row[2] = quantity;
        row[3] = price;
        row[4] = type;
        row[5] = storeName;
        row[6] = String.valueOf(Double.parseDouble(quantity) * Double.parseDouble(price));
        row[7] = totalQuantity;
        model.addRow(row);
    }
}