package vote.service.impl;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vote.pojo.User;
import vote.pojo.Vote;
import vote.pojo.VoteContent;
import vote.pojo.VoteType;
import vote.service.ShowVoteService;
import vote.util.HibernateUtil;
import vote.util.UserHolder;
 
@Service
@Transactional
public class ShowVoteServiceImpl extends HibernateUtil implements ShowVoteService {

	@Override
	public List getNewestVote() {
		String hql="select voteID ,mainTitle ,date from Vote where status=1 order by date desc ";
		Session session=this.getSessionfactory().getCurrentSession();
		List list= session.createQuery(hql).setFirstResult(0).setMaxResults(7).list();
		//session.flush();
		//session.close();
		return list;
	}
	@Override
	public List getMyVote() {
		User user=UserHolder.getUser();
		String id =user.getId();
		System.out.println(id);
		Session session=this.getSessionfactory().getCurrentSession();
		String hql;
		if(user.getAuthority()==1) {
			hql="from Vote t ";
			Criteria criteria=session.createCriteria(Vote.class);
			List list=criteria.list();
			return list;
		}
		else {
			hql="from Vote t where  voteCreater=? ";
			Criteria criteria=session.createCriteria(Vote.class);
			criteria.add(Restrictions.eq("voteCreater", id));
			criteria.add(Restrictions.ne("status", 2));
			List list=criteria.list();
			return list;
		}
		
		
	} 
	@Override
	public boolean updateVote(long id, int status) {
		String sql="update t_vote set status=? where voteID=? and voteCreater=?";
		Session session=this.getSessionfactory().getCurrentSession();
	    int num=session.createSQLQuery(sql).setInteger(0, status%2).setLong(1, id).setString(2, UserHolder.getUser().getId()).executeUpdate();
		return num>0?true:false;
	}
	@Override
	public boolean deleteVote(long id) {
		String sql="update t_vote set status=? where voteID=? and voteCreater=?";
		Session session=this.getSessionfactory().getCurrentSession();
		int num=session.createSQLQuery(sql).setInteger(0, 2).setLong(1, id).setString(2, UserHolder.getUser().getId()).executeUpdate();
		return num>0?true:false;
	}
	@Override
	public List getHotVote() {
		String sql="select t0.voteID ,t0.mainTitle ,t0.date from t_vote t0 join (select voteId,counts from (select voteId,count(*) counts from(SELECT voteId,userID FROM t_voteresult  group by voteID,userID) t1 group by t1.voteId) t2  order by t2.counts desc) t3 where t0.status=1 and t0.voteId=t3.voteId order by counts desc";
		Session session=this.getSessionfactory().getCurrentSession();
		List list=session.createSQLQuery(sql).setFirstResult(0).setMaxResults(7).list();
		return list;
	}

	@Override
	public List getImageUrl(HttpServletRequest request,Vote vote, List outList) throws Exception {
		List urlList=new ArrayList();
		List<VoteContent> allContent=vote.getAllContent();
		int count=0;
		for(int i=0;i<allContent.size();i++){
			VoteContent voteContent=allContent.get(i);
			if(voteContent.getContentType().equals(VoteType.CONTENTS)){
				urlList.add("");
			}
			else{			
				urlList.add(this.getColumnChart(request, voteContent.getSubTitle(), this.getDataSet(voteContent.getContentText(), outList, count, voteContent.getContentText().size())));
				count+=voteContent.getContentText().size();			
			}
			
		}
		
		return urlList;
	}
	
    public String getColumnChart(HttpServletRequest request,String chartTitle,CategoryDataset dataset) throws Exception{  
        //1. 获得数据集合  
       // CategoryDataset dataset = getDataSet();  
        //2. 创建柱状图  
        JFreeChart chart = ChartFactory.createBarChart3D(chartTitle, // 图表标题  
                "选项名", // 目录轴的显示标签  
                "", // 数值轴的显示标签  
                dataset, // 数据集  
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直  
                false, // 是否显示图例(对于简单的柱状图必须是false)  
                false, // 是否生成工具  
                false // 是否生成URL链接  
                );  
        //3. 设置整个柱状图的颜色和文字（char对象的设置是针对整个图形的设置）  
        chart.setBackgroundPaint(ChartColor.WHITE); // 设置总的背景颜色  
          
        //4. 获得图形对象，并通过此对象对图形的颜色文字进行设置  
        CategoryPlot p = chart.getCategoryPlot();// 获得图表对象  
        p.setBackgroundPaint(ChartColor.lightGray);//图形背景颜色  
        p.setRangeGridlinePaint(ChartColor.WHITE);//图形表格颜色  
          
        //5. 设置柱子宽度  
        BarRenderer renderer = (BarRenderer)p.getRenderer();  
        renderer.setMaximumBarWidth(0.06);  
          
        //解决乱码问题  
        getChartByFont(chart);  
          
        //6. 将图形转换为图片，传到前台  
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        return chartURL;
    }  
      
    //设置文字样式  
    private static void getChartByFont(JFreeChart chart) {  
        //1. 图形标题文字设置  
        TextTitle textTitle = chart.getTitle();     
        textTitle.setFont(new Font("宋体",Font.BOLD,20));  
          
        //2. 图形X轴坐标文字的设置  
        CategoryPlot plot = (CategoryPlot) chart.getPlot();  
        CategoryAxis axis = plot.getDomainAxis();  
        axis.setLabelFont(new Font("宋体",Font.BOLD,22));  //设置X轴坐标上标题的文字  
        axis.setTickLabelFont(new Font("宋体",Font.BOLD,15));  //设置X轴坐标上的文字  
          
      //2. 图形Y轴坐标文字的设置  
        ValueAxis valueAxis = plot.getRangeAxis();  
        valueAxis.setLabelFont(new Font("宋体",Font.BOLD,15));  //设置Y轴坐标上标题的文字  
        valueAxis.setTickLabelFont(new Font("sans-serif",Font.BOLD,12));//设置Y轴坐标上的文字  
    }  
  
    // 获取一个演示用的组合数据集对象  
    private static CategoryDataset getDataSet(List<String> listTitle,List<Object[]> listCount,int index,int size) {  
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        for(int i=0;i<listTitle.size();i++){
        	dataset.addValue((Number) listCount.get(index+i)[2], "",  listTitle.get(i));
        }
        return dataset;  
    }
	@Override
	public int getCreateCount() {
		String sql="SELECT count(*) num FROM vote.t_vote t where voteCreater=?";
		String id=UserHolder.getUser().getId();
		Session session=this.getSessionfactory().getCurrentSession();
		List list= session.createSQLQuery(sql).setString(0, id).list();
		String num=list.get(0).toString();
		return Integer.parseInt(num);
	}
	@Override
	public int getJoinCount() {
		String sql="select  count(*) num from  (SELECT count(*) num FROM vote.t_voteresult t where userid= ? group by voteid) t1;";
		String id=UserHolder.getUser().getId();
		Session session=this.getSessionfactory().getCurrentSession();
		List list= session.createSQLQuery(sql).setString(0, id).list();
		String num=list.get(0).toString();
		return Integer.parseInt(num);
		
		
	}
	@Override
	public List queryTalbe(int voteId, int subT1, int subT2) {
		String sql="SELECT " + 
				"	tt1.voteName as name1, " + 
				"	tt1.voteResult as result1, " + 
				"	tt2.voteName as name2, " + 
				"	tt2.voteResult as result2, " + 
				"	count( * )  " + 
				"FROM " + 
				"	( SELECT * FROM `t_voteresult` t1 WHERE t1.voteId = ? AND ( t1.voteName = ? OR t1.voteName = ? ) ) tt1 " + 
				"	JOIN ( SELECT * FROM `t_voteresult` t2 WHERE t2.voteId = ? AND ( t2.voteName = ? OR t2.voteName = ? ) ) tt2  " + 
				"WHERE " + 
				"	tt1.uuid = tt2.uuid  " + 
				"	AND ( tt1.voteName = ? AND tt2.voteName = ? )  " + 
				"GROUP BY " + 
				"	tt1.voteResult, " + 
				"	tt2.voteResult";
		Session session=sessionfactory.getCurrentSession();
		SQLQuery sqlQuery =session.createSQLQuery(sql);
		sqlQuery.setParameter(0, voteId);sqlQuery.setParameter(1, subT1);sqlQuery.setParameter(2, subT2);
		sqlQuery.setParameter(3, voteId);sqlQuery.setParameter(4, subT1);sqlQuery.setParameter(5, subT2);
		sqlQuery.setParameter(6, subT1);sqlQuery.setParameter(7, subT2);
		List list= sqlQuery.list();
		return list;
	}
	



}
