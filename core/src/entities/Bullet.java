package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;

public class Bullet {

    public static final int SPEED = 300;
    public static final int DEFAULT_Y = 80;
    private static Texture texture;

    CollisionRect rect;

    float x, y;

    public boolean remove = false;



    public Bullet(float x){
        this.x = x;
        this.y = DEFAULT_Y;

        this.rect = new CollisionRect(x,y,12,12);

        if (texture == null){
            texture = new Texture("bullet_orange0.png");
        }
    }
    public void update(float deltaTime){
        y += SPEED * deltaTime;
        if (y > Gdx.graphics.getHeight()){
            remove = true;
        }
        rect.move(x,y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }
}
