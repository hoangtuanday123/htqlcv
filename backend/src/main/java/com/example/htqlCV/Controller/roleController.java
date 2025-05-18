package com.example.htqlCV.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.roleRequestDTO;
import com.example.htqlCV.Model.role;
import com.example.htqlCV.Service.roleServices;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class roleController {
    private final roleServices roleServices;

    @GetMapping("/")
    public List<role> getRoleList() {
        return roleServices.getAllRole();
    }

    @PostMapping("/")
    public role createRole(@RequestBody roleRequestDTO roleRequestDTO) {
        return roleServices.createRole(roleRequestDTO);
    }
    
    @GetMapping("/{id}")
    public role getRole(@PathVariable UUID id) {
        return roleServices.getRoleById(id);
    }
    
    @PutMapping("/{id}/update")
    public role updateRole(@PathVariable UUID id, @RequestBody roleRequestDTO roleRequestDTO) {
       return roleServices.updateRole(id, roleRequestDTO);
    }
    
    @DeleteMapping("/{id}/delete")
    public void deleteRole(@PathVariable UUID id) {
        roleServices.deleteRole(id);
    }
}
