package vote.pojo;

public enum VoteType {
	RADIO("0"),CHECKBOX("1"),CONTENTS("2");
	
	private String voteType; 

	private VoteType(String voteType) {
		this.voteType=voteType;
	}
	
	public String getVoteType() {
		return voteType;
	}



	public void setVoteType(String voteType) {
		this.voteType = voteType;
	}

	@Override 
	public String toString() 
	{ 
		return this.voteType; 

	} 

	public static void main(String[] args) {
		System.out.println(VoteType.CONTENTS);
	}
	
}
