package org.system.library;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.system.library.frames.login.LoginFrame;

import java.awt.*;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {

  @Autowired
  final LoginFrame loginFrame;

  public static void main(String[] args) {
    var context = new SpringApplicationBuilder(Application.class)
      .headless(false).run(args);

    EventQueue.invokeLater(() -> {
      var loginFrame = context.getBean(LoginFrame.class);
      loginFrame.setVisible(true);
    });
  }

}