package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> getUser(String username) {
        return null;
    }
}
