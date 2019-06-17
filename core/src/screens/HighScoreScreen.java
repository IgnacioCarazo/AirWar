package screens;

import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import tools.Score;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HighScoreScreen implements Screen {

    private Texture home;
    private Texture sky;
    private BitmapFont font;
    private Main game;
    private HashMap hashMap;

    public HighScoreScreen(Main game){
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        this.hashMap = Score.extraerDatos();

    }
    @Override
    public void show() {
        sky = new Texture("sky.jpg");
        home = new Texture("home.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // fondo de pantalla
        game.batch.draw(sky,0,0);

        //Return button
        GlyphLayout layout1 = new GlyphLayout(font, "Press to return");
        font.draw(game.batch, layout1, 600, 150);
        if (Gdx.input.getX() < 870 && Gdx.input.getX() > 825 && Gdx.input.getY() < 840 && Gdx.input.getY() > 810){
            game.batch.draw(home,830,55);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            game.batch.draw(home,825,50);
        }
        // BAJAR DE 35 EN 35 LOS VALORES DE Y EN EL FOR CON LOS SCORES Y NOMBRES

        // Names
        GlyphLayout layout2 = new GlyphLayout(font, "Name");
        font.draw(game.batch, layout2, 100, 875);
        // Scores
        GlyphLayout layout3 = new GlyphLayout(font, "Score");
        font.draw(game.batch, layout3, 400, 875);

        //Scores displayer
        Set<String> keys = hashMap.keySet();
        int posy = 840;
        for(String key: keys){
            GlyphLayout layoutA = new GlyphLayout(font, "" + key);
            font.draw(game.batch, layoutA, 100, posy);
            posy -= 35;
        }
        Iterator<Integer> itr = hashMap.values().iterator();
        int posY = 840;
        while (itr.hasNext()){
            GlyphLayout layoutA = new GlyphLayout(font, "" + itr.next());
            font.draw(game.batch, layoutA, 400, posY);
            posY -= 35;
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
