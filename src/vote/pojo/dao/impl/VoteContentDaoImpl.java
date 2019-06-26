package vote.pojo.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.VoteContent;
import vote.pojo.dao.VoteContentDao;
import vote.util.HibernateUtil;
@Repository
@Transactional
public class VoteContentDaoImpl  extends HibernateUtil implements VoteContentDao {

	@Override
	public void save(VoteContent voteContent) {
		this.getSessionfactory().getCurrentSession().save(voteContent);
		
	}

	@Override
	public VoteContent get(Long id) {
		return (VoteContent) this.getSessionfactory().getCurrentSession().get(VoteContent.class, id);
		
	}
	@Override
	public List<VoteContent> getByVoteID(Long id) {
		String hql="from VoteContent where voteID = ?";
		Session session=this.getSessionfactory().getCurrentSession();
		List list=session.createQuery(hql).setLong(0, id).list();
		return list;
		
	}



	@Override
	public void delete(Long id) {
		this.getSessionfactory().getCurrentSession().delete(new VoteContent(id));
		
	}

}
