package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;

public class Airport {
    private static Texture texture;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static boolean flag;
    private CollisionRect rect;

    private int x, y;




    public Airport(int x, int y){
        this.x = x;
        this.y = y;


        if (texture == null){
            texture = new Texture("airport.png");
        }
        this.rect = new CollisionRect(x,y,texture.getWidth()/3,texture.getHeight()/3);

    }
    public void update(float deltaTime){
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }
}
