package screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Aircraft;
import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Random;

public class MainMenu implements Screen {


    public static final float MIN_AIRCRAFT_SPAWN_TIME = 0.1f;
    public static final float MAX_AIRCRAFT_SPAWN_TIME = 5f;

    float aircraftSpawnTimer;
    int x;
    Main game;
    boolean flag = true;
    private Texture quitButton;
    private Texture playButton;
    private Texture scoreButton;
    private Texture sky;

    ArrayList<Aircraft> aircrafts;




    Random random;



    public MainMenu (Main game){
        this.game = game;

        aircrafts = new ArrayList<Aircraft>();
        random = new Random();
        aircraftSpawnTimer = random.nextFloat() * (MAX_AIRCRAFT_SPAWN_TIME - MIN_AIRCRAFT_SPAWN_TIME) + MIN_AIRCRAFT_SPAWN_TIME;


    }

    @Override
    public void show() {
        playButton = new Texture("play_button.png");
        quitButton = new Texture("quit_button.png");
        scoreButton = new Texture("score_button.png");
        sky = new Texture("sky.jpg");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 191/255f, 255/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();


        game.batch.draw(sky,0,0);

        // Codigo de aviones en el fondo
        aircraftSpawnTimer -= delta;
        //Codigo de spawn de aviones
        if (aircraftSpawnTimer <= 0){

            aircraftSpawnTimer = random.nextFloat() * (MAX_AIRCRAFT_SPAWN_TIME - MIN_AIRCRAFT_SPAWN_TIME) + MIN_AIRCRAFT_SPAWN_TIME;
            aircrafts.add(new Aircraft(random.nextInt(Gdx.graphics.getWidth()- 120),5, (int) (Math.random()*10)));

        }

        //Update de aviones
        ArrayList<Aircraft> aircraftsToRemove = new ArrayList<Aircraft>();
        for (Aircraft aircraft: aircrafts) {
            aircraft.update(delta);
            if (aircraft.remove){
                aircraftsToRemove.add(aircraft);
            }
        }
        aircrafts.removeAll(aircraftsToRemove);

        //Render de aviones
        for (Aircraft aircraft: aircrafts) {
            aircraft.render(game.batch);
        }


        // codigo de botones
        if (Gdx.input.getX() < 500 + playButton.getWidth() && Gdx.input.getX() > 500 && Gdx.input.getY() < 300 + playButton.getHeight() && Gdx.input.getY() > 300){
            game.batch.draw(playButton,505,505);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MapScreen(game));
            }
        } else {
            game.batch.draw(playButton,500,500);
        }
        if (Gdx.input.getX() < 500 + scoreButton.getWidth() && Gdx.input.getX() > 500 && Gdx.input.getY() < 400 + scoreButton.getHeight() && Gdx.input.getY() > 400) {
            game.batch.draw(scoreButton,505,405);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new HighScoreScreen(game));
            }
        } else {
            game.batch.draw(scoreButton,500,400);
        }
        if (Gdx.input.getX() < 500 + quitButton.getWidth() && Gdx.input.getX() > 500 && Gdx.input.getY() < 500 + quitButton.getHeight() && Gdx.input.getY() > 500) {
            game.batch.draw(quitButton,505,305);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(quitButton,500,300);

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
