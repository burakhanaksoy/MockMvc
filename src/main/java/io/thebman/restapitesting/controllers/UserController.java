package io.thebman.restapitesting.controllers;

import io.thebman.restapitesting.service.UserService;
import io.thebman.restapitesting.view.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users/{id}")
    public User getUserById(@PathVariable int id){

        return userService.getUser(id);
    }

    @RequestMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public @ResponseBody
    ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.CREATED);
    }
}
