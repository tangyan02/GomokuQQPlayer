import ego.gomoku.enumeration.Color;

import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class MapWatcher {

    private static Color[][] map;

    static void init() {

    }

    private static ego.gomoku.enumeration.Color[][] getMap() {
        ego.gomoku.enumeration.Color[][] result = new Color[15][15];
        List<MapPoint> points = getPointList();
        BufferedImage bufferedImage = ScreenUtil.getScreenImage();
        int delta = 8;
        for (MapPoint point : points) {
            int x = point.screenX + delta;
            int y = point.screenY + delta;
            java.awt.Color color = ScreenUtil.getScreenPixel(x, y, bufferedImage);
            int colorValue = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
            result[point.x][point.y] = Color.NULL;
            if (colorValue > 200) result[point.x][point.y] = Color.WHITE;
            if (colorValue < 50) result[point.x][point.y] = Color.BLACK;
        }
        printMap(result);
        return result;
    }

    static {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(2000);
                    if (QQPlay.state == State.WAITING) {
                        map = getMap();
                        if (GameExecutor.needExecute(map)) {
                            QQPlay.state = State.CALCULATING;
                            ego.gomoku.entity.Point point = GameExecutor.getResult(map);
                            if (point == null) {
                                continue;
                            }
                            Point result = getScreenPointByMapPoint(point);
                            ScreenUtil.Click(result);
                            QQPlay.state = State.WAITING;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static List<MapPoint> getPointList() {
        List<MapPoint> result = new ArrayList<>();
        int size = 44;
        for (int i = -7; i <= 7; i++) {
            for (int j = -7; j <= 7; j++) {
                int x = QQPlay.centerPoint.x + i * size;
                int y = QQPlay.centerPoint.y + j * size;
                result.add(new MapPoint(i + 7, j + 7, x, y));
            }
        }
        return result;
    }

    private static Point getScreenPointByMapPoint(ego.gomoku.entity.Point point) {
        int size = 44;
        int x = QQPlay.centerPoint.x + (point.getX() - 7) * size;
        int y = QQPlay.centerPoint.y + (point.getY() - 7) * size;
        return new Point(x, y);
    }

    private static void printMap(Color[][] map) {
        for (int i = 0; i < 15; ++i) {
            for (int j = 0; j < 15; ++j) {
                if (map[i][j] == Color.NULL) {
                    drawCube(i, j, java.awt.Color.GRAY);
                }

                if (map[i][j] == Color.BLACK) {
                    drawCube(i, j, java.awt.Color.BLACK);
                }

                if (map[i][j] == Color.WHITE) {
                    drawCube(i, j, java.awt.Color.WHITE);
                }
            }
        }

    }

    private static void drawCube(int i, int j, java.awt.Color color) {
        Graphics g = QQPlay.frame.getGraphics();
        g.setColor(color);
        int size = 20;
        int baseX = 10 + size / 2;
        int baseY = 140 + size / 2;
        g.fillRect(baseX + i * size, baseY + j * size, size, size);
    }
}
