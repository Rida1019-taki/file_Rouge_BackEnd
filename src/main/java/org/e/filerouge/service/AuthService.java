package org.e.filerouge.service;

import org.e.filerouge.dto.auth.AuthResponse;
import org.e.filerouge.dto.auth.LoginRequest;
import org.e.filerouge.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest r);

    AuthResponse login(LoginRequest r);
}
