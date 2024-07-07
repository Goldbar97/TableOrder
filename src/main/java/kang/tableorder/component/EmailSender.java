package kang.tableorder.component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

  private final JavaMailSender javaMailSender;
  private final String SUBJECT = "[TableOrder] 인증 메일입니다.";

  public void sendVerificationEmail(String email, String verificationNumber) {

    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

      String htmlContent = getVerificationMessage(verificationNumber);

      messageHelper.setTo(email);
      messageHelper.setSubject(SUBJECT);
      messageHelper.setText(htmlContent, true);
      javaMailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  private String getVerificationMessage(String verificationNumber) {

    String sb = "<h1 style='text-align: center;'> [Chat-Connect] 인증매일</h1>"
        + "<h3 style='text-align: center;'> 인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>"
        + verificationNumber
        + "</strong></h3>";

    return sb;
  }
}