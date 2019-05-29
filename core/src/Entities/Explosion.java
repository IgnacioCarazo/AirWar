package Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {

    public static final float FRAME_LENGTH = 0.2f;
    public static final int OFFSET_HEIGHT = 20;
    public static final int OFFSET_WIDTH = 7;
    public static final int SIZEY = 92;
    public static final int SIZEX = 96;


    private static Animation<TextureRegion> animation = null;
    int x,y;
    float stateTime;


    public boolean remove = false;

    public Explosion (int x, int y) {
        this.x = x + OFFSET_WIDTH;
        this.y = y + OFFSET_HEIGHT;
        stateTime = 0;

        if (animation == null){
            animation = new Animation<TextureRegion>(FRAME_LENGTH,TextureRegion.split(new Texture("explosion.png"),SIZEX,SIZEY)[0]);
        }
    }

    public void update(float deltaTime){
        stateTime += deltaTime;
        if (animation.isAnimationFinished(stateTime)){
            remove = true;
        }

    }

    public void render (SpriteBatch batch){
        batch.draw(animation.getKeyFrame(stateTime),x,y);
    }

}
