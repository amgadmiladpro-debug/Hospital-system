public class Appointment {

    private String AppointmentID;
    private String patientID;
    private String doctorID;
    private String date;
    private String time;
    private String status;

    public Appointment(String AppointmentID,String patientID,String doctorID,String date,String time,String status){
        this.AppointmentID = AppointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public void setId(String AppointmentID){
        this.AppointmentID = AppointmentID;
    }

    public void setPatientID(String patientID){
        this.patientID=patientID;
    }

    public void setDoctorID(String doctorID){
        this.doctorID=doctorID;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setTime(String time){
        this.time=time;
    }

    public void setStatus(String status){

    if (this.status.equalsIgnoreCase("cancelled") &&  status.equals("completed")) {
        System.out.println("Cannot mark a cancelled appointment as completed!");
    } else {
        this.status = status;
        }   
    }

    public String getId() {
        return AppointmentID;
    }

    public String getPatientId() {
        return patientID;
    }

    public String getDoctorId() {
        return doctorID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time; 
    }

    public String getStatus() {
        return status;
    }

    public void DisplayAll(){

        System.out.println("Appointment ID: " + AppointmentID);
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Patient ID: " + patientID);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Status: " + status);

        System.out.println();
    }

}