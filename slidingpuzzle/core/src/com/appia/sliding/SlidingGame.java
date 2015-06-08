package com.appia.sliding;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SlidingGame extends Game {
	SpriteBatch batch;
	Texture img;
	MyStage stage;
	@Override
	public void create () {
		
		MyAssets.instance().create();
		stage = new MyStage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		//stage.setDebugAll(true);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);		
		stage.create();
		
		GameSound.instance().init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();		
	}
	
	@Override
	public void resize (int width, int height) {
	    // See below for what true means.
	    stage.getViewport().update(width, height, true);
	}
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		MyAssets.instance().dispose();
	}
}
