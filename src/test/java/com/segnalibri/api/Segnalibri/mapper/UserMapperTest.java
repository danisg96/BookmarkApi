package com.segnalibri.api.Segnalibri.mapper;

import com.segnalibri.api.Segnalibri.dto.UserDto;
import com.segnalibri.api.Segnalibri.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.segnalibri.api.Segnalibri.model.Role.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserMapperTest {

    private final UserMapper userMapper;

    private User user;
    private UserDto userDto;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void userToDtoTest(){
        user = User.builder()
                .email("danisg96@hotmail.it")
                .password("pwd")
                .role(ADMIN)
                .build();

        userDto = userMapper.EntityToDto(user);

        assertThat(userDto.email()).isEqualTo(user.getEmail());
        assertThat(userDto.password()).isEqualTo("***");
    }

    @Test
    void dtoToUserTest(){
        userDto = UserDto.builder()
                .email("danisg96@hotmail.it")
                .password("pwd")
                .build();


        user = userMapper.DtoToEntity(userDto);

        assertThat(user.getEmail()).isEqualTo(userDto.email());
        assertThat(user.getPassword()).isEqualTo(userDto.password());
    }
}
