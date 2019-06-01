package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;
import tools.Route;

import java.util.ArrayList;

public class Aircraft {

    private static final int SPEED = 100;
    private static Texture texture;
    private CollisionRect rect;
    public boolean invincible;
    public boolean destination;
    public int indexDestinationCarrier;
    public int indexDestinationAirport;
    public Route route;

    private int x, y, xBase, yBase;

    public int getxBase() {
        return xBase;
    }

    public int getyBase() {
        return yBase;
    }

    public int getX() {
        return x;
    }

    public int airportAssigned, carrierAssigned;

    public static void setTexture(Texture texture) {
        Aircraft.texture = texture;
    }

    public void setxBase(int xBase) {
        this.xBase = xBase;
    }

    public void setyBase(int yBase) {
        this.yBase = yBase;
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
    public Aircraft(int x,int y, int indexAssigned,String entityAssigned) {
        this.x = x;
        this.y = y;
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

        if (texture == null) {
            texture = aircraft9down;
        }
        if (entityAssigned.equals("carriers")){
            this.carrierAssigned = indexAssigned;
        } else {
            this.airportAssigned = indexAssigned;
        }
        this.rect = new CollisionRect(x, y, texture.getWidth(), texture.getHeight());
        this.invincible = false;
        this.destination = false;

    }
    public void update(float deltaTime){
        y -= SPEED * deltaTime;
        if (y < -texture.getHeight()){
            remove = true;
        }
        rect.move(x,y);
    }
    public void update(float deltaTime, String dif){

        if (x < xBase && y > yBase){
            texture = aircraft9down;
            x += SPEED * deltaTime;
            y -= SPEED * deltaTime;
        } else if (x > xBase && y > yBase){
            texture = aircraft9down;
            x -= SPEED * deltaTime;
            y -= SPEED * deltaTime;
        } else if (x < xBase && y < yBase){
            texture = aircraft9up;
            x += SPEED * deltaTime;
            y += SPEED * deltaTime;
        } else if (x > xBase && y < yBase){
            texture = aircraft9up;
            x -= SPEED * deltaTime;
            y += SPEED * deltaTime;
        } else if (y > yBase){
            texture = aircraft9down;
            y-=SPEED *deltaTime;
        }  else if (y < yBase){
            texture = aircraft9up;
            y+=SPEED *deltaTime;
        } else if (x > xBase){
            texture = aircraft9left;
            x-=SPEED *deltaTime;
        }  else if (x < xBase) {
            texture = aircraft9right;
            x += SPEED * deltaTime;
        }
        if (x == xBase && y == yBase){
            destination = true;
        }

        rect.move(x,y);
    }

    public void update(float deltaTime, Route route){
        if (x < route.xEnd && y > route.yEnd){
            texture = aircraft9down;
            x += SPEED * deltaTime;
            y -= SPEED * deltaTime;
        } else if (x > route.xEnd && y > route.yEnd){
            texture = aircraft9down;
            x -= SPEED * deltaTime;
            y -= SPEED * deltaTime;
        } else if (x < route.xEnd && y < route.yEnd){
            texture = aircraft9up;
            x += SPEED * deltaTime;
            y += SPEED * deltaTime;
        } else if (x > route.xEnd && y < route.yEnd){
            texture = aircraft9up;
            x -= SPEED * deltaTime;
            y += SPEED * deltaTime;
        } else if (y > route.yEnd){
            texture = aircraft9down;
            y-=SPEED *deltaTime;
        }  else if (y < route.yEnd){
            texture = aircraft9up;
            y+=SPEED *deltaTime;
        } else if (x > route.xEnd){
            texture = aircraft9left;
            x-=SPEED *deltaTime;
        }  else if (x < route.xEnd) {
            texture = aircraft9right;
            x += SPEED * deltaTime;
        }
        if (x == route.xEnd && y == route.yEnd){
            destination = true;
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
