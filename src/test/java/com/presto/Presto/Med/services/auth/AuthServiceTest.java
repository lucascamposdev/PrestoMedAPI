package com.presto.Presto.Med.services.auth;

import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.domain.user.UserRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.DataAlreadyExists;
import com.presto.Presto.Med.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> captorUser;

    private final UserRegisterDTO dto = new UserRegisterDTO(
            1L,
            "Lucas",
            "Campos",
            "test@gmail.com",
            "123456",
            "00999999999"
    );

    @Test
    void shouldReturnUserWhenRegister() {
//        ARRANGE
        BDDMockito.given(userRepository.findByEmail("test@gmail.com")).willReturn(null);

//        ACT
        authService.register(dto);

//        ASSERT
        BDDMockito.then(userRepository).should().save(captorUser.capture());
        User createdUser = captorUser.getValue();

        Assertions.assertEquals("test@gmail.com", createdUser.getEmail());
        Assertions.assertEquals("Lucas", createdUser.getName());
        Assertions.assertEquals("Campos", createdUser.getLast_name());
        Assertions.assertEquals("00999999999", createdUser.getPhone());
        Assertions.assertNotNull(createdUser.getPassword());
    }

    @Test
    void throwExceptionWhenEmailAlreadyExists() {
//        ARRANGE
        BDDMockito.given(userRepository.findByEmail("test@gmail.com")).willReturn(new User());

//        ACT
        Assertions.assertThrows(DataAlreadyExists.class, () -> authService.register(dto));
    }
}