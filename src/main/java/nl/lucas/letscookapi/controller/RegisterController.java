package nl.lucas.letscookapi.controller;

import nl.lucas.letscookapi.model.User;
import nl.lucas.letscookapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
        userService.createUser(newUser);
        return ResponseEntity.ok().body(newUser.getUsername() + " has been registered");

    }
}
