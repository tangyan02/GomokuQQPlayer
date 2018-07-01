import ego.gomoku.enumeration.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class QQPlay {

    static volatile State state = State.PREPARING;

    static JFrame frame = new JFrame("QQ五子棋工具");
    static Point centerPoint = new Point(915, 522);
    static Image screenImage;
    private static JLabel centerPointLabel = new JLabel();
    static ego.gomoku.enumeration.Color selfColor = Color.NULL;
    private static boolean enable = false;
    private static boolean autoPrepare = false;

    static public void main(String[] args) throws InterruptedException {

        frame.setBounds(100, 100, 340, 600);
        frame.setLayout(null);

        drawCenterPointCatchButton();
        drawCenterPointLabels();
        drawOperatingButtons();
        drawStateLabel();
        drawSelfColorLabel();

        MapWatcher.init();

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private static void drawCenterPointCatchButton() {
        JButton catchCenterPointButton = new JButton();
        catchCenterPointButton.setText("设置中心点");
        catchCenterPointButton.setBounds(10, 10, 150, 40);
        frame.getContentPane().add(catchCenterPointButton);
        catchCenterPointButton.addActionListener(new CatchCenterPointAction());
    }

    private static void drawCenterPointLabels() {
        JLabel label = new JLabel();
        label.setText("中心坐标:");
        label.setBounds(170, 10, 80, 40);
        frame.getContentPane().add(label);

        centerPointLabel.setBounds(250, 10, 100, 40);
        centerPointLabel.setText("N/A");
        frame.getContentPane().add(centerPointLabel);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    QQPlay.centerPointLabel.setText(centerPoint.x + " " + centerPoint.y);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void drawOperatingButtons() {
        JButton buttonTop = new JButton();
        buttonTop.setText("开始");
        buttonTop.setBounds(10, 60, 90, 40);
        frame.getContentPane().add(buttonTop);
        buttonTop.addActionListener(e -> {
            enable = true;
            autoPrepare = true;
        });

        JButton buttonStop = new JButton();
        buttonStop.setText("终止");
        buttonStop.setBounds(110, 60, 90, 40);
        frame.getContentPane().add(buttonStop);
        buttonStop.addActionListener(e -> {
            enable = false;
            autoPrepare = false;
        });

        JButton buttonCancel = new JButton();
        buttonCancel.setText("取消准备");
        buttonCancel.setBounds(210, 60, 100, 40);
        frame.getContentPane().add(buttonCancel);
        buttonCancel.addActionListener(e -> {
            autoPrepare = false;
        });

    }

    private static void drawStateLabel() {
        JLabel label = new JLabel();
        label.setText("当前状态:");
        label.setBounds(10, 430, 80, 40);
        frame.getContentPane().add(label);

        JLabel labelState = new JLabel();
        labelState.setBounds(100, 430, 80, 40);
        frame.getContentPane().add(labelState);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                    labelState.setText(state.getDescription());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void drawSelfColorLabel() {
        JLabel label = new JLabel();
        label.setText("当前角色:");
        label.setBounds(10, 490, 80, 40);
        frame.getContentPane().add(label);

        JLabel labelColor = new JLabel();
        labelColor.setBounds(100, 490, 80, 40);
        frame.getContentPane().add(labelColor);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                    if (selfColor == Color.BLACK) {
                        labelColor.setText("黑棋");
                    }
                    if (selfColor == Color.WHITE) {
                        labelColor.setText("白棋");
                    }
                    if (selfColor == Color.NULL) {
                        labelColor.setText("等待选择");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //监听黑棋状态
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(2000);
                    if (enable) {
                        BufferedImage bufferedImage = ScreenUtil.getScreenImage();
                        ScreenColor screenColor = ScreenUtil.getScreenColor(getStatePoint(), bufferedImage);
                        if (screenColor == ScreenColor.BLACK) {
                            selfColor = Color.BLACK;
                            state = State.WAITING;
                        }
                        if (screenColor == ScreenColor.WHITE) {
                            selfColor = Color.WHITE;
                            state = State.WAITING;
                        }
                        if (screenColor == ScreenColor.OTHER) {
                            selfColor = Color.NULL;
                            state = State.PREPARING;
                            if (autoPrepare) {
                                ScreenUtil.Click(new Point(centerPoint.x, centerPoint.y + 400));
                            }
                        }
                    } else {
                        selfColor = Color.NULL;
                        state = State.PREPARING;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static Point getStatePoint() {
        return new Point(centerPoint.x - 535, centerPoint.y - 90);
    }
}
