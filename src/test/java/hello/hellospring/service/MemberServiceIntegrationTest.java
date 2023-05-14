package hello.hellospring.service;

import hello.hellospring.domin.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;


// 통합 테스트
@SpringBootTest
@Transactional
    // 테스트가 끝나면 롤백 해준다.
    //즉, 테스트 케이스에 이 @(애노테이션)이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
    //테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을
    // 주지 않는다.
class MemberServiceIntegrationTest {
    //given  -> 무언가 주어지다.


    //when -> 이걸 실행했을 때?


    //then -> 결과는 이렇게 나와야 한다.

   @Autowired
   MemberService memberService;

    @Autowired
    MemberRepository memberRepository;



    @Test
    void 회원가입() {
        //given  -> 무언가 주어지다.
        Member member = new Member();
        member.setName("spring100");

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
}