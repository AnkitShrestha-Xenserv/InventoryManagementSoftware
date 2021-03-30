import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ankit on 3/28/2021.
 */
public class StoreSalesReport extends JFrame {
    private final Repository repository;
    private final String storeName;
    private Object []row;
    private int count = 0;
    private final DefaultTableModel model;

    StoreSalesReport(String storeName){
        repository = new Repository();
        ArrayList<StoreReportModel> storeReportModelList = repository.getStoreReportData(storeName);
        this.storeName = storeName;

        JLabel salesReportLabel = new JLabel("Sales Report");
        salesReportLabel.setFont(new Font("Serif", Font.BOLD, 30));

        setTitle("Sales Report");
        setResizable(false);
        JTable table = new JTable();
        Object[]columns = {"Sn","Item","Quantity","Price","Type","Sales Date","Buyer Name","Buyer Address", "Buyer Phone Number"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        row = new Object[9];
        storeReportModelList.forEach(this::addTableRow);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int x=0;x<9;x++){
            table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 22));
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setSelectionBackground(new Color(232,57,95));
        table.setShowVerticalLines(false);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(500);
        table.getColumnModel().getColumn(2).setMaxWidth(400);
        table.getColumnModel().getColumn(3).setMaxWidth(150);
        table.getColumnModel().getColumn(4).setMaxWidth(250);

        JScrollPane pane=new JScrollPane();

        pane.getViewport().add(table);
        pane.setBounds(20,50,1460,600);
        salesReportLabel.setBounds(600,10,200,40);

        add(pane);
        add(salesReportLabel);

        setSize(1500,700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    void addTableRow(StoreReportModel storeReportModel){
        count++;
        row[0] = count;
        row[1] = storeReportModel.getItem();
        row[2] = storeReportModel.getQuantity();
        row[3] = storeReportModel.getPrice();
        row[4] = storeReportModel.getType();
        row[5] = storeReportModel.getTimestamp();
        row[6] = storeReportModel.getBuyerName();
        row[7] = storeReportModel.getBuyerAddress();
        row[8] = storeReportModel.getBuyerPhoneNumber();
        model.addRow(row);
    }
}