package kang.tableorder.component;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {

  private final int length = 6;
  private final char[] table = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9'};

  public String generateCode() {

    StringBuilder sb = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      sb.append(table[random.nextInt(table.length)]);
    }

    return sb.toString();
  }
}