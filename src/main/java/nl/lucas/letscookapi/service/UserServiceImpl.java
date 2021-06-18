package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.model.Authority;
import nl.lucas.letscookapi.model.User;
import nl.lucas.letscookapi.repository.UserRepository;
import nl.lucas.letscookapi.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
    public boolean userExists(String username) {
        Optional<User> user = userRepository.findById(username);
        return user.isPresent();
    }

    @Override
    public String createUser(User user) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        user.setApiKey(randomString);
        User newUser = userRepository.save(user);
        return newUser.getUsername();
    }

    @Override
    public void updateUser(String username, User updatedUser) {
        if (!userRepository.existsById(username)) {
            throw new UsernameNotFoundException(username);
        }

        User user = userRepository.findById(username).get();
        user.setPassword(updatedUser.getPassword());
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
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }
}
