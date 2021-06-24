import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {

	public static void main(String[] args) {
		String src = "Info.json";
		// IO -> Stream(��Ʈ��)
		// Stream : ���� ����Ʈ�� ���ڿ��� �������� ��õ ������ �аų� �� ��, ��Ʈ��ũ ������ ���� ����� �� ���̴� �߻����� ����
		// InputStream : �����Ͱ� ������ ���(���Ͻ�Ʈ��, ��Ʈ��ũ ��Ʈ��)�� ���ҿ� ���� �����ϰ� �ִ� �߻� Ŭ����
		// getResourceAsStream() : ���� Ŭ������ ������� ������ ������ ������ �޼ҵ�
		
		InputStream is = Project01_C.class.getResourceAsStream(src);
		if(is == null) {
			throw new NullPointerException("Cannot find resource");
		}
		// JSONTokener : ���ڿ��� JSON��ü ���·� �޸𸮿� �ø��� Ŭ����
		JSONTokener tokener = new JSONTokener(is);
		// JSONTokener�� ����ִ� JSON�����͸� �ٷ�� ���ؼ� JSONObject Ÿ������ �ٽ� �ٲپ��ش�.
		JSONObject object = new JSONObject(tokener);
		// key(students) ���� JSONArray�� �����ͼ� students�� �޴´�.
		JSONArray students = object.getJSONArray("students");
		
		// �ݺ����� �̿��ؼ� �ϳ��� �޴´�.
		for(int i=0; i<students.length(); i++) {
			JSONObject	student = (JSONObject)students.get(i);
			System.out.print(student.get("name")+"\t");
			System.out.print(student.get("address")+"\t");
			System.out.println(student.get("phone"));
		}
	}
}
