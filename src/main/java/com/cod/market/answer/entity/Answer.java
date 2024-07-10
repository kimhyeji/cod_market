package com.cod.market.answer.entity;

import com.cod.market.member.entity.Member;
import com.cod.market.qustion.entity.Question;
import jakarta.persistence.Entity;

@Entity
public class Answer {
    private String comment;
    private Member member;

    private Question question;
}
