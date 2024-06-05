package gogo.com.gogo_kan.utils;


import gogo.com.gogo_kan.models.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserDetailsCache {
    HashMap<String, Object> cache = new HashMap<>();
    public void addToCache(User user) {
        cache.put(user.getEmail(), user);
    }

    public User getUserFromCache(String email) {
        if (cache.containsKey(email)) {
            User user = (User) cache.get(email);
            this.removeUserFromCache(email);
            return user;
        } else {
            return null;
        }
    }

    private void removeUserFromCache(String email) {
        cache.remove(email);

    }
}
