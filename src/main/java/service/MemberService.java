package service;

import entity.Member;

public interface MemberService {
	// 登入判斷
	Member login(String username, String password);
	// 註冊帳號
	void createMember(Member member);
	// 檢查重複帳號
	boolean usernameIsUsable(String username);
}
