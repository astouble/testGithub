package testGithub1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		Collections.sort(zongList,new Comparator<Map>() {
		    @Override
		    public int compare(Map o1, Map o2) {
		     int ret = 0;
		     try {
		      //比较两个对象的顺序，如果前者小于、等于或者大于后者，则分别返回-1/0/1
		      ret = sdf.parse(o2.get("date").toString()).compareTo(sdf.parse(o1.get("date").toString()));
		     } catch (ParseException e) {
		      e.printStackTrace();
		     }
		     return ret;
		    }
		   });
		for(Map<String, Object> mm:zongList) {
			System.out.println(mm.get("date"));
		}
	}
}
