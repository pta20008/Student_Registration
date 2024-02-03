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
   **/
  private static void processStudentData() {
      try {
          BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
          FileWriter writer = new FileWriter("status.txt");

      } catch (IOException e) {
          e.printStackTrace();
      }
  }


}
