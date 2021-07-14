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
		if(responseCode==200) { // ���� ȣ��
			InputStream is = con.getInputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			// ������ �̸����� ���� ����
			String tempname = Long.valueOf(new Date().getTime()).toString();
			File f = new File(tempname + ".jpg"); 
			f.createNewFile();
			OutputStream outputStream = new FileOutputStream(f);
			while ((read = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			is.close();
		} else { // ���� �߻�
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
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  // UTP-8 �� ���ڵ�
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
				System.out.println("�浵:" + temp.get("x"));
				System.out.println("����:" + temp.get("y"));
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
