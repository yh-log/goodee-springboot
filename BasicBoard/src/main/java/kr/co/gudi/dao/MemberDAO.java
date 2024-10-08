package kr.co.gudi.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	int login(MemberDTO memberDto);

}
