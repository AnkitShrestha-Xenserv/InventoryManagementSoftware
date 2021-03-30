import javax.swing.*;
import java.awt.*;

// Created on: 8/6/2020
public class ListRenderer extends JLabel implements ListCellRenderer<String> {

    public ListRenderer(){
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        setFont(new Font("Serif", Font.BOLD, 25));
        setHorizontalTextPosition(SwingConstants.RIGHT);

        setText(value);

        if (isSelected) {
            setBackground(Color.black);
            setForeground(Color.white);
        } else {
            setBackground(Color.orange);
            setForeground(Color.black);
        }

        return this;
    }
}