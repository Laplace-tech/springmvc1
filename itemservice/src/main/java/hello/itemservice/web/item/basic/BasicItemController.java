package hello.itemservice.web.item.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/basic/items")
@Controller
public class BasicItemController {

	private final ItemRepository itemRepository;
	
	@GetMapping("")
	public String items(Model model) {
		List<Item> itemList = this.itemRepository.findAll();
		model.addAttribute("itemList", itemList);
		return "basic/items";
	}
	
	@GetMapping("/{itemId}")
	public String item(@PathVariable("itemId") Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}
	
	@GetMapping("/add") // 빈 객체 전송
	public String addForm(Item item) {
		return "basic/addForm";
	}
	
	/*
	 * PRG Post/Redirect/Get
	 */
	@PostMapping("/add")
	public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
	    Item savedItem = itemRepository.save(item);
	    redirectAttributes.addAttribute("itemId", savedItem.getId()); // pathVariable 바인딩: {itemId}
	    redirectAttributes.addAttribute("status", true);
	    return "redirect:/basic/items/{itemId}";
	}
	
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable("itemId") Long itemId, Model model) {
		Item item = this.itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/editForm";
	}
	
	/*
	 * PRG Post/Redirect/Get
	 */
	@PostMapping("/{itemId}/edit")
	public String editForm(@PathVariable("itemId") Long itemId, Item item) {
		this.itemRepository.update(itemId, item);
		
		// 컨트롤러에 매핑된 @PathVariable 의 값("itemId")은 redirect 에도 사용 할 수 있다.
		return "redirect:/basic/items/{itemId}";
	}
	
	/*
	 * @PostConstruct
	 * 
	 * **@PostConstuct** 는 스프링 컨텍스트가 초기화된 후(즉, 빈이 생성되고 모든 의존관계가 주입된 후)
	 * 호출되는 메서드에 사용하는 애너테이션이다.
	 */
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("이아손의 상자", Integer.MAX_VALUE, Integer.MIN_VALUE));
		itemRepository.save(new Item("우담화", 300, 1));
	}
}
