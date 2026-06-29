package dao;

import entity.Member;

public interface MemberDao {
	// create
	void insert(Member member);
	// read
	Member selectByUsernameAndPassword(String username, String password);
	Member selectByUsername(String username);
	// update

	// delete
}
