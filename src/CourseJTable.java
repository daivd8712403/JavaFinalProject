import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.util.Vector;
import java.util.stream.IntStream;

public class CourseJTable extends JPanel{

    private JTable myCourseTable;
    private int[] selectRows;

    private CourseJTable() {
        super(new BorderLayout());
        myCourseTable = new JTable(MyCourse.getRowData(), MyCourse.getColumnName());

        // Set each column's width.
        IntStream.range(0, MyCourse.getColumnName().size()).forEach(i -> {
            TableColumnModel cm = myCourseTable.getColumnModel();
            TableColumn column = cm.getColumn(i);
            column.setPreferredWidth(MyCourse.columnPreferredWidth[i]);
        });

        // Disable table editor.
        myCourseTable.setDefaultEditor(Object.class, null);
        selectRows = myCourseTable.getSelectedRows();

        // Set the scrollPane
        JScrollPane scrollPane = new JScrollPane(myCourseTable);
        add(scrollPane);

        // Set sorter
        myCourseTable.setAutoCreateRowSorter(true);

        // Set Selection Mode, let user can select multiple rows.
        myCourseTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Row Sorter Listener
        myCourseTable.getRowSorter().addRowSorterListener(e -> {
            // Sorter Changed, remove all the selections and update the select rows.
            myCourseTable.removeRowSelectionInterval(0, myCourseTable.getRowCount() - 1);
            myCourseTable.removeColumnSelectionInterval(0, myCourseTable.getColumnCount() - 1);
            selectRows = myCourseTable.getSelectedRows();
        });

        // Add mouse listener
        myCourseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // When the mouse released, get the rows that user select and store in a array.
                selectRows = myCourseTable.getSelectedRows();

                // Print the rows you selected.
                System.out.print("Select " + selectRows.length +"  rows: ");
                for(int i: selectRows) {
                    System.out.print(myCourseTable.getValueAt(i, 0) + ", ");
                }
                System.out.println("");
            }
        });
    }

    // Get the rows on table that user selected.
    private int[] getSelectRows() {
        return selectRows;
    }

    // Get data on the single row.
    private Vector<String> getTableRowData(int r) {
        Vector<String> data = new Vector<>();

        for(int i = 0; i < myCourseTable.getColumnCount(); i++) {
            data.addElement((String) myCourseTable.getValueAt(r, i));
        }
        return data;
    }

    private static void createAndShowGUI() {
        // Create, set window.
        JFrame frame = new JFrame("E3B31 David Chen's 108_1 Courses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Calculate the table size.
        int preferredSize = IntStream.of(MyCourse.columnPreferredWidth).sum();

        CourseJTable myTable = new CourseJTable();
        frame.add(myTable, BorderLayout.NORTH);

        // Set the JTextFieldUI
        JTextFieldHintUI fileTextField = new JTextFieldHintUI("File name");
        fileTextField.setPreferredSize(new Dimension(200, 0));
        fileTextField.init();
        frame.add(fileTextField, BorderLayout.WEST);

        // Set the JButton
        JButton exportButton = new JButton("Click to export the selected data.");
        frame.add(exportButton, BorderLayout.EAST);
        exportButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // When button clicked, get the file name and the row data on the table.
                if(!fileTextField.isEmpty()) {
                    // File name not null.
                    if(myTable.getSelectRows().length == 0) {
                        // No table rows selected.
                        JOptionPane.showMessageDialog(frame, "Select at least one row on the table!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else {
                        // new a temp JTable to export and get the file name string.
                        String fileName = fileTextField.getText() + ".csv";
                        JTable tmpTable = new JTable(null, MyCourse.getColumnName());

                        // Get the row data user selected.
                        for(int i: myTable.getSelectRows()) {
                            ((DefaultTableModel)tmpTable.getModel()).addRow(myTable.getTableRowData(i));
                        }

                        if(CsvWriter.getExist(fileName)) {
                            // File is exist, update file.
                            JOptionPane.showMessageDialog(frame, fileName + " updated!.", "message", JOptionPane.WARNING_MESSAGE);
                            fileTextField.init();
                        }else {
                            // File is not exist, create file.
                            JOptionPane.showMessageDialog(frame, fileName + " created.");
                            fileTextField.init();
                        }

                        // Write the CSV file.
                        CsvWriter.write(tmpTable, fileName);
                    }
                }else {
                    // File name is null.
                    fileTextField.setBorder(new LineBorder(Color.RED));
                    JOptionPane.showMessageDialog(frame, "Enter the file name!", "message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set frame size
        frame.setSize(preferredSize, 490);

        // Disable resize window.
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}