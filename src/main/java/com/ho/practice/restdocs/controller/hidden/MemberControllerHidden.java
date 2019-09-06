package com.ho.practice.restdocs.controller.hidden;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ho.practice.restdocs.Member;

@RestController
@RequestMapping("/hidden")
public class MemberControllerHidden {

	private Map<String, Member> memberMap = new HashMap<>();
	
	@PostMapping(value = "/member")
    public String addMember(@RequestBody Member person) {
		memberMap.put(person.getId(), person);
        return "success";
    }
	
	@GetMapping(value = "/member/{id}")
    public Member getMember(@PathVariable String id) {
        return memberMap.get(id);
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
	
}
