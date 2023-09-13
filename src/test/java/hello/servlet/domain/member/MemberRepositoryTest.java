package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {

    // getInstance 메소드가 static이였으니까 타입형.메소드 로 접근해야됨 .
    // 메모리에 올라와있어야 수행이 가능한데 static으로 객체 만들기도 전에 메모리에 올려버려서... static은 그냥 메모리에 미리 올리는 명령어임
    MemberRepository memberRepository = MemberRepository.getInstance();

    //각 테스트를 실행할때마다 이 함수 실행
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Member member = new Member("hello", 20);
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll(){
        //given

        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> result = memberRepository.findAll();

        //then

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1,member2);
    }


}
