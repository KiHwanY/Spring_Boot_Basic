package hello.hellospring.repository;

import hello.hellospring.domin.Member;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    //모든 테스트는 순서가 보장이 안된다.
    // 모든 테스트는 순서랑 상관없이 메서드 들을 따로 동작한다.
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 실행이 끝날때마다 동작을 한다. 콜백함수와 비슷
    public void afterEach(){
        repository.clearStore(); // 테스트 끝날 때마다 저장소를 다 지운다.
    }

    @Test
    public void save(){
    Member member = new Member();

    member.setName("spring");
    repository.save(member);
    // 반환타입이 Optional 이라서 get()으로 꺼내온다. // 자주 사용 금지
   Member result= repository.findById(member.getId()).get();
       /* System.out.println("result =" + (result == member));*/

        //Assertions 에러 체크를 좀 더 편하게 할 수 있다.
        //member 데이터가 find 했을 때 나와야 한다.
       /* Assertions.assertEquals(member,result);*/
        // member 와 result 는 똑같다?
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result =  repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findALl(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
         // 2개의 데이터가 나와야한다.
        assertThat(result.size()).isEqualTo(2);


    }
}
