package com.ho.practice.restdocs;

import java.util.List;

public class MemberSearch {
	private List<Member> memberList;

	public MemberSearch() {
	}
	public MemberSearch(List<Member> list) {
		memberList = list;
	}
	public List<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	
}
