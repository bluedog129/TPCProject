import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Kr.inflearn.BookDTO;

public class Project01_A {

	public static void main(String[] args) {
		//JSON(BookDTO) -> JSON(String)
		BookDTO dto = new BookDTO("자바", 21000, "에이콘", 670);
		Gson g = new Gson();
		//toJson() : 객체가 가진 멤버변수 들을 키와 값으로 만들고 Json으로 바꾸어줌
		String json = g.toJson(dto);
		System.out.println(json);  //{"title":"자바","price":21000,"company":"에이콘","page":670}
		
		
		//JSON(String) -> Object(BookDTO)
		//fromJson(Json, b.class) : Json을 b객체로 바꾸어준다.
		BookDTO dto1 = g.fromJson(json, BookDTO.class);
		System.out.println(dto1.getTitle()+"\t"+dto1.getPrice()); //BookDTO [title=자바, price=21000, company=에이콘, page=670]
		
		
		// object(List<BookDTO>) -> JSON(String) : [{  }, {  }...]
		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("자바1", 21000, "에이콘1", 570));
		lst.add(new BookDTO("자바2", 31000, "에이콘2", 270));
		lst.add(new BookDTO("자바3", 11000, "에이콘3", 470));
		
		String lstJson = g.toJson(lst);
		System.out.println(lstJson);
		
		
		// JSON(String) -> object(List<BookDTO>)
		// List형식이면서 BookDTO타입으로 바꾸기 위해서 TypeToken이라는 타입을 알아내는 객체를 생성하고 바로 getType이라는 매서드로 데이터를 담는다.
		List<BookDTO> lst1= g.fromJson(lstJson, new TypeToken<List<BookDTO>>() {}.getType());
		for(BookDTO vo : lst1) {
			System.out.println(vo);
		}
	}
}
