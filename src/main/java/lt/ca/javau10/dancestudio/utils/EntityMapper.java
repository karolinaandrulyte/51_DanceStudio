package lt.ca.javau10.dancestudio.utils;

import org.springframework.stereotype.Component;

import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.entities.UserEntity;

@Component
public class EntityMapper {

	public UserEntity toUserEntity(UserDto dto) {
		
		UserEntity entity = new UserEntity();
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setDanceStyle(dto.getDanceStyle()); 
	    entity.setDescription(dto.getDescription());
		
		// Set roles if they are present in the DTO
	    if (dto.getRoles() != null) {
	        entity.setRoles(dto.getRoles()); // Ensure roles are being assigned
	    }
		return entity;		
	}
	//Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities)

	public UserDto toUserDto(UserEntity entity) {
		return new UserDto( 
				entity.getId(), 
				entity.getUsername(), 
				entity.getEmail(), 
				entity.getPassword(), 
				entity.getFirstName(),
				entity.getLastName(),
				entity.getRoles(),
				entity.getDanceStyle(), 
		        entity.getDescription() 
				
			);
	}

}
