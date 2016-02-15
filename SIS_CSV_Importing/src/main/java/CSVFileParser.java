/**
 * Created by ayoolaajakaiye on 1/5/16.
 */



import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CSVFileParser {

    private final List<String> student_columns = Arrays.asList("user_id", "user_name", "state");
    private final List<String> course_columns = Arrays.asList("course_id", "course_name", "state");
    private final List<String> enrollment_columns = Arrays.asList("user_id", "course_id", "state");

    private String student = "";
    private String course = "";
    private String enrollment = "";



    private File folder;
    private File[] listOfFiles;


    public CSVFileParser(String inFile, String inStudent, String inCourse, String inEnroll)
    {
        folder = new File(inFile);
        listOfFiles = folder.listFiles();
        student = inStudent;
        course = inCourse;
        enrollment = inEnroll;
    }

    public void DirectoryParser() throws IOException
    {
        if(listOfFiles != null)
        {
            FileProcessor(listOfFiles);
        }
    }

    public void FileProcessor(File[] infile) throws IOException {
        BufferedWriter studentWriter = getWriter(student);
        BufferedWriter courseWriter = getWriter(course);
        BufferedWriter enrollmentWriter = getWriter(enrollment);

        for (File file : infile) {
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";



                br = new BufferedReader(new FileReader(file));

                line = br.readLine();

                List<String> header = Arrays.asList(line.split(cvsSplitBy));

                if (student_columns.containsAll(header)) {

                    int userIdIndex = header.indexOf("user_id");
                    int userNameIndex = header.indexOf("user_name");
                    int stateIndex = header.indexOf("state");


                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        String[] record = line.split(cvsSplitBy);
                        studentProcessor(studentWriter, record[userIdIndex], record[userNameIndex], record[stateIndex]);

                    }


                } else if (course_columns.containsAll(header)) {
                    int userIdIndex = header.indexOf("course_id");
                    int courseIdIndex = header.indexOf("course_name");
                    int stateIndex = header.indexOf("state");


                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        String[] record = line.split(cvsSplitBy);
                        courseProcessor(courseWriter, record[userIdIndex], record[courseIdIndex], record[stateIndex]);

                    }

                } else if(enrollment_columns.containsAll(header)) {

                    int courseId = header.indexOf("user_id");
                    int courseName = header.indexOf("course_id");
                    int stateIndex = header.indexOf("state");


                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        String[] record = line.split(cvsSplitBy);
                        enrollmentProcessor(enrollmentWriter, record[courseId], record[courseName], record[stateIndex]);
                    }


                }else{
                    System.out.println(header);
                }
                br.close();

            }

        studentWriter.flush();
        studentWriter.close();
        courseWriter.flush();
        courseWriter.close();
        enrollmentWriter.flush();
        enrollmentWriter.close();
    }

    public void studentProcessor(BufferedWriter bw, String userID, String userName, String state) throws IOException{
        bw.write(userID + "," + userName + "," + state);
        bw.newLine();
    }

    public void courseProcessor(BufferedWriter bw, String courseId, String courseName, String state) throws IOException{
        bw.write(courseId + "," + courseName + "," + state);
        bw.newLine();
    }

    public void enrollmentProcessor(BufferedWriter bw, String userID, String courseID, String state) throws IOException{

        bw.write(userID + "," + courseID + "," + state);
        bw.newLine();
    }

    public BufferedWriter getWriter(String output) throws IOException{

        File outputFile = new File(output);
        // if file doesnt exists, then create it
        if (outputFile.exists()) {
            outputFile.delete();
        }
        outputFile.createNewFile();
        FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
	return bw;
    }

}
