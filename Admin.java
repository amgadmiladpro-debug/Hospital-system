import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    private HospitalSystem hospital;
    private Scanner input = new Scanner(System.in);

    Admin (HospitalSystem hospital) {
        super("A001", "Admin", "admin", "admin123", "00000000", "admin");
        this.hospital = hospital;
    }
    
    private void addDoctor() {
        input.nextLine();
        System.out.print("Enter Doctor ID: ");
        String id = input.nextLine();
        if (hospital.searchDoctor(id) != null) {
            System.out.println("Doctor with this id already exists!");
            return;
        }
        System.out.print("Enter Name: ");
        String name = input.nextLine();
        System.out.print("Enter Username: ");
        String username = input.nextLine();
        System.out.print("Enter Password: ");
        String password = input.nextLine();
        System.out.print("Enter Specialization: ");
        String spec = input.nextLine();
        System.out.print("Enter Department: ");
        String dept = input.nextLine();
        System.out.print("Enter Phone: ");
        String phone = input.nextLine();
        Doctor d = new Doctor(id, name, username, password, "doctor", spec, dept, phone);
        hospital.addDoctor(d);
    }

    private void registerPatient() {
        input.nextLine();

        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        if (hospital.searchPatient(id) != null) {
            System.out.println("Patient with this id already exists!");
            return;
        }
        System.out.print("Enter Name: ");
        String name = input.nextLine();
        System.out.print("Enter Username: ");
        String username = input.nextLine();
        System.out.print("Enter Password: ");
        String password = input.nextLine();
        System.out.print("Enter Age: ");
        int age = 0;
        while (true) {
            try {
                age = input.nextInt();
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number!");
                input.nextLine();
            }
        }
        System.out.print("Enter Gender: ");
        String gender = input.nextLine();
        System.out.print("Enter Phone: ");
        String phone = input.nextLine();
        Patient p = new Patient(id, name, username, password, age, gender, phone,hospital);
        hospital.addPatient(p);
    }

    private void assignPatientToDoctor () {
        input.nextLine();

        System.out.print("Enter Doctor ID: ");
        String docID = input.nextLine();
        Doctor d = hospital.searchDoctor(docID);
        if (d == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.print("Enter Patient ID: ");
        String patientID = input.nextLine();
        Patient p = hospital.searchPatient(patientID);
        if (p == null) {
            System.out.println("Patient not found!");
            return;
        }
        hospital.assignPatientToDoctor(d, p);
    }

    private void createAppointment () {
        input.nextLine();

        System.out.print("Enter Appointment ID: ");
        String appID = input.nextLine();
        if (hospital.searchAppointment(appID) != null) {
            System.out.println("Appointment with this id already exists!");
            return;
        }

        System.out.print("Enter Patient ID: ");
        String patID = input.nextLine();

        Patient p = hospital.searchPatient(patID);
        if (p == null) {
            System.out.println("Patient not found!");
            return;
        }
    
        System.out.print("Enter Doctor ID: ");
        String docID = input.nextLine();
        Doctor d = hospital.searchDoctor(docID);
        if (d == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.print("Enter Date: ");
        String date = input.nextLine();
        if (date.isEmpty()) {
            System.out.println("Date cannot be empty!");
            return;
        }

        System.out.print("Enter Time: ");
        String time = input.nextLine();
        if (time.isEmpty()) {
            System.out.println("Time cannot be empty!");
            return;
        }

        if (!hospital.isDoctorAvailable(docID, date, time)) {
            System.out.println("Doctor already has appointment at this time!");
            return;
        }

        Appointment a = new Appointment(appID, patID, docID, date, time, "Confirmed");
        hospital.addAppointment(a,d,p); 
    }

    private void searchPatientbyID () {
        input.nextLine();
        System.out.print("Enter Patient ID: ");
        String patientID = input.nextLine();
        Patient p = hospital.searchPatient(patientID);
        if (p == null) System.out.println("Patient not found!");
        else p.displayProfile();
    }

    private void searchDoctorbyID () {
        input.nextLine();
        System.out.print("Enter Doctor ID: ");
        String docID = input.nextLine();
        Doctor d = hospital.searchDoctor(docID);
        if (d == null) System.out.println("Doctor not found!");
        else d.displayProfile();
    }

    @Override
    public void showMenu() {
        int choice;

        do {
            System.out.println("\n=== ADMIN MENU ===\n");
            System.out.println("1. Add Doctor");
            System.out.println("2. Register Patient");
            System.out.println("3. Assign Patient to Doctor");
            System.out.println("4. Create Appointment");
            System.out.println("5. View All Doctors");
            System.out.println("6. View All Patients");
            System.out.println("7. View All Appointments");
            System.out.println("8. Search Patient by ID");
            System.out.println("9. Search Doctor by ID");
            System.out.println("10. Generate Reports");
            System.out.println("11. Save Data");
            System.out.println("12. Logout");

            System.out.print("\nEnter your choice (from 1 to 12): ");
            
            while (true) {
                try {
                    choice = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number!");
                    System.out.print("\nEnter choice: ");
                    input.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                
                case 2:
                    registerPatient();
                    break;

                case 3:
                    assignPatientToDoctor();
                    break;
                
                case 4:
                    createAppointment();
                    break;

                case 5:
                    hospital.displayDoctors();
                    break;

                case 6:
                    hospital.displayPatients();
                    break;

                case 7:
                    hospital.viewAllAppointments();
                    break;

                case 8:
                    searchPatientbyID();
                    break;

                case 9:
                    searchDoctorbyID();
                    break;

                case 10:
                    hospital.reports();
                    break;
                
                case 11:
                    FileManager.saveDoctors(hospital.getDoctors());
                    FileManager.savePatients(hospital.getPatients());
                    FileManager.saveAppointments(hospital.getAppointments());
                    ArrayList<User> users = new ArrayList<>();
                    users.add(new Admin(hospital));
                    users.addAll(hospital.getDoctors());
                    users.addAll(hospital.getPatients());
                    FileManager.saveUsers(users);
                    System.out.println("Data saved successfully!");
                    break;

                case 12: 
                    System.out.println("Logging out..");
                    break;

                default:
                    System.out.println("Invalid Choice!");
                    break;
            }

        } while (choice != 12);
        
    }
}