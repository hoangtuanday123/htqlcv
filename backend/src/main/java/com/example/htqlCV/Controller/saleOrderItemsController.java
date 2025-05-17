package com.example.htqlCV.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.htqlCV.DAO.request.saleOrderItemsRequestDTO;
import com.example.htqlCV.Model.saleOrderItems;
import com.example.htqlCV.Service.saleOrderItemsServices;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/saleOrderItems")
@RequiredArgsConstructor
public class saleOrderItemsController {
    private final saleOrderItemsServices saleOrderItemsServices;
    @GetMapping("/{id}/saleOrderItem")
    public List<saleOrderItems> getItemsByOrder(@PathVariable Long id) {
        return saleOrderItemsServices.getSaleOrderItemsBySaleOrder(id);
    }

    @PostMapping("/")
    public Long createItem(@RequestBody saleOrderItemsRequestDTO saleOrderItemsRequestDTO) {
        return saleOrderItemsServices.createSaleOrderItems(saleOrderItemsRequestDTO);
    }
    
    @PutMapping("/{id}/update")
    public void updateItem(@PathVariable Long id, @RequestBody saleOrderItemsRequestDTO saleOrderItemsRequestDTO) {
        saleOrderItemsServices.updateSaleOrderItems(id, saleOrderItemsRequestDTO);
    }

    @DeleteMapping("{id}/delete")
    public void deleteItem(@PathVariable Long id){
        saleOrderItemsServices.deleteSaleOrderItems(id);
    }
    
}
