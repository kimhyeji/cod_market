package com.cod.market.market.entity;

import com.cod.market.base.BaseEntity;
import com.cod.market.member.entity.Member;
import com.cod.market.product.entity.Product;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Market extends BaseEntity {
    private String email;
    private String name;
    private String info;

    private Member member;
    private List<Product> productList;
}
