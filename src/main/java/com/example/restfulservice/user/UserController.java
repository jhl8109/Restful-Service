package com.example.restfulservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//컨트롤러안에서 서비스를 사용할 수 있음.
// 서비스 에는 실제 동작하는 내용들을 담고 있음.
@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;

    }
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }
    //Get /users/1 or /users/10 -> String 형태로 변수가 전달됨, 타입을 지정함으로써 자동으러 컨버팅 됨
    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);
        
        if (user == null) {
            // 오류 발생시킴
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //Hateoas
        //"all-users", SERVER_PATH + "/users"
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        model.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(model);
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.save(user);
        //200 대신 201번 http status를 보냄
        //header의 loaction 에 http://localhost:8080/users/{id} 으로 나타남.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
