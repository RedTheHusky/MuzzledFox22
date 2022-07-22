package web.spring.mongodb.inter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import web.spring.mongodb.entity.GroceryItem;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<GroceryItem, String> {

    @Query("{name:'?0'}")
    GroceryItem findItemByName(String name);
    @Query("{id:'?0'}")
    GroceryItem findItemById(String id);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<GroceryItem> findAll(String category);

    public long count();

}