package com.airwar;
import Screens.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	public SpriteBatch batch;

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
