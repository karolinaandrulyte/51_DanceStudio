package lt.ca.javau10.dancestudio.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.entities.UserEntity;

public class EntityMapperTest {

    private EntityMapper entityMapper;

    @BeforeEach
    void setUp() {
        entityMapper = new EntityMapper();
    }

    @Test
    void testToUserEntity() {
        UserDto dto = new UserDto(1L, "testUser", "test@example.com", "password", "First", "Last", null, "DanceStyle", "Description");

        UserEntity entity = entityMapper.toUserEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getDanceStyle(), entity.getDanceStyle());
        assertEquals(dto.getDescription(), entity.getDescription());
    }

    @Test
    void testToUserDto() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername("testUser");
        entity.setEmail("test@example.com");
        entity.setPassword("password");
        entity.setFirstName("First");
        entity.setLastName("Last");
        entity.setDanceStyle("DanceStyle");
        entity.setDescription("Description");

        UserDto dto = entityMapper.toUserDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getPassword(), dto.getPassword());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getDanceStyle(), dto.getDanceStyle());
        assertEquals(entity.getDescription(), dto.getDescription());
    }
}
