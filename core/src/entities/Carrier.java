package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import tools.CollisionRect;
import tools.Route;

import java.util.ArrayList;

public class Carrier {
    private static Texture texture;
    private ArrayList<Route> routes;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static boolean flag;

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    private CollisionRect rect;

    private int x, y;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Carrier(int x, int y, int index){
        this.x = x;
        this.y = y;
        this.index = index;
        if (texture == null){
            texture = new Texture("carrier_horizontal.png");
        }

        this.rect = new CollisionRect(x,y,texture.getWidth()/2,texture.getHeight()/2);

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
