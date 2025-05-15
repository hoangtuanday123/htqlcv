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

import com.example.htqlCV.DAO.request.purchaseOrderItemsRequestDTO;
import com.example.htqlCV.Model.purchaseOrderItems;
import com.example.htqlCV.Service.purchaseOrderItemsServices;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/purchaseOrderItems")
@RequiredArgsConstructor
public class purchaseOrderItemsController {
    private final purchaseOrderItemsServices purchaseOrderItemsServices;
    @GetMapping("/{id}/purchaseOrder")
    public List<purchaseOrderItems> getItemsByOrder(@PathVariable Long id) {
        return purchaseOrderItemsServices.getPurchaseOrderItemsByPurchaseOrder(id);
    }
    
    @PostMapping("/")
    public Long createItemsByOrder(@RequestBody purchaseOrderItemsRequestDTO purchaseOrderItemsRequestDTO) {
        return purchaseOrderItemsServices.createPurchaseOrderItems(purchaseOrderItemsRequestDTO);
    }

    @PutMapping("/{id}/update")
    public void updateItems(@PathVariable Long id,@RequestBody purchaseOrderItemsRequestDTO purchaseOrderItemsRequestDTO){
        purchaseOrderItemsServices.updatePurchaseOrderItems(id, purchaseOrderItemsRequestDTO);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteItems(@PathVariable Long id){
        purchaseOrderItemsServices.deletePurchaseOrderItems(id);
    }
    
}
