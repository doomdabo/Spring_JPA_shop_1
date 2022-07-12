package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기에는 true! 쓰기에는 무조건 true하면 안됨. 수정안됨.
public class MemberService {
    private MemberRepository memberRepository;
    //test하거나 할때 멤버 레포 바꾸고싶은데 못바꿈

    @Autowired //setter injunction
    // 장점: test 코드 작성할 때 직접 주입 가능
    // 단점: 런타임에 누군가 바꿀 수 있음. 
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
        * 화원 가입
        /
         */
    @Transactional //기본이 readOnly = false
    public Long join(Member member){
        validateDuplicateMember(member);//중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); //동시에 회원가입하면 문제됨 --> member의 name에 unique 제약 조건 걸어야함
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
