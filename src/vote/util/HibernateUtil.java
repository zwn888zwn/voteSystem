package vote.util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateUtil  {
	@Autowired
	protected SessionFactory sessionfactory;
	
	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public List queryPage(final String hql,final int currentPage ,final int pageSize){
		Session session=this.sessionfactory.getCurrentSession();
		List list=session.createQuery(hql).setMaxResults(pageSize).setFirstResult((currentPage-1)*pageSize).list();
				return list;
	}
	public List querySQLPage(final String sql,final int currentPage ,final int pageSize){
		Session session=this.sessionfactory.getCurrentSession();
		List list=session.createSQLQuery(sql).setMaxResults(pageSize).setFirstResult((currentPage-1)*pageSize).list();
				return list;
	}
	
	public int listSize(String hql){
		return this.sessionfactory.getCurrentSession().createQuery(hql).list().size();
	}
	public int SQLlistSize(String hql){
		return this.sessionfactory.getCurrentSession().createSQLQuery(hql).list().size();
	}

	
	
}
