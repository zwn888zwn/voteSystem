package vote.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.listener.ListenerHostConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.Vote;
import vote.pojo.VoteContent;
import vote.pojo.dao.VoteDao;
import vote.pojo.dao.impl.VoteDaoImpl;
import vote.service.VoteAlsService;
import vote.service.VoteService;
import vote.util.Apriori;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
@Transactional
@Service
public class VoteAlsImpl  implements VoteAlsService{

	@Autowired
	SessionFactory sessionFactory;
	

	public Map<String, Integer> importDataFromDBAndAls(int voteid) {
		String sql ="SELECT uuid,voteName,voteResult "
				+ " FROM `t_voteresult` "
				+ "WHERE voteId=? "
				+ "and voteName "
				+ "not in "
				+ "(SELECT list_order+1 from t_vote_content WHERE voteId=? and contentType='CONTENTS' )";
		
		LinkedHashMap<String , String> datamap=new LinkedHashMap<String, String>();
		
		Session session=sessionFactory.getCurrentSession();
		List list= session.createSQLQuery(sql).setInteger(0, voteid).setInteger(1, voteid).list();
		for(Iterator iterator = list.iterator();iterator.hasNext();){  
			 Object[] obj = (Object[]) iterator.next(); 
			 if(datamap.containsKey(obj[0])) {
				 String temp=datamap.get(obj[0]);
				 datamap.replace(obj[0].toString(), temp+obj[1]+"-"+obj[2]+" ");
			 }
			 else {
				 datamap.put(obj[0].toString(), obj[1]+"-"+obj[2]+" ");
			 }
			//System.out.println(obj[0]+" "+obj[1]+" "+obj[2]+" ");
		}
		System.out.println(datamap);
		Apriori apri=new Apriori();
		apri.setData2DList(new ArrayList(datamap.values()));
		return apri.apriori();
	}
	/*
	 * 求置信度
	 * 1.找到子集
	 * 2.对每个子集从数据库中统计数量，求置信度
	 * 3.（前端）返回json展示
	 * 
	 * 测试数据：
	 * 1-2 2-0 2-1    
	 * 2-0
	 * */
	public List<Map.Entry<String, Integer>> getConfidence(int voteId,String target,String str) {
		HashMap<String, Integer> resultMap=new HashMap<String, Integer>();
		
		String[] strArr=str.split(" ");
		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<strArr.length;i++) {
			if(target.trim().equals(strArr[i].trim())) {
				continue;
			}
			list.add(strArr[i].trim());
		}
		String vName1,vResult1,vName2,vResult2;
		vName1=target.split("-")[0];
		vResult1=target.split("-")[1];
		//list:1-2, 2-1  
		for(int i=0;i<list.size();i++) {
			vName2=list.get(i).split("-")[0];
			vResult2=list.get(i).split("-")[1];
			int resultValue = this.getTwoItemCount(voteId, vName1, vResult1, vName2, vResult2);
			resultMap.put(list.get(i), resultValue);
		}
		
		//sort
		List<Map.Entry<String, Integer>> sortMapList=new ArrayList(resultMap.entrySet());
		Collections.sort(sortMapList, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue()-o1.getValue();
			}


		});
		
		return sortMapList;
	}
	public int getOneItemCount(int voteId,String vName,String vResult) {
		String sql="SELECT count(*) FROM `t_voteresult` WHERE voteId= ? and voteName= ? and voteResult= ?";
		Session session=sessionFactory.getCurrentSession();
		List list=session.createSQLQuery(sql).setParameter(0, voteId).setParameter(1, vName).setParameter(2, vResult).list();
		if(list!=null) {
			String result=list.get(0).toString();
			return Integer.parseInt(result); 
		}
		return -1;

	}
	public int getTwoItemCount(int voteId,String vName1,String vResult1,String vName2,String vResult2) {
		String sql="SELECT  " + 
				"	COUNT( * )   " + 
				"FROM  " + 
				"	( SELECT * FROM `t_voteresult` t WHERE t.voteId = ? AND t.voteName = ? AND t.voteResult = ? ) tt1  " + 
				"	JOIN ( SELECT * FROM `t_voteresult` t WHERE t.voteId = ? AND t.voteName = ? AND t.voteResult = ? ) tt2   " + 
				"WHERE  " + 
				"	tt1.uuid = tt2.uuid";
		Session session=sessionFactory.getCurrentSession();
		List list=session.createSQLQuery(sql).setParameter(0, voteId).setParameter(1, vName1).setParameter(2, vResult1).setParameter(3, voteId).setParameter(4, vName2).setParameter(5, vResult2).list();
		if(list!=null) {
			String result=list.get(0).toString();
			return Integer.parseInt(result); 
		}
		return -1;

	}
	public int getVoteCount(int voteId) {
		String sql="SELECT count(*) from ( SELECT (uuid ) from t_voteresult WHERE voteId=? GROUP BY uuid) t";
		Session session=sessionFactory.getCurrentSession();
		List list=session.createSQLQuery(sql).setParameter(0, voteId).list();
		if(list!=null) {
			String result=list.get(0).toString();
			return Integer.parseInt(result); 
		}
		return -1;

	}
	
	@Test
	public void testConfidence() {
		String target="2-0";
		String str="1-2 2-0 2-1 2-2 ";
		String[] strArr=str.split(" ");
		ArrayList list=new ArrayList<String>();
		for(int i=0;i<strArr.length;i++) {
			if(target.trim().equals(strArr[i].trim())) {
				continue;
			}
			list.add(strArr[i].trim());
		}
		//1-2, 2-1  System.out.println(list);
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));//
		}
/*		for(int i=0;i<list.size();i++) { 
			for(int j=i+1;j<list.size();j++) {
				System.out.println(list.get(i)+" "+ list.get(j));//只计算两个的条件概率
			}
		}*/
		
		
		
	}
	



}
