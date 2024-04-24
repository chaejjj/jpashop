package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    /*리포지토리도 생성자 인젝션 가능.
    스프링 부트와 스프링 jpa를 사용하면 service에서 했던 것처럼
    final 붙은 필드를 자동으로 생성자 인젝션 해주는 어노테이션(@RequiredArgsConstructor) 사용 가능.*/
/*    public MemberRepository(EntityManager em) {
        this.em = em;
    }*/

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}