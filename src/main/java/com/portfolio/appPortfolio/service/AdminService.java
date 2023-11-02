package com.portfolio.appPortfolio.service;

import com.portfolio.appPortfolio.shared.AdminDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService {
    AdminDto createAdmin(AdminDto adminDto);
    UserDetails loadUserByUsername(String adminName);
}
