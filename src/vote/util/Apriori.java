package vote.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author ���
 * �㷨���ƣ������ھ�Apriori�㷨������֧�ֶ��µ����е�Ƶ����
 * �㷨ԭ��
 *
 * 0. ��ȡC1��ʱ����Ϊû��L(0)Ƶ���������Ե�������һ������
 * 1. �Ѿ�Ƶ����L(k-1)��{}������+��֦}��������ĺ�ѡ��C(k)
 * 2. ���ݾ����ĺ�ѡ��C(k)�������Ƶ����L(k)
 *
 * ���ݽṹ��
 * 1. ʹ��List<String> �洢�������ݿ�����ݣ�string����ÿһ�е�����
 * 2. ʹ��HashMap<String,Integer> �洢��Լ�����ظ����������ڲ��ҡ���{�}�ļ��ϡ�
 * 3. ÿ�����String���ʹ洢����е�ÿһ��ʹ��String.split(" ")����������ݣ��Ա�ȡ����е�ÿһ��
 *
 * ���������ݸ�ʽ��
 * 1. ÿ�������Կո����
 * 2. ÿһ�д���һ������T,�����ž����к�
 *
 * ʾ�����ݣ�
 * 1. {11��12��13}��һ��Ƶ�����������HashMap<String,Integer>�е�һ��Ԫ�أ���������ΪString.
 * 2. Set<String> = HashMap.keySet(); �����洢HashMap������key��ֵ��Ҳ���Ǵ洢���е��������������ظ�������
 *
 * ��ظ��
 * 1. ֧�ֶȰٷֱ�
 * 2. ֧�ֶ�
 * 3. ����
 * 4. ��ѡ�
 * 5. Ƶ���

 */




public class Apriori {

    private  double SUPPORT_PERCENT = 0.01;
    private  List<String> data2DList = new ArrayList();

    
    
    public void setData2DList(List<String> data2dList) {
		data2DList = data2dList;
	}



	public static void main(String[] args) {
        System.out.println("===================Apriori�㷨���������====================");

        //0. ��������
        /*Scanner in=new Scanner(System.in);
        SUPPORT_PERCENT = in.nextDouble();*/

        //1. ��������
        //importData();

        //2. �����㷨����
        Apriori ap=new Apriori();
        ap.apriori();
    }


	
	

    /**
     * Apriori�㷨��������Ҫ�ݹ鴦��
     *
     * @return
     */
    public  Map<String, Integer> apriori(){


        //ɨ���������ݿ�D����ÿһ����м��������һ���{��ѡ���}
        Map<String, Integer> stepFrequentSetMap = new HashMap();
        Map<String, Integer> lastFrequentSetMap = new HashMap();
        System.out.println("\n=====================��" + 1 + "��ɨ���Ƶ����б�======================" + "\n");


        stepFrequentSetMap.putAll(getFrequentSets(findCandidateOneSets()));
        Set<String> stringSet = stepFrequentSetMap.keySet();

        for (String string: stringSet){
            System.out.println("Ƶ������" + string +  "֧�ֶ�:" + stepFrequentSetMap.get(string));
        }
        System.out.println("\nƵ����ĸ�����" + stringSet.size());

        int i = 1;
        //�����ɵ�Ƶ���Ϊ�յ�ʱ���˳�ѭ��
        while(stepFrequentSetMap != null && stepFrequentSetMap.size()>0){

            i++;

            //��ӡ��ǰ��Ƶ�������Ϣ
            System.out.println("\n=====================��" + i + "��ɨ���Ƶ����б�======================" + "\n");
            lastFrequentSetMap = stepFrequentSetMap;
            stepFrequentSetMap = getFrequentSets(getMinCandidate(stepFrequentSetMap));

            if (stepFrequentSetMap != null){
                stringSet = stepFrequentSetMap.keySet();
                for (String string: stringSet){
                    System.out.println("Ƶ������" + string +  "֧�ֶ�:" + stepFrequentSetMap.get(string));
                }
                System.out.println("\nƵ����ĸ�����" + stringSet.size());
            }

        }
        
        return lastFrequentSetMap;
        
    }

    /**
     * ��������
     *
     * @return ���Ϻͼ��ϣ�����ά����
     */
    private  void importData(){

        File file = new File("retail.dat");//test.dat   retail.dat

        try {
            //�ļ�������Ϊ�ļ�����ִ�н������Ĳ���
            if (file.isFile() && file.exists()){
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file),
                        "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null){//��ȡ�ļ��е�һ��

                    data2DList.add(lineTxt);
                }
                reader.close();
            }else {
                System.err.println("�Ҳ���ָ���ļ���");
            }
        }catch (Exception e){
            System.err.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
        }

    }


    /**
     * Ѱ��1��ĺ�ѡ��C1����ʼ������Ĵ���
     *
     * @return ����map���ϣ����������ÿһ���Լ���Ӧ���ظ�������key->value
     *
     * key��ÿһ����ѡ��/Ƶ����
     * value����Ӧ��ѡ��/Ƶ������ظ�����
     */
    private  HashMap<String, Integer> findCandidateOneSets()
    {
        HashMap<String, Integer> resultSetMap = new HashMap();

        for(String dataList :data2DList)
        {
            String[] dataString = dataList.split(" ");
            //��ѯmap�������Ƿ��и�Ԫ�أ����û�У������Ԫ�أ��������Ԫ�ض��ڵ�value(�ظ�������+1
            for (String string :dataString){
                string += " ";
                if (resultSetMap.get(string) == null){
                    resultSetMap.put(string,1);
                }else {
                    resultSetMap.put(string,resultSetMap.get(string) + 1);
                }
            }
        }
        return resultSetMap;
    }



    /**
     * ��L(k-1)Ƶ�������еõ������C(k)��ѡ��
     *
     * 1. {���� + ��֦}  = {�����ĺ�ѡ��C(k)}
     * �Ƕ�L(k-1)Ƶ������ÿһ�����ϣ�����[�����ӳɺ�ѡ�����߼�֦]��������ȫ��������{��ѡ��}��Ȼ���ٽ���һ�δ�ѭ�������м�֦������������ʱ�临�Ӷ�
     *
     * ����ԭ����ĳ�����ϴ���һ���ǿ��Ӽ�����Ƶ�������ü��ϲ���Ƶ���
     *
     * 1. ��������ԭ��ѹ����ѡ����С��õ�����ĺ�ѡ������
     * 2. �Ծ�����ĺ�ѡ������ۼӼ���
     *
     * @param frequentMapSet
     * @return ����C(k)��ѡ����
     */
    private  Map<String, Integer> getMinCandidate(Map<String, Integer> frequentMapSet){

        //��Ҫ���صľ������ĺ�ѡ�
        Map<String,Integer> minCandidateMapSet = new HashMap();

        //����Ƶ�����KEY
        Set<String> frequentSet = frequentMapSet.keySet();

        /**
         * 1. {�����ӹ���:������ѡ�}
         *
         * ����ÿһ������𿪳�������顣�������ÿһ�����������ÿһ��Ƚϣ�
         * ����в��ظ�����ͽ�������ԭ��������������K��ĺ�ѡ�����
         */

        for (String frequentItemList1: frequentSet){
            for (String frequentItemList2: frequentSet){
                String[] itemArray1 =  frequentItemList1.split(" ");
                String[] itemArray2 = frequentItemList2.split(" ");

                //���������ɵĺ�ѡ�
                String linkString = "";
                boolean flag = true;//�Ƿ��������
                /**
                 * �ж��Ƿ���������ӵ�������
                 * 1. ǰK-1�������ͬ
                 * 2. itemArray1�����һ�����С�ڵڶ���������һ��
                 * �����ӹ��̣�
                 * ����һ�����ڶ���������һ����������
                 */

                for (int i =0;i<itemArray1.length -1 ;i++){
                    if (itemArray1[i].equals(itemArray2[i])){
                        flag = false;
                        break;
                    }
                }
                if (flag && itemArray1[itemArray1.length - 1].compareTo(itemArray2[itemArray1.length -1]) < 0){
                    linkString = frequentItemList1  + itemArray2[itemArray2.length - 1] + " ";
                }

                /**
                 * 2. {��֦���̣�Ҳ������������ʹ��}
                 *
                 * �ҳ��ú�ѡ���������Ӽ������ж�ÿ���Ӽ��Ƿ�����Ƶ���Ӽ�
                 */
                boolean hasInfrequentSubSet = false;// �Ƿ��з�Ƶ�������Ĭ����
                if (linkString != ""){//�����ӳɹ�
                    //System.out.println(linkString);
                    //��ѡ��������������
                    String[] itemArray = linkString.split(" ");
                    //*�ص㣺�ҳ��ú�ѡ���������Ӽ���ʵ�ʲ�����ʱ��ֻ��Ҫ�ҳ��Ⱥ�ѡ����һ����Ӽ�����
                    //*�ص㣺��Ϊ�ⶼ�ǲ��ϵĵݹ������ģ�������С����϶���Ƶ���
                    for (int i = 0; i <itemArray.length; i++){
                        String subString = "";
                        for (int j = 0;j<itemArray.length; j++){
                            if (j!=i){
                                subString += itemArray[j] + " ";
                            }
                        }
                        if (frequentMapSet.get(subString) == null){
                            hasInfrequentSubSet = true;//��һ������ǣ�������Ͳ���Ƶ���
                            break;
                        }
                    }
                }else{
                    hasInfrequentSubSet = true;//���ﲢ���Ǵ�����ڷ�Ƶ���Ӽ���ֻ�Ǳ�ʾû�������ӳɹ���û���ҵ���ѡ��
                }
                //�����ӳɹ������뵽��ѡ��������
                if (!hasInfrequentSubSet){
                    minCandidateMapSet.put(linkString,0);
                }
            }
        }

        /**
         * 3. {�����ɵĺ�ѡ������ͳ��֧�ֶ�}
         */

        Set<String> minCandidateSet = minCandidateMapSet.keySet();


        // ��ÿһ�еĺ�ѡ�����String���ݱ���ַ������顣
        // ��ÿһ�е�������Stringת����List<String>
        for (String itemList: minCandidateSet){
            String[] strings = itemList.split(" ");
            int num = 0;

            for (String data:data2DList){
                List<String>dataList = Arrays.asList(data.split(" "));

                Boolean flag = true;
                //�����ѡ�����һ���ڵ�ǰ�������Ҳ�����֧�ֶ��򲻻�����
                for (int i =0;i < strings.length;i++){

                    if (!dataList.contains(strings[i])){

                        flag = false;
                        break;
                    }
                }
                if (flag){
                    minCandidateMapSet.put(itemList,minCandidateMapSet.get(itemList) + 1);
                }
            }

        }

        return minCandidateMapSet;
    }


    /**
     * �Ӿ�����C(k)��ѡ���еõ�L(k)Ƶ������
     *
     * {ͳ�ƾ����ĺ�ѡ��C(k)���ظ�����}  = {���õ�L(k)Ƶ����}
     *
     * 1. �Ծ�����ĺ�ѡ�������жϣ�֮ǰ�Ѿ������˼�����������������֧�ֶȵĽ����ų�
     *
     * @param minCandidateMapSet
     * @return
     */
    private  Map<String, Integer> getFrequentSets(Map<String, Integer> minCandidateMapSet){

        if (minCandidateMapSet == null){
            //�����ĺ�ѡ��Ϊ�գ���ʾ��ǰ�����ĺ�ѡ�������ڣ���ʱ��Ҫ�������㷨��
            System.err.println("��ѡ�Ϊ��");
            return null;
        }else{
            Map<String,Integer> frequentMapSet = new HashMap();//��Ҫ���ص�Ƶ���

            Set<String> minCandidateSet = minCandidateMapSet.keySet();//��ȡ��ѡ���KEY��Ҳ�������е���ľ�������


            Double SUPPORT = (data2DList.size() * SUPPORT_PERCENT);//��С֧�ֶ�
            //Double SUPPORT = 5.0;
            System.out.println("��С֧�ֶ�Ϊ��" + SUPPORT + " ��ѡ��Ĵ�СΪ��" + minCandidateMapSet.size() + "\n");
            for (String itemListString: minCandidateSet){
                //���������ظ��������ڻ��ߵ�����С֧�ֶȣ��ͰѸ�����뵽Ƶ�����
                if (minCandidateMapSet.get(itemListString) >= SUPPORT){
                    frequentMapSet.put(itemListString,minCandidateMapSet.get(itemListString));
                }
            }
            if (frequentMapSet.size() == 0){
                //����õ���Ƶ���Ϊ�գ���ʾ��ʱѭ��Ӧ�ý�����
                return null;
            }else{
                return frequentMapSet;
            }
        }
    }

}
