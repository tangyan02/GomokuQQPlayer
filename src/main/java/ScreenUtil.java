import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

class ScreenUtil {

    static Color getScreenPixel(int x, int y, BufferedImage bufferedImage) { // 函数返回值为颜色的RGB值。
        int pixelColor = bufferedImage.getRGB(x, y);
        return new Color(16777216 + pixelColor); // pixelColor的值为负，经过实践得出：加上颜色最大值就是实际颜色值。
    }

    static BufferedImage getScreenImage() {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // 获取缺省工具包
        Dimension dimension = toolkit.getScreenSize(); // 屏幕尺寸规格
        Rectangle rectangle = new Rectangle(0, 0, dimension.width, dimension.height);
        return robot.createScreenCapture(rectangle);
    }

    static void Click(Point point) {
        try {
            Robot robot = new Robot();
            //很奇怪，要执行很多次才能准确移动到那个坐标
            robot.mouseMove(point.x, point.y);
            robot.mouseMove(point.x, point.y);
            robot.mouseMove(point.x, point.y);
            robot.mouseMove(point.x, point.y);
            robot.mouseMove(point.x, point.y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(100);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    static Point getMousePoint() {
        return java.awt.MouseInfo.getPointerInfo().getLocation();
    }

}
