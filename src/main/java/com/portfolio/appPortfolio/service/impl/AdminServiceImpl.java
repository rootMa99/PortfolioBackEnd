package com.portfolio.appPortfolio.service.impl;

import com.portfolio.appPortfolio.entity.AdminEntity;
import com.portfolio.appPortfolio.repositories.AdminRepository;
import com.portfolio.appPortfolio.service.AdminService;
import com.portfolio.appPortfolio.shared.AdminDto;
import com.portfolio.appPortfolio.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;
    //@Autowired
    //Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public AdminDto createAdmin(AdminDto adminDto) {

        if (adminRepository.findByAdminName(adminDto.getAdminName()).isPresent()){
            throw new RuntimeException("admin already existing");
        }

        ModelMapper mp= new ModelMapper();
        AdminEntity adminEntity=mp.map(adminDto, AdminEntity.class);
        //String value= utils.generateAdminId(24);
        String value= "iiiiooaa";
        adminEntity.setAdminId(value);
        adminEntity.setEncryptedAdminPassword( bCryptPasswordEncoder.encode(adminDto.getAdminPassword()));

        AdminEntity storedAdminValue = adminRepository.save(adminEntity);
        AdminDto returnDto = new AdminDto();
        BeanUtils.copyProperties(storedAdminValue, returnDto);
        return returnDto;
    }

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {

        Optional<AdminEntity> admin= adminRepository.findByAdminName(adminName);
        UserBuilder userBuilder;

        if (admin.isPresent()){
            AdminEntity currentUser=admin.get();
            userBuilder=User.withUsername(adminName);
            userBuilder.password(currentUser.getEncryptedAdminPassword());
            userBuilder.roles("USER");
        }else{
            throw new UsernameNotFoundException("Admin not found or blocked");
        }
        return userBuilder.build();
    }
}
