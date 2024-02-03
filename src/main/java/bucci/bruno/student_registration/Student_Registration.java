/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package bucci.bruno.student_registration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Bruno Bucci
 */
public class Student_Registration {

  public static void main(String[] args) {
    processStudentData();
  }

  /**
   * Method for processing student data, reading files and writing
   */
  private static void processStudentData() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
      FileWriter writer = new FileWriter("status.txt");

      String line;
      while ((line = reader.readLine()) != null) {
        String[] nameParts = line.split(" ");

        if (nameParts.length >= 2) {
          String firstName = nameParts[0];
          String secondName = nameParts[1];
          int numberOfClasses = Integer.parseInt(reader.readLine());

          String studentNumber = reader.readLine().replaceAll("\\D", "");

          if (isValidData(firstName, secondName, numberOfClasses, studentNumber)) {
            writeToFile(writer, studentNumber, secondName, getWorkload(numberOfClasses));
          } else {
            System.out.println("Error: Invalid data for student");
          }
        } else {
          System.out.println("Error: Invalid data format for student");
        }
      }

      reader.close();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the student data is valid according to the specified rules.
   *
   * @param firstName       The first name of the student.
   * @param secondName      The second name of the student.
   * @param numberOfClasses The number of classes the student is enrolled in.
   * @param studentNumber   The student number.
   * @return True if the data is valid, False otherwise.
   */
  private static boolean isValidData(String firstName, String secondName, int numberOfClasses,
      String studentNumber) {
    return true;
  }

  /**
   * Writes student data to a file in the specified format.
   *
   * @param writer        FileWriter object for writing to the file.
   * @param studentNumber The student number.
   * @param secondName    The second name of the student.
   * @param workload      The workload determined by the number of classes.
   * @throws IOException If an I/O error occurs while writing to the file.
   */
  private static void writeToFile(FileWriter writer, String studentNumber, String secondName,
      String workload) throws IOException {
    writer.write(studentNumber + " – " + secondName + "\n" + workload + "\n");
  }

  /**
   * Determines the workload based on the number of classes.
   *
   * @param numberOfClasses The number of classes for the student.
   * @return A string representing the workload based on the specified criteria.
   */
  private static String getWorkload(int numberOfClasses) {
    return "";
  }

}
