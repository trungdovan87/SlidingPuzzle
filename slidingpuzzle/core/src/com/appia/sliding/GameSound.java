package com.appia.sliding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {
	private static final GameSound _instance = new GameSound();

	public static GameSound instance() {
		return _instance;
	}

	private GameSound() {
	}

	public boolean enable = true;

	private void playSound(Sound sound) {
		if (enable) {
			sound.play();
		}
	}

	private Sound applause;
	private Sound click;
	private Sound whoosh;

	public void init() {
		applause = Gdx.audio.newSound(Gdx.files.internal("sound/applause.mp3"));
		click = Gdx.audio.newSound(Gdx.files.internal("sound/click.mp3"));
		whoosh = Gdx.audio.newSound(Gdx.files.internal("sound/whoosh.mp3"));
	}

	public void playClick() {
		playSound(click);
	}
	
	public void playApplause() {
		playSound(applause);
	}
	
	public void playWhoosh() {
		playSound(whoosh);
	}
	
}
