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
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query={�ּ�}";
		String client_id = "mji6g1yo7d";
		String client_secret="zZ0Ii1oNJMPymgkNbCHQb0iOh7bywzieHUeFERLO";
		
		// System.in�� ����Ʈ ��Ʈ���̴�. ���� ���ڽ�Ʈ���� ����Ʈ��Ʈ���� �ٷ� ������ ��Ʊ� 
		// ������ �߰��� InputStreamReader��� �긴����Ʈ������ �������־���.
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("�ּҸ� �Է��ϼ��� : ");
			String address = io.readLine();
			String addr = URLEncoder.encode(address, "UTF-8");
			String reqUrl = apiURL+addr;
			
			URL url = new URL(reqUrl);
			HttpURLConnection con =(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			BufferedReader br;
			
			int responseCode=con.getResponseCode(); // 200
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer();
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
				System.out.println("�浵:" + temp.get("x"));
				System.out.println("����:" + temp.get("y"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
