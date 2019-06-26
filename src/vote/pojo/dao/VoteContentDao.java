package vote.pojo.dao;

import java.util.List;

import vote.pojo.VoteContent;

public interface VoteContentDao {
	void save(VoteContent voteContent);
	VoteContent get(Long id);
	//void update(VoteContent voteContent);
	void delete(Long id);
	List<VoteContent> getByVoteID(Long id);
}
