import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Doctor extends User {

    private String specialization;
    private String Department;
    private ArrayList <Patient> patients;
    private ArrayList<Appointment> appointments;
    private ArrayList<Appointment> allAppointments;
    private String availability = "Available";


    public Doctor(String id, String name, String username, String password, String role,
            String specialization, String department, String phoneNumber) {
        super(id, name, username, password, phoneNumber, "doctor");
        this.specialization = specialization;
        this.Department = department;

        patients=new ArrayList<>();
        appointments=new ArrayList<>();
       
    }

    
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getDepartment() {
        return Department;
    }
    public String getAvailability() {
        return availability;
    }
    public void setDepartment(String department) {
        Department = department;
    }
    public void setAvailability(String a) {
        this.availability = a;
    }
    public void setAllAppointments(ArrayList<Appointment> appointments){
    this.allAppointments = appointments;
    }
    public ArrayList<Patient> getPatients() {
        return patients;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }
    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }
    public void viewPatients(){
        System.out.println("\n=== Your Patients ===\n");
        if (patients.isEmpty()) {
            System.out.println("You have no patients.");
            return;
        }
        for(Patient patient : patients){
            System.out.println("Patient ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Phone: " + patient.getPhone());
            System.out.println("Age: " + patient.getAge());
            System.out.println("Gender: " + patient.getGender());
            System.out.println();
        }
    }
    public void updateAppointmentStatus(String id,String status){
        for(Appointment appointment : appointments){
            if(appointment.getId().equals(id)){
                if (appointment.getStatus().equalsIgnoreCase("cancelled") && status.equalsIgnoreCase("completed")) {
                        System.out.println("Cannot mark a cancelled appointment as completed!");
                        return;
                }

                if (appointment.getStatus().equalsIgnoreCase(status)) {
                System.out.println("Appointment is already " + status + "!");
                return;
                }
                
                appointment.setStatus(status);
                System.out.println("Appointment status updated successfully!");
                
                return;
            }
        }
        System.out.println("Appointment Not Found! Please, try again");
    }

    @Override
    public void displayProfile() {
        super.displayProfile();
        System.out.println("specialization: " + specialization);
        System.out.println("Department: " + Department);

    }

    public void viewAppointments(){
        System.out.println("\n=== Your Appointments ===\n");
        if (appointments.isEmpty()) {
            System.out.println("You have no appointments.");
            return;
        }
        for(Appointment appointment : appointments){
            appointment.DisplayAll();
        }
    }

    @Override
    public void showMenu(){
        Scanner scan = new Scanner(System.in);
        int choice;

        do{
            
            System.out.println("\n=== DOCTOR MENU ===\n");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Patients");
            System.out.println("3. View My Appointments ");
            System.out.println("4. Update Appointment Status");
            System.out.println("5. Logout");

            System.out.print("\nEnter choice (from 1 to 5): ");

            while (true) {
                try {
                    choice = scan.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number!");
                    System.out.print("\nEnter choice: ");
                    scan.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    displayProfile();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    viewAppointments();
                    break;
                case 4:
                    scan.nextLine();
                    
                    System.out.print("Enter appointment id: ");
                    String id=scan.nextLine();
                    System.out.print("Enter new status: ");
                    String status=scan.nextLine();
                    if (!status.equalsIgnoreCase("Confirmed") && !status.equalsIgnoreCase("Completed") && 
                        !status.equalsIgnoreCase("Cancelled")) {
                        System.out.println("Invalid status! Choose: Confirmed, Completed, or Cancelled");
                        break;
                    }
                    updateAppointmentStatus(id, status);
                    FileManager.saveAppointments(allAppointments);
                    break;   
                case 5:
                    System.out.println("Logging Out...");
                    break;               
                default:
                    System.out.println("Invalid choice, please try agian!");
                    break;
            }
        } while(choice !=5);
    }
}