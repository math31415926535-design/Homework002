package service.impl;

import dao.impl.MemberDaoImpl;
import entity.Member;
import service.MemberService;

public class MemberServiceImpl implements MemberService {

	MemberDaoImpl mdi=new MemberDaoImpl();

	@Override
	public Member login(String username, String password) {
		return mdi.selectByUsernameAndPassword(username, password);
	}

	@Override
	public void createMember(Member member) {
		mdi.insert(member);
	}

	@Override
	public boolean usernameIsUsable(String username) {
		return mdi.selectByUsername(username)==null;
	}
}
