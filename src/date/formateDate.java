package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class formateDate {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();  
		try {
//			calendar.setTime(sdf.parse("2018/08/30"));
			calendar.setTime(sdf1.parse("2018/08/31 17:29:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long createTimes=calendar.getTime().getTime();
		String createTime="刚刚";
		long time=new Date().getTime()-createTimes;
		if(60*60*1000>time&&time>=10*60*1000) {
			long mins=time/(60*1000);
			createTime=mins+"分钟前";
		}else if(60*60*1000<=time&&time<=24*60*60*1000) {
			long hours=time/(60*60*1000);
			createTime=hours+"小时前";
		}else if(time>24*60*60*1000){
			createTime=sdf.format(calendar.getTime());
		}
		System.out.println(createTime);
		int originPrice=0;
		for(int i=0;i<4;i++) {
				originPrice+=i;
		}
		System.out.println(originPrice);
	}
}
