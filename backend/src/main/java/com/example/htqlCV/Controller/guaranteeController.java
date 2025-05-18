package com.example.htqlCV.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.guaranteeRequestDTO;
import com.example.htqlCV.Model.guarantee;
import com.example.htqlCV.Service.guaranteeServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/guarantee")
@RequiredArgsConstructor
public class guaranteeController {
    private final guaranteeServices guaranteeServices;
    @GetMapping("/{id}/product")
    public List<guarantee> getGuaranteeProduct(@PathVariable UUID id) {
        return guaranteeServices.getGuaranteeByProductId(id);
    }

    @PostMapping("/")
    public UUID createProduct(@RequestBody guaranteeRequestDTO guaranteeRequestDTO) {
        return guaranteeServices.createGuarantee(guaranteeRequestDTO);
    }
    
    @DeleteMapping("/{id}/delete")
    public void deleteProduct(@PathVariable UUID id) {
        guaranteeServices.deleteGuarantee(id);
    }
}
