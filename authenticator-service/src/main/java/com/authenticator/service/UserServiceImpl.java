package com.authenticator.service;

import com.authenticator.dto.request.RegisterRequest;
import com.authenticator.dto.response.UserResponse;
import com.authenticator.model.Role;
import com.authenticator.model.User;
import com.authenticator.repository.RoleRepository;
import com.authenticator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }

    @Override
    public UserResponse getUserById(long id){

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){

            return modelToResponse(userOptional.get());
        }

        return null;
    }

    @Override
    public User findByUsername(String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()){

            return userOptional.get();
        }

        return null;
    }

    @Override
    public User findByEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()){

            return userOptional.get();
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username);

        if(user != null){
            return new UserDetailsImpl(user);
        }
        else{
            throw new UsernameNotFoundException(username);
        }
    }

    @Override
    public List<UserResponse> getUsers() {

        List<UserResponse> response = new ArrayList<>();

        for(User user : userRepository.findAll()){

            response.add(modelToResponse(user));
        }

        return response;
    }



    @Override
    public Set<Long> getRoles(User user){

        Set<Long> roles = new HashSet<>();

        for(Role role : user.getRoles()){
            roles.add(role.getId());
        }

        return roles;
    }

    @Override
    public User saveUser(User user){

        return userRepository.save(user);
    }

    private UserResponse modelToResponse(User user){

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.isStatus())
                .roles(getRoles(user))
                .build();
    }

    @Override
    public boolean register(RegisterRequest registerRequest) {

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

        User newUser = saveUser(user);

        return newUser != null;
    }

    @Override
    public boolean createAdmin(RegisterRequest registerRequest) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("ROLE_ADMIN"));

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setRoles(roles);

        User newUser = saveUser(user);

        return newUser != null;
    }
}
