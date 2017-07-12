package com.luigi.playground;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Playground extends Game {
	SpriteBatch batch;
	BitmapFont font;

	@Override
	public void create () {
		font = new BitmapFont(Gdx.files.internal("Ravie.fnt"));
		batch = new SpriteBatch();
		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
	}
}
