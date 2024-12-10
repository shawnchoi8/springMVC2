package hello.login.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId; //log in ID
    @NotEmpty
    private String name; // user's name
    @NotEmpty
    private String password;
}
