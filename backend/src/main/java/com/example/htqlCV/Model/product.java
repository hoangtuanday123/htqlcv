package com.example.htqlCV.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_product")
public class product extends abstractEntity {
    @Column(name = "name")
    private String name;
    @Column(name="capital_price")
    private Long capitalPrice;
    @Column(name="sale_price")
    private Long salePrice;
    @Column(name="stock_quantity")
    private Long stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private category category;

    @ManyToOne
    @JoinColumn(name = "branch_product_id")
    @JsonManagedReference
    private branchProduct branchProduct;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private guarantee guarantee;
}
