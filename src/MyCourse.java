import java.io.*;
import java.util.Vector;

public enum MyCourse implements Serializable {
    DSP("李清坤", 3, "Elective","A3-214", "Friday", "N/A"),
    IC_Design("黃淑絹", 3, "Elective", "A3-214", "Monday", "Tuesday"),
    MCU("古聖如", 3, "Required", "A3-214", "Monday", "Thursday"),
    Java("周俊賢", 3, "Elective", "A3-317", "Tuesday", "Thursday"),
    Data_Structure("謝尚琳", 3, "Required", "A3-102", "Wednesday", "N/A"),
    AIoT_Implementation("許超雲" , 1, "Elective", "A3-207", "Saturday", "N/A"),
    AWS_Implementation("張伯廷", 1, "Elective", "A3-200", "Saturday", "N/A");

    MyCourse(String teacher, int credit, String requiredOrElective, String classRoom, String day1, String day2) {
        this.teacher = teacher;
        this.credit = credit;
        this.requiredOrElective = requiredOrElective;
        this.classRoom = classRoom;
        this.day1 = day1;
        this.day2 = day2;
    }

    private final String teacher;
    private final int credit;
    private final String requiredOrElective;
    private final String classRoom;
    private final String day1;
    private final String day2;

    public static int[] columnPreferredWidth = {150, 70, 60, 150, 100, 100, 100};

    // Get the all data in MyCourse into Vector<Vector>
    public static Vector<Vector> getRowData() {
        Vector<Vector> rowData = new Vector<>();

        for (MyCourse c : MyCourse.values()) {
            Vector<String> data = new Vector<>();
            data.addElement(c.name());
            data.addElement(c.teacher);
            data.addElement(String.valueOf(c.credit));
            data.addElement(c.requiredOrElective);
            data.addElement(c.classRoom);
            data.addElement(c.day1);
            data.addElement(c.day2);

            rowData.addElement(data);
        }
        return rowData;
    }

    // get the columns name.
    public static Vector<String> getColumnName() {
        Vector<String> columnName = new Vector<>();

        columnName.add("Name");
        columnName.add("Teacher");
        columnName.add("Credits");
        columnName.add("Required/Elective");
        columnName.add("Class Room");
        columnName.add("Day1");
        columnName.add("Day2");

        return columnName;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getCredit() {
        return credit;
    }

    public String getRequiredOrElective() {
        return requiredOrElective;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getDay1() {
        return day1;
    }

    public String getDay2() {
        return day2;
    }
}
