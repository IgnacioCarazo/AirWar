package screens;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import entities.*;
import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import grafomatriz.MatrizAdyacencia;
import grafomatriz.Recorrido;
import tools.Methods;
import tools.Route;
import java.util.ArrayList;
import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen1 implements Screen {
    // constants
    private final int MEDIUM_SPEED = 300;
    private final int LOW_SPEED = 200;
    private final int HIGH_SPEED = 400;
    private final float MOVE_WAIT_TIME = 0.02f;
    private final float AIRCRAFT_SPAWN_TIME = 5f;
    private final float ROUTE_DANGER_TIMER = 30f;
    private final float AIRCRAFT_ENTITY_MIN_WAIT_TIME = 1F;
    private final float AIRCRAFT_ENTITY_MAX_WAIT_TIME = 2F;
    private final float MOVE_SPEED_CHANGE_MIN_WAIT_TIME = 5F;
    private final float MOVE_SPEED_CHANGE_MAX_WAIT_TIME = 10F;

    //statics
    public static int routeIdentifier;
    public  ArrayList<Route> existingRoutes;
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
   //timers
    private float moveSpeedTimer;
    private float aircraftWaitTimer;
    private float aircraftSpawnTimer;
    private float shootTimer;
    private float moveTimer;
    private float dangerTimer;
    private float rocketTimer;
    // tools
    private ShapeRenderer shapeRender;
    private float x, y;
    private boolean left;
    private boolean right;
    private BitmapFont font;
    private Main game;
    private int rocketSpeed;
    private boolean paused;
    private int nodeQuantity;
    private float SHOOT_WAIT_TIME_A = 0.8f;
    private ArrayList<Integer> movementSpeeds;
    private int tempspeed;
    public int aircraftNumber = 0;
    private String username;

    // Entities
    private ArrayList<Bullet> bullets;
    private ArrayList<Rocket> rockets;
    private ArrayList<Explosion> explosions;
    private ArrayList<Aircraft> aircrafts;
    private ArrayList<Carrier> carriers;
    private ArrayList<Airport> airports;
    private ArrayList<String> ports;
    //Pathfinding
    private Recorrido actualPaths;
    private MatrizAdyacencia grafo;

    public GameScreen1(Main game, String username){
        this.username = username;
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        font.getData().setScale((float) 0.5);
        score = 0;
        tempspeed = 0;
        shootTimer = 0;
        moveTimer = 0;
        dangerTimer = 0;
        moveSpeedTimer = random.nextFloat() * (MOVE_SPEED_CHANGE_MAX_WAIT_TIME - MOVE_SPEED_CHANGE_MIN_WAIT_TIME) + MOVE_SPEED_CHANGE_MIN_WAIT_TIME;
        aircraftWaitTimer = random.nextFloat() * (AIRCRAFT_ENTITY_MAX_WAIT_TIME - AIRCRAFT_ENTITY_MIN_WAIT_TIME) + AIRCRAFT_ENTITY_MIN_WAIT_TIME;
        rocketSpeed = 300;
        aircrafts = new ArrayList<Aircraft>();
        explosions = new ArrayList<Explosion>();
        bullets = new ArrayList<Bullet>();
        rockets = new ArrayList<Rocket>();
        carriers = new ArrayList<Carrier>();
        shapeRender = new ShapeRenderer();
        airports = new ArrayList<Airport>();
        ports = new ArrayList<String>();
        ports.add("carriers") ;
        ports.add("airports");
        ArrayList<String> ports = new ArrayList<String>();
        existingRoutes = new ArrayList<Route>();
        movementSpeeds = new ArrayList<Integer>();
        movementSpeeds.add(LOW_SPEED);
        movementSpeeds.add(MEDIUM_SPEED);
        movementSpeeds.add(HIGH_SPEED);
        aircraftSpawnTimer = 0;
        routeIdentifier = 0;
        right = true;
        left = false;
    }

    @Override
    public void show() {
        home = new Texture("home.png");
        sky = new Texture("sky.jpg");
        sea = new Texture("sea_small.png");
        grass = new Texture("terrain_center_small.png");
        carrier_vertical = new Texture("carrier_vertical.png");
        carrier_horizontal = new Texture("carrier_horizontal.png");
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
        copter = new Texture("copter.png");
        rotor = new Texture("rotor.png");
        goback = new Texture("return.png");
        pause = new Texture("pause.png");
        unpause = new Texture("forward.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (paused){
            delta = 0;
        }
        //Spawn Carriers
        if (!Carrier.flag) {
                int xCarrier1 = 600 + (int) (Math.random() * ((700 - 600) + 1));
                int xCarrier2 = 300 + (int) (Math.random() * ((400 - 300) + 1));
                int yCarrier1 = 430 + (int) (Math.random() * ((630 - 430) + 1));
                int yCarrier2 = 130 + (int) (Math.random() * ((630 - 130) + 1));
                carriers.add(new Carrier(xCarrier1, yCarrier1,routeIdentifier));
                carriers.add(new Carrier(xCarrier2, yCarrier2,routeIdentifier));
                nodeQuantity += 2;
        }
        //Spawn Airports
        if (!Airport.flag) {
            int xAirport1 = (int) (Math.random() * ((200) + 1));
            int xAirport2 = (int) (Math.random() * ((200) + 1));
            int yAirport1 = 130 + (int) (Math.random() * ((330 - 130) + 1));
            int yAirport2 = 430 + (int) (Math.random() * ((630 - 430) + 1));
            int xAirport3 = 220 + (int) (Math.random() * ((800 - 220) + 1));
            int yAirport3 = 700 + (int) (Math.random() * ((800 - 700) + 1));
            airports.add(new Airport(xAirport1, yAirport1, routeIdentifier));
            airports.add(new Airport(xAirport2, yAirport2, routeIdentifier));
            airports.add(new Airport(xAirport3,yAirport3, routeIdentifier));
            nodeQuantity += 3;
        }
        if (!Airport.flag){
            //creacion de Grafo de rutas en blanco
            grafo = new MatrizAdyacencia(nodeQuantity);
            //Asignacion de rutas
            Methods.assignRoutes(carriers, airports, existingRoutes,routeIdentifier, grafo);
            Methods.addRouteArrayList(grafo, carriers,airports, existingRoutes, actualPaths);
            System.out.println("Total routes: " +  existingRoutes.size());
        }

        //Codigo de rutas mas cortas

        //Codigo de spawn de aviones
        aircraftSpawnTimer += delta;
        if (aircraftSpawnTimer >= AIRCRAFT_SPAWN_TIME) {
            Methods.aircraftSpawn(ports, carriers, airports,  aircrafts, aircraftNumber);
            aircraftSpawnTimer = 0;
            aircraftNumber ++;
        }

        //Update de aviones
        ArrayList<Aircraft> aircraftsToRemove = new ArrayList<Aircraft>();
        aircraftWaitTimer -= delta;
        Methods.aircraftUpdate(aircrafts,aircraftsToRemove,aircraftWaitTimer, delta, AIRCRAFT_ENTITY_MIN_WAIT_TIME, AIRCRAFT_ENTITY_MAX_WAIT_TIME);
        if (aircraftWaitTimer <= 0){
            aircraftWaitTimer = random.nextFloat() * (AIRCRAFT_ENTITY_MAX_WAIT_TIME - AIRCRAFT_ENTITY_MIN_WAIT_TIME) + AIRCRAFT_ENTITY_MIN_WAIT_TIME;
        }

        //Update explosions
        ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        Methods.explosionsUpdate(explosions, explosionsToRemove,  delta);

        // shooting code bullets
        shootTimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME_A) {
            shootTimer = 0;
            if (SHOOT_WAIT_TIME_A >= 0.3f) {
                SHOOT_WAIT_TIME_A = SHOOT_WAIT_TIME_A - 0.05f;
            }
            bullets.add(new Bullet(x +  15));
        } else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME_A) {
            SHOOT_WAIT_TIME_A = 0.8f;
        }

        //shooting code rockets
//        if (Gdx.input.isKeyPressed(Input.Keys.R) && shootTimer >= SHOOT_WAIT_TIME_A) {
//            rocketTimer += delta;
//            shootTimer = 0;
//            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
//                if (rocketTimer >= 5f) {
//                    rockets.add(new Rocket(x, 800));
//                    rocketTimer = 0;
//
//                } else if (rocketTimer >= 4f) {
//                    rockets.add(new Rocket(x, 600));
//                    rocketTimer = 0;
//
//                } else if (rocketTimer >= 3f) {
//                    rockets.add(new Rocket(x, 400));
//                    rocketTimer = 0;
//
//                } else if (rocketTimer < 3f) {
//                    rockets.add(new Rocket(x, 200));
//                    rocketTimer = 0;
//
//                }
//            }
//        }

        // update bullets
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        Methods.bulletsUpdate(bullets, bulletsToRemove, delta);

        // update rockets
        ArrayList<Rocket> rocketsToRemove = new ArrayList<Rocket>();
        Methods.rocketsUpdate(rockets, rocketsToRemove, delta);

        // codigo de direccion
        moveTimer += delta;
        moveSpeedTimer -= delta;
        if (!Airport.flag){
            int number = (int) (Math.random() * (3));
            tempspeed = movementSpeeds.get(number);
        }
        if (moveSpeedTimer <= 0){
            int number = (int) (Math.random() * (3));
            tempspeed = movementSpeeds.get(number);
            moveSpeedTimer = random.nextFloat() * (MOVE_SPEED_CHANGE_MAX_WAIT_TIME - MOVE_SPEED_CHANGE_MIN_WAIT_TIME) + MOVE_SPEED_CHANGE_MIN_WAIT_TIME;
        }
        if (moveTimer >= MOVE_WAIT_TIME && right) {
            x += tempspeed * Gdx.graphics.getDeltaTime();
            if (x + copter.getWidth() > 900) {
                x = 900 - copter.getWidth();
                left = true;
                right = false;
            }
            moveTimer = 0;
        }
        if (moveTimer >= MOVE_WAIT_TIME && left) {
            x -= tempspeed * Gdx.graphics.getDeltaTime();
            if (x < 0){
                x = 0;
                left = false;
                right = true;
            }
            moveTimer = 0;
        }



        //route dmgLVL modification over time
        dangerTimer += delta;
        if (dangerTimer >= ROUTE_DANGER_TIMER) {
            Methods.changeRouteDangerLVL(existingRoutes, carriers, airports);
            dangerTimer = 0;
        }

        //Check collisions bullet/aircraft
        for (Bullet bullet: bullets) {
            for (Aircraft aircraft: aircrafts) {
                if (bullet.getCollisionRect().collideWith(aircraft.getCollisionRect())){
                    if (aircraft.invincible) {
                        bulletsToRemove.add(bullet);
                    } else {
                        Methods.destroyAircraft(bulletsToRemove,bullet,aircraftsToRemove,aircraft, explosions, existingRoutes, carriers, airports);
                        score += 100;
                        for (Carrier carrier : carriers){
                            Methods.addDMGtoWeight(carrier.getRoutes(), aircraft);
                        }
                        for (Airport airport : airports){
                            Methods.addDMGtoWeight(airport.getRoutes(), aircraft);
                        }
                    }
                }
            }
        }

        //Check collisions rocket/aircraft
        for (Rocket rocket: rockets) {
            for (Aircraft aircraft: aircrafts) {
                if (rocket.getCollisionRect().collideWith(aircraft.getCollisionRect())){
                    if (aircraft.invincible) {
                        rocketsToRemove.add(rocket);

                    } else {
                        rocketsToRemove.add(rocket);
                        aircraftsToRemove.add(aircraft);
                        explosions.add(new Explosion(aircraft.getX(), aircraft.getY()));
                        Methods.changeRouteDanger(existingRoutes,carriers,airports, aircraft.route.identifier);
                        score += 100;
                    }
                }
            }
        }

        //Check collisions aircraft/carrier
        for (Aircraft aircraft: aircrafts){
            if (aircraft.destination){
                aircraftsToRemove.add(aircraft);
            }
        }
        bullets.removeAll(bulletsToRemove);
        aircrafts.removeAll(aircraftsToRemove);
        rockets.removeAll(rocketsToRemove);

        game.batch.begin(); // Le dice al compilador que va a empezar a dibujar imagenes

        //Map rendering
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
        game.batch.draw(copter,x,y);
        game.batch.draw(rotor, x + copter.getWidth() + 17 - rotor.getWidth(),20);
        game.batch.draw(smallMountain, 120,400);
        Methods.drawTerrain(120,120 + smallMountain.getWidth(),400,415,forest1, game);
        Methods.drawTerrain(0,890,87,92,forest4,game);
        int towny = 130;
        int townx = 670;
        for (int i = 0; i < 9; i++) {
            game.batch.draw(town1,townx,towny);
            towny += 30;
        }
        Methods.drawTerrain(600,700, 700, 740,town2, game);

        // Aircraft identifier
        int ypos = 400;

        for (Aircraft aircraft : aircrafts){
            GlyphLayout Alayout = new GlyphLayout(font, " " + aircraft.identifier);
            font.draw(game.batch, Alayout, aircraft.getX(), aircraft.getY());
            GlyphLayout layout1 = new GlyphLayout(font, "A" + aircraft.identifier);
            font.draw(game.batch, layout1, 905, ypos);
            GlyphLayout layout2 = new GlyphLayout(font, "" + Methods.printRoutes(aircraft.routesToFollow));
            font.draw(game.batch, layout2, 960, ypos);

            ypos -= 20;
        }


        // Airport/Carrier identifier render
        for (Carrier carrier : carriers){
            GlyphLayout layout = new GlyphLayout(font, " " + carrier.identifier);
            font.draw(game.batch, layout, carrier.getX(), carrier.getY());
        }
        for (Airport airport : airports){
            GlyphLayout layout = new GlyphLayout(font, " " + airport.identifier);
            font.draw(game.batch, layout, airport.getX(), airport.getY());
        }

        // Score
        GlyphLayout scoreLayout = new GlyphLayout(font, "SCORE");
        font.draw(game.batch, scoreLayout, 905, 855);
        GlyphLayout scoreLayout2 = new GlyphLayout(font, " " + score);
        font.draw(game.batch, scoreLayout2, 900, 835);
        GlyphLayout usernameLayout = new GlyphLayout(font, "" + username);
        font.draw(game.batch, usernameLayout, 905, 875);

        // Route
        GlyphLayout routeLayout = new GlyphLayout(font, "ROUTE");
        font.draw(game.batch, routeLayout, 905, 805);
        // Weight
        GlyphLayout weightLayout = new GlyphLayout(font, "WEIGHT");
        font.draw(game.batch, weightLayout, 1000, 805);
        // Route/Weight data
            int posy = 785;
            for (Route route : existingRoutes){
                if (route.index != route.indexAssigned) {
                    GlyphLayout temprouteLayout = new GlyphLayout(font, " " + route.identifier);
                    font.draw(game.batch, temprouteLayout, 900, posy);
                    // Weight
                    int tempweight = route.weight + route.dmgweight;
                    GlyphLayout tempweightLayout = new GlyphLayout(font, " " + tempweight);
                    font.draw(game.batch, tempweightLayout, 1000, posy);
                    posy -= 20;
                }
            }
        // Render bullets
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        // Render rockets
        for (Rocket rocket : rockets) {
            rocket.render(game.batch);
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
        //buttons

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

        //Speed layout
        GlyphLayout tempSpeedLayoutA = new GlyphLayout(font, "CURRENT SPEED");
        font.draw(game.batch, tempSpeedLayoutA, 950, 150);
        GlyphLayout tempSpeedLayoutB = new GlyphLayout(font, "" + tempspeed);
        font.draw(game.batch, tempSpeedLayoutB, 1020, 120);

        game.batch.end();// le dice al compilador que ya no va a dibujar mas imagenes
            for (Route route: existingRoutes){
                Methods.drawDottedLine(shapeRender,5,route.xStart + 40, route.yStart,route.xEnd + 50, route.yEnd);
            }


            if (aircraftNumber == 30){
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}