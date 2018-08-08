package testGithub1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class duilieshichangceshi {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		List<Integer> list3 = new ArrayList<Integer>();
		List<Integer> list4 = new ArrayList<Integer>();
		List<Integer> list5 = new ArrayList<Integer>();
		List<Integer> list6 = new ArrayList<Integer>();
		List<Integer> list7 = new ArrayList<Integer>();
		List<Integer> list8 = new ArrayList<Integer>();
		List<Integer> list9 = new ArrayList<Integer>();
		List<Integer> list0 = new ArrayList<Integer>();
			List<Integer> list = new ArrayList<Integer>();
			for(int j=0;j<10000;j++) {
				list.add(j);
			}
			list0.addAll(list);
			list1.addAll(list);
			list2.addAll(list);
			list3.addAll(list);
			list4.addAll(list);
			list5.addAll(list);
			list6.addAll(list);
			list7.addAll(list);
			list8.addAll(list);
			list9.addAll(list);
			
			long s1=System.currentTimeMillis();
			for(int i=0;i<list.size();i++) {
				if(list0.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list1.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list2.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list3.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list4.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list5.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list6.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list7.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list8.get(i)==9999) {
					break;
				}
			}
			for(int i=0;i<list.size();i++) {
				if(list9.get(i)==999) {
					break;
				}
			}
			long s2=System.currentTimeMillis();
			System.out.println(s1);
			System.out.println(s2-s1);
			
			int fr=55;
			int pri=12312;
			System.out.println((fr*pri)/1000);
			
			String[] staffs = new String[]{"Tom", "Tom", "Jane"};
			List<String> staffsList = Arrays.asList(staffs);

			Set<String> result = new HashSet(staffsList);
//			String str = StringUtils.join(result.toArray(), ",");
			System.out.println(result);
	}
}
