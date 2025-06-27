package model;

public class User {
    private String username;
    private String password;
    private String role;
    private String ssn;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getSsn() { return ssn; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setSsn(String ssn) { this.ssn = ssn; }
}

