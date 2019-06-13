package screens;

import com.airwar.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MapScreen implements Screen {
    private Main game;
    private Texture sky;
    private Texture map1;
    private Texture map2;
    private Texture map3;
    private Texture home;
    private BitmapFont font;



    public MapScreen(Main game){
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

    }
    @Override
    public void show() {
        sky = new Texture("sky.jpg");
        map1 = new Texture("tempmap.png");
        map2 = new Texture("tempmap.png");
        map3 = new Texture("tempmap.png");
        home = new Texture("home.png");


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // fondo de pantalla
        game.batch.draw(sky,0,0);

        //Map1
        GlyphLayout layout1 = new GlyphLayout(font, "Map 1");
        font.draw(game.batch, layout1, 200, 850);
        if (Gdx.input.getX() < 200 + map1.getWidth() && Gdx.input.getX() > 200 && Gdx.input.getY() < 150 + map1.getHeight() && Gdx.input.getY() > 150){
            game.batch.draw(map1,205,505);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen1(game));
            }
        } else {
            game.batch.draw(map1,200,500);
        }
        //Map2
        GlyphLayout layout2 = new GlyphLayout(font, "Map 2");
        font.draw(game.batch, layout2, 200, 450);
        if (Gdx.input.getX() < 200 + map2.getWidth() && Gdx.input.getX() > 200 && Gdx.input.getY() < 550 + map2.getHeight() && Gdx.input.getY() > 550){
            game.batch.draw(map2,205,105);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen2(game));
            }
        } else {
            game.batch.draw(map2,200,100);
        }
        //Map3
        GlyphLayout layout3 = new GlyphLayout(font, "Map 3");
        font.draw(game.batch, layout3, 800, 850);
        if (Gdx.input.getX() < 800 + map3.getWidth() && Gdx.input.getX() > 800 && Gdx.input.getY() < 150 + map3.getHeight() && Gdx.input.getY() > 150){
            game.batch.draw(map3,805,505);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen3(game));
            }
        } else {
            game.batch.draw(map3,800,500);
        }
        //Return button
        GlyphLayout layout4 = new GlyphLayout(font, "Press to return");
        font.draw(game.batch, layout4, 600, 350);

        if (Gdx.input.getX() < 870 && Gdx.input.getX() > 825 && Gdx.input.getY() < 690 && Gdx.input.getY() > 660){
            game.batch.draw(home,830,205);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            game.batch.draw(home,825,200);
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
