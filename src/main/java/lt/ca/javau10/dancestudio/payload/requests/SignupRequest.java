package lt.ca.javau10.dancestudio.payload.requests;

import java.util.Set;

public class SignupRequest {

	private String username;
	private String email;
	private String firstName;
	private String lastName;

	private Set<String> role;

	private String password;

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

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
	
	public boolean isTeacher() { //checkBox teacher or not
		return true;
	}

	@Override
	public String toString() {
		return "SignupRequest [username=" + username + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", role=" + role + ", password=" + password + "]";
	}	
	
}
