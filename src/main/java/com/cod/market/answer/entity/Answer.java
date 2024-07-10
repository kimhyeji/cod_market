package com.cod.market.answer.entity;

import com.cod.market.member.entity.Member;
import com.cod.market.qustion.entity.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Answer {
    private String comment;

    @OneToOne
    private Member member;
    @OneToOne
    private Question question;
}
