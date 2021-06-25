import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_D {

	public static void main(String[] args) {
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query={주소}";
		String client_id = "mji6g1yo7d";
		String client_secret="zZ0Ii1oNJMPymgkNbCHQb0iOh7bywzieHUeFERLO";
		
		// System.in은 바이트 스트림이다. 따라서 문자스트림과 바이트스트림은 바로 연결이 어렵기 
		// 때문에 중간에 InputStreamReader라는 브릿지스트림으로 연결해주었다.
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("주소를 입력하세요 : ");
			// readLine() 으로 
			String address = io.readLine();
			// address를 encode()에 주고 UTF-8로 인코딩시킨다. 
			String addr = URLEncoder.encode(address, "UTF-8"); // 입력 공백도 문자처리 해줘야함.
			
			String reqUrl = apiURL+addr;
			// URL 객체에 URL 정보를 넣는다 (유효한 주소면 정상작동, 아니면 에러발생)
			URL url = new URL(reqUrl);
			// URL 객체의 openConnection()으로 url과 연결서버의 커넥션이 생김
			HttpURLConnection con =(HttpURLConnection) url.openConnection();
			// URL 요청을 GET방식으로 받아온다.
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			BufferedReader br;
			
			int responseCode=con.getResponseCode(); // 200이면 정상적 연결
			if(responseCode == 200) {
				// 네이버 지도 API와 연결된 con을 통해서 IO로 읽어들여야 하기 때문에 getInputStream을 만든다.
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  // UTP-8 로 인코딩
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			// 서버에서 내려보내주는 데이터를 line에 저장
			// readLine으로 한줄씩 읽어서 String변수 line에 저장
			// response 안에 모든 JSON형태가 문자열로 저장됨
			StringBuffer response = new StringBuffer();
			while((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			
			// 받은 JSON문자열을 JSONTokener을 통해 JSON객체로 메모리에 올린다.
			JSONTokener tokener = new JSONTokener(response.toString());
			// tokener를 JSONObject객체로 만든다. 
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(3));
			
			// addresses에 있는 값들을 배열로 읽어들이면 된다.
			JSONArray arr = object.getJSONArray("addresses");
			for(int i = 0; i<arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				System.out.println("address:" + temp.get("roadAddress"));
				System.out.println("jibunAddress:" + temp.get("jibunAddress"));
				System.out.println("경도:" + temp.get("x"));
				System.out.println("위도:" + temp.get("y"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
