import java.io.*; 
import java.util.*;

public class FileManager {

    // ================== DOCTORS ==================
    public static void saveDoctors(ArrayList<Doctor> doctors) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("doctors.txt"))) {

            for (Doctor d : doctors) {
                writer.println(
                        d.getId() + "," +
                        d.getName() + "," +     
                        d.getSpecialization() + "," +
                        d.getDepartment() + "," +
                        d.getPhone() + "," +
                        d.getAvailability()   
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving doctors");
        }
    }

    public static ArrayList<Doctor> loadDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("doctors.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Doctor d = new Doctor(
                        data[0], // id
                        data[1], // name
                        "", // username
                        "", // password 
                        "doctor", // role
                        data[2], // specialization
                        data[3], // department
                        data[4]   // phone
                );

                doctors.add(d);
            }

        } catch (IOException e) {
            System.out.println("No doctors file found");
        }

        return doctors;
    }

    // ================== PATIENTS ==================
    public static void savePatients(ArrayList<Patient> patients) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("patients.txt"))) {

            for (Patient p : patients) {
                String doctorId = "None";
                
                if(p.getDoctor()!= null){
                doctorId = p.getDoctor().getId();
                }
                writer.println(
                        p.getId() + "," +
                        p.getName() + "," +
                        p.getAge() + "," +
                        p.getGender() + "," +
                        p.getPhone() + "," +
                        doctorId        
                );
                
            }
            

        } catch (IOException e) {
            System.out.println("Error saving patients");
        }
    }

    public static ArrayList<Patient> loadPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        ArrayList<Doctor> doctors = loadDoctors();

        try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Patient p = new Patient(
                        data[0], // id
                        data[1], // name
                        "", // username
                        "", // password
                        Integer.parseInt(data[2]), // age
                        data[3], // gender
                        data[4],  // phone  
                        null
                );
                String doctorId = data[5];
                if(!doctorId.equals("None")){
                  for(Doctor d : doctors){
                    if(d.getId().equals(doctorId)){
                       p.setDoctor(d);
                       break;
                    }
                  }
                }

                patients.add(p);
            }

        } catch (IOException e) {
            System.out.println("No patients file found");
        }

        return patients;
    }

    // ================== APPOINTMENTS ==================
    public static void saveAppointments(ArrayList<Appointment> appointments) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("appointments.txt"))) {

            for (Appointment a : appointments) {
                writer.println(
                        a.getId() + "," +
                        a.getPatientId() + "," +
                        a.getDoctorId() + "," +
                        a.getDate() + "," +
                        a.getTime() + "," +
                        a.getStatus()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving appointments");
        }
    }

    public static ArrayList<Appointment> loadAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Appointment a = new Appointment(
                        data[0], // id
                        data[1], // patientId
                        data[2], // doctorId
                        data[3], // date
                        data[4], // time
                        data[5]  // status
                );

                appointments.add(a);
            }

        } catch (IOException e) {
            System.out.println("No appointments file found");
        }

        return appointments;
    }
    // ================== USERS ==================
    public static void saveUsers(ArrayList<User> users){
        
    try(PrintWriter writer = new PrintWriter (new FileWriter ("users.txt"))){
        for(User U : users){
        String role = "";
        if(U instanceof Admin){
        role = "Admin";}
        else if(U instanceof Doctor){
        role = "Doctor";}
        else if(U instanceof Patient){
        role = "Patient";}

        writer.println(
        U.getUsername()+ "," + 
        U.getPassword()+ "," + 
        role + ","+
        U.getId()        
        );
        }
    } catch(IOException e){
        System.out.println("Error saving users");
        }
    }

    public static void loadUsers(ArrayList<Doctor> doctors, ArrayList<Patient> patients) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            String role = data[2];

            if (role.equalsIgnoreCase("doctor")) {
                for (Doctor d : doctors) {
                    if (d.getId().equals(data[3] )) { // data[3] = id
                        d.setUsername(data[0]);
                        d.setPassword(data[1]);
                        break;
                }
                }
            } 
            else if (role.equalsIgnoreCase("patient")) {
                for (Patient p : patients) {
                    if (p.getId().equals(data[3])) {
                        p.setUsername(data[0]);
                        p.setPassword(data[1]);
                        break;
                }
            }
            }
        }
    } catch(IOException e){
        System.out.println("No users file found");
    }
}
}

