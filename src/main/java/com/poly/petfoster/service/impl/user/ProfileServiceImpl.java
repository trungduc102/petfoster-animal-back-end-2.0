package com.poly.petfoster.service.impl.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Role;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.AuthoritiesRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.ProfileRepuest;
import com.poly.petfoster.request.users.ChangePasswordRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.users.UserProfileResponse;
import com.poly.petfoster.service.user.ProfileService;
import com.poly.petfoster.ultils.ImageUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PortUltil portUltil;

  @Autowired
  private ServletContext app;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthoritiesRepository authoritiesRepository;

  @Override
  public ApiResponse getProfile(String jwt) {

    // find user by token
    String username = jwtProvider.getUsernameFromToken(jwt);

    // if have user
    User user = userRepository.findByUsername(username).orElse(null);

    // find role by user
    Role role = authoritiesRepository.findByUser(user).get(0).getRole();

    // check if user no role
    if (role == null) {
      return ApiResponse.builder()
          .message("Get failure !")
          .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .errors(true).data(null).build();
    }

    // build a user profile
    UserProfileResponse userProfile = UserProfileResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .fullname(user.getFullname())
        .birthday(user.getBirthday())
        .gender(user.getGender())
        .phone(user.getPhone())
        .email(user.getEmail())
        .avatar(user.getAvatar() == null ? null : portUltil.getUrlImage(user.getAvatar()))
        .role(role.getRole())
        .displayName(user.getDisplayName())
        .provider(user.getProvider())
        .createAt(user.getCreateAt())
        .build();

    // return data
    return ApiResponse.builder().message("Successfully!").status(200).errors(false).data(userProfile).build();

  }

  @Override
  public ApiResponse updateProfile(@Valid ProfileRepuest profileRepuest, String token) {
    Map<String, String> errorsMap = new HashMap<>();
    String username = jwtProvider.getUsernameFromToken(token);

    User user = userRepository.findByUsername(username).orElse(null);

    if (user == null) {
      return ApiResponse.builder()
          .message("User not found !")
          .status(HttpStatus.NOT_FOUND.value())
          .errors(true)
          .data(null)
          .build();
    }

    // start validate
    if (user.getProvider() == null || !user.getProvider().equals("facebook")) {
      if (!user.getEmail().equals(profileRepuest.getEmail()) && user.getUuid() == null) {
        errorsMap.put("email", "Can't update email !");
      }
    }

    if (profileRepuest.getFullname().isEmpty()) {
      errorsMap.put("fullname", "Fullname can't be blank");
    }

    if (profileRepuest.getEmail().isEmpty()) {
      errorsMap.put("email", "Email can't be blank");
    }

    if (profileRepuest.getPhone().isEmpty()) {
      errorsMap.put("phone", "Phone can't be blank");
    }

    if (profileRepuest.getBirthday().orElse(null) == null) {
      errorsMap.put("birthday", "Birthday can't be blank");
    } else {
      user.setBirthday(profileRepuest.getBirthday().orElse(null));
    }

    // end validate

    // check errors
    if (!errorsMap.isEmpty()) {
      return ApiResponse.builder()
          .message("Update faild !")
          .errors(errorsMap)
          .data(null)
          .build();
    }

    if (profileRepuest.getAvartar() != null) {
      if (profileRepuest.getAvartar().getSize() > 500000) {
        errorsMap.put("avartar", "Image size is too large");
      } else {
        try {
          File file = ImageUtils.createFileImage();

          profileRepuest.getAvartar().transferTo(new File(file.getAbsolutePath()));
          user.setAvatar(file.getName());
        } catch (Exception e) {
          System.out.println("Erorr in update avatar in Profile service impl");
          e.printStackTrace();
          return ApiResponse.builder()
              .message(RespMessage.INTERNAL_SERVER_ERROR.getValue())
              .errors(true)
              .status(500)
              .data(null)
              .build();
        }
      }
    }

    // check errors
    if (!errorsMap.isEmpty()) {
      return ApiResponse.builder()
          .message("Update faild !")
          .status(501)
          .errors(errorsMap)
          .data(null)
          .build();
    }

    user.setFullname(profileRepuest.getFullname());
    user.setGender(profileRepuest.getGender());
    user.setPhone(profileRepuest.getPhone());

    // can update email of user when this user login by facebook
    if (user.getProvider() != null) {
      if (user.getUuid() != null && user.getProvider().equals("facebook") &&
          user.getEmail() == null) {
        // do save email
        user.setEmail(profileRepuest.getEmail());
      }
    }

    User newUser = userRepository.save(user);

    newUser.setAvatar(portUltil.getUrlImage(user.getAvatar()));

    return ApiResponse.builder()
        .message("Update success!")
        .errors(false)
        .status(HttpStatus.OK.value())
        .data(newUser)
        .build();
  }

  @Override
  public ApiResponse changePassword(ChangePasswordRequest changePasswordRequest, String token) {
    Map<String, String> errorsMap = new HashMap<>();

    String username = jwtProvider.getUsernameFromToken(token);
    User user = userRepository.findByUsername(username).orElse(null);

    if (user == null) {
      return ApiResponse.builder()
          .message("User not found !")
          .status(HttpStatus.NOT_FOUND.value())
          .errors(true)
          .data(null)
          .build();
    }

    if (!passwordEncoder.matches(changePasswordRequest.getPassword(), user.getPassword())) {
      errorsMap.put("password", "password is incorrect, please try again!!");
      return ApiResponse.builder()
          .message(RespMessage.FAILURE.getValue())
          .errors(errorsMap)
          .status(HttpStatus.BAD_REQUEST.value())
          .data(null)
          .build();
    }

    // all good
    user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

    return ApiResponse.builder()
        .message("Update success!")
        .errors(false)
        .status(HttpStatus.OK.value())
        .data(userRepository.save(user))
        .build();
  }

}
