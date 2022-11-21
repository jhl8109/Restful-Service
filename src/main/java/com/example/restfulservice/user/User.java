package com.example.restfulservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties(value={"password"})
//@JsonFilter("UserInfo")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;

    //@JsonIgnore를 통해 무시할 수 있음
    private String password;
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(int i,String name, Date joinDate,String password,String ssn){
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }

}
