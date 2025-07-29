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

import com.example.htqlCV.DAO.request.saleOrdersRequestDTO;
import com.example.htqlCV.Model.saleOrders;
import com.example.htqlCV.Service.saleOrdersServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/saleOrders")
@RequiredArgsConstructor
public class saleOrdersController {
    private final saleOrdersServices saleOrdersServices;
    @GetMapping
    public List<saleOrders> getSaleOrders(@RequestParam String businessId) {
        return saleOrdersServices.getAllSaleOrders(businessId);
    }
    @GetMapping("/{id}")
    public saleOrders getSaleOrder(@PathVariable String id) {
        return saleOrdersServices.getSaleOrdersById(id);
    }
    @PostMapping("/")
    public String createSaleOrder(@RequestBody saleOrdersRequestDTO saleOrdersRequestDTO) {
        return saleOrdersServices.createSaleOrder(saleOrdersRequestDTO);
    }
    @PutMapping("/{id}/update")
    public void updateSaleOrder(@PathVariable String id, @RequestBody saleOrdersRequestDTO saleOrdersRequestDTO) {
        saleOrdersServices.updateSaleOrder(id, saleOrdersRequestDTO);
    }
    @PutMapping("/{id}/refund")
    public void refundSaleOrder(@PathVariable String id, @RequestBody saleOrdersRequestDTO saleOrdersRequestDTO) {
        saleOrdersServices.refundSaleOrder(id, saleOrdersRequestDTO);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteSaleOrder(@PathVariable String id){
        saleOrdersServices.deleteSaleOrder(id);
    }
}
