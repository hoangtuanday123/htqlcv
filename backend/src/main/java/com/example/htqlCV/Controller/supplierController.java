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

import com.example.htqlCV.DAO.request.supplierRequestDTO;
import com.example.htqlCV.Model.supplier;
import com.example.htqlCV.Service.supplierServices;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class supplierController {
    private final supplierServices supplierServices;

    @GetMapping("/")
    public List<supplier> getSuppliers() {
        return supplierServices.getAllSupplier();
    }

    @GetMapping("/{id}")
    public supplier getSupplier(@PathVariable UUID id) {
        return supplierServices.getSupplierById(id);
    }

    @PostMapping("/")
    public UUID createSupplier(@RequestBody supplierRequestDTO supplierRequestDTO) {
        return supplierServices.createSupplier(supplierRequestDTO);
    }

    @PutMapping("{id}/update")
    public void updateSupplier(@PathVariable UUID id, @RequestBody supplierRequestDTO supplierRequestDTO) {
       supplierServices.updateSupplier(id, supplierRequestDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteSupplier(@PathVariable UUID id) {
        supplierServices.deleteSupplier(id);
    }
    
    
    
}
