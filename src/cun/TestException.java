package cun;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestException {

	private String desc;
	 
	public String getDesc() {
		return desc;
	}
 
	public void setDesc(String desc) {
		this.desc = desc;
	}
 
	public static void main(String[] args) {
//		TestException e = null;
//		e.processException();
//		System.out.println(e.getDesc());
		try {
			try {
				if(1>0) {
					throw new NumberFormatException(" test ");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(111);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println(222);
		}
		
		
		String addrString="asfasdf/afdafsa/asdfasd/asdfa";
		String region=addrString.substring(0,addrString.lastIndexOf("/"));
		String detailInfo=addrString.substring(addrString.lastIndexOf("/")+1);
		System.out.println(region);
		System.out.println(detailInfo);
//		long time=(7*24*60*60*1000)/1000;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time;
		try {
			time = ((sdf.parse("2018-09-07 00:00:00").getTime()+(604800000))-new Date().getTime())/1000;
			time = ((1536249600000L+(604800000))-new Date().getTime())/1000;
//			String expiredStr=time/(24*60*60)+"天"+time/(60*60)+"小时"+time/(60)+"分钟后结束交易";
			 long day = time / (24*60*60);
			 long hour = time % (24*60*60) / (60*60);
			 long min = time % (24*60*60) % (60*60) / 60;
			System.out.println(time/(24*60*60)+"天"+time%(60*60)+"小時"+time%(60)+"分種");
			System.out.println(day+"天"+hour+"小時"+min+"分種");
//			System.out.println(expiredStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String data="111_11123";
		int ptActId=Integer.valueOf(data.substring(0, data.indexOf("_")));
//		int goodsId=Integer.valueOf(null);
		
		System.out.println(ptActId);
//		System.out.println(goodsId);
		int i=0;
		while(i>=0) {
			if(i>1) {
				break;
			}
			i++;
		}
		System.out.println(i);
		
		StringBuffer goodsIds=new StringBuffer();
		System.out.println(goodsIds.length());
		System.out.println(Math.ceil(1000*((double)85/100)));
		System.out.println(1001*((double)85/100));
		DecimalFormat df=new DecimalFormat("#.0");
		System.out.println(df.format((double)85/10));
		System.out.println((double)111/100);
		String sss=null;
//		System.out.println(sss.trim());
		
		List<Map<Object,Object>> bookCatalogTwo=new ArrayList<>();
		Map<Object,Object> twoMap=new HashMap<>();
		for(int num=0;num<10;num++) {
			twoMap.put("test1", num);
			bookCatalogTwo.add(twoMap);
		}
		for(Map<Object,Object> map:bookCatalogTwo) {
			System.out.println(map.get("test1"));
		}
		
	}
	
	private void processException() {
		try {
			mayThrowsException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void mayThrowsException () throws Exception {
		throw new Exception("My Exception");

	}
}
