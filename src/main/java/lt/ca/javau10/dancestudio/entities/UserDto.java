package lt.ca.javau10.dancestudio.entities;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto implements UserDetails {
	
	private static final long serialVersionUID = -1L;
	
	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	
	private Set<Role> roles;
	
	@JsonIgnore //One way road 
	private String password; 
	
	private String danceStyle;
	private String description;
	
	public UserDto() {}
	
	public UserDto(Long id, String username, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UserDto(Long id, String username, String email, String password, String firstName, String lastName, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
	}

	public UserDto(Long id, String username, String email, String password, String firstName, String lastName, Set<Role> roles, String danceStyle, String description) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.danceStyle = danceStyle; 
	    this.description = description;
	}
	
	public UserDto(String username, String email, String firstName, String lastName, String password) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	public UserDto(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.roles = userEntity.getRoles();
        this.danceStyle = userEntity.getDanceStyle(); 
        this.description = userEntity.getDescription(); 
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDanceStyle() {
		return danceStyle;
	}

	public void setDanceStyle(String danceStyle) {
		this.danceStyle = danceStyle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override // serializable object to provide to JSON, returns an object to return authority
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
               .map(role -> new SimpleGrantedAuthority(role.getName()))
               .collect(Collectors.toSet());
	} // needed to create/add roles to users

	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	  @Override
	  public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (o == null || getClass() != o.getClass())
	      return false;
	    UserDto user = (UserDto) o;
	    return Objects.equals(id, user.id);
	  }

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", roles=" + roles + ", password=" + password + ", danceStyle="
				+ danceStyle + ", description=" + description + "]";
	}

}
