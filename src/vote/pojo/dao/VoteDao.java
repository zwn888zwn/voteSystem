package vote.pojo.dao;

import vote.pojo.Vote;

public interface VoteDao {
	void save(Vote vote);
	Vote get(Long id);
	void delete(Long id);
	void update(Vote vote);
}
