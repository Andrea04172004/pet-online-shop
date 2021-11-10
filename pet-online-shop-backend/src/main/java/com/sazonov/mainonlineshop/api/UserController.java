package com.sazonov.mainonlineshop.api;

import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formdto.LoginFormDto;
import com.sazonov.mainonlineshop.dto.formdto.UserSingUpDtoRequest;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.security.JWT.JwtResponse;
import com.sazonov.mainonlineshop.serviceinterface.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private BusinessMapper businessMapper;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginFormDto loginForm) {
        try {
            return ResponseEntity.ok(userService.signIn(loginForm));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping ("/logout")
    public ResponseEntity logout (){
        System.out.println("Join in logout");
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> save(@RequestBody UserSingUpDtoRequest userSingUpDto) {
        System.out.println("userSingUpDto.toString()----> " + userSingUpDto.toString());
        UserDto userDto = businessMapper.getUserDtoForSignUp(userSingUpDto);
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @GetMapping("/find-one-by/{email}")
    public ResponseEntity<UserDto> findOneByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/update-user-put")
    public ResponseEntity<UserDto> updateUserPut(@RequestBody UserDto userDto, Principal principal) {
        System.out.println("userDto----> " + userDto.toString());
        UserDto moderator = userService.findUserByEmail(principal.getName());
        if (moderator.getRole().equals("ROLE_ADMIN") || moderator.getEmail().equals(userDto.getEmail()))
            return ResponseEntity.ok(userService.updateUser(userDto));
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable("id") int id) {
        UserDto userDto = userService.findById(id);
        userService.deleteUser(userDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-if-email-exist")
    public ResponseEntity<List<UserDto>> getUsersEmailContains(@PathParam("email") String email) {
        System.out.println("wsp");
        return ResponseEntity.ok(userService.getUsersEmails(email));
    }

    @GetMapping("/check-if-phone-exist")
    public ResponseEntity<Integer> checkPhoneUnique(@PathParam("phone") String phone) {
        System.out.println(phone);
        System.out.println("wsp");

        int count = userService.checkPhoneUnique(phone);
        System.out.println("count---> " + count);

        return ResponseEntity.ok(count);
    }

}
