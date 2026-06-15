package CodeSoft;


import java.util.Scanner;

public class StudentGradeCalculator {

    public static String findGrade(double marks) {

        if (marks >= 90) return "O";
        if (marks >= 80) return "A+";
        if (marks >= 70) return "A";
        if (marks >= 60) return "B+";
        if (marks >= 50) return "B";
        if (marks >= 40) return "C";
        if (marks >= 35) return "PASS";

        return "FAIL";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] subjects = {
                "JAVA",
                "DSA",
                "DBMS",
                "OS",
                "CN"
        };

        char choice;

        System.out.println("==================================");
        System.out.println("     STUDENT GRADE CALCULATOR");
        System.out.println("==================================");

        do {

            int[] marks = new int[5];
            int total = 0;
            boolean fail = false;

            for (int i = 0; i < subjects.length; i++) {

                while (true) {

                    try {

                        System.out.print("Enter "
                                + subjects[i]
                                + " Marks : ");

                        marks[i] =
                                Integer.parseInt(sc.nextLine());

                        if (marks[i] < 0 || marks[i] > 100) {

                            System.out.println(
                                    "Marks must be between 0 and 100");
                            continue;
                        }

                        break;
                    }

                    catch (NumberFormatException e) {

                        System.out.println(
                                "Please enter numbers only");
                    }
                }

                total += marks[i];

                if (marks[i] < 35) {
                    fail = true;
                }
            }

            double average = total / 5.0;

            System.out.println("\n========== RESULT ==========");

            for (int i = 0; i < subjects.length; i++) {

                System.out.println(
                        subjects[i]
                                + " Grade : "
                                + findGrade(marks[i]));
            }

            System.out.println("\nTotal Marks : " + total);
            System.out.println("Percentage  : " + average + "%");

            String overallGrade =
                    fail ? "FAIL" : findGrade(average);

            System.out.println(
                    "Overall Grade : " + overallGrade);

            System.out.println(
                    "Result : " + (fail ? "FAIL" : "PASS"));

            System.out.print(
                    "\nDo you want to calculate again? (y/n): ");

            choice = sc.next().charAt(0);
            sc.nextLine();

        } while (choice == 'y' || choice == 'Y');

        System.out.println("\nThank You!");

        sc.close();
    }
}