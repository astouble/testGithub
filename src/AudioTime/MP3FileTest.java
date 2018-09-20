package AudioTime;

public class MP3FileTest {

	public static void main(String[] args) {
		double t = 0;
		try {
			MP3File f = (MP3File) AudioFileIO.read(file);
			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
			t = audioHeader.getPreciseTrackLength();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
