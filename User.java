public abstract class User {

    
    private String id;
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
    private String role; // This for the user.txt to know who is the user 
    

    
    public User (String id, String name, String username, String password, String phoneNumber, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    
    public void setName (String name) { this.name = name; }
    public void setUsername (String username) { this.username = username; }
    public void setPassword (String password) { this.password = password; }
    public void setPhoneNumber (String phoneNumber) { this.phoneNumber = phoneNumber; }

    
    public String getId () { return id; }
    public String getName () { return name; }
    public String getUsername () { return username; }
    public String getPassword () { return password; }
    public String getPhone () { return phoneNumber; }
    public String getRole () { return role; }

    
    public void displayProfile  () {
        System.out.println("\nID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Phone: " + phoneNumber);
    }
    
    public abstract void showMenu ();

}