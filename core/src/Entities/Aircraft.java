package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;

import java.util.ArrayList;

public class Aircraft {

    private static final int SPEED = 200;
    private static Texture texture;
    CollisionRect rect;

    int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // up sprites
    public static Texture aircraft1up = new Texture("aircraft_01_up.png");
    public static Texture aircraft2up = new Texture("aircraft_02_up.png");
    public static Texture aircraft3up = new Texture("aircraft_03_up.png");
    public static Texture aircraft4up = new Texture("aircraft_04_up.png");
    public static Texture aircraft5up = new Texture("aircraft_05_up.png");
    public static Texture aircraft6up = new Texture("aircraft_06_up.png");
    public static Texture aircraft7up = new Texture("aircraft_07_up.png");
    public static Texture aircraft8up = new Texture("aircraft_08_up.png");
    public static Texture aircraft9up = new Texture("aircraft_09_up.png");
    public static Texture aircraft10up = new Texture("aircraft_10_up.png");

    //down sprites
    public static Texture aircraft1down = new Texture("aircraft_01_down.png");
    public static Texture aircraft2down = new Texture("aircraft_02_down.png");
    public static Texture aircraft3down = new Texture("aircraft_03_down.png");
    public static Texture aircraft4down = new Texture("aircraft_04_down.png");
    public static Texture aircraft5down = new Texture("aircraft_05_down.png");
    public static Texture aircraft6down = new Texture("aircraft_06_down.png");
    public static Texture aircraft7down = new Texture("aircraft_07_down.png");
    public static Texture aircraft8down = new Texture("aircraft_08_down.png");
    public static Texture aircraft9down = new Texture("aircraft_09_down.png");
    public static Texture aircraft10down = new Texture("aircraft_10_down.png");

    //left sprites
    public static Texture aircraft1left = new Texture("aircraft_01_left.png");
    public static Texture aircraft2left = new Texture("aircraft_02_left.png");
    public static Texture aircraft3left = new Texture("aircraft_03_left.png");
    public static Texture aircraft4left = new Texture("aircraft_04_left.png");
    public static Texture aircraft5left = new Texture("aircraft_05_left.png");
    public static Texture aircraft6left = new Texture("aircraft_06_left.png");
    public static Texture aircraft7left = new Texture("aircraft_07_left.png");
    public static Texture aircraft8left = new Texture("aircraft_08_left.png");
    public static Texture aircraft9left = new Texture("aircraft_09_left.png");
    public static Texture aircraft10left = new Texture("aircraft_10_left.png");

    //right sprites
    public static Texture aircraft1right = new Texture("aircraft_01_right.png");
    public static Texture aircraft2right = new Texture("aircraft_02_right.png");
    public static Texture aircraft3right = new Texture("aircraft_03_right.png");
    public static Texture aircraft4right = new Texture("aircraft_04_right.png");
    public static Texture aircraft5right = new Texture("aircraft_05_right.png");
    public static Texture aircraft6right = new Texture("aircraft_06_right.png");
    public static Texture aircraft7right = new Texture("aircraft_07_right.png");
    public static Texture aircraft8right = new Texture("aircraft_08_right.png");
    public static Texture aircraft9right = new Texture("aircraft_09_right.png");
    public static Texture aircraft10right = new Texture("aircraft_10_right.png");

    public boolean remove = false;

    private ArrayList<Texture> downSprites;



    public Aircraft(int x,int y,int index){
        this.x = x;
        this.y = Gdx.graphics.getHeight();
        downSprites = new ArrayList<Texture>();
        downSprites.add(aircraft1down);
        downSprites.add(aircraft2down);
        downSprites.add(aircraft3down);
        downSprites.add(aircraft4down);
        downSprites.add(aircraft5down);
        downSprites.add(aircraft6down);
        downSprites.add(aircraft7down);
        downSprites.add(aircraft8down);
        downSprites.add(aircraft9down);
        downSprites.add(aircraft10down);


        if (texture == null){
            System.out.println(index);
            texture = downSprites.get(index);
        }
        this.rect = new CollisionRect(x,y,texture.getWidth(),texture.getHeight());

    }
    public Aircraft(int x,int y) {
        this.x = x;
        this.y = y;

        if (texture == null) {
            texture = aircraft6down;
        }
        this.rect = new CollisionRect(x, y, texture.getWidth(), texture.getHeight());
    }
    public void update(float deltaTime){
        y -= SPEED * deltaTime;
        if (y < -texture.getHeight()){
            remove = true;
        }
        rect.move(x,y);
    }
    public void update(float deltaTime, int xBase, int yBase){

        if (x < xBase && y > yBase){
            x += SPEED * deltaTime;
            y -= SPEED * deltaTime;
        } else if (x > xBase && y > yBase){
            x -= SPEED * deltaTime;
            y -= SPEED * deltaTime;
        }

        if (x == xBase){
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