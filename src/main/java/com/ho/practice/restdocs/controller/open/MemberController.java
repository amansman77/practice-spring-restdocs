package com.ho.practice.restdocs.controller.open;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ho.practice.restdocs.Member;

@RestController
@RequestMapping("/open")
public class MemberController {

	private Map<String, Member> memberMap = new HashMap<>();
	
	@PostMapping(value = "/member")
    public String addMember(@RequestBody Member person) {
		memberMap.put(person.getId(), person);
        return "success";
    }
	
	@GetMapping(value = "/member/{id}")
    public Member getMember(
    		@RequestHeader String remoteAddr1,
    		@RequestHeader(required = true) String remoteAddr2,
    		@RequestHeader(required = false) String remoteAddr3,
    		@PathVariable String id) {
        return memberMap.get(id);
    }
	
	/**
	 * 회원을 검색한다.
	 * @param query
	 * @param offset
	 * @param limit
	 * @return
	 */
	@GetMapping(value = "/member")
    public Member getMember(
    		@RequestParam(value = "q") String query,
    		@RequestParam String offset,
    		@RequestParam String limit) {
        return new Member("1", "andy", 1);
    }
	
	/**
	 * 회원을 검색한다.
	 * @param query
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Deprecated
	@GetMapping(value = "/member/legacy")
    public Member getMemberLegacy(
    		@RequestParam(value = "q", required = false) String query,
    		@RequestParam(required = false) String offset,
    		@RequestParam(required = false) String limit) {
        return new Member();
    }
	
	@PutMapping(value = "/member/{id}")
    public String updateMember(@PathVariable String id, @RequestBody Member person) {
		memberMap.put(id, person);
        return "success";
    }

	@DeleteMapping(value = "/member/{id}")
    public String deleteMember(@PathVariable String id) {
		memberMap.remove(id);
        return "success";
    }
	
	@GetMapping(value = "/hidden/member")
    public Member getHiddenMember(@PathVariable String id) {
        return memberMap.get(id);
    }
}
