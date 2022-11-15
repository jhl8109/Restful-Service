package com.example.restfulservice.user;

//비즈니스 관련 로직은 service에 사용
// 데이터 관련은 Dao 사용

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1,"Kenneth",new Date()));
        users.add(new User(2,"Alice",new Date()));
        users.add(new User(3,"Elina",new Date()));
    }
    public List<User> findAll() {
        return users;
    }
    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User save(User user) {
        //존재하지 않으면 카운트 추가하고 db에 추가함.
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }
}
