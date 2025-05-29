package com.example.htqlCV.Controller;

import java.util.Arrays;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.roleRequestDTO;
import com.example.htqlCV.DAO.request.userRequestDTO;
import com.example.htqlCV.Model.role;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Service.roleServices;
import com.example.htqlCV.Service.userHasRoleServices;
import com.example.htqlCV.Service.userSevices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class commonController {
    private final userSevices userSevices;
    private final roleServices roleServices;
    private final userHasRoleServices userHasRoleServices;
    @PostMapping("/setup")
    public String setup(@RequestParam String secure_code) {
        // try {
            if(secure_code.equals("ttt")) {
                roleRequestDTO adminRole=new roleRequestDTO();
                adminRole.setName("admin");
                role adminRoleId=roleServices.createRole(adminRole);
    
                roleRequestDTO UserRole=new roleRequestDTO();
                UserRole.setName("user");
                role userRoleId=roleServices.createRole(UserRole);
                roleRequestDTO OwneRole=new roleRequestDTO();
                OwneRole.setName("owner");
                role OwneRoleId=roleServices.createRole(OwneRole);

                userRequestDTO admin = new userRequestDTO();
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPhoneNumber("0123456789");
                admin.setAddress("Hà Nội");
                admin.setRoles(Arrays.asList(adminRoleId.getId()));
                user adminId=userSevices.createUser(admin);
                return "Setup user admin successfully";
            }
            return "Setup user admin failed";
            
        // } catch (Exception e) {
        //     return "Setup user admin failed";
        // }
        
            
    
    }   
}
