package hello.itemservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;

public class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();
	
	@AfterEach
	void afterEach() {
		itemRepository.clearStore();
	}
	
	@Test
	void save() {
		// given
		Item newItem = Item.builder().itemName("Item A").price(10000).quantity(10).build();
		
		// when
		Item savedItem = itemRepository.save(newItem);
		
		// then
		Item findItem = itemRepository.findById(newItem.getId());
		assertThat(findItem).isEqualTo(savedItem);
	}
	
	@Test
	void findAll() {
		Item item1 = Item.builder().itemName("Item A").price(10000).quantity(10).build();
		Item item2 = Item.builder().itemName("Item B").price(20000).quantity(20).build();
		
		itemRepository.save(item1);
		itemRepository.save(item2);
		
		List<Item> itemList = itemRepository.findAll();
		
		assertThat(itemList.size()).isEqualTo(2);
		assertThat(itemList).contains(item1, item2);
	}
	
    @Test
    void updateItem() {
        //given
        Item item = new Item("item1", 10000, 10);
        
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();
        
        //when
        Item updateParam = new Item("item2", 20000, 30);
        itemRepository.update(itemId, updateParam);
        
        Item findItem = itemRepository.findById(itemId);
        
        //then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
	
}
