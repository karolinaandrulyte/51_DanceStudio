package lt.ca.javau10.dancestudio.payload.requests;

public class SignupRequest {

	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	
	private boolean isTeacher;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

	@Override
	public String toString() {
		return "SignupRequest [username=" + username + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", password=" + password + ", isTeacher=" + isTeacher + "]";
	}
    
}