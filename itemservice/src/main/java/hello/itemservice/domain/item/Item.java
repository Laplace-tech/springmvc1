package hello.itemservice.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {

	private Long id;
	private String itemName;
	private Integer price;
	private Integer quantity;
	
	@Builder
	public Item(String itemName, Integer price, Integer quantity) {
		System.out.println("item");
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
