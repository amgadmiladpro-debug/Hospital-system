import java.util.ArrayList;

public class HospitalSystem {
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public HospitalSystem(){
        this.doctors = FileManager.loadDoctors();
        this.patients = FileManager.loadPatients();
        this.appointments = FileManager.loadAppointments();
        FileManager.loadUsers(doctors, patients);
        for (Patient p : patients) p.setHospital(this);
        restoreAppointments();
    }

    public void restoreAppointments() {
        for (Patient p : patients) {
            if (p.getDoctor() != null) {
                Doctor d = searchDoctor(p.getDoctor().getId());
                if (d != null) {
                    p.setDoctor(d);   
                    d.addPatient(p);  
                }
            }
        }
        for (Appointment a : appointments) {
            Doctor d = searchDoctor(a.getDoctorId());
            Patient p = searchPatient(a.getPatientId());
            if (d != null) d.getAppointments().add(a);
            if (p != null) p.getAppointments().add(a);
        }
    }

    //Managing the Hospital DataBase
    public void addDoctor(Doctor d){
        doctors.add(d);
        System.out.println("Doctor is added successfully!");
        FileManager.saveDoctors(doctors);
    }

    public void addPatient(Patient p){
        patients.add(p);
        System.out.println("Patient is added successfully!");
        FileManager.savePatients(patients);
    }

    public void addAppointment(Appointment a, Doctor d, Patient p){
        appointments.add(a);
        p.getAppointments().add(a);
        d.getAppointments().add(a);
        System.out.println("Appointment is booked successfully!");
        FileManager.saveAppointments(appointments);
    }


    //Searching in Hospital DataBase by ID
    public Doctor searchDoctor(String id){
        for (Doctor d : doctors) {
            if (id.equals(d.getId())) {
                return d;
            }
        }
        return null;
    }

    public Doctor loginDoctor(String username, String password){
        for (Doctor d : doctors) {
            if (d.getUsername().equals(username) &&
                d.getPassword().equals(password)) {
                return d;
            }
        }
        return null;
    }

    public Patient searchPatient(String id){
        for (Patient p : patients) {
            if (id.equals(p.getId())) {
                return p;
            }
        }
        return null;
    }

    public Patient loginPatient(String username, String password){
        for (Patient p : patients) {
            if (p.getUsername().equals(username) &&
                p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    public boolean isDoctorAvailable(String doctorID, String date, String time) {
        for (Appointment a : appointments) {
            if (a.getDoctorId().equals(doctorID) &&
                a.getDate().equals(date) &&
                a.getTime().equals(time) && !a.getStatus().equalsIgnoreCase("Cancelled")) {
                
                return false;
            }
        }
        return true;
    }

    public Appointment searchAppointment(String id){
        for (Appointment a : appointments) {
            if (id.equals(a.getId())) {
                return a;
            }
        }
        return null;
    }

    //The process of assigning doctor to patient
    public void assignPatientToDoctor(Doctor d, Patient p){
        if (p.getDoctor() != null && p.getDoctor().getId().equals(d.getId())) {
            System.out.println("Patient is already assigned to this doctor!");
            return;
        }   
        d.addPatient(p);
        p.setDoctor(d);
        FileManager.saveDoctors(doctors);
        FileManager.savePatients(patients);
        System.out.println("The Assignment is done successfully!");
    }

    
    //Display Doctors and patients
    public void displayDoctors(){
        System.out.println("=== Display Doctors Information ===");
        for (Doctor d : doctors) {
            d.displayProfile();
            System.out.println();
            System.out.println("====================================");
        }
    }

    public void displayPatients(){
        System.out.println("=== Display Patients Information ===\n");
        for (Patient p : patients) {
            p.displayProfile();
            System.out.println();
            System.out.println("====================================");
        }
    }

    public void viewAllAppointments() {
    if (appointments.isEmpty()) {
        System.out.println("No appointments found.");
        return;
    }
    System.out.println("\n=== All Appointments ===");
    for (Appointment a : appointments) {
        System.out.println();
        a.DisplayAll();
        System.out.println();
        System.out.println("====================================");
    }
}

    //Getters for Admin
    public ArrayList<Doctor> getDoctors(){
        return this.doctors;
    }

    public ArrayList<Patient> getPatients(){
        return this.patients;
    }

    public ArrayList<Appointment> getAppointments(){
        return this.appointments;
    }

    //======== Generating Reports ===========
    public void reports(){
        System.out.println("\n============ Hospital Reports ============\n");
        System.out.println("Total number of Doctors: " + doctors.size());
        System.out.println("Total number of Patients: " + patients.size());
        System.out.println("Total number of Appointments: " + appointments.size());

        int confirmed = 0, completed = 0, cancelled = 0;
        for (Appointment a : appointments) {
            switch (a.getStatus().toLowerCase()) {
                case "confirmed":
                    confirmed++;
                    break;
                case "completed":
                    completed++;
                    break;
                case "cancelled":
                    cancelled++;
                    break;
            }
        }

        System.out.println("\nConfirmed Appointments: " + confirmed);
        System.out.println("Completed Appointments: " + completed);
        System.out.println("Cancelled Appointments: " + cancelled);

        // TOP 3 DOCTORS HERE
        ArrayList<Doctor> sortDoctors = new ArrayList<>(doctors);
        for (int i = 0; i < sortDoctors.size() - 1; i++) {
            for (int j = i + 1; j < sortDoctors.size(); j++) {
                if (sortDoctors.get(j).getAppointments().size() >
                    sortDoctors.get(i).getAppointments().size()) {

                    Doctor temp = sortDoctors.get(j);
                    sortDoctors.set(j, sortDoctors.get(i));
                    sortDoctors.set(i, temp);
                }
            }
        }

        System.out.println("\n======= Top 3 Doctors =======");
        int size = Math.min(3, sortDoctors.size());
        for (int i = 0; i < size; i++) {
            System.out.println("\nDoctor number " + (i + 1) + ":\n");
            System.out.println("Name: " + sortDoctors.get(i).getName());
            System.out.println("number of appointment: " +
                sortDoctors.get(i).getAppointments().size());
            System.out.println("====================================");
        }
    }
}