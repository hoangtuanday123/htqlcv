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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.customerRequestDTO;
import com.example.htqlCV.Model.customer;
import com.example.htqlCV.Service.customerServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class customerController {
    private final customerServices customerServices;

    @GetMapping
    public List<customer>getCustomerList(@RequestParam UUID businessId) {
        return customerServices.getCustomers(businessId);
    }

    @GetMapping("/{id}")
    public customer getcustomer(@PathVariable UUID id){
        return customerServices.getCustomer(id);
    }

    @PostMapping("/")
    public UUID createCustomer(@RequestBody customerRequestDTO customerRequestDTO) {
    
        return customerServices.createCustomer(customerRequestDTO);
    }

    @PutMapping("{id}/update")
    public void updateCustomer(@PathVariable UUID id, @RequestBody customerRequestDTO customerRequestDTO) {
        customerServices.updateCustomer(id, customerRequestDTO);
        
    }

    @DeleteMapping("/{id}/delete")
    public void deleteCustomer(@PathVariable UUID id){
        customerServices.deleteCustomer(id);
    }
    
    
}
