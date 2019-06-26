package vote.pojo.dao;

import java.util.List;

public interface VoteResultDao {
	void save(long voteID,String uuid,String user,String name,String result);
	public int getCountByNameAndResult(long voteID,String voteName,String voteResult );
	public List getVoteCountList(long voteId);
	List getVoteBlankList(long id);
}
