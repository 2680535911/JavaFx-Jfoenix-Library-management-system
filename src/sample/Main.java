package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage mainStage;

    /**
     * 登录主界面
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        mainStage.setResizable(false);
        //设置窗口的图标.
        mainStage.getIcons().add(new Image(
                Main.class.getResourceAsStream("/sample/image/logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/resources/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("图书管理系统");
        sample.Controller controller = loader.getController();
        controller.setApp(this);
        Scene scene = new Scene(root, 1440, 810);
        scene.getStylesheets().add(Main.class.getResource("/sample/resources/main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 管理员界面
     * @param userId
     */
    public void gotoMainUi(String userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/resources/main_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            MainUiController controller = loader.getController();
            controller.setApp(this);
            controller.setMyName(userId);
            Scene scene = new Scene(root, 1440, 810);
            scene.getStylesheets().add(Main.class.getResource("/sample/resources/main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 读者主界面
     * @param id
     */
    public void gotoReaderUi(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/resources/reader_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            ReaderUi controller = loader.getController();
            controller.setApp(this);
            controller.setUserInfo(id);
            Scene scene = new Scene(root, 1440, 810);
            scene.getStylesheets().add(Main.class.getResource("/sample/resources/main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage()+e.toString());
        }

    }

    public void closeWindow() {
        mainStage.close();
    }

    public void hideWindow(){ mainStage.hide();}

    public void showWindow(){ mainStage.show();}


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 用户注册界面
     * @param
     */
    public void gotoLoginUi() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/resources/sample.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            sample.Controller controller = loader.getController();
            controller.setApp(this);
            Scene scene = new Scene(root, 1440, 810);
            scene.getStylesheets().add(Main.class.getResource("/sample/resources/main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
