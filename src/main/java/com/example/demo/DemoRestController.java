package com.example.demo;

import com.example.demo.domain.PersonEntity;
import com.example.demo.domain.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoRestController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    //StringRedisTemplate stringRedisTemplate;

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/hello")
    public String justSayHello() {
        return "Hello world - 51%";
    }

    @GetMapping("/redis/set-msg/{u}")
    public String setMsgInRedis(@PathVariable("u") String u) {
        redisTemplate.opsForHash().put("TEST", "u", u);

        String v = (String) redisTemplate.opsForHash().get("TEST", "u");
        return "successfully added value : " + v;
    }

    @GetMapping("/redis/get-msg")
    public String getMsgFromRedis() {
        String v = (String) redisTemplate.opsForHash().get("TEST", "u");

        return "last added value : " + v;
    }


    @GetMapping("/db/save/{name}")
    public PersonEntity save(@PathVariable("name") String name) {
        PersonEntity person = new PersonEntity();
        person.setName(name);

        personRepository.save(person);
        return person;
    }

    @GetMapping("/db/all")
    public List<PersonEntity> listAll() {
        return personRepository.findAll();
    }

}
