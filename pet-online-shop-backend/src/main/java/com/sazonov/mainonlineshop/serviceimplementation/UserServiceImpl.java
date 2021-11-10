package com.sazonov.mainonlineshop.serviceimplementation;

import com.sazonov.mainonlineshop.dto.UserDto;
import com.sazonov.mainonlineshop.dto.formdto.LoginFormDto;
import com.sazonov.mainonlineshop.exception.UserNotFoundException;
import com.sazonov.mainonlineshop.mapper.BusinessMapper;
import com.sazonov.mainonlineshop.repository.AddressRepository;
import com.sazonov.mainonlineshop.repository.CartRepository;
import com.sazonov.mainonlineshop.repository.UserRepository;
import com.sazonov.mainonlineshop.security.JWT.JwtProvider;
import com.sazonov.mainonlineshop.security.JWT.JwtResponse;
import com.sazonov.mainonlineshop.serviceinterface.UserService;
import com.sazonov.mainonlineshop.userentity.AddressEntity;
import com.sazonov.mainonlineshop.userentity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {



    @Resource
    private BusinessMapper businessMapper;

    @Resource
    private AddressRepository addressRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRepository userRepository;

    @Resource
    private CartRepository cartRepository;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    JwtProvider jwtProvider;

    public JwtResponse signIn(LoginFormDto loginForm) {

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());
        Authentication authentication = authenticationManager.authenticate(userToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = this.findOneByEmail(userDetails.getUsername());

        return new JwtResponse(jwt, user.getEmail(), user.getPassword(), user.getRole());
    }

    public UserDto saveUser(UserDto userDto) {
        UserEntity userEntity = businessMapper.getUserEntityForSignUp(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        AddressEntity addressEntity = AddressEntity.builder()
                .city("Your new City")
                .street("Your new street")
                .buildingNumber("Your new buildingNumber")
                .apartmentNumber("Your new apartmentNumber")

                .build();

        addressRepository.save(addressEntity);
        Set<AddressEntity> addressEntitySet = new HashSet<>();
        addressEntitySet.add(addressEntity);
        addressRepository.saveAll(addressEntitySet);

        userEntity.setAddressEntitySet(addressEntitySet);

        userRepository.save(userEntity);
        return businessMapper.getUserDto(userEntity);
    }

    public UserEntity findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return businessMapper.collectionToList(userEntityList, businessMapper.userToDto);
    }

    public UserDto findById(int id) {
        UserEntity userEntity = userRepository.findById(id);
        return businessMapper.getUserDto(userEntity);
    }

    public UserDto updateUser(UserDto userDto) {
        Set<AddressEntity> addressEntitySetFromDto = new HashSet<>(businessMapper.collectionToSet(userDto.getAddressDtoSet(), businessMapper.addressToEntity));
        addressRepository.saveAll(addressEntitySetFromDto);
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        userEntity.setAddressEntitySet(addressEntitySetFromDto);
        userEntity.setPhoneList(userDto.getPhoneList());
        userRepository.save(userEntity);
        return businessMapper.getUserDto(userEntity);
    }

    public void deleteUser(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId());
        userRepository.delete(userEntity);
        cartRepository.delete(userEntity.getCartEntity());
    }

    public UserDto findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException("UserNotFoundException");
        }
        return businessMapper.getUserDto(userEntity);
    }

    public List<UserDto> getUsersEmails(String email) {
        List<UserEntity> userEntityList = userRepository.findAllByEmailContaining(email);
        return businessMapper.collectionToList(userEntityList, businessMapper.userToDto);
    }

    public int checkPhoneUnique(String phone) {
        return userRepository.countPhones(phone);
    }
}



