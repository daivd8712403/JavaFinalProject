import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.stream.IntStream;

public class CourseGUI {

    private static JFrame frame = new JFrame("E3B31 David Chen's 108_1 Courses");
    private JTable myTable;
    private JTextField myTextField;
    private JButton myButton;
    private JPanel panel;
    private JLabel myLabel;

    private static int[] selectRows;

    public CourseGUI() {
        $$$setupUI$$$();

        // Table Listeners
        myTable.getRowSorter().addRowSorterListener(new RowSorterListener() {
            @Override
            public void sorterChanged(RowSorterEvent e) {
                // Sorter Changed, remove all the selections and update the select rows.
                myTable.removeRowSelectionInterval(0, myTable.getRowCount() - 1);
                myTable.removeColumnSelectionInterval(0, myTable.getColumnCount() - 1);
                selectRows = myTable.getSelectedRows();
            }
        });

        myTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // When the mouse released, get the rows that user select and store in a array.
                selectRows = myTable.getSelectedRows();

                // Print the rows you selected.
                System.out.print("Select " + selectRows.length + "  rows: ");
                for (int i : selectRows) {
                    System.out.print(myTable.getValueAt(i, 0) + ", ");
                }
                System.out.println("");
            }
        });

        // Button Listeners
        myButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // When button clicked, get the file name and the row data on the table.
                if (myTextField.getText().length() != 0) {
                    // File name not null.
                    if (CourseGUI.getSelectRows().length == 0) {
                        // No table rows selected.
                        JOptionPane.showMessageDialog(frame, "Select at least one row on the table!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // new a temp JTable to export and get the file name string.
                        String fileName = myTextField.getText() + ".csv";
                        JTable tmpTable = new JTable(null, MyCourse.getColumnName());

                        // Get the row data user selected.
                        for (int i : CourseGUI.getSelectRows()) {
                            ((DefaultTableModel) tmpTable.getModel()).addRow(CourseGUI.getTableRowData(myTable, i));
                        }

                        if (CsvWriter.getExist(fileName)) {
                            // File is exist, update file.
                            JOptionPane.showMessageDialog(frame, fileName + " updated!.", "message", JOptionPane.WARNING_MESSAGE);
                            myTextField.setText("");
                        } else {
                            // File is not exist, create file.
                            JOptionPane.showMessageDialog(frame, fileName + " created.");
                            myTextField.setText("");
                        }

                        // Write the CSV file.
                        CsvWriter.write(tmpTable, fileName);
                    }
                } else {
                    // File name is null.
                    myTextField.setBorder(new LineBorder(Color.RED));
                    JOptionPane.showMessageDialog(frame, "Enter the file name!", "message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // TextField Listener
        myTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                myTextField.setBorder(new LineBorder(Color.GRAY));
            }
        });
    }

    private static int[] getSelectRows() {
        return selectRows;
    }

    // Get data on the single row.
    private static Vector<String> getTableRowData(JTable table, int r) {
        Vector<String> data = new Vector<>();

        for (int i = 0; i < table.getColumnCount(); i++) {
            data.addElement((String) table.getValueAt(r, i));
        }
        return data;
    }

    private void createUIComponents() {
        myTable = new JTable(MyCourse.getRowData(), MyCourse.getColumnName());
        // Disable data edition.
        myTable.setDefaultEditor(Object.class, null);
        // Set width.
        myTable.setPreferredSize(new Dimension(MyCourse.columnWidthSum, 120));
        // Set each column's width.
        IntStream.range(0, MyCourse.getColumnName().size()).forEach(i -> {
            myTable.getColumnModel().getColumn(i).setPreferredWidth(MyCourse.columnPreferredWidth[i]);
        });
        // Disable column dragging.
        myTable.getTableHeader().setReorderingAllowed(false);
    }

    public static void main(String[] args) {
        frame.setContentPane(new CourseGUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(MyCourse.columnWidthSum, 250));
        frame.setVisible(true);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        myTextField = new JTextField();
        panel.add(myTextField, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder("108-1 Courses"));
        myTable.setAutoCreateRowSorter(true);
        myTable.setFillsViewportHeight(true);
        Font myTableFont = this.$$$getFont$$$("Menlo", -1, 11, myTable.getFont());
        if (myTableFont != null) myTable.setFont(myTableFont);
        myTable.setPreferredScrollableViewportSize(new Dimension(600, 150));
        myTable.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        myTable.putClientProperty("Table.isFileList", Boolean.FALSE);
        scrollPane1.setViewportView(myTable);
        myButton = new JButton();
        Font myButtonFont = this.$$$getFont$$$(null, Font.BOLD, 16, myButton.getFont());
        if (myButtonFont != null) myButton.setFont(myButtonFont);
        myButton.setText("Export data to .csv file");
        panel.add(myButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        myLabel = new JLabel();
        myLabel.setText("Enter file name:");
        panel.add(myLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
