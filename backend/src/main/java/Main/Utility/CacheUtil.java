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
    public void addOneItemToList(String CacheName, String key, T value) {
        Cache cache = cacheManager.getCache(CacheName);

        if(cache == null){return;}

        //List.class --> cast to List<T>
        @SuppressWarnings("unchecked")  // to suppress unchecked cast warning
        List<T> cachedList =  cache.get(key, List.class);

        if(cachedList == null) { return; }

        cachedList.add(value);
        cache.put(key, cachedList);

    }

    // update an item in a cached list, if not cached, do nothing
    public void updateOneItemToList(String CacheName, String key, T value) {
        int id = value.getId();
        Cache cache = cacheManager.getCache(CacheName);

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

    public void deleteOneItemFromList(String CacheName, String key, T value) {
        int id = value.getId();
        Cache cache = cacheManager.getCache(CacheName);

        if(cache == null) {return;}

        //List.class --> cast to List<T>
        @SuppressWarnings("unchecked")  // to suppress unchecked cast warning
        List<T> cachedList =  cache.get(key, List.class);
        if(cachedList == null) {return;}

        // find By id and remove
        cachedList.removeIf(item -> item.getId() == id);
        cache.put(key, cachedList);
    }

}
