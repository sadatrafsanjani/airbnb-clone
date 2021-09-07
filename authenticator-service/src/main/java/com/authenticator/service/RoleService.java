package com.authenticator.service;

import com.authenticator.model.Role;

public interface RoleService {

    Role findRole(String role);
    Role saveRole(Role role);
}
