import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class CsvWriter {

    // Return whether the file is exist.
    static boolean getExist(String fileName) {
        File f = new File(fileName);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }else
            return false;
    }

    // Write the JTable data to csv file.
    static void write(JTable table, String fileName) {
        try{
            TableModel model = table.getModel();
            File file = new File(fileName);
            FileWriter excel = new FileWriter(file);

            for(int i = 0; i < model.getColumnCount(); i++){
                excel.write(model.getColumnName(i) + "\t");
            }

            excel.write("\n");

            for(int i = 0; i < model.getRowCount(); i++) {
                for(int j = 0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i, j).toString()+"\t");
                }
                excel.write("\n");
            }

            excel.close();

        }catch(IOException e){ System.out.println(e); }
    }
}
