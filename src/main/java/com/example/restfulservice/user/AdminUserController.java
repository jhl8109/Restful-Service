package com.example.restfulservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

//컨트롤러안에서 서비스를 사용할 수 있음.
// 서비스 에는 실제 동작하는 내용들을 담고 있음.
@RestController
@RequestMapping("admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;

    }
    @GetMapping("/v1/users")
    public MappingJacksonValue retrieveAllUsers(){
        var users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn","password");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }


    //필요한 json 값만 필터를 통해 불러올 수 있도록 함.

    //@GetMapping("/v1/users/{id}")
    //params를 통한 버전관리
    //@GetMapping(value= "/users/{id}",params="version=1")
    //header를 통한 버전 관리
    //@GetMapping(value= "/users/{id}",headers="X-API-VERSION=1")
    //Mime type을 통한 버전 관리
    @GetMapping(value= "/users/{id}",produces="application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveV1User(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null) {
            // 오류 발생시킴
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn","password");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);


        return mappingJacksonValue;
    }
    //v2라는 버전 관리 시나리오
    //@GetMapping("/v2/users/{id}")
    //params를 통한 버전 관리
    //@GetMapping(value= "/users/{id}",params="version=2")
    //header를 통한 버전 관리
    //@GetMapping(value= "/users/{id}",headers="X-API-VERSION=2")
    //mime type을 통한 버전 관리
    @GetMapping(value= "/users/{id}",produces="application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveV2User(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null) {
            // 오류 발생시킴
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);


        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);


        return mappingJacksonValue;
    }
}
