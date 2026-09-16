package org.e.filerouge.service;

import org.e.filerouge.dto.admin.*;
import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse update(Long id, UserUpdateRequest request);

    void delete(Long id);
}