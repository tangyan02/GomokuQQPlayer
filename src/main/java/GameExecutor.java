import ego.gomoku.entity.Point;
import ego.gomoku.entity.Result;
import ego.gomoku.enumeration.Color;
import ego.gomoku.enumeration.Level;
import ego.gomoku.player.GomokuPlayer;

public class GameExecutor {

    static boolean needExecute(Color[][] map) {
        int blackCount = 0;
        int whiteCount = 0;
        for (Color[] colors : map) {
            for (Color color : colors) {
                if (color == Color.BLACK) {
                    blackCount++;
                }
                if (color == Color.WHITE) {
                    whiteCount++;
                }
            }
        }
        Color shouldPlayColor = Color.NULL;
        if (blackCount == whiteCount) {
            shouldPlayColor = Color.BLACK;
        }
        if (blackCount == whiteCount + 1) {
            shouldPlayColor = Color.WHITE;
        }
        return QQPlay.selfColor == shouldPlayColor;
    }

    static Point getResult(Color[][] map) {
        GomokuPlayer gomokuPlayer = new GomokuPlayer(map, Level.HIGH);
        Result result = gomokuPlayer.randomBegin(QQPlay.selfColor);
        if (result == null) {
            return null;
        }
        return result.getPoint();
    }

}
