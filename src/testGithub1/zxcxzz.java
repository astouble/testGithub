package testGithub1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zxcxzz {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> zongList=new ArrayList<Map<String, Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("date", "2018-01-01 11:11:11");
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("date", "2018-01-01 11:11:01");
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("date", "2018-01-01 11:11:13");
		Map<String, Object> map3=new HashMap<String, Object>();
		map3.put("date", "2018-01-01 11:10:11");
		zongList.add(map);
		zongList.add(map1);
		zongList.add(map2);
		zongList.add(map3);
//		Collections.sort(zongList, new Comparator<Map>() {
//			@Override
//			public int compare(Map o1, Map o2) {
//				int ret = 0;
//				try {
//					// 比较两个对象的顺序，如果前者小于、等于或者大于后者，则分别返回-1/0/1
//					ret = sdf.parse(o1.get("date").toString()).compareTo(sdf.parse(o2.get("date").toString()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				return ret;
//			}
//		});
//		String;
//		Map<String, Object>[] maap = new Map[5]; 
//		Arrays.sort(zongList.toArray(new Map[0]));;
//		for(Map<String, Object> mm:zongList) {
//			System.out.println(mm.get("date"));
//		}
//		
//		
//		
//		
//		ArrayList<String> list=new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		list.add("4");
//		list.add("0");
//
//		String[] strings =list.toArray(new String[0]);
//		for(String s:strings) {
//			System.out.println(s);
//		}
		
		String ss="\t123\t按客户发动机\ta拉很多事\t打两局a\tad";

		System.out.println("\t123\t按客户发动机\ta拉很多事\t打两局a\tad");
		
		BufferedWriter osw = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("F:\\ceshi.txt");
			osw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			osw.write(ss);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		BufferedReader bre = null;
		try {
			String str="";
		String file = "D:\\微博导出数据文件.txt";
		bre = new BufferedReader(new FileReader(file));//此时获取到的bre就是整个文件的缓存流
		while ((str = bre.readLine())!= null) // 判断最后一行不存在，为空结束循环
		{
//		System.out.println(str);//原样输出读到的内容，此处即可根据规则进行内容切分
		String[] sss=str.split("\t");
		for(String sssss:sss) {
			System.out.println(sssss);
		}
		};
		}catch(Exception e) {
			
		}
	}
}
