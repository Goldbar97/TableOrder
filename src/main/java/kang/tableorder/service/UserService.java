package kang.tableorder.service;

import kang.tableorder.dto.UserDto;
import kang.tableorder.entity.UserEntity;
import kang.tableorder.exception.CustomException;
import kang.tableorder.exception.ErrorCode;
import kang.tableorder.repository.UserRepository;
import kang.tableorder.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  // 회원가입
  public UserDto.SignUp.Response signUp(UserDto.SignUp.Request form) {

    // 이메일 존재 여부
    if (userRepository.existsByEmail(form.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_EMAIL);
    }

    // 닉네임 존재 여부
    if (userRepository.existsByNickname(form.getNickname())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);
    }

    UserEntity userEntity = UserDto.SignUp.Request.toEntity(form);

    // 비밀번호 인코딩
    userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
    UserEntity saved = userRepository.save(userEntity);

    return UserDto.SignUp.Response.toDto(userEntity);
  }

  // 로그인
  public UserDto.SignIn.Response signIn(UserDto.SignIn.Request form) {

    // 이메일로 가입한 회원 존재 여부
    UserEntity userEntity = userRepository.findByEmail(form.getEmail())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    String token = tokenProvider.generateToken(userEntity.getEmail(), userEntity.getRoles());

    return UserDto.SignIn.Response.toDto(userEntity, token);
  }


  // 사용자 본인 조회
  public UserDto.Read.Response readProfile(String header, String password) {
    String email = tokenProvider.getEmail(header);

    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(password, userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    return UserDto.Read.Response.toDto(userEntity);
  }

  // 사용자 정보 수정
  public UserDto.Update.Response updateProfile(String header, UserDto.Update.Request form) {
    String email = tokenProvider.getEmail(header);

    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    if (StringUtils.hasText(form.getNewNickname())) {
      userEntity.setNickname(form.getNewNickname());
    }

    if (StringUtils.hasText(form.getNewPhoneNumber())) {
      userEntity.setPhoneNumber(form.getNewPhoneNumber());
    }

    if (StringUtils.hasText(form.getNewPassword())) {
      userEntity.setPassword(passwordEncoder.encode(form.getNewPassword()));
    }

    UserEntity updated = userRepository.save(userEntity);

    return UserDto.Update.Response.toDto(updated);
  }

  // 사용자 탈퇴
  public boolean deleteProfile(String header, String password) {
    String email = tokenProvider.getEmail(header);

    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(password, userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    userRepository.delete(userEntity);

    return true;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws CustomException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));
  }
}
