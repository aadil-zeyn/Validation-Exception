package com.javatechie.api.controller;

import com.javatechie.api.dto.UserRequest;
import com.javatechie.api.entity.User;
import com.javatechie.api.exception.UserNotFoundException;
import com.javatechie.api.repository.UserRepository;
import com.javatechie.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private UserRepository repository;

    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest){
    	User user =new User(0, userRequest.getName(), userRequest.getEmail(),
                userRequest.getMobile(), userRequest.getGender(), userRequest.getAge(), userRequest.getNationality());
    	repository.save(user);
    	return ResponseEntity.ok().body(user);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
    	User user= repository.findByUserId(id);
        if(user!=null){
        	return ResponseEntity.ok(user);
        }else{
            throw new UserNotFoundException("user not found with id : "+id);
        }
        
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserRequest userRequest,@PathVariable int id) throws UserNotFoundException{
        
    	User u=repository.findByUserId(id);
		if(u==null)
			throw new UserNotFoundException("user not found with id : "+id);
		 User user =new User(id, userRequest.getName(), userRequest.getEmail(),
                 userRequest.getMobile(), userRequest.getGender(), userRequest.getAge(), userRequest.getNationality());

    	return ResponseEntity.ok().body(user);
    }
    
}
