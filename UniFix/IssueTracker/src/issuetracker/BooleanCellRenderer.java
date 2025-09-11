
package issuetracker;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class BooleanCellRenderer extends JCheckBox implements TableCellRenderer {
    public BooleanCellRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Boolean) {
            setSelected((Boolean) value); // Set checkbox state based on value
        }
        return this;
    }
}

