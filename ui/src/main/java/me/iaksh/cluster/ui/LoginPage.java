package me.iaksh.cluster.ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class LoginPage {
    // 数据库连接信息
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://192.168.23.134:3306/Cluster";
    private static final String USER = "root";
    private static final String PASS = "123456";

    // SQL语句
    private static final String INSERT_USER = "INSERT INTO users (username, password,vip) VALUES (?, ?,?)";
    private static final String SELECT_USER = "SELECT * FROM users WHERE username = ? AND password = ?";

    public void start() {
        Stage stage = new Stage();
        stage.setTitle("cluster 8bit music editor 的登录和注册界面");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(80, 80, 80, 80));

        Text title = new Text("欢迎使用cluster 8bit music editor");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);

        Label usernameLabel = new Label("用户名:");
        grid.add(usernameLabel, 0, 1);

        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("密码:");
        grid.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Button loginButton = new Button("登录");
        Button registerButton = new Button("注册");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        hbBtn.getChildren().add(registerButton);
        grid.add(hbBtn, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        // 登录按钮的事件处理
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (checkUser(username, password)) {
                if (checkVIP(username,password)){
                    actionTarget.setText("登录成功");
                    actionTarget.setFill(Color.BLUE);
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    // 在动画结束时关闭当前窗口
                    delay.setOnFinished(event -> {
                        stage.close();
                        FXApplication.getInstance().getPrimaryStage().show();
                    });
                    // 开始动画
                    delay.play();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("注册成功");
                    alert.setHeaderText(null);
                    alert.setContentText("warning：你还不是VIP!");
                    alert.getButtonTypes().setAll(new ButtonType("充值"), new ButtonType("退出"));

                    // 显示对话框，并获取用户的选择
                    Optional<ButtonType> result = alert.showAndWait();

                    // 根据用户的选择，执行相应的操作
                    if (result.get().getText().equals("充值")) {
                        // 继续操作，比如跳转到其他界面
                        addVIP(username);



                    } else if (result.get().getText().equals("退出")) {
                        // 退出操作，比如关闭程序
                        System.out.println("退出操作");
                        System.exit(0);
                    }

                }
            } else {
                actionTarget.setText("error:密码错误");
                actionTarget.setFill(Color.RED);
            }
        });

        // 注册按钮的事件处理
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (addUser(username, password)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("注册成功");
                alert.setHeaderText(null);
                alert.setContentText("warning：你还不是VIP!");
                alert.getButtonTypes().setAll(new ButtonType("充值"), new ButtonType("退出"));

                // 显示对话框，并获取用户的选择
                Optional<ButtonType> result = alert.showAndWait();

                // 根据用户的选择，执行相应的操作
                if (result.get().getText().equals("充值")) {
                    // 继续操作，比如跳转到其他界面
                    addVIP(username);

                } else if (result.get().getText().equals("退出")) {
                    // 退出操作，比如关闭程序
                    System.out.println("退出操作");
                    System.exit(0);
                }
            } else {
                actionTarget.setText("error:用户名重复");
                actionTarget.setFill(Color.rgb(204,204,0));
            }
        });

        Scene scene = new Scene(grid, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    // 检查用户是否存在并匹配密码
    public boolean checkUser(String username, String password) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(SELECT_USER);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkVIP(String username, String password) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT VIP FROM users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3,1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int VIP=rs.getInt("VIP");
                if (VIP==1)
                    return true;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 添加用户到数据库中
    public boolean addUser(String username, String password) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement(INSERT_USER);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3,0);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return true;
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addVIP(String username) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("update users set VIP = ? where username = ?");
            ps.setInt(1, 1);
            ps.setString(2, username);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return true;
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
