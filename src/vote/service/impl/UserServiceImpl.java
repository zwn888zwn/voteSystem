package vote.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.User;
import vote.service.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	SessionFactory sessionFactory;
	@Override
	public void saveUser(User user) {
		Session	sessions =sessionFactory.openSession();
		Transaction ts=sessions.beginTransaction();
		sessions.save(user);
		ts.commit();
		sessions.close();
	}

	@Override
	public User getUserBySession(HttpSession session) {
		Object obj = session.getAttribute("USER_LOGIN_INFO_IN_SESSION");
		if(obj!=null){
			User user=(User)obj;
			return user;
		}
		return null;
	}

	@Override
	public User getUserById(String id) {
		User user=null;
		Session	sessions =sessionFactory.openSession();
		Criteria cri=sessions.createCriteria(User.class);
		List<User> list=cri.add(Restrictions.eq("id", id)).list();
		if(list!=null&&list.size()>0)
			user=list.get(0);
		sessions.close();
		return user;
	}

}
