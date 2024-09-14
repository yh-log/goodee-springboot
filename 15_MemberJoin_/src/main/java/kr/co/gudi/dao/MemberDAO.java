package kr.co.gudi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

	int join(Map<String, String> params);

	int login(String id, String pw);

	int overlay(String id);

}
