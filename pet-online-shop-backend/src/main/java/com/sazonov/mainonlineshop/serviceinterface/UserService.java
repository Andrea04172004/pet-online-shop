package com.sazonov.mainonlineshop.serviceinterface;

import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formdto.LoginFormDto;
import com.sazonov.mainonlineshop.security.JWT.JwtResponse;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import java.util.List;


public interface UserService {

    JwtResponse signIn(LoginFormDto loginFormDto);
    UserDto saveUser(UserDto userDto);
    UserDto findUserByEmail(String email);
    UserDto findById(int id);
    UserDto updateUser(UserDto userDto);
    List<UserDto> getAllUsers();
    void deleteUser(UserDto userDto);

    UserEntity findOneByEmail(String email);

    List<UserDto> getUsersEmails(String email);



    int checkPhoneUnique(String phone);

















}
