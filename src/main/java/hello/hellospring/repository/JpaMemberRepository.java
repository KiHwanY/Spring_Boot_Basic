package hello.hellospring.repository;

import hello.hellospring.domin.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
    // JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환할 수 있다.
    // JPA를 사용하면 개발 생산성을 크게 높일 수 있다.

    //application 참고
    //show-sql : JPA가 생성하는 SQL을 출력한다.
    //ddl-auto : JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 none를 사용하면 해당 기능을 끈다.
    // create를 사용하면 Entity 정보를 바탕으로 테이블도 직접 생성해준다.

    private final EntityManager em;
    // EntityManager -> jpa를 사용하기 위해서는 Database 구조와 맵핑된 JPA Entity 들을 먼저 생성하게 된다.
    // 그리고, 모든 JPA 의 동작은 이 Entity들을 기준으로 돌아가게 되는데, 이 때
    // Entity들을 관리하는 역할을 하는 녀석이 바로 EntityManager인 것이다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
         em.persist(member); // 영구 저장하다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name =:name", Member.class)
                .setParameter("name" , name)
                .getResultList();

                return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                //Member의 entity를 조회한다. / member의 entity 자체를 select 한다.
                 .getResultList();
    }
}
