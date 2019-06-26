package vote.pojo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.Vote;
import vote.pojo.VoteContent;
import vote.pojo.dao.VoteContentDao;
import vote.pojo.dao.VoteDao;
import vote.util.HibernateUtil;
@Transactional
@Repository
public class VoteDaoImpl extends HibernateUtil implements VoteDao {
	@Autowired
	VoteContentDao voteContentDao;
	@Override
	public void save(Vote vote) {
		List<VoteContent> list=vote.getAllContent();
		vote.setDate(new Date());
		for(int i=0;i<list.size();i++){
			voteContentDao.save(list.get(i));
		}
		this.getSessionfactory().getCurrentSession().save(vote);
		
	}

	@Override
	@Transactional
	public Vote get(Long id) {
		Vote vote=(Vote) this.getSessionfactory().getCurrentSession().get(Vote.class, id);
		return vote;
	}


	@Override
	public void update(Vote vote) {
		
		this.getSessionfactory().getCurrentSession().update(vote);
	}

	@Override
	public void delete(Long id) {
		this.getSessionfactory().getCurrentSession().delete(new Vote(id));
		
	}


}
