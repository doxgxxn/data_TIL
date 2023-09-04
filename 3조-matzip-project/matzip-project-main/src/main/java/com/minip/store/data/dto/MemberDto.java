package com.minip.store.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

import com.minip.store.data.entity.Member;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class MemberDto {
    private Long mem_no;
    private String name;
    private String password;
    private String mem_role;

    public Member toEntity() {
        return new Member(mem_no, name, password, mem_role="member");
    }
}
