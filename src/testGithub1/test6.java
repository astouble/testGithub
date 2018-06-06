package testGithub1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class test6 {

	public static void main(String[] args) throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream("F:\\微博导出数据文件.txt"), "gbk");  
	    OutputStreamWriter isw = new OutputStreamWriter(new FileOutputStream("D:\\微博导出数据文件.txt"), "utf-8");  
	    int len = 0;  
	    while ((len = isr.read()) != -1) {  
	        isw.write(len);  
	    }  
	    isw.close();  
	    isr.close();  
	}
}
