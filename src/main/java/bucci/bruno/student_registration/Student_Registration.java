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
    FileWriter writer = null;

    try {
      BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
      writer = new FileWriter("status.txt");

      String line;
      while ((line = reader.readLine()) != null) {
        // Rule: Ensure that the line contains at least one part (full name)
        if (!line.isEmpty()) {
          String fullName = line;
          int numberOfClasses = Integer.parseInt(reader.readLine());

          String studentNumberLine = reader.readLine();
          String studentNumber = studentNumberLine.trim().replaceAll("[^0-9a-zA-Z]", "");

          // Rule: Validate the student data
          if (isValidData(fullName, numberOfClasses, studentNumber)) {
            // Rule: Write the valid data to the "status.txt" file
            writeToFile(writer, studentNumber, fullName, getWorkload(numberOfClasses));
          } else {
            System.out.println("Error: Invalid data for student");
          }
        } else {
          System.out.println("Error: Invalid data format for student");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Checks if the student data is valid according to the specified rules.
   *
   * @param fullName        The first name of the student.
   * @param numberOfClasses The number of classes the student is enrolled in.
   * @param studentNumber   The student number.
   * @return True if the data is valid, False otherwise.
   */
  private static boolean isValidData(String fullName, int numberOfClasses, String studentNumber) {
    // Rule a) The full name must contain only letters;
    if (!fullName.matches("^[a-zA-Z ]+$")) {
      return false;
    }

    // Rule c) The number of classes must be an integer value between 1 and 8 (inclusive);
    if (numberOfClasses < 1 || numberOfClasses > 8) {
      return false;
    }

    // Rule d) The "number" of the student must have a minimum of 6 characters,
    // with the first 2 characters being numbers, the 3rd and 4th characters (and possibly the 5th)
    // being a letter, and everything after the last letter character being a number.
    if (!studentNumber.matches("^\\d{2}[a-zA-Z]{2,3}\\d+$")) {
      return false;
    }

    return true;
  }

  /**
   * Writes student data to a file in the specified format.
   *
   * @param writer        FileWriter object for writing to the file.
   * @param studentNumber The student number.
   * @param fullName      The second name of the student.
   * @param workload      The workload determined by the number of classes.
   * @throws IOException If an I/O error occurs while writing to the file.
   */
  private static void writeToFile(FileWriter writer, String studentNumber, String fullName,
      String workload) throws IOException {
    // Writing formatted student data to the file
    writer.write(String.format("%s – %s\n%s\n", studentNumber, fullName, workload));
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
