package vote.pojo;

import java.io.Serializable;

public class VoteResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7024803649118989524L;
	/**
	 * 
	 */
	
	private long id;
	private Vote  voteId;
	private String uuid;
	private User userId;
	private String voteName;
	private String voteResult;
	public VoteResult(){}
	public VoteResult(Vote voteID, User user, String name, String result){
		this.voteId=voteID;
		this.userId=user;
		this.voteName=name;
		this.voteResult=result;
	}
	public VoteResult(Vote voteID, String uuid,User user, String name, String result){
		this.uuid=uuid;
		this.voteId=voteID;
		this.userId=user;
		this.voteName=name;
		this.voteResult=result;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Vote getVoteId() {
		return voteId;
	}
	public void setVoteId(Vote voteId) {
		this.voteId = voteId;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public String getVoteName() {
		return voteName;
	}
	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}
	public String getVoteResult() {
		return voteResult;
	}
	public void setVoteResult(String voteResult) {
		this.voteResult = voteResult;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
