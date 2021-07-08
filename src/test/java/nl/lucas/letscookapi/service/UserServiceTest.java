package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.BadRequestException;
import nl.lucas.letscookapi.model.Authority;
import nl.lucas.letscookapi.model.User;
import nl.lucas.letscookapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldGetUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userRepository.findAll()).thenReturn(userList);
        Collection<User> actualUsers = this.userService.getUsers();
        assertSame(userList, actualUsers);
        assertTrue(actualUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    public void shouldGetUserByUsername() {
        User user = new User();
        user.setPassword("password");
        user.setApiKey("Api Key");
        user.setEmail("test@test.com");
        user.setUsername("user");
        Optional<User> ofResult = Optional.<User>of(user);

        when(this.userRepository.findById(anyString())).thenReturn(ofResult);
        Optional<User> actualUser = this.userService.getUser("user");
        assertSame(ofResult, actualUser);
        assertTrue(actualUser.isPresent());
        verify(this.userRepository).findById(anyString());
        assertTrue(this.userService.getUsers().isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFoundWhileGettingUser() {
        when(this.userRepository.findById(anyString())).thenReturn(Optional.<User>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.userService.getUser("user"));
        verify(this.userRepository).findById(anyString());
    }

    @Test
    public void shouldDeleteUser() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("test@test.com");
        user.setUsername("user");
        user.setEnabled(true);
        Optional<User> ofResult = Optional.<User>of(user);

        doNothing().when(this.userRepository).deleteById(anyString());
        when(this.userRepository.findById(anyString())).thenReturn(ofResult);
        this.userService.deleteUser("user");
        verify(this.userRepository).deleteById(anyString());
        verify(this.userRepository).findById(anyString());
        assertTrue(this.userService.getUsers().isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFoundWhileDeleting() {
        doNothing().when(this.userRepository).deleteById(anyString());
        when(this.userRepository.findById(anyString())).thenReturn(Optional.<User>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.userService.deleteUser("user"));
        verify(this.userRepository).findById(anyString());
    }

    @Test
    public void shouldGetUserAuthorities() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("test@test.com");
        user.setUsername("user");
        HashSet<Authority> authoritySet = new HashSet<Authority>();
        user.setAuthorities(authoritySet);
        Optional<User> ofResult = Optional.<User>of(user);

        when(this.userRepository.findById(anyString())).thenReturn(ofResult);
        Set<Authority> actualAuthorities = this.userService.getAuthorities("user");
        assertSame(authoritySet, actualAuthorities);
        assertTrue(actualAuthorities.isEmpty());
        verify(this.userRepository).findById(anyString());
        assertTrue(this.userService.getUsers().isEmpty());
    }

    @Test
    public void shouldThrowErrorWhenUsernameNotFoundWhileGettingAuthorities() {
        when(this.userRepository.findById(anyString())).thenReturn(Optional.<User>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.userService.getAuthorities("user"));
        verify(this.userRepository).findById(anyString());
    }

    @Test
    public void shouldAddAuthorityToUser() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("test@test.nl");
        user.setUsername("user");
        user.setAuthorities(new HashSet<Authority>());
        Optional<User> ofResult = Optional.<User>of(user);

        User user1 = new User();
        user1.setPassword("password");
        user1.setEmail("test1@test.com");
        user1.setUsername("user1");
        user1.setAuthorities(new HashSet<Authority>());

        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById(anyString())).thenReturn(ofResult);
        this.userService.addAuthority("user", "ROLE_TEST");
        verify(this.userRepository).findById(anyString());
        verify(this.userRepository).save((User) any());
        assertTrue(this.userService.getUsers().isEmpty());
    }

    @Test
    public void shouldThrowErrorWhenUsernameNotFoundWhileAddingAuthority() {
        User user = new User();
        user.setPassword("password");
        user.setEmail("test@test.com");
        user.setUsername("user");
        user.setAuthorities(new HashSet<Authority>());

        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findById(anyString())).thenReturn(Optional.<User>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.userService.addAuthority("janedoe", "JaneDoe"));
        verify(this.userRepository).findById(anyString());
    }

    @Test
    public void shouldRemoveAuthority() {
        Authority authority = new Authority();
        authority.setUsername("admin");
        authority.setAuthority("ROLE_ADMIN");

        Authority authority1 = new Authority();
        authority1.setUsername("user");
        authority1.setAuthority("ROLE_USER");

        HashSet<Authority> authoritySet = new HashSet<Authority>();
        authoritySet.add(authority1);
        authoritySet.add(authority);

        User user = new User();
        user.setPassword("password");
        user.setEmail("test@test.com");
        user.setUsername("admin");
        user.setAuthorities(authoritySet);
        Optional<User> ofResult = Optional.<User>of(user);

        User user1 = new User();
        user1.setPassword("password");
        user1.setEmail("test@test.com");
        user1.setUsername("user");
        user1.setAuthorities(new HashSet<Authority>());

        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById(anyString())).thenReturn(ofResult);
        when(this.userRepository.existsById(anyString())).thenReturn(true);
        this.userService.removeAuthority("admin", "ROLE_ADMIN");
        verify(this.userRepository).existsById(anyString());
        verify(this.userRepository).findById(anyString());
        verify(this.userRepository).save((User) any());
        assertTrue(this.userService.getUsers().isEmpty());
    }

    @Test
    public void shouldThrowBadRequestExceptionWhileRemovingAuthority() {
        when(this.userRepository.existsById(anyString())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> this.userService.removeAuthority("user", "ROLE_TEST"));
        verify(this.userRepository).existsById(anyString());
    }

    @Test
    public void shouldThrowUsernameNotFoundExceptionWhileRemovingAuthority() {
        when(this.userRepository.existsById(anyString())).thenReturn(false);
        assertThrows(UsernameNotFoundException.class, () -> this.userService.removeAuthority("user", "ROLE_TEST"));
        verify(this.userRepository).existsById(anyString());
    }
}
