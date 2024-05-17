package sample;

import Util.DataBaseUtil;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.PasswordField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.entity.Reader;
import sample.entity.User;
import java.net.URL;
import java.util.ResourceBundle;

public class UserLogUp implements Initializable {

    @FXML
    JFXTextField tf_id;
    @FXML
    JFXTextField tf_userName;
    @FXML
    JFXTextField tf_email;
    @FXML
    PasswordField tf_passWord;
    @FXML
    JFXRadioButton rb_sex_man;
    @FXML
    JFXRadioButton rb_sex_woman;
    @FXML
    JFXRadioButton rdb_reader;
    @FXML
    JFXRadioButton rdb_user;
    @FXML
    JFXTextField tf_admin;

    private Main myApp;
    private Stage myStage;
    private RequiredFieldValidator validator;
    private RequiredFieldValidator validatorEmail;
    private RequiredFieldValidator validatorPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new RequiredFieldValidator();
        validator.setMessage("请输入...");
        tf_id.getValidators().add(validator);
        tf_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_id.validate();
        });

        tf_userName.getValidators().add(validator);
        tf_userName.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_userName.validate();
        });

        // 邮箱验证器和错误提示
        validatorEmail = new RequiredFieldValidator();
        validatorEmail.setMessage("请输入有效的邮箱，请检查邮箱格式");

        tf_email.getValidators().add(validatorEmail);
        tf_email.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) tf_email.validate();
        });

        // 密码验证器和错误提示
        validatorPassword = new RequiredFieldValidator();
        validatorPassword.setMessage("请输入6-16位数字、字母或常用符号");

        tf_admin.setEditable(false);
        rdb_reader.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                tf_admin.setEditable(false);
                tf_admin.setText(""); // 清空任何已存在的文本
            } else {
                tf_admin.setEditable(true);
            }
        });

    }

    public void setMyApp(Main myApp) {
        this.myApp = myApp;
    }

    public void setController(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    public void goBackLogin() {
        myApp.showWindow();
        myStage.close();
    }


    @FXML
    public void confirm() {
        String userName = tf_userName.getText().trim();
        String passWord = tf_passWord.getText().trim();
        String email = tf_email.getText().trim();
        String id = tf_id.getText().trim();
        String sex = "";
        String adminCode = tf_admin.getText().trim();
        if (rb_sex_man.isSelected()) {
            sex = "男";
        } else {
            sex = "女";
        }
        boolean email_ready = false;
        boolean pass_ready = false;

        if (!id.equals("") || !email.equals("") || !passWord.equals("") || !userName.equals("")) {
            if (email.endsWith(".com") || email.contains("@")) {
                email_ready = true;
            } else {
                tf_email.setText("");
                tf_email.validate();
            }
            if (passWord.length() >= 6) {
                pass_ready = true;
            } else {
                tf_passWord.setText("");
            }
            if (email_ready && pass_ready) {
                boolean isok = false;
                if (rdb_reader.isSelected()) {
                    Reader reader = new Reader(id, userName, passWord, "学生", sex, 12, 30, 0);
                    isok = DataBaseUtil.addNewReader(reader);

                } else {
                    // 验证管理密钥
                    if (!adminCode.equals("123456")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setHeaderText("注册失败!");
                        alert.setContentText("管理员验证失败，请输入正确的管理员代码！");
                        alert.setTitle("图书管理系统");
                        alert.showAndWait();

                        // 退出该方法，不进行进一步处理
                        return;
                    }
                    User user = new User(id, userName, passWord, email, 1);
                    isok = DataBaseUtil.addNewUser(user);
                }
                if (isok) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("感谢注册!");
                    alert.setContentText("注册成功！您的用户名为："+ id +"您的密码为："+ passWord +",即将返回登录界面。");
                    alert.setTitle("图书管理系统");
                    alert.showAndWait();
                    myApp.showWindow();
                    myStage.close();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setHeaderText("注册失败!");
                    alert.setContentText("该用户名已被注册，请修改用户名！");
                    alert.setTitle("图书管理系统");
                    alert.showAndWait();
                    tf_id.setText("");
                }
            }
        }
    }
}
