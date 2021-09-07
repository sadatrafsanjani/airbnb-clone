package com.authenticator.service;

import com.authenticator.dto.request.RegisterRequest;
import com.authenticator.dto.response.UserResponse;
import com.authenticator.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<UserResponse> getUsers();
    User saveUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    Set<Long> getRoles(User user);
    UserResponse getUserById(long id);
    boolean register(RegisterRequest registerRequest);
    boolean createAdmin(RegisterRequest registerRequest);
}
