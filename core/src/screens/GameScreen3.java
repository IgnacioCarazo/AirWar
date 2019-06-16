package screens;

import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import entities.*;
import tools.Route;

import java.util.ArrayList;

public class GameScreen3 implements Screen {
    // constants
    private final int SPEED = 300;
    private final float MOVE_WAIT_TIME = 0.02f;
    private static float SHOOT_WAIT_TIME_A = 0.8f;
    private static final float AIRCRAFT_SPAWN_TIME = 1f;
    private static final float ROUTE_DANGER_TIMER = 10f;

    //statics
    public static int routeIdentifier;
    public static ArrayList<Route> existingRoutes;
    public static int score;

    // Textures in Screen
    private Texture cliff_top;
    private Texture cliff_bottom;
    private Texture cliff_right;
    private Texture cliff_left;
    private Texture top_left;
    private Texture top_right;
    private Texture top_left_sea;
    private Texture bottom_left;
    private Texture bottom_right;
    private Texture tank;
    private Texture sky;
    private Texture grass;
    private Texture sea;
    private Texture forest1;
    private Texture forest2;
    private Texture forest3;
    private Texture forest4;
    private Texture town1;
    private Texture town2;
    private Texture largeMountain;
    private Texture smallMountain;
    private Texture carrier_vertical;
    private Texture carrier_horizontal;
    private Texture rotor;
    private Texture copter;
    private Texture goback;
    private Texture pause;
    private Texture unpause;
    private Texture home;

    // tools
    private String direction;
    private ShapeRenderer shapeRender;
    private float x, y;
    private float aircraftSpawnTimer;
    private float shootTimer;
    private float moveTimer;
    private float dangerTimer;
    private float rocketTimer;
    private boolean left;
    private boolean right;
    private BitmapFont font;
    private Main game;
    private int rocketSpeed;
    private boolean paused;
    private Screen savedScreen;
    private String username;
    // Entities
    private ArrayList<Bullet> bullets;
    private ArrayList<Rocket> rockets;
    private ArrayList<Explosion> explosions;
    private ArrayList<Aircraft> aircrafts;
    private ArrayList<Carrier> carriers;
    private ArrayList<Airport> airports;


    public GameScreen3(Main game, String username){
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        score = 0;
        shootTimer = 0;
        moveTimer = 0;
        dangerTimer = 0;
        rocketSpeed = 300;
        aircrafts = new ArrayList<Aircraft>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        rockets = new ArrayList<Rocket>();
        carriers = new ArrayList<Carrier>();
        shapeRender = new ShapeRenderer();
        airports = new ArrayList<Airport>();
        existingRoutes = new ArrayList<Route>();
        aircraftSpawnTimer = 0;
        routeIdentifier = 0;
        right = true;
        left = false;
        this.username = username;
    }
    @Override
    public void show() {
        home = new Texture("home.png");
        sky = new Texture("sky.jpg");
        goback = new Texture("return.png");
        pause = new Texture("pause.png");
        unpause = new Texture("forward.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(sky,900,0);

        //Home button
        if (Gdx.input.getX() < 960 && Gdx.input.getX() > 915 && Gdx.input.getY() < 880 && Gdx.input.getY() > 850){
            game.batch.draw(home,920,15);
            if (Gdx.input.isTouched()) {
                this.dispose();
                Airport.flag = false;
                Carrier.flag = false;
                game.setScreen(new MainMenu(game));
            }
        } else {
            game.batch.draw(home,915,10);
        }
        //Return button
        if (Gdx.input.getX() < 1005 && Gdx.input.getX() > 960 && Gdx.input.getY() < 880 && Gdx.input.getY() > 850){
            game.batch.draw(goback,965,15);
            if (Gdx.input.isTouched()) {
                this.dispose();
                Airport.flag = false;
                Carrier.flag = false;
                game.setScreen(new MapScreen(game));
            }
        } else {
            game.batch.draw(goback,960,10);
        }
        //Pause button
        if (Gdx.input.getX() < 1050 && Gdx.input.getX() > 1005 && Gdx.input.getY() < 880 && Gdx.input.getY() > 850){
            if (paused){
                game.batch.draw(unpause,1010,10);

            } else {
                game.batch.draw(pause,1010,15);

            }
            if (Gdx.input.isTouched()) {
                if (paused){
                    paused = false;
                } else {
                    paused = true;
                }
            }
        } else {
            if (paused){
                game.batch.draw(unpause,1005,5);

            } else {
                game.batch.draw(pause,1005,10);

            }
        }




        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
