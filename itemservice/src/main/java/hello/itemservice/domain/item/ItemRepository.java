package hello.itemservice.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

	private static final Map<Long, Item> store = new HashMap<>();
	private static long sequence = 0L;
	
	public Item save(Item item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		
		return item;
	}
	
	public Item findById(Long itemId) {
		return store.get(itemId);
	}
	
	public List<Item> findAll() {
		return new ArrayList<>(store.values());
	}
	
	public void update(Long itemId, Item updateItem) {
		Item findItem = findById(itemId);
		findItem.setItemName(updateItem.getItemName());
		findItem.setPrice(updateItem.getPrice());
		findItem.setQuantity(updateItem.getQuantity());
	}
	
	public void clearStore() {
		store.clear();
	}
	
}
