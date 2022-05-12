package com.usermanagement.usermanagement.services;

import com.usermanagement.usermanagement.entities.User;
import com.usermanagement.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("CustomUserDetailsService: " +username);

        try{
            User user = userRepository.findById(username).get();
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        }
        catch(Exception e) {
            return new org.springframework.security.core.userdetails.User("foo", "foo", new ArrayList<>());
        }

//        return new org.springframework.security.core.userdetails.User("foo", "foo", new ArrayList<>());
    }
}
