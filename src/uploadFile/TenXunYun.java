package uploadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.utils.Base64;

public class TenXunYun {

	public static void main(String[] args) {
		File cosFile=new File("F:\\Googledown\\1489228773296bcqaa.mp3");
		COSCredentials cred = new BasicCOSCredentials("AKIDpmjlntjMlHsYKBaBKbzV7qeacrAZsLAq","wmYDi2BD9WuBehXsASegYK0Vq12uETaW");
		// 2 设置bucket的区域, COS地域的简称请参照
		// https://cloud.tencent.com/document/product/436/6224
//		ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
//		// 3 生成cos客户端
//		COSClient cosClient = new COSClient(cred, clientConfig);
		Date date=new Date();
//		int index0 = cosFile.getName().lastIndexOf(".");
//		String fileFormat = cosFile.getName().substring(index0);
//	    String fileName = date.getTime() + '-' + Math.round(Math.random()*100000) + fileFormat;
		Calendar cal = Calendar.getInstance();
		String currHourDir = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.HOUR_OF_DAY);
//		String bucketName = "sktsaas-1256254171";
//		String key = "saas/"+currHourDir+ "/"+fileName;
//		// 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
//		// 大文件上传请参照 API 文档高级 API 上传
//		// 指定要上传到 COS 上的路径
// 
//		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, cosFile);
//		cosClient.putObject(putObjectRequest);
//		cosClient.shutdown();
//		Date expiration = new Date(new Date().getTime() + 5 * 60 * 10000);
//		URL url = cosClient.generatePresignedUrl(bucketName, key, expiration);
//		System.out.println(url);
	}
	
	public String DownloadFromUrl(String urlstr,String code,String user,String password) throws MalformedURLException{
		  StringBuilder data = new StringBuilder();
		  URL url = new URL(urlstr);
		  int counts = 0;
		  while(true){
		   try {
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    String authString = user + ":" + password;
		    byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		    String authStringEnc = new String(authEncBytes);
		    connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		    connection.setRequestProperty("User-Agent", "MSIE 7.0");
		    connection.setConnectTimeout(5000);
		    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), code));
		    String line = null;
		    while ((line = br.readLine()) != null) {
		     data.append(line);
		     data.append("\r\n");
		    }
		    connection.disconnect();
		    break;
		   } catch (ConnectException e) {           
		    if(counts >=5){
		     System.out.println(urlstr+" : "+e.toString());
		     return null;
		    }
		    counts++;
		    System.out.println("再次尝试");
		    continue;
		   } catch (Exception e) {           
		    if(counts >=5){
		     System.out.println(urlstr+" : "+e.toString());
		     return null;
		    }
		    counts++;
		    System.out.println("再次尝试");
		    continue;
		   }
		  }
		  return data.toString(); 
		 }
}
