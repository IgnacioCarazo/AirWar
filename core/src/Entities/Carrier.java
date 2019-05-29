package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;

public class Carrier {
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




    public Carrier(int x, int y){
        this.x = x;
        this.y = y;

        this.rect = new CollisionRect(x,y,190,60);

        if (texture == null){
            texture = new Texture("carrier_horizontal.png");
        }
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
