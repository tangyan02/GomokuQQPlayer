import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatchCenterPointAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        QQPlay.screenImage = ScreenUtil.getScreenImage();

        double width = Toolkit.getDefaultToolkit().getScreenSize().width; //得到当前屏幕分辨率的高
        double height = Toolkit.getDefaultToolkit().getScreenSize().height;//得到当前屏幕分辨率的宽
        JFrame imgFrame = new JFrame();
        imgFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        imgFrame.setTitle(""); //设置标题处的文字
        imgFrame.setSize((int) width, (int) height);//设置大小
        imgFrame.setLocation(0, 0); //设置窗体居中显示
        imgFrame.setVisible(true);

        ImgPanel imgPanel = new ImgPanel();
        imgFrame.add(imgPanel);
        imgPanel.setVisible(true);

    }

}
