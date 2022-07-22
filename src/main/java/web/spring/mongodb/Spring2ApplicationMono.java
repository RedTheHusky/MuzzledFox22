package web.spring.mongodb;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.spring.mongodb.entity.GroceryItem;
import web.spring.mongodb.inter.ItemRepository;

import java.util.*;

//https://www.mongodb.com/compatibility/spring-boot

//https://stackoverflow.com/questions/65242002/how-to-resolve-tls-version-issue-with-mongodb-cluster-in-java-spring-boot-web-pr
//19bb7db3-ebdf-4198-8f15-a8a1b61f99d4
@SpringBootApplication
@RestController
@EnableMongoRepositories
public class Spring2ApplicationMono {
    Logger logger = Logger.getLogger(getClass());
    public Spring2ApplicationMono(){

    }
    @Autowired
    ItemRepository groceryItemRepo;

    @RequestMapping(value = "/grocery/check",  method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> checkConnection(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[createGroceryItems]";
        logger.info(fName+".init");
        try{
            Map <String, Object>map=new HashMap();

            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/grocery/create",  method = RequestMethod.POST)
    ResponseEntity<Map<String, Object>> createGroceryItems(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[createGroceryItems]";
        logger.info(fName+".init");
        try{
            Map <String, Object>map=new HashMap();
            logger.warn(fName+"options="+ options.toString());
            logger.warn(fName+"start creating");
            groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
            groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
            groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
            groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
            groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
            logger.warn(fName+"ended creating");
            map.put("message","done");
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Map <String, Object> getItemDetails(GroceryItem item) {
        Map <String, Object>map=new HashMap();
        map.put("id",item.getId());
        map.put("name",item.getName());
        map.put("category",item.getCategory());
        map.put("quantity",item.getQuantity());
        return map;
    }
    @RequestMapping(value = "/grocery/item",  method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> getGroceryItemByName(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[getGroceryItemByName]";
        logger.info(fName+".init");
        try{
            Map <String, Object>map=new HashMap();
            if(options==null) {
                logger.warn(fName+"no option provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","ino option provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            logger.warn(fName+"options="+ options.toString());
            if(!options.containsKey("name")){
                logger.warn(fName+"no option[name] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","no option[name] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }

            else if(!(options.get("name") instanceof String)){
                logger.warn(fName+"ivalid type of  option[name]");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","ivalid type of  option[name]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            GroceryItem item = groceryItemRepo.findItemByName((String) options.get("name"));
            if(item==null){
                logger.warn(fName+"groceries[name] does not exists");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","groceries[name] does not exists"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            map=getItemDetails(item);
            if(map==null||map.isEmpty()){
                logger.warn(fName+"groceries[name] failed to render");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","groceries[name] failed to render"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));

            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/grocery/items",  method = RequestMethod.GET)
    ResponseEntity<Object> getGroceryItems(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[getGroceryItems]";
        logger.info(fName+".init");
        try{
            List<Object>list=new ArrayList<>();List<GroceryItem> items = null;
            if(options!=null) {
                logger.warn(fName+"options="+ options.toString());
                if(!options.containsKey("category")){
                    logger.warn(fName+"no option[name] provided");
                    items=groceryItemRepo.findAll();
                }else
                if(!(options.get("category") instanceof String)){
                    logger.warn(fName+"ivalid type of  option[name]");
                    return new ResponseEntity<>(
                            (Map<String, Object>) new HashMap<String, Object>().put("message","ivalid type of  option[name]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
                }else{
                    items = groceryItemRepo.findAll((String) options.get("category"));
                }
            }else{
                items=groceryItemRepo.findAll();
            }
            if(items==null||items.isEmpty()){
                logger.warn(fName+"could not generate grocery items list by category");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","could not generate grocery items list by category"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            items.forEach(item->list.add(getItemDetails(item)));
            return new ResponseEntity<>(
                    list, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));

            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    ResponseEntity<Object> getGroceryItemsByCategory(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[getGroceryItemsByCategory]";
        logger.info(fName+".init");
        try{
            Map <String, Object>map=new HashMap();List<Object>list=new ArrayList<>();
            if(options==null) {
                logger.warn(fName+"no option provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","ino option provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            logger.warn(fName+"options="+ options.toString());
            if(!options.containsKey("category")){
                logger.warn(fName+"no option[name] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","no option[name] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(!(options.get("category") instanceof String)){
                logger.warn(fName+"ivalid type of  option[name]");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","ivalid type of  option[name]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            List<GroceryItem> items = groceryItemRepo.findAll((String) options.get("category"));
            if(items==null||items.isEmpty()){
                logger.warn(fName+"could not generate grocery items list by category");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","could not generate grocery items list by category"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            items.forEach(item->list.add(getItemDetails(item)));
            return new ResponseEntity<>(
                    list, new HttpHeaders(), HttpStatus.OK);


        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));

            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/grocery/items",  method = RequestMethod.PATCH)
    ResponseEntity<Object> updateCategoryName(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[updateCategoryName]";
        logger.info(fName+".init");
        try{
            Map <String, Object>map=new HashMap();
            if(options==null) {
                logger.warn(fName+"no option provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","ino option provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            logger.warn(fName+"options="+ options.toString());
            if(!options.containsKey("category_old")&&!options.containsKey("category_new")){
                logger.warn(fName+"no option[category_old, category_new] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","no option[category_old, category_new] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(!options.containsKey("category_old")){
                logger.warn(fName+"no option[category_old] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","no option[category_old] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(!options.containsKey("category_new")){
                logger.warn(fName+"no option[category_new] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","no option[category_new] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(!(options.get("category_old") instanceof String)&&!(options.get("category_new") instanceof String)){
                logger.warn(fName+"invalid type of  option[category_old, category_new]");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","invalid type of  option[category_old, category_new]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(!(options.get("category_old") instanceof String)){
                logger.warn(fName+"invalid type of  option[category_old]");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","invalid type of  option[category_old]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(!(options.get("category_new") instanceof String)){
                logger.warn(fName+"invalid type of  option[category_new]");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","invalid type of  option[category_new]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            String newcategory=(String) options.get("category_old"),oldcategory= (String) options.get("category_old");
            List<GroceryItem> items = groceryItemRepo.findAll(oldcategory);
            if(items==null||items.isEmpty()){
                logger.warn(fName+"could not generate grocery items list by category");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","could not generate grocery items list by category"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }

            items.forEach(item->{item.setCategory(newcategory);});
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message","successfully updated category from '"+oldcategory+"' to '"+newcategory+"'"), new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));

            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/grocery/item",  method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteGroceryItem(@RequestBody(required=false)Map<String,Object>options) {
        String fName="[deleteGroceryItem]";
        logger.info(fName+".init");
        try{
            Map <String, Object>map=new HashMap();
            if(options==null) {
                logger.warn(fName+"no option provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","ino option provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            logger.warn(fName+"options="+ options.toString());
            if(!options.containsKey("id")&&!options.containsKey("name")){
                logger.warn(fName+"no option[id/name] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","no option[id/name] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(options.containsKey("id")&&options.containsKey("name")){
                logger.warn(fName+"cant provide both option[id, name] provided");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","cant provide both option[id, name] provided"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            GroceryItem item = null;
            String id,name;
            if(options.containsKey("id")){
                if(!(options.get("id") instanceof String)){
                    logger.warn(fName+"invalid type of  option[id]");
                    return new ResponseEntity<>(
                            (Map<String, Object>) new HashMap<String, Object>().put("message","invalid type of  option[id]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
                }
                id= (String) options.get("id");
                item=groceryItemRepo.findItemById(id);
            }
            if(options.containsKey("name")){
                if(!(options.get("name") instanceof String)){
                    logger.warn(fName+"invalid type of  option[name]");
                    return new ResponseEntity<>(
                            (Map<String, Object>) new HashMap<String, Object>().put("message","invalid type of  option[name]"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
                }
                name= (String) options.get("name");
                item=groceryItemRepo.findItemByName(name);
            }
            if(item==null){
                logger.warn(fName+"could not generate grocery items list by category");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","could not get item"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            groceryItemRepo.deleteById(item.getId());
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message","successfully deleted"), new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));

            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
