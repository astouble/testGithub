	package testGithub1.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class push {

	public static void main(String[] args) {
		Map<String, Object> ptActMap = new HashMap<String, Object>();
		ptActMap.put("ptActId", "1");
		ptActMap.put("ptActId", "2");
		System.out.println(ptActMap.get("ptActId"));
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
		Date date;
		try {
			date = df.parse("2018-07-30");
			Calendar calendar=Calendar.getInstance();   
		    calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+2);//让日期加1  
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date zero = calendar.getTime();
            System.out.println(zero.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	}
}
