package screens;


import entities.*;
import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import tools.Methods;
import tools.Route;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class GameScreen1 implements Screen {

    private static final int SPEED = 600;
    public static final int TANK_WIDTH = 92;
    public static final int TANK_HEIGHT = 110;
    public static final float SHOOT_WAIT_TIME = 0.3f;
    public static final float LOW_DANGER_MAX_AIRCRAFT_SPAWN_TIME = 5f;
    public static final float AIRCRAFT_SPAWN_TIME = 1f;


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
    private Texture grass0;
    private Texture grass1;
    private Texture grass2;
    private Texture grass3;
    private Texture grass4;
    private Texture grass5;
    private Texture grass6;
    private Texture grass7;
    private Texture grass8;
    private Texture grass9;
    private Texture grass10;
    private Texture grass11;
    private Texture grass12;
    private Texture largeMountain;
    private Texture smallMountain;



    private Texture carrier_vertical;
    private Texture carrier_horizontal;
    private int x, y;
    private String direction;
    private ShapeRenderer shapeRender;

    private float aircraftSpawnTimer;

    private Random random;


    private Main game;

    private ArrayList<Bullet> bullets;
    private ArrayList<Explosion> explosions;
    private ArrayList<Aircraft> aircrafts;
    private ArrayList<Carrier> carriers;
    private ArrayList<Airport> airports;
    private ArrayList<Texture> grass_tiles;
    private float shootTimer;

    public GameScreen1(Main game){
        this.game = game;
        random = new Random();
        shootTimer = 0;
        aircrafts = new ArrayList<Aircraft>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        carriers = new ArrayList<Carrier>();
        shapeRender = new ShapeRenderer();
        grass_tiles = new ArrayList<Texture>();
        airports = new ArrayList<Airport>();
        aircraftSpawnTimer = AIRCRAFT_SPAWN_TIME;
    }

    @Override
    public void show() {
        tank = Aircraft.aircraft10right;
        sky = new Texture("sky.jpg");
        sea = new Texture("sea_small.png");
        grass = new Texture("terrain_center_small.png");
        carrier_vertical = new Texture("carrier_vertical.png");
        carrier_horizontal = new Texture("carrier_horizontal.png");
        grass0 = new Texture("grass_corner_northwest_f1.png");
        grass1 = new Texture("grass_corner_northeast_f1.png");
        grass2 = new Texture("grass_corner_inner_northwest_f1.png");
        grass3 = new Texture("grass_edge_north_A_f1.png");
        grass4 = new Texture("grass_corner_inner_northeast_f1.png");
        grass5 = new Texture("grass_edge_west_A_f1.png");
        grass6 = new Texture("grass_center_A_f1.png");
        grass7 = new Texture("grass_edge_east_A_f1.png");
        grass8 = new Texture("grass_corner_inner_southwest_f1.png");
        grass9 = new Texture("grass_edge_south_A_f1.png");
        grass10 = new Texture("grass_corner_inner_southwest_f1.png");
        grass11 = new Texture("grass_corner_southwest_f1.png");
        grass12 = new Texture("grass_corner_southeast_f1.png");
        smallMountain = new Texture("mountain_small.png");
        largeMountain = new Texture("mountain_medium.png");
        cliff_top = new Texture("terrain_edge_south_A_f1.png");
        cliff_bottom = new Texture("terrain_edge_north_A_f1.png");
        cliff_left = new Texture("terrain_edge_east_A_f1.png");
        cliff_right = new Texture("terrain_edge_west_A_f1.png");
        top_left = new Texture("terrain_corner_inner_northwest_f1.png");
        top_right = new Texture("terrain_corner_inner_northeast_f1.png");
        bottom_left = new Texture("terrain_corner_inner_southwest_f1.png");
        bottom_right = new Texture("terrain_corner_inner_southeast_f1.png");
        top_left_sea = new Texture("terrain_corner_northwest_f1.png");
        forest1 =  new Texture("tree_A.png");
        forest2 =  new Texture("tree_B.png");
        forest3 =  new Texture("tree_C.png");
        forest4 =  new Texture("tree_D.png");
        town1 =  new Texture("town_C.png");
        town2 =  new Texture("town_D.png");

        grass_tiles.add(grass0);
        grass_tiles.add(grass);
        grass_tiles.add(grass1);
        grass_tiles.add(grass3);
        grass_tiles.add(grass5);
        grass_tiles.add(grass6);
        grass_tiles.add(grass7);
        grass_tiles.add(grass8);
        grass_tiles.add(grass9);
        grass_tiles.add(grass10);
        grass_tiles.add(grass11);
        grass_tiles.add(grass);
        grass_tiles.add(grass12);


    }



    private void drawGrass(int xDraw,int yDraw, ArrayList<Texture> textures){
        int cont = 0;
        int row = xDraw;
        int column = yDraw;
        int index = 0;
        for (int i = 0; i < 3; i++) {
                while (cont != 3) {
                    game.batch.draw(textures.get(index), row, column);
                    cont++;
                    row += 16;
                    index++;
                }
                row = xDraw;
                column += 16;
                cont = 0;

        }
    }


    private void assignRoutes(){

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Codigo de aviones en el fondo

        aircraftSpawnTimer -= delta;



        //Spawn Carriers
        if (!Carrier.flag) {
                int xCarrier1 = 600 + (int) (Math.random() * ((700 - 600) + 1));
                int xCarrier2 = 300 + (int) (Math.random() * ((400 - 300) + 1));
                int yCarrier1 = 430 + (int) (Math.random() * ((630 - 430) + 1));
                int yCarrier2 = 130 + (int) (Math.random() * ((630 - 130) + 1));
                carriers.add(new Carrier(xCarrier1, yCarrier1, createRandomRoutes()));
                carriers.add(new Carrier(xCarrier2, yCarrier2, createRandomRoutes()));
        }

        //Spawn Airports
        if (!Airport.flag) {
            int xAirport1 = (int) (Math.random() * ((200) + 1));
            int xAirport2 = (int) (Math.random() * ((200) + 1));
            int yAirport1 = 130 + (int) (Math.random() * ((330 - 130) + 1));
            int yAirport2 = 430 + (int) (Math.random() * ((630 - 430) + 1));
            int xAirport3 = 220 + (int) (Math.random() * ((800 - 220) + 1));
            int yAirport3 = 700 + (int) (Math.random() * ((800 - 700) + 1));

            airports.add(new Airport(xAirport1, yAirport1));
            airports.add(new Airport(xAirport2, yAirport2));
            airports.add(new Airport(xAirport3,yAirport3));
        }

        //Codigo de spawn de aviones (TEMPORAL)
        if (aircraftSpawnTimer <= 0) {
            if (carriers.size() <= 2) {
                aircraftSpawnTimer = AIRCRAFT_SPAWN_TIME;

                int randomAirportIndex = Methods.randomAirportIndex();
                int randomCarrierIndex = Methods.randomCarrierIndex();
                System.out.println("From Carrier " + randomCarrierIndex);
                ArrayList<String> ports = new ArrayList<String>();
                ports.add("carriers");
                ports.add("airports");
                aircrafts.add(new Aircraft(carriers.get(randomCarrierIndex).getX(),carriers.get(randomCarrierIndex).getY(),randomAirportIndex,ports.get(randomCarrierIndex)));
            }
        }

        //Update de aviones (TEMPORAL)
        ArrayList<Aircraft> aircraftsToRemove = new ArrayList<Aircraft>();
        for (Aircraft aircraft: aircrafts) {
            if (aircraft.getX() == carriers.get(0).getX() && aircraft.getY() == carriers.get(0).getY()){
                aircraft.setxBase(airports.get(aircraft.carrierAssigned).getX());
                aircraft.setyBase(airports.get(aircraft.carrierAssigned).getY());

            } else if (aircraft.getX() == carriers.get(1).getX() && aircraft.getY() == carriers.get(1).getY()){
                aircraft.setxBase(airports.get(aircraft.airportAssigned).getX());
                aircraft.setyBase(airports.get(aircraft.airportAssigned).getY());
            }
            aircraft.update(delta,"");
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


        //Check collisions bullet/aircraft
        for (Bullet bullet: bullets) {
            for (Aircraft aircraft: aircrafts) {
                if (bullet.getCollisionRect().collideWith(aircraft.getCollisionRect())){
                    if (aircraft.invincible) {
                        bulletsToRemove.add(bullet);

                    } else {
                        bulletsToRemove.add(bullet);
                        aircraftsToRemove.add(aircraft);
                        explosions.add(new Explosion(aircraft.getX(), aircraft.getY()));
                    }
                }
            }
        }



        //Check collisions aircraft/carrier
        for (Aircraft aircraft: aircrafts){
            if (aircraft.carrierAssigned == 0){
                if (aircraft.getCollisionRect().collideWith(carriers.get(aircraft.carrierAssigned).getCollisionRect())){
                aircraftsToRemove.add(aircraft);
            }
        }
            if (aircraft.carrierAssigned == 1){
                if (aircraft.getCollisionRect().collideWith(carriers.get(aircraft.carrierAssigned).getCollisionRect())){
                aircraftsToRemove.add(aircraft);
                }
            }

        }

        //Check collisiions aircraft/airport
        for (Aircraft aircraft: aircrafts){
//            if (aircraft.airportAssigned == 0){
//                if (aircraft.getCollisionRect().collideWith(airports.get(0).getCollisionRect())){
//                    aircraftsToRemove.add(aircraft);
//                }
//            }
//            if (aircraft.airportAssigned == 1){
//                if (aircraft.getCollisionRect().collideWith(airports.get(1).getCollisionRect())){
//                    aircraftsToRemove.add(aircraft);
//                }
//            }
//            if (aircraft.airportAssigned == 2){
//                if (aircraft.getCollisionRect().collideWith(airports.get(2).getCollisionRect())){
//                    aircraftsToRemove.add(aircraft);
//                }
//            }
            if (aircraft.destination){
                aircraftsToRemove.add(aircraft);
            }
        }
        bullets.removeAll(bulletsToRemove);
        aircrafts.removeAll(aircraftsToRemove);

        game.batch.begin(); // Le dice al compilador que va a empezar a dibujar imagenes
        Methods.drawTerrain(0,900,0,900, grass, game);

        Methods.drawTerrain(600,1200,130,900, grass,game);

        Methods.drawTerrain(300,600,130,630, sea,game);
        Methods.drawTerrain(600,900,430,630,sea,game);
        Methods.drawTerrain(0,300,130,900, grass,game);
        Methods.drawTerrain(0,900,650,900, grass,game);
        Methods.drawTerrain(300,900,625,630, cliff_top,game);
        Methods.drawTerrain(300,310,130,630, cliff_left,game);
        Methods.drawTerrain(600,610,130,430, cliff_right,game);
        Methods.drawTerrain(610,900,430,440, cliff_bottom,game);
        Methods.drawTerrain(300,600,130,140, cliff_bottom,game);
        Methods.drawTerrain(0,900,850,900,largeMountain,game);



        game.batch.draw(top_left,300,625);
        game.batch.draw(top_left_sea,600,430);
        game.batch.draw(bottom_left,300,130);
        game.batch.draw(bottom_right,600,130);





        game.batch.draw(sky,900,0);
        game.batch.draw(tank, x, y);
        game.batch.draw(smallMountain, 120,400);
        Methods.drawTerrain(120,120 + smallMountain.getWidth(),400,415,forest1, game);
        Methods.drawTerrain(0,890,90,95,forest4,game);
        int towny = 130;
        int townx = 670;
        for (int i = 0; i < 9; i++) {
            game.batch.draw(town1,townx,towny);
            towny += 30;

        }
        Methods.drawTerrain(600,700, 700, 740,town2, game);

        // Render bullets
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        // Render Carriers
        for (Carrier carrier : carriers) {
            carrier.render(game.batch);
        }

        // Render Airports
        for (Airport airport : airports) {
            airport.render(game.batch);
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
        Airport.flag = true;




        game.batch.end();// le dice al compilador que ya no va a dibujar mas imagenes
        Methods.drawDottedLine(shapeRender,10,carriers.get(0).getX() + carrier_horizontal.getWidth()/2, carriers.get(0).getY(),carriers.get(1).getX()+carrier_horizontal.getWidth()/2, carriers.get(1).getY());

    }


    private ArrayList<Route> createRandomRoutes() {
        ArrayList<Route> routes = new ArrayList<Route>();
        return routes;
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
