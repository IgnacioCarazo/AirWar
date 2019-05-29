package Screens;


import Entities.Aircraft;
import Entities.Bullet;
import Entities.Carrier;
import Entities.Explosion;
import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Random;

import static Screens.MainMenu.MAX_AIRCRAFT_SPAWN_TIME;
import static Screens.MainMenu.MIN_AIRCRAFT_SPAWN_TIME;

public class GameScreen1 implements Screen {

    private static final int SPEED = 600;
    public static final int TANK_WIDTH = 92;
    public static final int TANK_HEIGHT = 110;
    public static final float SHOOT_WAIT_TIME = 0.3f;


    private Texture tank;
    private Texture sky;
    private Texture grass;
    private Texture sea;
    private Texture beach;
    private Texture beach_sea;
    private Texture beach_sea_deep;
    private Texture carrier_vertical;
    private Texture carrier_horizontal;
    private int x, y;
    private String direction;

    private float aircraftSpawnTimer;

    private Random random;


    private Main game;

    private ArrayList<Bullet> bullets;
    private ArrayList<Explosion> explosions;
    private ArrayList<Aircraft> aircrafts;
    private ArrayList<Carrier> carriers;
    private float shootTimer;

    public GameScreen1(Main game){
        this.game = game;

        random = new Random();
        shootTimer = 0;

        aircrafts = new ArrayList<Aircraft>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        carriers = new ArrayList<Carrier>();

        aircraftSpawnTimer = random.nextFloat() * (MAX_AIRCRAFT_SPAWN_TIME - MIN_AIRCRAFT_SPAWN_TIME) + MIN_AIRCRAFT_SPAWN_TIME;


    }

    @Override
    public void show() {
        tank = Aircraft.aircraft10right;
        sky = new Texture("sky.jpg");
        carrier_vertical = new Texture("carrier_vertical.png");
        carrier_horizontal = new Texture("carrier_horizontal.png");


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Codigo de aviones en el fondo

        aircraftSpawnTimer -= delta;



        //Spawn Carriers
        if (!Carrier.flag) {
            for (int i = 0; i < 2; i++) {
                int xCarrier = 300 + (int) (Math.random() * ((600 - 300) + 1));
                int yCarrier = 130 + (int) (Math.random() * ((630 - 130) + 1));
                System.out.println(xCarrier);
                System.out.println(yCarrier);
                carriers.add(new Carrier(xCarrier, yCarrier));
            }
        }

        //Codigo de spawn de aviones
        if (aircraftSpawnTimer <= 0) {
            System.out.println(carriers.size());
            if (carriers.size() <= 2) {
                aircraftSpawnTimer = random.nextFloat() * (MAX_AIRCRAFT_SPAWN_TIME - MIN_AIRCRAFT_SPAWN_TIME) + MIN_AIRCRAFT_SPAWN_TIME;
                int randomCarrierIndex = (int) (Math.random() * (2));
                aircrafts.add(new Aircraft(carriers.get(randomCarrierIndex).getX(), carriers.get(randomCarrierIndex).getY()));
            }
        }

        //Update de aviones
        ArrayList<Aircraft> aircraftsToRemove = new ArrayList<Aircraft>();
        for (Aircraft aircraft: aircrafts) {
            aircraft.update(delta,x,y);
            if (aircraft.remove){
                aircraftsToRemove.add(aircraft);
            }
        }

        //Update explosions
        ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        for (Explosion explosion: explosions) {
            explosion.update(delta);
            if (explosion.remove){
                explosionsToRemove.add(explosion);
            }
        }
        explosions.removeAll(explosionsToRemove);

        // codigo de disparos
        shootTimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;
            bullets.add(new Bullet(x + 46));
        }
        // update bullets
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove){
                bulletsToRemove.add(bullet);
            }
        }


        // codigo de direccion
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            tank = Aircraft.aircraft10right;
            x += SPEED * Gdx.graphics.getDeltaTime();


            if (x + TANK_WIDTH > Main.WIDTH){
                x = Main.WIDTH - TANK_WIDTH;
            }
        } if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            tank = Aircraft.aircraft10left;
            x -= SPEED * Gdx.graphics.getDeltaTime();
            if (x < 0){
                x = 0;
            }
        }


        //Check collisions aircraft/bullet
        for (Bullet bullet: bullets) {
            for (Aircraft aircraft: aircrafts) {
                if (bullet.getCollisionRect().collideWith(aircraft.getCollisionRect())){
                    bulletsToRemove.add(bullet);
                    aircraftsToRemove.add(aircraft);
                    explosions.add(new Explosion(aircraft.getX(),aircraft.getY()));
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        aircrafts.removeAll(aircraftsToRemove);


        game.batch.begin(); // Le dice al compilador que va a empezar a dibujar imagenes
        game.batch.draw(sky,0,0);

        // Render bullets
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        // Render Carriers
        for (Carrier carrier : carriers) {
            carrier.render(game.batch);
        }
        //Render aviones
        for (Aircraft aircraft: aircrafts) {
            aircraft.render(game.batch);
        }

        //Render explosions
        for (Explosion explosion : explosions) {
            explosion.render(game.batch);
        }

        Carrier.flag = true;


        game.batch.draw(tank, x, y);

        game.batch.end();// le dice al compilador que ya no va a dibujar mas imagenes
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
        tank.dispose();

    }
}
