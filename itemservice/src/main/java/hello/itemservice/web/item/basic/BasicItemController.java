package hello.itemservice.web.item.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Controller
/*
 * 이 클래스는 "/basic/items"로 시작하는 URL을 처리하는 컨트롤러임
 * @RequiredArgsConstructor 덕분에 final 로 선언된 itemRepository를 자동 주입받음
 * @Controller로 스프링 MVC가 이 클래스를 웹 요청 처리기로 인식함.
 */
public class BasicItemController {

	private final ItemRepository itemRepository;

	/*
	 * @GetMapping 
	 * 	@RequestMapping(method = RequestMethod.GET)의 축약형
	 * 	@RequestMapping("/basic/items")와 결합되어, GET /basic/items 요청을 처리한다
	 */
	@GetMapping
	public String showItemList(Model model) {
		List<Item> itemList = itemRepository.findAll();
		/*
		 * (Model model) 
		 * 	Spring MVC에서 뷰에 데이터를 전달할 때 사용하는 객체이다. 
		 * 	내부적으로 request.setAttribute(..) 처럼 동작한다.
		 */
		model.addAttribute("itemList", itemList);
		return "basic/items";
	}

	@GetMapping("/{itemId}")
	/*
	 * @GetMapping("/{itemId}")
	 * 	URL에 포함된 itemId 값을 매핑한다.
	 * 	/basic/items/4 이면 itemId=4가 된다.
	 */
	public String showitem(@PathVariable("itemId") Long itemId, Model model) {
		/*
		 * @PathVariable("itemId") Long itemId
		 * 	경로에 포함된 변수 값을 매개변수에 바인딩한다
		 * 	변수명과 파라미터명이 같으면 ("itemId")는 생략 가능
		 */
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}
	
	@GetMapping("/add")
	public String showAddForm(Item item) {
		/*
		 * (Item item) 
		 * 	특별한 의미 없이 빈 Item 객체를 만들어 자동으로 Model 객체에 담는다
		 * 		ㄴ> (Ex : model.addAttribute("item", new Item());
		 * 
		 * 	Spring은 th:object="${item}" 같은 타임리프 바인딩을 위해 이렇게 빈 객체를
		 *  자동으로 넣어주는 기능이 있다.
		 */
		return "basic/addForm";
	}
	
	/*
	 * PRG 패턴 (Post / Redirect / Get)
	 * 	문제 : 새로고침 시 데이터가 중복 저장되는 문제
	 * 		사용자가 POST 요청으로 상품 등록 -> 브라우저가 POST 상태임 
	 * 		-> 새로고침(F5) 하면 다시 POST됨 -> 데이터 중복 저장
	 * 
	 * 	해결 : 사용자 -> POST -> 서버 (상품 저장)
	 * 		  서버 -> Redirect -> Get : /items/1
	 * 		  사용자 -> GET 요청 (상품 상세 조회)
	 */
	
	@PostMapping("/add")
	public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
	    /*
	     * (Item item)
	     * 	요청 바디에 담긴 itemName, price, quantity 값을 자동으로 매핑하여 
	     * 	Item 객체로 바인딩 한다. @NoArgsConstructor -> @Setter
	     * 
	     * (RedirectAttributes redirectAttributes)
	     * 	리다이렉트 시 URL 쿼리 파라미터를 편리하게 설정할 수 있게 도와준다
	     */
		Item savedItem = itemRepository.save(item);
		
		// 저장된 상품의 ID를 URL 경로 변수로 지정
	    redirectAttributes.addAttribute("itemId", savedItem.getId());
	    // 상태(status)를 쿼리 파라미터로 설정 
	    redirectAttributes.addAttribute("status", true);
	    
	    // 리다이렉트 시켜서 새로고침을 해도 중복 저장이 되지 않도록 함.
	    return "redirect:/basic/items/{itemId}";
	}
	
	@GetMapping("/{itemId}/edit")
	public String showEditForm(@PathVariable("itemId") Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/editForm";
	}
    
	@PostMapping("/{itemId}/edit")
	public String editForm(@PathVariable("itemId") Long itemId, Item item) {
		/*
		 *  URL에서 @PathVariable로 itemId를 추출하고, 
		 *  폼에서 수정된 데이터는 (Item item)에 바인딩 됨.
		 */
		this.itemRepository.update(itemId, item);
		return "redirect:/basic/items/{itemId}";
	}

    /**
	* 테스트용 데이터 추가
	*/
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("이아손의 상자", Integer.MAX_VALUE, Integer.MAX_VALUE));
		itemRepository.save(new Item("우담화", 300, 1));
	}
}
