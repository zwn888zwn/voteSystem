package vote.controller;

import java.awt.Font;  

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
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
import org.springframework.stereotype.Controller;  
import org.springframework.ui.ModelMap;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.servlet.ModelAndView;  
  
@Controller  
public class ColumnChartController {  
      
    @RequestMapping("/resultmap.do")  
    public String resultmap(){  
        return "/resultmap";  
    }  
      
    //��ʾ��״ͼ  
    @RequestMapping(value = "/getColumnChart.do")  
    public ModelAndView getColumnChart(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        //1. ������ݼ���  
        CategoryDataset dataset = getDataSet();  
        //2. ������״ͼ  
        JFreeChart chart = ChartFactory.createBarChart3D("ѧ���Խ�ʦ�ڿ������", // ͼ�����  
                "ѡ����", // Ŀ¼�����ʾ��ǩ  
                "", // ��ֵ�����ʾ��ǩ  
                dataset, // ���ݼ�  
                PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ  
                false, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)  
                false, // �Ƿ����ɹ���  
                false // �Ƿ�����URL����  
                );  
        //3. ����������״ͼ����ɫ�����֣�char������������������ͼ�ε����ã�  
        chart.setBackgroundPaint(ChartColor.WHITE); // �����ܵı�����ɫ  
          
        //4. ���ͼ�ζ��󣬲�ͨ���˶����ͼ�ε���ɫ���ֽ�������  
        CategoryPlot p = chart.getCategoryPlot();// ���ͼ�����  
        p.setBackgroundPaint(ChartColor.lightGray);//ͼ�α�����ɫ  
        p.setRangeGridlinePaint(ChartColor.WHITE);//ͼ�α����ɫ  
          
        //5. �������ӿ��  
        BarRenderer renderer = (BarRenderer)p.getRenderer();  
        renderer.setMaximumBarWidth(0.06);  
          
        //�����������  
        getChartByFont(chart);  
          
        //6. ��ͼ��ת��ΪͼƬ������ǰ̨  
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        modelMap.put("chartURL", chartURL);  
        return new ModelAndView("resultmap",modelMap);  
    }  
      
    //����������ʽ  
    private static void getChartByFont(JFreeChart chart) {  
        //1. ͼ�α�����������  
        TextTitle textTitle = chart.getTitle();     
        textTitle.setFont(new Font("����",Font.BOLD,20));  
          
        //2. ͼ��X���������ֵ�����  
        CategoryPlot plot = (CategoryPlot) chart.getPlot();  
        CategoryAxis axis = plot.getDomainAxis();  
        axis.setLabelFont(new Font("����",Font.BOLD,22));  //����X�������ϱ��������  
        axis.setTickLabelFont(new Font("����",Font.BOLD,15));  //����X�������ϵ�����  
          
      //2. ͼ��Y���������ֵ�����  
        ValueAxis valueAxis = plot.getRangeAxis();  
        valueAxis.setLabelFont(new Font("����",Font.BOLD,15));  //����Y�������ϱ��������  
        valueAxis.setTickLabelFont(new Font("sans-serif",Font.BOLD,12));//����Y�������ϵ�����  
    }  
  
    // ��ȡһ����ʾ�õ�������ݼ�����  
    private static CategoryDataset getDataSet() {  
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        dataset.addValue(40, "", "��ͨ����ѧ");  
        dataset.addValue(50, "", "����ѧ");  
        dataset.addValue(60, "", "�������ѧ");  
        dataset.addValue(70, "", "�������ۿ�");  
        dataset.addValue(80, "", "�������ۿ�");  
        return dataset;  
    }  
}  