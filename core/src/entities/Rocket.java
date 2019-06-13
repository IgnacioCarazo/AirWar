package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;

public class Rocket {

    public static final int DEFAULT_Y = 80;
    private static Texture texture;
    private int speed;
    CollisionRect rect;

    float x, y;

    public boolean remove = false;



    public Rocket(float x, int speed){
        this.x = x;
        this.y = DEFAULT_Y;
        this.speed = speed;
        this.rect = new CollisionRect(x,y,7,20);

        if (texture == null){
            texture = new Texture("rocket_purple.png");
        }
    }
    public void update(float deltaTime){
        y += speed * deltaTime;
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

