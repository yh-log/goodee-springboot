package kr.co.gudi.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDAO {

	String login(String id, String pw);

}