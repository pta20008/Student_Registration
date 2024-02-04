/*
 * Github: https://github.com/pta20008/Student_Registration.git
 */

package bucci.bruno.student_registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bruno Bucci
 */
public class Student_Registration {

  static Scanner consoleScanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {

    //Create variables
    int choice = 0;
    boolean repeat = false;

    //Method that run system and menu
    do {
      System.out.println("========= Main Menu =========");
      System.out.println(
          "1. Standard operation\n2. Add student data\n3. Press 3 for exit program\n");
      choice = consoleScanner.nextInt();

      // Condition Structure to validate the choice
      if (choice == 1) {
        BufferedReader reader = null;

        try {
          reader = new BufferedReader(new FileReader("students.txt"));
        } catch (FileNotFoundException e) {
          System.out.println("Students.txt file not found");
        }

        if (reader != null) {
          processStudentData(reader);
          showStatusDetails();
        }

        repeat = true;
      } else if (choice == 2) {
        createNewStudent();
        repeat = true;
      } else {
        System.out.println("Program finished!");
        repeat = false;
      }

    } while (repeat);
  }

  /**
   * Create new student following the rules necessary.
   *
   * @return True if the student are created with success.
   */
  private static boolean createNewStudent() {
    try {
      System.out.println("Please, input Student First Name:");
      String firstName = consoleScanner.next();

      System.out.println("Please, input Student Second Name:");
      String secondName = consoleScanner.next();

      System.out.println("Please, input number of classes:");
      int numberOfClasses = consoleScanner.nextInt();

      System.out.println("Please, input Student Number:");
      String studentNumber = consoleScanner.next();

      // Check if the provided data are valid
      boolean validData = isValidData(firstName, secondName, numberOfClasses, studentNumber);

      // If is valid write on the students.txt
      if (validData) {
        // Using try-with-resources to automatically close the BufferedWriter
        try (BufferedWriter br = new BufferedWriter(new FileWriter("students.txt", true))) {
          writeToFileStudent(br, firstName, secondName, numberOfClasses, studentNumber);
        }
      }

      return validData;
      // If an exception happened return false
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Method to show students details at terminal
   *
   * @throws FileNotFoundException If an error occurs while searching for the file.
   */
  private static void showStatusDetails() throws FileNotFoundException {
    Scanner fileScanner = new Scanner(new File("status.txt"));

    while (fileScanner.hasNext()) {
      System.out.println(fileScanner.nextLine());
      System.out.println(fileScanner.nextLine());
    }
  }

  /**
   * Method for processing student data, reading files and writing
   */
  private static void processStudentData(BufferedReader reader) {
    FileWriter writer = null;

    try {
      // Get students.txt file
      writer = new FileWriter("status.txt");

      String line;
      while ((line = reader.readLine()) != null) {
        // Rule: Ensure that the line contains at least one part (full name)
        if (!line.isEmpty()) {
          String[] firstAndSecondName = line.split(" ");
          int numberOfClasses = Integer.parseInt(reader.readLine());

          String studentNumberLine = reader.readLine();
          String studentNumber = studentNumberLine.trim().replaceAll("[^0-9a-zA-Z]", "");

          // Rule: Validate the student data
          if (isValidData(firstAndSecondName[0], firstAndSecondName[1], numberOfClasses,
              studentNumber)) {
            // Rule: Write the valid data to the "status.txt" file
            writeToFileStatus(writer, studentNumber, firstAndSecondName[1],
                getWorkload(numberOfClasses));
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
      // Close connection
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
   * @param firstName       The first name of the student.
   * @param secondName      The second name of the student.
   * @param numberOfClasses The number of classes the student is enrolled in.
   * @param studentNumber   The student number.
   * @return True if the data is valid, False otherwise.
   */
  private static boolean isValidData(String firstName, String secondName, int numberOfClasses,
      String studentNumber) {
    // Rule a) The first name must contain only letters;
    if (!firstName.matches("^[a-zA-Z ]+$")) {
      System.out.println("Error: The first name must contain only letters");
      return false;
    }

    // Rule b) The second name must contain letters and numbers.
    if (!secondName.matches("[a-zA-Z0-9]+")) {
      System.out.println("Error: The second name must contain letters and numbers");
      return false;
    }

    // Rule c) The number of classes must be an integer value between 1 and 8 (inclusive);
    if (numberOfClasses < 1 || numberOfClasses > 8) {
      System.out.println("Error: The number of classes must be an integer value between 1 and 8");
      return false;
    }

    // Rule d) The "number" of the student must have a minimum of 6 characters,
    // with the first 2 characters being numbers, the 3rd and 4th characters (and possibly the 5th)
    // being a letter, and everything after the last letter character being a number.
    if (!studentNumber.matches("^\\d{2}[a-zA-Z]{2,3}\\d+$")) {
      System.out.println(
          "Error: Students number must have min 6 char or the first 2 char being numbers");
      System.out.println(
          "Error: the 3rd and 4th characters (and possibly the 5th being a letter and after the last letter char being a number");
      return false;
    }

    // Rule e) Validate student number year (at least 2020)
    int year = Integer.parseInt(studentNumber.substring(0, 2));
    if (year < 20) {
      System.out.println("Error: Validate student number year (at least 2020)");
      return false;
    }

    // Rule f) Validate number after letters in student number (between 1 and 200)
    Pattern pattern = Pattern.compile("[a-zA-Z](\\d+)");
    Matcher matcher = pattern.matcher(studentNumber);
    if (matcher.find()) {
      Integer finalNumber = Integer.valueOf(matcher.group(1));
      if (finalNumber < 1 || finalNumber > 2000) {
        System.out.println("Error: Validate number after letters in student number");
        return false;
      }
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
  private static void writeToFileStatus(FileWriter writer, String studentNumber, String secondName,
      String workload) throws IOException {
    // Writing formatted student data to the file
    writer.write(String.format("%s – %s\n%s\n", studentNumber, secondName, workload));
  }

  /**
   * Writes student data to a file in the specified format.
   *
   * @param br              BufferedWriter object for writing to the file.
   * @param firstName       The First name of the student.
   * @param secondName      The second name of the student.
   * @param numberOfClasses The number of classes that student has enrolled.
   * @param studentNumber   The number of student registration.
   * @throws IOException If an I/O error occurs while writing to the file.
   */
  private static void writeToFileStudent(BufferedWriter br, String firstName, String secondName,
      int numberOfClasses,
      String studentNumber) throws IOException {
    // Writing formatted student data to the file
    br.write(
        String.format("%s %s\n%s\n%s\n", firstName, secondName, numberOfClasses, studentNumber));
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
