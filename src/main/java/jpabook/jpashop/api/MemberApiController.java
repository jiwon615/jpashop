package jpabook.jpashop.api;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller와 @ResponseBody를 합친 것
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 1. Entity를 반환하면 안됨. API스펙은 언제든 변경될 수 있는데 유지보수에 어려워 DTO 등을 따로 만들어서 관리해야 함
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {   // @RequestBody는 JSON으로 온 body를 Member로 매핑해줌
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    // 2. 더 나은 방법 (엔티티 스펙이 변해도 API 스펙이 변경 되지 않아도 됨)
    /* ex) Member 속성 중 name -> userName으로 바꾸면, 아래에서 setName -> setUserName으로만 바꾸면 됨 */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

}
