package fmi.sports.tournament.organizer.backend.services;

import fmi.sports.tournament.organizer.backend.dtos.NewUserDTO;
import fmi.sports.tournament.organizer.backend.entities.user.User;
import fmi.sports.tournament.organizer.backend.entities.user.UserRole;
import fmi.sports.tournament.organizer.backend.repositories.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UsersRepository userRepository;

    @Mock
    private PasswordServiceImpl passwordService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Register user and see whether the password will be hashed")
    void registerUser_ShouldEncodePasswordAndSave() {
        NewUserDTO request = new NewUserDTO("john@doe.com",
                "password",
                "John",
                "Doe",
                LocalDate.now());

        when(passwordService.hash("password")).thenReturn("encoded_securePass123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("john@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.registerUser(request);

        verify(passwordService).hash("password");
        verify(userRepository).save(argThat(user ->
                user.getEmail().equals("john@doe.com") &&
                        user.getPassword().equals("encoded_securePass123")
        ));
    }
}
