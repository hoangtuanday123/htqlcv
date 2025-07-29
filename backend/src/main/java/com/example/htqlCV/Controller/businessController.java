package com.example.htqlCV.Controller;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.businessRequestDTO;
import com.example.htqlCV.Model.business;
import com.example.htqlCV.Service.businessServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/businesses")
@RequiredArgsConstructor
public class businessController {
    private final businessServices businessServices;
    @GetMapping("/")
    public List<business> getbusinesses() {
        return businessServices.getBusinesses();
    }

    @GetMapping("/{id}")
    public business getBusiness(@PathVariable String id){
        return businessServices.getBusinessById(id);
    }

    @PostMapping("/")
    public String createBusiness(@RequestBody businessRequestDTO businessRequestDTO) {
       return businessServices.createBusiness(businessRequestDTO);
    }

    @PutMapping("/{id}/update")
    public void  updateBusiness(@PathVariable String id, @RequestBody businessRequestDTO businessRequestDTO) {
        businessServices.updateBusiness(id, businessRequestDTO);
    }

    @PutMapping("/{id}/block")
    public void blockBusiness(@PathVariable String id) {
        businessServices.blockBusiness(id);
    }

    @PutMapping("/{id}/open")
    public void openBlockBusiness(@PathVariable String id) {
        businessServices.openBlockBusiness(id);
    }
    
    
}
