package DateTime;

import java.sql.Date;

public class format {
	//2019��10��31�� �������� ����
	
	public format(){}
	
	public String changeDateFormat(String d) {
		String date = d;
		
		date = date.replace("��", "-");
		date = date.replace("��", "-");
		date = date.replace("��", " ");
		date = date.replace("��", ":");
		date = date.replace("��", ":00");
		
		return date;
	}
	
	public String changeDateFormat2(String d) {
		String date1 = d;
		
		String date = date1.substring(0,16) + " ~ " + date1.substring(32,37); //��Ī ��ŸƮ ����� �ð� �� + ��Ī ���� �ð���
		return date;
	}
	
	public String changeDateFormat3(String d) {
		String date1 = "20"+d;
		
		String date = date1.substring(0,16) + " ~ " + date1.substring(32,37); //��Ī ��ŸƮ ����� �ð� �� + ��Ī ���� �ð���
		return date;
	}
}