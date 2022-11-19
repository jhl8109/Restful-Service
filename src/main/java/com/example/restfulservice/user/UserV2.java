package com.example.restfulservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


//User를 상속받기 때문에 User에 default 생성자가 존재하지 않으면 오류가 발생함.
// 수동으로 구현해도 되고 @NoArgsConstructor를 사용해도 됨
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password"})
@JsonFilter("UserInfoV2")
public class UserV2 extends User{
    private String grade;

}
