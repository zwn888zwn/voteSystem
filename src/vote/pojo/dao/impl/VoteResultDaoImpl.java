package vote.pojo.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.User;
import vote.pojo.Vote;
import vote.pojo.VoteResult;
import vote.pojo.dao.VoteResultDao;
import vote.util.HibernateUtil;
@Transactional
@Repository
public class VoteResultDaoImpl  extends HibernateUtil implements VoteResultDao  {

	@Override
	public void save(long voteID, String uuid,String user, String name, String result) {
		VoteResult voteResult=new VoteResult(new Vote(voteID),uuid,new User(user),name,result);
		this.getSessionfactory().getCurrentSession().save(voteResult);

	}
	
	public int getCountByNameAndResult(long voteID,String voteName,String voteResult ){
		String hql="from VoteResult where voteId=? and voteName=? and voteResult=?";
		
		return this.getSessionfactory().getCurrentSession().createQuery(hql).setParameter(0, new Vote(voteID)).setString(1, voteName).setString(2, voteResult).list().size();
	}
	
	public List getVoteCountList(long voteId){
		String sql="SELECT t.voteName,t.voteResult,count(*) FROM vote.t_voteresult t  where voteId=?  and exists(SELECT * FROM vote.t_vote_content t1 where t1.voteId=? and t1.contentType!='contents' and t.voteName=t1.contentId) group by t.voteName,t.voteResult";
		Session session=this.getSessionfactory().getCurrentSession();
		List list=session.createSQLQuery(sql).setLong(0, voteId).setLong(1, voteId).list();
		return list;
	}

	@Override
	public List getVoteBlankList(long voteId) {
		String sql="SELECT t.voteName,t.voteResult FROM vote.t_voteresult t  where voteId=?  and not exists(SELECT * FROM vote.t_vote_content t1 where t1.voteId=? and t1.contentType!='contents' and t.voteName=t1.contentId) group by t.voteName,t.voteResult ";
		Session session=this.getSessionfactory().getCurrentSession();
		List list=session.createSQLQuery(sql).setLong(0, voteId).setLong(1, voteId).list();
		return list;
	}
	

}
