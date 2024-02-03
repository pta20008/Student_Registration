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
        // Splitting the line into parts using space as a separator
        String[] nameParts = line.split(" ");

        // Rule: Ensure that the line contains at least two parts (first name and second name)
        if (nameParts.length >= 2) {
          String firstName = nameParts[0];
          String secondName = nameParts[1];
          int numberOfClasses = Integer.parseInt(reader.readLine());

          // Extracting numeric part from student number
          String studentNumber = reader.readLine().replaceAll("\\D", "");

          // Rule: Validate the student data
          if (isValidData(firstName, secondName, numberOfClasses, studentNumber)) {
            // Rule: Write the valid data to the "status.txt" file
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
    // Rule a) The first name must contain only letters;
    if (!firstName.matches("^[a-zA-Z]+$")) {
      return false;
    }

    // Rule b) The second name can contain letters and/or numbers and must be separated from the first name by a single space;
    if (!secondName.matches("^[a-zA-Z0-9]+ [a-zA-Z0-9]+$")) {
      return false;
    }

    // Rule c) The number of classes must be an integer value between 1 and 8 (inclusive);
    if (numberOfClasses < 1 || numberOfClasses > 8) {
      return false;
    }

    // Rule d) The "number" of the student must have a minimum of 6 characters,
    // with the first 2 characters being numbers, the 3rd and 4th characters (and possibly the 5th)
    // being a letter, and everything after the last letter character being a number.
    if (!studentNumber.matches("^\\d{2}[a-zA-Z]{1,2}\\d+$")) {
      return false;
    }

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
    // Writing formatted student data to the file
    writer.write(String.format("%s – %s\n%s\n", studentNumber, secondName, workload));
  }

  /**
   * Determines the workload based on the number of classes.
   *
   * @param numberOfClasses The number of classes for the student.
   * @return A string representing the workload based on the specified criteria.
   */
  private static String getWorkload(int numberOfClasses) {
    // Determine workload based on the number of classes
    if (numberOfClasses == 1) {
      return "Very Light";
    } else if (numberOfClasses == 2) {
      return "Light";
    } else if (numberOfClasses >= 3 && numberOfClasses <= 5) {
      return "Part Time";
    } else if (numberOfClasses >= 6) {
      return "Full Time";
    } else {
      return "Invalid Number of Classes";
    }
  }


}
