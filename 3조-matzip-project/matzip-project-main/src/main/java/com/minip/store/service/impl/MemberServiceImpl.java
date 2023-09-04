package com.minip.store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minip.store.data.dto.MemberDto;
import com.minip.store.data.entity.Member;
import com.minip.store.data.repository.MemberRepository;
import com.minip.store.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
    
    private MemberRepository memberRepository;
    
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> index2() {
        return memberRepository.findAll();
    }

    @Override
    public List<MemberDto> searchMemberByMatch(String name, String password) {
        List<Member> members = memberRepository.findByName(name, password);
        return members.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MemberDto> checkName(String name) {
        List<Member> members = memberRepository.existsByName(name);
        return members.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Member create(MemberDto dto) {
        Member member = dto.toEntity();
        // 새로운 글이기 때문에 id값 없어야 함
        // 있으면 null 리턴
        if(member.getMem_no() != null) {
            return null;
        }
        return memberRepository.save(member);
    }

    @Override
    public List<MemberDto> delete(String[] name) {
        List<Member> target = memberRepository.deleteByNames(name);

        if(target == null) {
            return null;
        }
        memberRepository.deleteAll(target);
        
        return null;
    }
    
    private MemberDto convertToDTO(Member member){
        MemberDto dto = new MemberDto();
        dto.setName(member.getName());
        dto.setPassword(member.getPassword());
        dto.setMem_role(member.getMem_role());
        return dto;
    }
}
