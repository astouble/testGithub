package uploadFile;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.UrlEncoderUtils;
import com.youyi.tools.bean.ResultModel;
import com.youyi.tools.cos.HMACSHA1;
import com.youyi.tools.utils.BaseConfig;
import com.youyi.tools.utils.LogUtils;

@Controller
@RequestMapping("cos")
public class CosInterfaceContorller {

	@RequestMapping("/uploadtest")
    public String uploadTest(){
        return "index/uploadtest";
    }
	
    /**
     * 腾讯云上传图片接口鉴权
     *
     * @return
     */
    @RequestMapping("cos2auth")
    @ResponseBody
    public ResultModel cos2auth(String pathname) {
        String auth = genCosInterfaceAuth(pathname);
        return ResultModel.buildMapResultModel().put("cos2auth", auth);
    }
    
    public static String genCosInterfaceAuth(String folder) {
	    if(StringUtils.isBlank(folder)) {
	    	    folder = "/";
	    }
	    folder = "saas/";
    // 开发者的项目 ID，即COS控制台密钥管理里的 APPID
    String appId = BaseConfig.getValue("cos.appId", "1256254171");

    // 空间名称 Bucket
    String bucket = BaseConfig.getValue("cos.bucket", "sktsaas-1256254171");

    // 项目的 Secret ID
    String secretId = BaseConfig.getValue("cos.secretId", "AKIDpmjlntjMlHsYKBaBKbzV7qeacrAZsLAq");

    // 项目的 Secret Key
    String secretKey = BaseConfig.getValue("cos.secretKey", "wmYDi2BD9WuBehXsASegYK0Vq12uETaW");
//    String secretId = BaseConfig.getValue("cos.secretId", "AKIDhGertnoHm6GF3gSQbbR6at25ygFduwAJ");
    // 项目的 Secret Key
//    String secretKey = BaseConfig.getValue("cos.secretKey", "61kKkz75wc6RbaijbIoBMMCwZDg1BAxb");

    // 单次签名，e 必须设置为0；多次有效签名时，e 为签名的时间戳，单位是秒
    int expiredTime = 0;

    // 当前时间戳，是一个符合 Unix Epoch 时间戳规范的数值，单位为秒
    int currentTime = (int) System.currentTimeMillis() / 1000;

    // 随机串，无符号10进制整数，用户需自行生成，最长 10 位
    int rand = new Random().nextInt((int) Math.pow(2, 32));

    try {
        // 唯一标识存储资源的相对路径。格式为 /appid/bucketname/dirname/[filename]
        String fileid = URLEncoder.encode("/" + appId + "/" + bucket + "/" + folder, "utf-8");
        // 每个字段具体格式查看文档：https://www.qcloud.com/document/product/436/6054
        String plainText = "a=" + appId + "&k=" + secretId + "&e=" + expiredTime + "&t=" + currentTime + "&r=" + rand + "&f=" + fileid + "&b=" + bucket;

        byte[] bytes1 = HMACSHA1.HmacSHA1Encrypt(plainText, secretKey);
        byte[] bytes2 = plainText.getBytes("utf-8");
        byte[] bytes3 = addBytes(bytes1, bytes2);
        return Base64.encodeBase64String(bytes3);
    } catch (Exception e) {
        LogUtils.error("gen cos auth error for folder '%s'", e, folder);
    }

    return "";
}

/**
 * @param data1
 * @param data2
 * @return data1 与 data2拼接的结果
 */
public static byte[] addBytes(byte[] data1, byte[] data2) {

    byte[] data3 = new byte[data1.length + data2.length];
    System.arraycopy(data1, 0, data3, 0, data1.length);
    System.arraycopy(data2, 0, data3, data1.length, data2.length);
    return data3;

}


public static String genPostCos2Auth(String httpUri) {

    String host = "tuan-1256254171.cos.ap-beijing.myqcloud.com";
//    String x_cos_content_sha1 = "7b502c3a1f48c8609ae212cdfb639dee39673f5e";
//    String x_cos_storage_class = "nearline";

    String q_sign_algorithm = "sha1";
    String q_ak = BaseConfig.getValue("cos.secretId", "AKIDpmjlntjMlHsYKBaBKbzV7qeacrAZsLAq");
    long nowTime = System.currentTimeMillis() / 1000;
    String q_sign_time = nowTime + ";" + (nowTime + 60 * 10);
    String q_key_time = q_sign_time;
//    String q_header_list = "host;x-cos-content-sha1;x-cos-storage-class";
    String q_header_list = "host";
    String q_url_param_list = "";

    String SecretKey = BaseConfig.getValue("cos.secretKey", "wmYDi2BD9WuBehXsASegYK0Vq12uETaW");
    String SignKey = HmacUtils.hmacSha1Hex(SecretKey, q_key_time);
    String HttpMethod = "post";
//    String HttpURI = "/tuan/act";
    httpUri= "/";
    String HttpURI = httpUri;
    String HttpParameters = "";

    StringBuilder HttpHeaders = new StringBuilder();
    HttpHeaders.append("host=").append(UrlEncoderUtils.encode(host));
//    HttpHeaders.append("&x-cos-content-sha1=").append(UrlEncoderUtils.encode(x_cos_content_sha1));
//    HttpHeaders.append("&x-cos-storage-class=").append(UrlEncoderUtils.encode(x_cos_storage_class));

    StringBuilder HttpString = new StringBuilder();
    HttpString.append(HttpMethod).append("\n");
    HttpString.append(HttpURI).append("\n");
    HttpString.append(HttpParameters).append("\n");
    HttpString.append(HttpHeaders).append("\n");

    StringBuilder StringToSign = new StringBuilder();
    StringToSign.append(q_sign_algorithm).append("\n");
    StringToSign.append(q_sign_time).append("\n");
    StringToSign.append(DigestUtils.sha1Hex(HttpString.toString())).append("\n");

    String q_signature = HmacUtils.hmacSha1Hex(SignKey, StringToSign.toString());
    LogUtils.info("genPostCos2Auth|" + httpUri + "|" + SignKey + "|" + HttpHeaders + "|" + StringToSign + "|" + q_signature);
    StringBuilder sb = new StringBuilder();
    sb.append("q-sign-algorithm=").append(q_sign_algorithm).append("&");
    sb.append("q-ak=").append(q_ak).append("&");
    sb.append("q-sign-time=").append(q_sign_time).append("&");
    sb.append("q-key-time=").append(q_key_time).append("&");
    sb.append("q-header-list=").append(q_header_list).append("&");
    sb.append("q-url-param-list=").append(q_url_param_list).append("&");
    sb.append("q-signature=").append(q_signature);
    LogUtils.info("genPostCos2Auth|"+sb.toString());
    return sb.toString();
}
public static void main(String[] args) {
	String bucketName = "sktsaas-1256254171";
	COSCredentials cred = new BasicCOSCredentials("AKIDpmjlntjMlHsYKBaBKbzV7qeacrAZsLAq","wmYDi2BD9WuBehXsASegYK0Vq12uETaW");
	// 2 设置bucket的区域, COS地域的简称请参照
	// https://cloud.tencent.com/document/product/436/6224
	ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
//	// 3 生成cos客户端
	COSClient cosClient = new COSClient(cred, clientConfig);
	String key = "upload20180810.jpg";
	// 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(5分钟)
	// 这里设置签名在半个小时后过期
	Date expirationTime = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);
 
	try {
		 URL url=cosClient.generatePresignedUrl(bucketName, key, expirationTime, HttpMethodName.PUT);
		 System.out.println(url);
		FileInputStream fileInputStream=new FileInputStream("F:\\图片\\1530254768471ilvuc.jpg");
	    int responseCode = 0;
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setDoOutput(true);
	    connection.setRequestMethod("PUT");
	    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	    
	    //out=new OutputStreamWriter(new FileOutputStream(new File("c:/qqq.zip")));
	    // 写入要上传的数据 
	    byte buff[]= new byte[1024];
	    int len;
	    while( (len=fileInputStream.read(buff))!=-1  ) {
	    	out.write(buff, 0, len);
	    }
	    out.close();
	    responseCode = connection.getResponseCode();
	    System.out.println("Service returned response code " + responseCode);
	} catch (ProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    
}
}
