import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Patient extends User{
    private int age;
    private String gender;
    private Doctor doctor;
    private ArrayList<Appointment> appointments;
    private HospitalSystem hospital;


    public Patient(String id, String name, String username, String password, int age,
            String gender, String phoneNumber, HospitalSystem hospital) {
        super(id, name, username, password, phoneNumber, "patient");
        this.age = age;
        this.gender = gender;
        this.doctor = null;  //there isn't assigned doctor yet
        this.hospital=hospital;

        appointments=new ArrayList<>();

    }


    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Doctor getDoctor () {
        return this.doctor;
    }
    public void setDoctor (Doctor doctor) {
        this.doctor = doctor;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public void setHospital(HospitalSystem hospital) {
        this.hospital = hospital;
    }

    public void viewDoctor () {
        if (doctor !=null) {
            System.out.println("\nDoctor: " +  doctor.getName());
            System.out.println("Doctor's Number: " + doctor.getPhone());
        } else {
            System.out.println("\nThere is no assigned doctor");
        }
    }

    public void cancelAppointment(String id){
        for (Appointment appointment:appointments){
            if(appointment.getId().equals(id)){
                if (appointment.getStatus().equalsIgnoreCase("Cancelled")) {
                    System.out.println("Appointment is already cancelled!");
                    return;
                }
                appointment.setStatus("Cancelled");
                System.out.println("Appointment cancelled successfully!");
                FileManager.saveAppointments(hospital.getAppointments());
                return;
            }
        }
        System.out.println("Appointment not found!");
    }

    @Override
    public void displayProfile() {
        super.displayProfile();
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);

    }
    
    public void viewAppointments(){
        System.out.println();
        if (appointments.isEmpty()) {
            System.out.println("You have no appointments!");
            return;
        }
        for(Appointment appointment:appointments){
            appointment.DisplayAll();
        }
    }
    @Override
    public void showMenu(){
         Scanner scan = new Scanner(System.in);
        int choice;

        do{
            System.out.println("\n=== PATIENT MENU ===\n");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Doctor");
            System.out.println("3. View My Appointments");
            System.out.println("4. Book An Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Logout");

            System.out.print("\nEnter your choice (from 1 to 6): ");
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
                    viewDoctor();
                    break;
                case 3:
                    viewAppointments();
                    break;
                case 4:
                    scan.nextLine();
                    if (doctor==null) {
                        System.out.println("You must assign to Doctor first!");
                    } else {
                        System.out.print("Enter appointment id: ");
                        String id = scan.nextLine();
                        if (hospital.searchAppointment(id) != null) {
                        System.out.println("Appointment with this id already exists!");
                        break;
                    }
                    System.out.print("Enter date: ");
                    String date = scan.nextLine();
                    if (date.isEmpty()) {
                        System.out.println("Date cannot be empty!");
                        break;
                    }
                    System.out.print("Enter time: ");
                    String time = scan.nextLine();
                    if (time.isEmpty()) {
                        System.out.println("Time cannot be empty!");
                        break;
                    }

                    if (!hospital.isDoctorAvailable(doctor.getId(), date, time)) {
                        System.out.println("Doctor already has an appointment at this time!");
                        break;
                    }

                    Appointment appointment = new Appointment(id, this.getId(),doctor.getId() , date, time, "Confirmed");
                    hospital.addAppointment(appointment, doctor, this);
                }

                    break;

                case 5:
                    scan.nextLine();
                    System.out.print("Enter appointment id: ");
                    String Id = scan.nextLine();
                    cancelAppointment(Id);
                    break;

                case 6:
                    System.out.println("Logging Out...");
                    break;                    
            
                default:
                    System.out.println("invalid choice, please try again!");
                    break;
            }

        } while (choice !=6);
    }
}