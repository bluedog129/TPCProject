import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {

	public static void main(String[] args) {
		String src = "Info.json";
		// IO -> Stream(스트림)
		// Stream : 개별 바이트나 문자열인 데이터의 원천 파일을 읽거나 쓸 때, 네트워크 소켓을 거쳐 통신할 때 쓰이는 추상적인 개념
		// InputStream : 데이터가 들어오는 통로(파일스트림, 네트워크 스트림)의 역할에 관해 규정하고 있는 추상 클래스
		// getResourceAsStream() : 현재 클래스가 만들어진 곳에서 파일을 얻어오는 메소드
		
		InputStream is = Project01_C.class.getResourceAsStream(src);
		if(is == null) {
			throw new NullPointerException("Cannot find resource");
		}
		// JSONTokener : 문자열을 JSON객체 형태로 메모리에 올리는 클래스
		JSONTokener tokener = new JSONTokener(is);
		// JSONTokener에 들어있는 JSON데이터를 다루기 위해서 JSONObject 타입으로 다시 바꾸어준다.
		JSONObject object = new JSONObject(tokener);
		// key(students) 값을 JSONArray로 가져와서 students로 받는다.
		JSONArray students = object.getJSONArray("students");
		
		// 반복문을 이용해서 하나씩 받는다.
		for(int i=0; i<students.length(); i++) {
			JSONObject	student = (JSONObject)students.get(i);
			System.out.print(student.get("name")+"\t");
			System.out.print(student.get("address")+"\t");
			System.out.println(student.get("phone"));
		}
	}
}
