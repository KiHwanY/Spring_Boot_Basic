package hello.hellospring.repository;

import hello.hellospring.domin.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    //Optional<> : java 8 부터 추가된 기능 / 값이 없는 , null 처리를 할 때 감싸서 반환한다.


    Member save(Member member);

    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    List<Member> findAll();

}
