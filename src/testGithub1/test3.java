package testGithub1;

public class test3 {

	public static void main(String[] args) {
		String stre ="1231";
		 String str="123456789";
//		 str1=str1.replaceAll(, "x");
//		   String ragex = "[^x00-xff]|\\w";
//		   String ragex = "^.[\\s\\S]$";
//		   String newstr = "X";
//		   String s =str.replaceAll(ragex, newstr);
//		   System.out.println(s);
		   
		    String content="l123456";
		    String firstStr=content.substring(0, 1);
	        String subStr=content.substring(1);
			String repStr =subStr.replaceAll("[\\s\\S]", "X");
			System.out.println(firstStr+repStr); 
			
			
			System.out.println("1:"+content.replaceAll("(?<=[\\s\\S]{1})[\\s\\S]","*"));
			
			
			System.out.println("2:"+content.replaceAll("(?<=.{1}).+(?=.{1})","x"));
			System.out.println("2:"+content.replaceAll("(?<=.{1}).(?=.{1})","x"));//去除第一位和最后一位，中间都替换成X
			
			
			System.out.println("3:"+content.replaceAll("^(.)(.*?)(.)$", "$1xx$3")); 
			
			
			String idCard = "123456789987654321";
	        //$1、$2、……表示正则表达式里面第一个、第二个、……括号里面的匹配内容
	        String idCardNumber = idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1****$2");
	        System.out.println("身份证号长度："+idCard.length());
	        System.out.println("正则idCard中4*：" + idCardNumber);
	        
	        System.out.println(replaceStr("李宝鑫"));
	}
	
	public static String replaceStr(String content){
    	if(content.length()>1) {
    		return content.replaceAll("(?<=[\\s\\S]{1})[\\s\\S]","*");
    	}else {
    		return content;
    	}
    	
        
    }
}
