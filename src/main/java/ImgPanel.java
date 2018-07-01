import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

class ImgPanel extends Panel {

    private ImgPanel self;

    ImgPanel() {
        super();
        self = this;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                QQPlay.centerPoint = ScreenUtil.getMousePoint();
                drawPreviewPoints(self.getGraphics());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                getSelfFrame().dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // 获取缺省工具包
        Dimension dimension = toolkit.getScreenSize(); // 屏幕尺寸规格
        ImageIcon image = new ImageIcon(QQPlay.screenImage); // 获取图片路径
        g.drawImage(image.getImage(), 0, getDeltaHeight()
                , dimension.width, dimension.height, null);// 绘制图片与组件大小相同
    }

    /**
     * 画出基准的坐标点
     */
    private void drawPreviewPoints(Graphics g) {
        if (QQPlay.centerPoint != null) {
            List<MapPoint> pointList = MapWatcher.getPointList();
            pointList.forEach(point -> {
                        g.setColor(Color.red);
                        g.drawRect(point.screenX - 3,
                                point.screenY + getDeltaHeight() - 3, 6, 6);
                    }
            );
        }
    }

    private JFrame getSelfFrame(){
        return ((JFrame) self.getParent().getParent().getParent().getParent());
    }

    private int getDeltaHeight() {
        return -getSelfFrame().getInsets().top + getSelfFrame().getInsets().bottom;
    }
}