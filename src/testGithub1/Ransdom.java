package testGithub1;

import java.util.Calendar;
import java.util.Date;

public class Ransdom {
	public static void main(String[] args){  
//		Random rand = new Random();  
//		for(int i=0; i<10; i++) {  
//			System.out.println(rand.nextInt(2));  
//		}  
		int totalRowCount=7;
		int pgTotal=(int) Math.ceil((double)totalRowCount/3);
		System.out.println(pgTotal);
		
		StringBuffer picStrBuffer=new StringBuffer();
		String[] picShuzu="132214124,1234".split(",");
		for(int i=0;i<picShuzu.length;i++) {
			picStrBuffer.append("test"+picShuzu[i]+",");
		}
		System.out.println(picStrBuffer.toString().substring(0, picStrBuffer.length()-1));
		Date createTime=new Date();
		Calendar calendar=Calendar.getInstance();  
		calendar.setTime(createTime);
	    calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1  
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
		System.out.println(calendar.getTime().getTime());
	}
}
