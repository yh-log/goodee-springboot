package kr.co.gudi.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	int login(String id, String pw);

	int join(Map<String, String> params);

	int overlay(String id);

}
