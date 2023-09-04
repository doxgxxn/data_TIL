package com.minip.store.service;

import java.util.List;

import com.minip.store.data.dto.MemberDto;

public interface MemberService {
    List<MemberDto> searchMemberByMatch(String name, String password);
    
    List<MemberDto> checkName(String name);

    List<MemberDto> delete(String[] name);
}
