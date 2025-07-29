package com.example.htqlCV.Controller;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.purchaseOrdersRequestDTO;
import com.example.htqlCV.Model.purchaseOrders;
import com.example.htqlCV.Service.purchaseOrdersServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/purchaseOrders")
@RequiredArgsConstructor
public class purchaseOrdersController {
    private final purchaseOrdersServices purchaseOrdersServices;
    @GetMapping
    public List<purchaseOrders> getPurchaseOrdersBusinessId(@RequestParam String businessId) {
        return purchaseOrdersServices.getAllPurchaseOrders(businessId);
    }
    @GetMapping("/{id}")
    public purchaseOrders getPurchaseOrders(@PathVariable String id) {
        return purchaseOrdersServices.getPurchaseOrdersById(id);
    }
    @PostMapping("/")
    public String createPurchaseOrders(@RequestBody purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        return purchaseOrdersServices.createPurchaseOrders(purchaseOrdersRequestDTO);
    }
    @DeleteMapping("/{id}/delete")
    public void deletePurchaseOrders(@PathVariable String id) {
        purchaseOrdersServices.deletePurchaseOrders(id);
    }
    @PutMapping("/{id}/update")
    public void updatePurchaseOrders(@PathVariable String id, @RequestBody purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        purchaseOrdersServices.updatePurchaseOrders(id, purchaseOrdersRequestDTO);
    }
    @PutMapping("/{id}/refund")
    public void refundPurchaseOrder(@PathVariable String id, @RequestBody purchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        purchaseOrdersServices.refundPurchaseOrder(id, purchaseOrdersRequestDTO);
    }
    
}
