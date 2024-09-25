package kr.co.gudi.dto;

public class MemberDTO {

	// bean 규약사항 (무조건 캡슐화를 해주는 것) - 내가 보여주고 싶고, 수정 가능한 것만 열어주는 것
	// 실수로 건들면 문제가 생길 수 있기 때문에 보안을 위해 묶어두는 것 (못보게 하는게 아니라 못건들이게)
	private String id;
	private String pw;
	private String name;
	private String age; // int 여야 하지만 특별히 계산하는게 아닌 입출력용도라면 Stirng로 가도 된다.
	private String gender;
	private String email;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
