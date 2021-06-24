package org.gwb.modules.swing;

import lombok.extern.slf4j.Slf4j;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author yaoqi
 */
@Slf4j
public class LoginFrame {

    private static LoginFrame instance;
    private JPanel root;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    @Autowired
    private ApplicationContext applicationContext;
//    ISysUserService sysUserService = applicationContext.getBean(ISysUserService.class);

    public static LoginFrame getInstance() {
        if (null == instance) {
            synchronized (LoginFrame.class) {
                if (null == instance) {
                    instance = new LoginFrame();
                }
            }
        }
        return instance;
    }

    public LoginFrame() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = String.valueOf(passwordField.getPassword());
                System.out.println(applicationContext);
//                ISysUserService userService = applicationContext.getBean(ISysUserService.class);
//                SysUser sysUser = userService.getById(1);
//                log.info(sysUser.getUsername());
            }
        });
    }



    public static void main(String[] args) {
//        startLoginFrame();
    }

    public void startLoginFrame(){
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
            JFrame frame = new JFrame("LoginFrame");
            frame.setContentPane(new LoginFrame().root);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,400);
            frame.setVisible(true);
            setCenter(frame);
            frame.setTitle("登录");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setCenter(JFrame frame){
        int windowWidth = frame.getWidth();                     //获得窗口宽
        int windowHeight = frame.getHeight();                   //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包
        Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸
        int screenWidth = screenSize.width;                     //获取屏幕的宽
        int screenHeight = screenSize.height;                   //获取屏幕的高
        frame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
    }

    public void initUI(){
        startLoginFrame();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
