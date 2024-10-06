package kr.co.gudi.dto;

public class LikesDTO {
	
	private int like_idx;
	private int board_idx;
	private String member_id;
	
	public int getLike_idx() {
		return like_idx;
	}
	public void setLike_idx(int like_idx) {
		this.like_idx = like_idx;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	@Override
	public String toString() {
		return "LikesDTO [like_idx=" + like_idx + ", board_idx=" + board_idx + ", member_id=" + member_id + "]";
	}
	
	

}
