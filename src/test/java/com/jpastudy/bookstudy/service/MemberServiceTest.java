package com.jpastudy.bookstudy.service;

import com.jpastudy.bookstudy.domain.Member;
import com.jpastudy.bookstudy.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    //   @Rollback(false)
    public void registId() throws Exception {

        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);
        //       em.flush();
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class)
    public void checkDuplicate() throws Exception {

        Member member = new Member();
        member.setName("kim1");

        Member member1 = new Member();
        member1.setName("kim1");


        memberService.join(member);
        memberService.join(member1);

        fail("예외가 발생해야 합니다.");
    }

}