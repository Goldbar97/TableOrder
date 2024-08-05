package kang.tableorder.service;

import java.util.Objects;
import kang.tableorder.component.CodeGenerator;
import kang.tableorder.component.EmailSender;
import kang.tableorder.component.UserEntityGetter;
import kang.tableorder.component.deleter.UserEntityDeleter;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final UserEntityGetter userEntityGetter;
  private final UserRepository userRepository;
  private final UserEntityDeleter userEntityDeleter;
  private final RedisService redisService;
  private final CodeGenerator codeGenerator;
  private final EmailSender emailSender;

  // 인증번호 발급
  @Transactional
  public void generateCode(UserDto.Code.Request request) {

    String email = request.getEmail();
    String code = codeGenerator.generateCode();

    redisService.save(email, code);

    emailSender.sendVerificationEmail(email, code);
  }

  // 회원가입
  @Transactional
  public UserDto.SignUp.Response signUp(UserDto.SignUp.Request request) {

    // 이메일 존재 여부
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_EMAIL);
    }

    // 닉네임 존재 여부
    if (userRepository.existsByNickname(request.getNickname())) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);
    }

    // 인증번호 일치 여부
    if (!Objects.equals(redisService.getString(request.getEmail()),
        request.getVerificationCode())) {
      throw new CustomException(ErrorCode.WRONG_CODE);
    }

    UserEntity userEntity = request.toEntity();

    // 비밀번호 인코딩
    userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

    return UserDto.SignUp.Response.toDto(userRepository.save(userEntity));
  }

  // 로그인
  @Transactional
  public UserDto.SignIn.Response signIn(UserDto.SignIn.Request request) {

    // 이메일로 가입한 회원 존재 여부
    UserEntity userEntity = userRepository.findByEmailWithRole(request.getEmail())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    String token = tokenProvider.generateToken(userEntity.getEmail(), userEntity.getRole());

    return UserDto.SignIn.Response.toDto(userEntity, token);
  }

  // 사용자 본인 조회
  @Transactional(readOnly = true)
  public UserDto.Read.Response readUser(UserDto.Read.Request request) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 비밀번호 확인
    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    return UserDto.Read.Response.toDto(userEntity);
  }

  // 사용자 정보 수정
  @Transactional
  public UserDto.Update.Response updateUser(UserDto.Update.Request request) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 비밀번호 확인
    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    // 현재 비밀번호와 새 비밀번호 중복 확인
    if (request.getPassword().equals(request.getNewPassword())) {
      throw new CustomException(ErrorCode.ALREADY_USING_PASSWORD);
    }

    userEntity.setNickname(request.getNewNickname());

    userEntity.setPhoneNumber(request.getNewPhoneNumber());

    userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));

    return UserDto.Update.Response.toDto(userEntity);
  }

  // 사용자 탈퇴
  @Transactional
  public void deleteUser(UserDto.Delete.Request request) {

    UserEntity userEntity = userEntityGetter.getUserEntity();

    // 비밀번호 확인
    if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    userEntityDeleter.deleteByUserEntity(userEntity);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws CustomException {

    UserEntity userEntity = userRepository.findByEmailWithRole(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

    return new UserDetailsDto(userEntity);
  }
}