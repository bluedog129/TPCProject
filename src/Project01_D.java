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
			// readLine() ���� 
			String address = io.readLine();
			// address�� encode()�� �ְ� UTF-8�� ���ڵ���Ų��. 
			String addr = URLEncoder.encode(address, "UTF-8"); // �Է� ���鵵 ����ó�� �������.
			
			String reqUrl = apiURL+addr;
			// URL ��ü�� URL ������ �ִ´� (��ȿ�� �ּҸ� �����۵�, �ƴϸ� �����߻�)
			URL url = new URL(reqUrl);
			// URL ��ü�� openConnection()���� url�� ���Ἥ���� Ŀ�ؼ��� ����
			HttpURLConnection con =(HttpURLConnection) url.openConnection();
			// URL ��û�� GET������� �޾ƿ´�.
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			BufferedReader br;
			
			int responseCode=con.getResponseCode(); // 200�̸� ������ ����
			if(responseCode == 200) {
				// ���̹� ���� API�� ����� con�� ���ؼ� IO�� �о�鿩�� �ϱ� ������ getInputStream�� �����.
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  // UTP-8 �� ���ڵ�
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			// �������� ���������ִ� �����͸� line�� ����
			// readLine���� ���پ� �о String���� line�� ����
			// response �ȿ� ��� JSON���°� ���ڿ��� �����
			StringBuffer response = new StringBuffer();
			while((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			
			// ���� JSON���ڿ��� JSONTokener�� ���� JSON��ü�� �޸𸮿� �ø���.
			JSONTokener tokener = new JSONTokener(response.toString());
			// tokener�� JSONObject��ü�� �����. 
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(3));
			
			// addresses�� �ִ� ������ �迭�� �о���̸� �ȴ�.
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
