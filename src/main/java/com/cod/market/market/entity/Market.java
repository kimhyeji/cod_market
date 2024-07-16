package com.cod.market.market.entity;

import com.cod.market.base.BaseEntity;
import com.cod.market.member.entity.Member;
import com.cod.market.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
public class Market extends BaseEntity {
    private String email;
    private String name;
    private String info;
}
