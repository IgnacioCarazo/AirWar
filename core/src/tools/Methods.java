package tools;

import com.airwar.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Methods {


    public static void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, int x1, int y1, int x2, int y2) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for(int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.point(x1 + vec2.x, y1 + vec2.y, 0);
        }

        shapeRenderer.end();
    }

    public static int randomAirportIndex() {
        int number = (int) (Math.random() * (3));
        System.out.println("To airport " + number);
        return number;
    }

    public static int randomCarrierIndex() {
        return (int) (Math.random() * (2));
    }

    public static void drawTerrain(int xMin,int xMax, int yMin, int yMax, Texture texture, Main game){
        int temp = xMin;
        for (int i = yMin; i < yMax;) {
            while (xMin < xMax) {
                game.batch.draw(texture, xMin, i);
                xMin+=texture.getWidth();
            }
            xMin = temp;
            i += texture.getHeight();
        }
    }

}
