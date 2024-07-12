package com.cod.market.cart.repository;


import com.cod.market.cart.entity.Cart;
import com.cod.market.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember(Member member);
}
