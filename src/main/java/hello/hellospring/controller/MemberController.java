package hello.hellospring.controller;


import hello.hellospring.domin.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    //@Component : 어노테이션이 있으면 스프링 빈으로 자동 등록된다.
    //@Component를 포함하는 다음 어노테이션도 스프링 빈으로 자동 등록된다.
    // <@Controller , @Service , @Repository>
    //@Controller : 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
    // 필드주입 : 교체할 수 있는 방법이 없다.
    // setter 주입 : 중간에 코드 교체 시 문제가 발생할 확률이 높고, 아무 개발자나 사용할 수 있다.
    private final MemberService memberService;

    //spring 컨테이너 등록
    @Autowired // AutoWired -> DI 의존 관계 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member =new Member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

    }
}
