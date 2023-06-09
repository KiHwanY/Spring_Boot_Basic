package hello.hellospring.service;

import hello.hellospring.domin.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


//@Transactional -> 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고,
// 메서드가 정상 종료되면 트랜잭션을 커밋한다. 만약 런타임 예외가 발생하면 롤백한다.
// JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    *
    * 회원 가입
    * */
    public Long join(Member member){

/*
        AOP 필요한 상황
        - 모든 메소드의 호출 시간을 측정하고 싶다면?
        - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
        - 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?
*/

           //같은 이름이 있는 중복 회원 x
           validateDuplicateMember(member);

           memberRepository.save(member);
           return member.getId();
       }



    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m ->{
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    /*
    *  전체회원 조회
    *
    * */
    public List<Member> findMembers(){

            return memberRepository.findAll();


    }

    public Optional<Member> findOne(Long memberId){

        return memberRepository.findById(memberId);
    }
}
