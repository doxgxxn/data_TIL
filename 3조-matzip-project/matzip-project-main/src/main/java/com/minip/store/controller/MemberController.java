package com.minip.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.minip.store.data.dto.MemberDto;
import com.minip.store.data.dto.SharedData;
import com.minip.store.data.entity.Member;
import com.minip.store.data.repository.MemberRepository;
import com.minip.store.service.MemberService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private SharedData sharedData;

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Autowired
    public MemberController(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String newArticleForm() {
        return "members/new";
    }
    
    @PostMapping("/members/create")
    public String createGuset(String name, MemberDto dto, RedirectAttributes attributes) {
        List<MemberDto> dupCheck = memberService.checkName(name);

        if (!dupCheck.isEmpty()) {
            attributes.addFlashAttribute("error", "아이디가 중복되었습니다."); // 에러 메시지 전달
            return "redirect:/members/new"; // 에러 메시지와 함께 로그인 화면으로 리다이렉트
        }
        Member member = dto.toEntity();
        memberRepository.save(member);
        return "redirect:/login/";
    }
    
    @PostMapping("/login")
    public String processLogin(String name, String password, String mem_role, RedirectAttributes attributes) {
        List<MemberDto> member = memberService.searchMemberByMatch(name, password);
        
        if (!member.isEmpty() && name.equals("root")) {
            sharedData.setTest("1");
            sharedData.setAdmin("1");
            return "redirect:/stores";

        }else if(!member.isEmpty())  {
            sharedData.setTest("1");
            return "redirect:/stores";

        } else {
            attributes.addFlashAttribute("error", "아이디 혹은 비밀번호가 틀렸습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        String testValue = sharedData.getTest();
        boolean isTestValueZero = "1".equals(testValue);
        model.addAttribute("isTestValueZero", isTestValueZero);

        // 모델에 필요한 데이터를 추가 (예: 오류 메시지)
        model.addAttribute("errorMessage", ""); // 초기에는 빈 문자열로 설정

        if(isTestValueZero) {
            return "redirect:/stores";
        } else {
            return "members/login"; // 렌더링할 Mustache 템플릿 파일명
        }
    }

    @GetMapping("/logout")
    public String logout() {
        sharedData.setTest("0");
        sharedData.setAdmin("0");
        return "redirect:/login";
    }
    
    @GetMapping("/members")
    public String index(Model model) {
        String testValue = sharedData.getTest();
        boolean isTestValueZero = "1".equals(testValue);
        model.addAttribute("isTestValueZero", isTestValueZero);

        List<Member> memberEntityList = (List<Member>)memberRepository.findAll();
    
        if (!memberEntityList.isEmpty()) {
            memberEntityList.remove(0);
        }

        model.addAttribute("memberList", memberEntityList);
        
        if(!isTestValueZero) {
            return "redirect:/login/";
        } else {
            return "members/members";
        }
    }

    @PostMapping("/delete")
    public String deleteMembers(@RequestParam(value = "selectedMembers", required = false) String[] selectedMembers, Model model) {
        String testValue = sharedData.getTest();
        boolean isTestValueZero = "1".equals(testValue);
        model.addAttribute("isTestValueZero", isTestValueZero);

        if (selectedMembers != null && selectedMembers.length > 0) {
            List<MemberDto> deleted = memberService.delete(selectedMembers);
        }

        if(!isTestValueZero) {
            return "redirect:/login/";
        } else {
            return "redirect:/members";
        }
    }
}
