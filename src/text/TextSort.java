package text;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TextSort {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	Map<String, Integer> map;
	Map.Entry<String, Integer> top[];

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		TextSort textFile = new TextSort();
		textFile.readFlie();
		textFile.sort();
	}

	public void readFlie() throws FileNotFoundException {
		char temp;
		int value;
		map = new HashMap<String, Integer>();
		StringBuffer stemp = new StringBuffer();
		String key;
		File file = new File("bigtext.txt");
		DataInputStream din = new DataInputStream(new FileInputStream(file));
		try {
			while (din.available() != 0) {
				temp = (char) din.readByte();
				if ((temp <= 'z') && (temp >= 'a')) {
					stemp.append(temp);
				} else if ((temp <= 'Z') && (temp >= 'A')) {
					temp = Character.toLowerCase(temp);
					stemp.append(temp);
				} else {
					if (stemp.length() != 0) {
						key = new String(stemp);
						if (map.containsKey(key)) {
							value = (int) map.get(key);
							map.remove(key);
							value++;
							map.put(key, value);
						} else {
							map.put(key, 1);
						}
						stemp.delete(0, stemp.length());
					} else {

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sort() {
		top = new Map.Entry[50];
		Map.Entry<String, Integer> entry;
		Map.Entry<String, Integer> temp;

		for (int i = 0; i < 50; i++) {
			Iterator<Entry<String, Integer>> iterator = this.map.entrySet().iterator();// �õ�һ�������
			temp = null;
			while (iterator.hasNext()) {// ����
				entry = (Entry<String, Integer>) iterator.next();
				if ((((String) entry.getKey()).length() > 7) && ((int) entry.getValue() > 7)){
					if ((temp == null) || ((int) entry.getValue() > (int) temp.getValue())) {
						int j = 0;
						boolean flag = false;
						for(j = 0; j < i; j++){
							if(entry.equals(top[j]) == true){
								flag = true;
								break;
							}
						}
						if((i == 0) || (flag != true)){
							temp = entry;
						}
					} else if ((int) entry.getValue() == (int) temp.getValue()) {
						int j = 0;
						boolean flag = false;
						for(j = 0; j < i; j++){
							if(entry.equals(top[j]) == true){
								flag = true;
								break;
							}
						}
						if((flag != true) || (i == 0)){
							if (((String) entry.getKey()).compareTo((String) temp.getKey()) < 0) {
								temp = entry;
							}
						}
						
					}
				}
			}
			top[i] = temp;
			System.out.println("word:" + top[i].getKey() + ", count:" + top[i].getValue());
			//map.remove(temp.getKey());
		}
	}

}
