package com.gymstarter.library.service;

import com.gymstarter.library.dto.AdminDto;
import com.gymstarter.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
