package AudioTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ffmpeg {
	public static int getVideoTime(String video_path, String ffmpeg_path) {
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(video_path);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			final Process p = builder.start();
			
			//从输入流中读取视频信息
	        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	        StringBuffer sb = new StringBuffer();
	        String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
	        br.close();
	        
	        //从视频信息中解析时长
	        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
	        Pattern pattern = Pattern.compile(regexDuration);
	        Matcher m = pattern.matcher(sb.toString());
	        if (m.find()) {
	        	String time=m.group(1);
	        	String audioTime=time.substring(time.indexOf(":"), time.indexOf("."));
				System.out.println("音频文件时长" + audioTime);
				System.out.println("音频文件时长" + m.group(1));
	        	return 0;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//格式:"00:00:10.68"
    private static int getTimelen(String timelen){
        int min=0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min+=Integer.valueOf(strs[0])*60*60;//秒
        }
        if(strs[1].compareTo("0")>0){
            min+=Integer.valueOf(strs[1])*60;
        }
        if(strs[2].compareTo("0")>0){
            min+=Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
//		getVideoTime("http://zdmedia.lifeweek.com.cn/bg/20170329/1490757610632cvgen.mp3", 
//				"F:\\Googledown\\ffmpeg-20180813-551a029-win64-static\\bin\\ffmpeg.exe");
		String ffmpegPath="‪F:\\Googledown\\ffmpeg-20180813-551a029-win64-static\\bin\\ffmpeg.exe";
		List<String> commands = new java.util.ArrayList<String>();
		commands.add("F:\\Googledown\\ffmpeg-20180813-551a029-win64-static\\bin\\ffmpeg.exe");
		commands.add("-i");
		commands.add("http://zdmedia.lifeweek.com.cn/bg/20170623/1498194888970pmkex.mp3");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			final Process p = builder.start();
			
			//从输入流中读取文件信息
	        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	        StringBuffer sb = new StringBuffer();
	        String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
	        br.close();
	        
	        //从视频信息中解析时长
	        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
	        Pattern pattern = Pattern.compile(regexDuration);
	        Matcher m = pattern.matcher(sb.toString());
	        if (m.find()) {
	        	String time=m.group(1);
	        	String kps=m.group(3);
	        	int bitrate=Integer.valueOf(kps);
	        	String min=time.substring(time.indexOf(":")+1, time.lastIndexOf(":"));
	        	String mis=time.substring(time.lastIndexOf(":")+1);
	        	double fileSize=((bitrate/8)*(Integer.valueOf(min)*60+Double.valueOf(mis)))/1024;
	        	String audioTime=time.substring(time.indexOf(":")+1, time.indexOf("."));
	        	System.out.println(audioTime);
	        	System.out.println(fileSize);
	        	
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
}
