package com.usermanagement.usermanagement.controllers;

import com.usermanagement.usermanagement.entities.LoginRequest;
import com.usermanagement.usermanagement.entities.LoginResponse;
import com.usermanagement.usermanagement.entities.User;
import com.usermanagement.usermanagement.exceptions.UserAlreadyPresentInDatabaseException;
import com.usermanagement.usermanagement.repositories.UserRepository;
import com.usermanagement.usermanagement.services.CustomUserDetailsService;
import com.usermanagement.usermanagement.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping(value = "/welcome")
    public String welcome() {
        return "<h1>Welcome</h1>";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }
        catch(AuthenticationException e) {
            return new ResponseEntity<>("Bad Credentials", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody User signupRequest) {
        System.out.println("Mango");
        System.out.println(signupRequest.getEmail());

        try{
            System.out.println(signupRequest.getEmail());

            Optional<User> opObj = userRepository.findById(signupRequest.getEmail());

            if(opObj.isPresent())
                throw new UserAlreadyPresentInDatabaseException("The user is already registered. Kindly login or signup with a different mail id");

            userRepository.save(signupRequest);

            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        }
        catch(UserAlreadyPresentInDatabaseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping(value = "isAuthenticated")
//    public ResponseEntity<?> isAuthenticated() {
//
//    }
}
