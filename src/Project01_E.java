import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Project01_E {
	
	public static void map_service(String point_x, String point_y, String address) {
		String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		try {
		String pos=URLEncoder.encode(point_x + " " + point_y, "UTF-8");
		String url = URL_STATICMAP;
		url += "center=" + point_x + "," + point_y;
		url += "&level=16&w=700&h=500";
		url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8");
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection)u.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "mji6g1yo7d");
		con.setRequestProperty("X-NCP-APIGW-API-KEY", "zZ0Ii1oNJMPymgkNbCHQb0iOh7bywzieHUeFERLO");
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if(responseCode==200) { // 정상 호출
			InputStream is = con.getInputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			// 랜덤한 이름으로 파일 생성
			String tempname = Long.valueOf(new Date().getTime()).toString();
			File f = new File(tempname + ".jpg"); 
			f.createNewFile();
			OutputStream outputStream = new FileOutputStream(f);
			while ((read = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			is.close();
		} else { // 에러 발생
		br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
		response.append(inputLine);
		}
		br.close();
		System.out.println(response.toString());
		}
		} catch (Exception e) {
		System.out.println(e);
		}
		
	}
	

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
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  // UTP-8 로 인코딩
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer(); //JSON
			
			String x =""; String y = ""; String z="";
			while((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			
			
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(3));
			
			JSONArray arr = object.getJSONArray("addresses");
			for(int i = 0; i<arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				System.out.println("address:" + temp.get("roadAddress"));
				System.out.println("jibunAddress:" + temp.get("jibunAddress"));
				System.out.println("경도:" + temp.get("x"));
				System.out.println("위도:" + temp.get("y"));
				x = (String) temp.getString("x");
				y = (String) temp.getString("y");
				z = (String) temp.get("roadAddress");
			}
			map_service(x, y, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
