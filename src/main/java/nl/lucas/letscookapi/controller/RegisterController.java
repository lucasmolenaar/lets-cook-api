package nl.lucas.letscookapi.controller;

import nl.lucas.letscookapi.model.User;
import nl.lucas.letscookapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/v1/api/register")
    public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
        userService.createUser(newUser);
        return ResponseEntity.ok().body(newUser.getUsername() + " has been registered");

    }
}
