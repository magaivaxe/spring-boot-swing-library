package org.system.library.frames.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.system.library.business.model.UserLibrary;
import org.system.library.business.validations.UserLibraryValidations;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.Dialogs;
import org.system.library.frames.IFrame;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.AbstractButtonBuilder;
import org.system.library.frames.component.builder.LabelBuilder;
import org.system.library.frames.component.builder.PanelBuilder;
import org.system.library.frames.component.builder.TextFieldBuilder;
import org.system.library.frames.component.container.ComponentContainer;
import org.system.library.frames.component.container.IComponentContainer;
import org.system.library.frames.component.utils.SpringLayoutUtils;
import org.system.library.frames.menubar.builder.MenuBarBuilder;
import org.system.library.frames.menubar.builder.MenuBarComponentBuilder;
import org.system.library.services.LoginService;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class LoginFrame extends JFrame implements IFrame {

  private final MessageLibrary message;
  private final MenuBarComponentBuilder menuBarBuilder;
  private final ComponentContainer<JComponent> textComponentContainer;
  private final ComponentContainer<AbstractButton> buttonContainer;
  private final ComponentContainer<JPanel> panelContainer;
  private final ComponentContainer<JLabel> labelContainer;

  private final ApplicationContext applicationContext;
  private final LoginService loginService;
  private final Dialogs dialogs;
  private final UserLibraryValidations userValidations;

  @PostConstruct
  private void build() {
    buildHeaderPanel();
    buildBodyPanel();
    buildLinkPanel();
    buildFooterPanel();
    buildFrame();
    setListeners();
  }

  private void buildHeaderPanel() {
    var panelHeader = panelContainer.addToContainer("panelHeader",
                                                    HEADER_DIMENSION,
                                                    Position.ONE,
                                                    PanelBuilder.GRID_BAG_LAYOUT);
    setComponentsHeaderPanel(panelHeader);
  }

  private void setComponentsHeaderPanel(JPanel panelHeader) {
    var labelHeader = labelContainer.addToContainer("loginframe.panel.title",
                                                    null,
                                                    Position.ONE,
                                                    LabelBuilder.LABEL);
    labelHeader.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
    addComponentsByPosition(panelHeader, List.of(labelContainer.getAllAndClear(panelHeader.getName())));
  }

  private void buildLinkPanel() {
    var panelLink = panelContainer.addToContainer("panelLink",
                                                  LINK_DIMENSION,
                                                  Position.THREE,
                                                  PanelBuilder.BOX_LAYOUT);
    setComponentsLinkPanel(panelLink);
  }

  private void setComponentsLinkPanel(JPanel panelLink) {
    var passwordForgoten = buttonContainer.addToContainer("loginframe.user.password.forgotten",
                                                          LINK_DIMENSION,
                                                          Position.ONE,
                                                          AbstractButtonBuilder.BUTTON_HYPER_LINK);
    var createAccount = buttonContainer.addToContainer("loginframe.create.account",
                                                       LINK_DIMENSION, Position.TWO,
                                                       AbstractButtonBuilder.BUTTON_HYPER_LINK);
    passwordForgoten.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    createAccount.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    addComponentsByPosition(panelLink, List.of(buttonContainer.getAllAndClear(panelLink.getName())));
  }

  private void buildFooterPanel() {
    var panelFooter = panelContainer.addToContainer("panelFooter",
                                                    HEADER_DIMENSION,
                                                    Position.FOUR,
                                                    PanelBuilder.GRID_BAG_LAYOUT);
    setComponentsFooterPanel(panelFooter);
  }

  private void setComponentsFooterPanel(JPanel panelFooter) {
    buttonContainer.addToContainer("loginframe.button.connect",
                                   BUTTON_DIMENSION,
                                   Position.ONE,
                                   AbstractButtonBuilder.BUTTON);
    addComponentsByPosition(panelFooter, List.of(buttonContainer.getAllAndClear(panelFooter.getName())));
  }

  private void buildBodyPanel() {
    var panelBody = panelContainer.addToContainer("panelBody", null, Position.TWO, PanelBuilder.SPRING_LAYOUT);
    buildTextFieldsBodyPanel();
    buildLabelsBodyPanel();
    buildBodyLayoutPanel(panelBody);
  }

  private void buildTextFieldsBodyPanel() {
    textComponentContainer.addToContainer("application.user",
                                          TEXT_FIELD_DIMENSION,
                                          Position.TWO,
                                          TextFieldBuilder.TEXT_FIELD);
    textComponentContainer.addToContainer("application.password",
                                          TEXT_FIELD_DIMENSION,
                                          Position.FOUR,
                                          TextFieldBuilder.PASSWORD_FIELD);
  }

  private void buildLabelsBodyPanel() {
    labelContainer.addToContainer("application.user", null, Position.ONE, LabelBuilder.LABEL);
    labelContainer.addToContainer("application.password", null, Position.THREE, LabelBuilder.LABEL);
    IComponentContainer.setLabelFor(labelContainer.getAllNotIndexed(), textComponentContainer.getAllNotIndexed());
  }

  private void buildBodyLayoutPanel(JPanel panelBody) {
    addComponentsByPosition(panelBody,
                            List.of(textComponentContainer.getAllAndClear(panelBody.getName()),
                                    labelContainer.getAllAndClear(panelBody.getName())));
    SpringLayoutUtils.makeCompactGrid(panelBody, 2, 2, 0, 0, FORM_PADDING, FORM_PADDING);
  }

  private void buildFrame() {
    setTitle(message.getMessage("loginframe.title"));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setJMenuBar(menuBarBuilder.buildMenuBarByType(MenuBarBuilder.LOGIN_FRAME));
    setLayoutFrame();
  }

  private void setLayoutFrame() {
    var mainPane = getContentPane();
    setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
    addComponentsByPosition(mainPane, List.of(panelContainer.getAllAndClear("mainPane")));

    setSize(setDimensionBySizeComponents(mainPane));
    setDefaultLookAndFeelDecorated(true);
    setResizable(false);
    setLocationRelativeTo(CENTER_POSITION);
  }


  public void setListeners() {
    passwordForgottenListener();
    createUserListener();
    connectionListener();
  }

  private void passwordForgottenListener() {
    var passwordForgotten = buttonContainer.getComponentFromParent("panelLink",
                                                                   "loginframe.user.password.forgotten");
  }

  private void createUserListener() {
    var create = buttonContainer.getComponentFromParent("panelLink",
                                                        "loginframe.create.account");
  }

  private void connectionListener() {
    var connection = buttonContainer.getComponentFromParent("panelFooter",
                                                            "loginframe.button.connect");
    connection.addActionListener((event) -> {
      final var textFields = textComponentContainer.getAllFromParent("panelBody");
      final var username = ((JTextField) textFields.get("application.user")).getText();
      final var password = ((JPasswordField) textFields.get("application.password")).getPassword();

      try {
        final var userLibrary = loginService.loadUserByUsername(username);
        passwordEvent(username, password, userLibrary);
      } catch (UsernameNotFoundException e) {
        dialogs.dialogErrorMessage(this, "dialog.error.login");
      }
    });
  }

  private void passwordEvent(String username, char[] password, UserLibrary userLibrary) throws
                                                                                        UsernameNotFoundException {
    if (userValidations.passwordMatcher(password, userLibrary.getPasswordEncoded())) {
      var userBean = applicationContext.getBean(UserLibrary.class);
      userBean.update(userLibrary);
      //TODO: inject next window -> HOME dashboard
    } else {
      throw new UsernameNotFoundException(username);
    }
  }
}