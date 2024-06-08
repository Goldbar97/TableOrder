package kang.tableorder.service;

import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.dto.UserDetailsDto;
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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final UserEntityGetter userEntityGetter;
  private final UserRepository userRepository;

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

    UserEntity userEntity = form.toEntity();

    // 비밀번호 인코딩
    userEntity.setPassword(passwordEncoder.encode(form.getPassword()));

    return UserDto.SignUp.Response.toDto(userRepository.save(userEntity));
  }

  // 로그인
  public UserDto.SignIn.Response signIn(UserDto.SignIn.Request form) {

    // 이메일로 가입한 회원 존재 여부
    UserEntity userEntity = userRepository.findByEmailWithRole(form.getEmail())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    String token = tokenProvider.generateToken(userEntity.getEmail(), userEntity.getRole());

    return UserDto.SignIn.Response.toDto(userEntity, token);
  }


  // 사용자 본인 조회
  public UserDto.Read.Response readUser(UserDto.Read.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    return UserDto.Read.Response.toDto(userEntity);
  }

  // 사용자 정보 수정
  public UserDto.Update.Response updateUser(UserDto.Update.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    // 현재 비밀번호와 새 비밀번호 중복 확인
    if (form.getPassword().equals(form.getNewPassword())) {
      throw new CustomException(ErrorCode.ALREADY_USING_PASSWORD);
    }

    userEntity.setNickname(form.getNewNickname());

    userEntity.setPhoneNumber(form.getNewPhoneNumber());

    userEntity.setPassword(passwordEncoder.encode(form.getNewPassword()));

    UserEntity updated = userRepository.save(userEntity);

    return UserDto.Update.Response.toDto(updated);
  }

  // 사용자 탈퇴
  public void deleteUser(UserDto.Delete.Request form) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    userRepository.delete(userEntity);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws CustomException {

    UserEntity userEntity = userRepository.findByEmailWithRole(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    return new UserDetailsDto(userEntity);
  }
}
