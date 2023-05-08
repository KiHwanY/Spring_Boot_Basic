package hello.hellospring.service;

import hello.hellospring.domin.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    //given  -> 무언가 주어지다.


    //when -> 이걸 실행했을 때?


    //then -> 결과는 이렇게 나와야 한다.

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach // 테스트를 실행하기 전에 메서드를 생성해준다.(동일한 객체를 사용하기 위한 기법)
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 실행이 끝날때마다 동작을 한다. 콜백함수와 비슷
    public void afterEach(){
        memberRepository.clearStore(); // 테스트 끝날 때마다 저장소를 다 지운다.
    }

    @Test
    void 회원가입() {
        //given  -> 무언가 주어지다.
        Member member = new Member();
        member.setName("spring");

        //when -> 이걸 실행했을 때?
        Long saveId = memberService.join(member);

        //then -> 결과는 이렇게 나와야 한다.
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);


        //try~ catch문 대신 예외 발생 처리 기법
        // IllegalStateException(설정한 에러) 에러가 발생해야한다. 어떤게? -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


     /*   try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then

    }

    @Test
    void findOne() {
    }
}