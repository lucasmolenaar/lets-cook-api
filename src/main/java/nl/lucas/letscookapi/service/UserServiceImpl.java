package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.exception.BadRequestException;
import nl.lucas.letscookapi.exception.UsernameAlreadyInUseException;
import nl.lucas.letscookapi.model.Authority;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.model.User;
import nl.lucas.letscookapi.repository.UserRepository;
import nl.lucas.letscookapi.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UsernameNotFoundException(username);
        }

    }

    @Override
    public String createUser(User user) {
        if (userRepository.existsById(user.getUsername())) throw new UsernameAlreadyInUseException();

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        //Setting standard authority
        Authority standardAuthority = new Authority(user.getUsername(), "ROLE_USER");
        Set<Authority> standardAuthorities = new HashSet<>();
        standardAuthorities.add(standardAuthority);

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setEmail(user.getEmail());
        newUser.setEnabled(true);
        newUser.setApiKey(randomString);
        newUser.setAuthorities(standardAuthorities);

        userRepository.save(newUser);

        return newUser.getUsername();
    }

    @Override
    public void updateUser(String username, User updatedUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        if (userRepository.existsById(updatedUser.getUsername())) throw new UsernameAlreadyInUseException();

        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());

        User user = userRepository.findById(username).get();
        user.setPassword(encodedPassword);
        user.setEmail(updatedUser.getEmail());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userRepository.deleteById(username);
        } else {
            throw new UsernameNotFoundException(username);
        }
     }

    @Override
    public Set<Authority> getAuthorities(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get().getAuthorities();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public void addAuthority(String username, String authority) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addAuthority(new Authority(username, authority));
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public void removeAuthority(String username, String authority) {
        //Check if username exists and if authority is valid
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        if (!authority.equals("ROLE_ADMIN") && !authority.equals("ROLE_USER")) throw new BadRequestException();

        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    @Override
    public List<Recipe> getOwnedRecipes(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getOwnedRecipes();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
