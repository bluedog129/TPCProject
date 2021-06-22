import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Kr.inflearn.BookDTO;

public class Project01_A {

	public static void main(String[] args) {
		//JSON(BookDTO) -> JSON(String)
		BookDTO dto = new BookDTO("�ڹ�", 21000, "������", 670);
		Gson g = new Gson();
		//toJson() : ��ü�� ���� ������� ���� Ű�� ������ ����� Json���� �ٲپ���
		String json = g.toJson(dto);
		System.out.println(json);  //{"title":"�ڹ�","price":21000,"company":"������","page":670}
		
		
		//JSON(String) -> Object(BookDTO)
		//fromJson(Json, b.class) : Json�� b��ü�� �ٲپ��ش�.
		BookDTO dto1 = g.fromJson(json, BookDTO.class);
		System.out.println(dto1.getTitle()+"\t"+dto1.getPrice()); //BookDTO [title=�ڹ�, price=21000, company=������, page=670]
		
		
		// object(List<BookDTO>) -> JSON(String) : [{  }, {  }...]
		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("�ڹ�1", 21000, "������1", 570));
		lst.add(new BookDTO("�ڹ�2", 31000, "������2", 270));
		lst.add(new BookDTO("�ڹ�3", 11000, "������3", 470));
		
		String lstJson = g.toJson(lst);
		System.out.println(lstJson);
		
		
		// JSON(String) -> object(List<BookDTO>)
		// List�����̸鼭 BookDTOŸ������ �ٲٱ� ���ؼ� TypeToken�̶�� Ÿ���� �˾Ƴ��� ��ü�� �����ϰ� �ٷ� getType�̶�� �ż���� �����͸� ��´�.
		List<BookDTO> lst1= g.fromJson(lstJson, new TypeToken<List<BookDTO>>() {}.getType());
		for(BookDTO vo : lst1) {
			System.out.println(vo);
		}
	}
}
