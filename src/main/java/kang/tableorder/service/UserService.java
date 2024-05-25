package kang.tableorder.service;

import kang.tableorder.dto.SignInDto;
import kang.tableorder.dto.SignUpDto;
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

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  // 회원가입
  public SignUpDto.Response signUp(SignUpDto.Request form) {

    // 이메일 존재 여부
    boolean emailExists = userRepository.existsByEmail(form.getEmail());
    if (emailExists) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_EMAIL);
    }

    // 닉네임 존재 여부
    boolean nicknameExists = userRepository.existsByNickname(form.getNickname());
    if (nicknameExists) {
      throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);
    }

    // 비밀번호 인코딩
    form.setPassword(passwordEncoder.encode(form.getPassword()));

    UserEntity saved = userRepository.save(UserEntity.from(form));

    return SignUpDto.Response.builder()
        .id(saved.getId())
        .email(saved.getEmail())
        .name(saved.getName())
        .nickname(saved.getNickname())
        .role(saved.getRoles().toString())
        .build();
  }

  public SignInDto.Response signIn(SignInDto.Request form) {

    // 이메일로 가입한 회원 존재 여부
    UserEntity userEntity = userRepository.findByEmail(form.getEmail())
        .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_USER));

    // 비밀번호 확인
    if (!passwordEncoder.matches(form.getPassword(), userEntity.getPassword())) {
      throw new CustomException(ErrorCode.WRONG_PASSWORD);
    }

    String token = tokenProvider.generateToken(userEntity.getEmail(), userEntity.getRoles());

    return SignInDto.Response.builder()
        .id(userEntity.getId())
        .token(token)
        .build();
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws CustomException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_USER));
  }
}
