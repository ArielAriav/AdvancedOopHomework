package Graphics;

import java.awt.*;

public interface IDrawable {
    public final static String PICTURE_PATH = "...";
    public void loadImages (String nm);
    public void drawObject (Graphics g);
}
