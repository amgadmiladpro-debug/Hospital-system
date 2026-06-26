import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HospitalSystem hospital = new HospitalSystem(); 

        int choice;

        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Doctor");
            System.out.println("3. Login as Patient");
            System.out.println("4. Exit");

            System.out.print("\nEnter choice: ");

            while (true) { // validate choice 
                try {
                    choice = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number!");
                    System.out.print("\nEnter choice: ");
                    input.nextLine();
                }
            }
            input.nextLine(); // empty the buffer

            String username, password;

            switch (choice) {
                case 1:
                    System.out.println("\n=== Log in as admin ===\n");
                    System.out.print("Enter username: ");
                    username = input.nextLine();
                    System.out.print("Enter password: ");
                    password = input.nextLine();

                    if (username.equals("admin") && password.equals("admin123")) {
                        Admin admin = new Admin(hospital);
                        admin.showMenu();
                    } else {
                        System.out.println("Wrong username or password!");
                    }

                    break;

                case 2:
                    System.out.println("\n=== Log in as doctor ===\n");
                    System.out.print("Enter username: ");
                    username = input.nextLine();
                    System.out.print("Enter password: ");
                    password = input.nextLine();

                    Doctor doctor = hospital.loginDoctor(username, password);
                    
                    if (doctor == null) {
                        System.out.println("Wrong username or password!");
                    } else {
                        doctor.setAllAppointments(hospital.getAppointments()); // it's for update method in Doctor class
                        doctor.showMenu();
                    }
                    break;

                case 3:
                    System.out.println("\n=== Log in as patient ===\n");
                    System.out.print("Enter username: ");
                    username = input.nextLine();
                    System.out.print("Enter password: ");
                    password = input.nextLine();

                    Patient patient = hospital.loginPatient(username, password);
                    if (patient == null) {
                        System.out.println("Wrong username or password!");
                    } else {
                        patient.showMenu();
                    }

                    break;

                case 4:
                    input.close();
                    System.exit(0);
                    break;
                
                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        } while (true);
    }
}