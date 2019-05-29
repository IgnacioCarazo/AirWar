package Screens;

import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class HighScoreScreen implements Screen {

    Texture gobackarrow;
    Main game;

    public HighScoreScreen(Main game){
        this.game = game;
        gobackarrow = new Texture("go_back_score.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        if (Gdx.input.getX() < 100 + gobackarrow.getWidth() && Gdx.input.getX() > 100 && Gdx.input.getY() < 50 + gobackarrow.getHeight() && Gdx.input.getY() > 50){
            game.batch.draw(gobackarrow,105,505);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            game.batch.draw(gobackarrow,100,500);
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
