package Main.Utility;

import Main.Common.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class CacheUtil<T extends BaseEntity>{
    private final CacheManager cacheManager;

    // add an item to a cached list, if not cached, do nothing
    public void addOneItemToList(String cacheName, String key, T value) {
        if(cacheName == null || key == null || value ==null) {return;}

        Cache cache = cacheManager.getCache(cacheName);

        if(cache == null){return;}

        //List.class --> cast to List<T>
        @SuppressWarnings("unchecked")  // to suppress unchecked cast warning
        List<T> cachedList =  (List<T>)cache.get(key, List.class);

        if(cachedList == null) { return; }

        cachedList.add(value);
        cache.put(key, cachedList);

    }

    // update an item in a cached list, if not cached, do nothing
    public void updateOneItemToList(String cacheName, String key, T value) {
        if(cacheName == null || key == null || value ==null) {return;}

        int id = value.getId();
        Cache cache = cacheManager.getCache(cacheName);

        if(cache == null) {return;}

        //List.class --> cast to List<T>
        @SuppressWarnings("unchecked")  // to suppress unchecked cast warning
        List<T> cachedList =  cache.get(key, List.class);

        if(cachedList == null) {return;}

        // find By id and update
        int index = IntStream.range(0, cachedList.size())
                .filter(i -> cachedList.get(i).getId() == id)
                .findFirst()
                .orElse(-1);

        if(index == -1) {return;}

        cachedList.set(index, value);
        cache.put(key, cachedList);


    }

    //
    public void deleteItem(String cacheName, String key, T value){
        if(cacheName == null || key == null || value ==null) {return;}
        Cache cache = cacheManager.getCache(cacheName);
    }
    // delete an item from a cached list, if not cached, do nothing
    public void deleteOneItemFromList(String cacheName, String key, T value) {
        if(cacheName == null || key == null || value ==null) {return;}

        int id = value.getId();
        Cache cache = cacheManager.getCache(cacheName);

        if(cache == null) {return;}

        //List.class --> cast to List<T>
        @SuppressWarnings("unchecked")  // to suppress unchecked cast warning
        List<T> cachedList =  cache.get(key, List.class);
        if(cachedList == null) {return;}

        // find By id and remove
        boolean removeSuccess = cachedList.removeIf(item -> item.getId() == id);
        if(removeSuccess) cache.put(key, cachedList);
    }

    public void moveCacheFromToOtherList(String cacheName, String oldKey,String newKey, T value){
        if(cacheName == null || oldKey == null || newKey == null || value ==null) {return;}

        deleteOneItemFromList(cacheName,oldKey,value);
        addOneItemToList(cacheName,newKey,value);
    }

}
