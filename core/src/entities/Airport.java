package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.GameScreen1;
import tools.CollisionRect;
import tools.Methods;
import tools.Route;

import java.util.ArrayList;

public class Airport {
    private static Texture texture;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public char identifier;
    public int intidentifier;
    public static boolean flag;
    private CollisionRect rect;

    private int x, y, index;
    private ArrayList<Route> routes;
    public ArrayList<ArrayList<Route>> destiny;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public Airport(int x, int y, int index){
        this.x = x;
        this.y = y;
        this.index = index;


        if (texture == null){
            texture = new Texture("airport.png");
        }
        this.intidentifier = GameScreen1.routeIdentifier;
        this.identifier = Methods.assignRouteIdentifier(GameScreen1.routeIdentifier);
        this.rect = new CollisionRect(x,y,texture.getWidth()/3,texture.getHeight()/3);
        GameScreen1.routeIdentifier += 1;
        this.destiny = new ArrayList<ArrayList<Route>>();

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
