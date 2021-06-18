package nl.lucas.letscookapi.service;

import nl.lucas.letscookapi.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String username);

}
