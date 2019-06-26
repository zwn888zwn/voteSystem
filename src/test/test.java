package test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vote.pojo.Vote;
import vote.pojo.VoteContent;
import vote.pojo.VoteType;
import vote.pojo.dao.impl.VoteDaoImpl;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		/*SessionFactory sf=(SessionFactory) context.getBean("sessionFactory");
		Session session= sf.openSession();
		Transaction ts=session.beginTransaction();
		System.out.println(session.createQuery("from Vote").list());
		ts.commit();*/
		/*//aop
		OriginClazz oc=(OriginClazz) context.getBean("oringc");
		oc.show();*/

		
	}
/*	public static void addTest(){
		VoteContent vc=new VoteContent();
		vc.setContentType(VoteType.RADIO);
		List list=vc.getContentText();
		list.add("haochi");
		list.add("buhaochi");
		session.save(vc);
		Vote vote=new Vote();
		vote.setVoteCreater("zwn");
		vote.getAllContent().add(vc);
		session.save(vote);
	}*/

}
