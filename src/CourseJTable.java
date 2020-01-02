import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
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
        JScrollPane scrollPane = new JScrollPane(myCourseTable);
        add(scrollPane);

        // Set sorter
        myCourseTable.setAutoCreateRowSorter(true);

        // Set Selection Mode, let user can select multiple rows.
        myCourseTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Row Sorter Listener
        myCourseTable.getRowSorter().addRowSorterListener(e -> {
            // Sorter Changed, remove all the selections.
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

    private Vector<String> getTableRowData(int r) {
        Vector<String> data = new Vector<>();
        // Get data on the single row.
        for(int i = 0; i < myCourseTable.getColumnCount(); i++) {
            data.addElement((String) myCourseTable.getValueAt(r, i));
        }
        return data;
    }

    private static void createAndShowGUI() {
        // Create, set window
        JFrame frame = new JFrame("E3B31 David Chen's 108_1 Courses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int preferredSize = IntStream.of(MyCourse.columnPreferredWidth).sum();

        CourseJTable myTable = new CourseJTable();
        frame.add(myTable, BorderLayout.NORTH);

        JTextFieldHintUI myTextField = new JTextFieldHintUI("File name");
        myTextField.setPreferredSize(new Dimension(200, 0));
        myTextField.init();
        frame.add(myTextField, BorderLayout.WEST);

        JButton myButton = new JButton("Click to export the selected data.");
        frame.add(myButton, BorderLayout.EAST);
        myButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // When button clicked, get the file name and the row data on the table.
                if(!myTextField.isEmpty()) {
                    // File name not null.
                    if(myTable.getSelectRows().length == 0) {
                        // No table rows selected.
                        JOptionPane.showMessageDialog(frame, "Select at least one row on the table!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else {
                        // new a temp JTable to export and get the file name string.
                        String fileName = myTextField.getText() + ".csv";
                        JTable tmpTable = new JTable(null, MyCourse.getColumnName());

                        // Get the row data user selected.
                        for(int i: myTable.getSelectRows()) {
                            ((DefaultTableModel)tmpTable.getModel()).addRow(myTable.getTableRowData(i));
                        }

                        if(CsvWriter.getExist(fileName)) {
                            // File is exist, update file.
                            JOptionPane.showMessageDialog(frame, fileName + " updated!.", "message", JOptionPane.WARNING_MESSAGE);
                            myTextField.init();
                        }else {
                            // File is not exist, create file.
                            JOptionPane.showMessageDialog(frame, fileName + " created.");
                            myTextField.init();
                        }

                        // Write the CSV file.
                        CsvWriter.write(tmpTable, fileName);
                    }
                }else {
                    // File name is null.
                    myTextField.setBorder(new LineBorder(Color.RED));
                    JOptionPane.showMessageDialog(frame, "Enter the file name!", "message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set frame size
        frame.setSize(preferredSize, 490);
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