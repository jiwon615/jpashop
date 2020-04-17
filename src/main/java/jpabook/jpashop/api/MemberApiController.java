package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController  // @Controller와 @ResponseBody를 합친 것
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /*
    * 회원 조회
    * */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();

    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()  // Member를 MemberDto로 변환
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;  // data의 값은 위 list의 값이 될 것임
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

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

    /*
    *  회원 등록 API
    * */

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

   /*
   * 회원 수정 API
   * */
   @PutMapping("/api/v2/members/{id}")
   public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                              @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
       Member findMember = memberService.findOne(id);

       return new UpdateMemberResponse(findMember.getId(), findMember.getName());
   }


    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
       @NotEmpty
        private String name;
    }

}
