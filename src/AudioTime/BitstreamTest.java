package AudioTime;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

public class BitstreamTest {

	public static void main(String[] args) {
		URL url;
		URLConnection urlcon = null;
		try {
			String audioTime=null;
			url = new URL("http://audio.xmcdn.com/group22/M02/64/21/wKgJLlg2v0OQudtNAH8v4jpV2rY409.m4a");
			urlcon = url.openConnection();
			int b = urlcon.getContentLength();// 得到音乐文件的总长度
			BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream());
			Bitstream bt = new Bitstream(bis);
			Header h = bt.readFrame();
			long time = (long) h.total_ms(b);
			DecimalFormat df = new DecimalFormat("00");
			if (time < 3600000) {
				audioTime = df.format(time / 60000 - time / 3600000 * 60) + ":"
						+ df.format(time / 1000 - time / 60000 * 60);
			} else {
				audioTime = df.format(time / 3600000) + ":" + df.format(time / 60000 - time / 3600000 * 60) + ":"
						+ df.format(time / 1000 - time / 60000 * 60);
			}
			System.out.println("音频文件时长" + audioTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
