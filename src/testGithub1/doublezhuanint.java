package testGithub1;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class doublezhuanint {
	public static void main(String[] args) {

        double d=(double)2/3;
        DecimalFormat df=new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        String dd = df.format((double)1/100);
        System.out.println(dd);
	}
}
