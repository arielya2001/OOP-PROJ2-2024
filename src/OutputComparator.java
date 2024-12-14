import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OutputComparator {

    public static void main(String[] args) {
        String requiredOutputFile = "C:\\Users\\user\\Desktop\\הכל - חדש\\OOP-PROJ2-2024\\src\\output.txt"; // Path to required output file
        String studentOutputFile = "C:\\Users\\user\\Desktop\\הכל - חדש\\OOP-PROJ2-2024\\src\\student_output.txt";   // Path to student output file

        try {
            compareOutputs(requiredOutputFile, studentOutputFile);
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    public static void compareOutputs(String requiredFilePath, String studentFilePath) throws IOException {
        try (
                BufferedReader requiredReader = new BufferedReader(new FileReader(requiredFilePath));
                BufferedReader studentReader = new BufferedReader(new FileReader(studentFilePath))
        ) {
            String requiredLine;
            String studentLine;
            int lineNumber = 1;
            boolean isIdentical = true;

            while ((requiredLine = requiredReader.readLine()) != null | (studentLine = studentReader.readLine()) != null) {
                if (requiredLine == null || studentLine == null || !requiredLine.equals(studentLine)) {
                    isIdentical = false;
                    System.out.println("Mismatch at line " + lineNumber + ":");
                    System.out.println("  Required: " + (requiredLine != null ? requiredLine : "<missing line>"));
                    System.out.println("  Student:  " + (studentLine != null ? studentLine : "<missing line>"));
                    System.out.println();
                }
                lineNumber++;
            }

            if (isIdentical) {
                System.out.println("The student's output matches the required output perfectly!");
            } else {
                System.out.println("The student's output has differences compared to the required output.");
            }
        }
    }
}
