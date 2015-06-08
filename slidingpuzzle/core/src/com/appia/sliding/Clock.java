package com.appia.sliding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

;

public class Clock extends Group {
	private float seconds;
	private boolean pause;
	private Label label;

	public Clock(BitmapFont font, Color color) {
		seconds = 0;
		pause = true;
		this.label = new Label("00:00", new LabelStyle(font, color));
		this.setWidth(label.getWidth());
		this.setHeight(label.getHeight());

		this.addActor(label);

	}

	public void start() {
		this.pause = false;
	}

	public void stop() {
		this.pause = true;
	}

	public float getTime() {
		return seconds;
	}

	public void reset() {
		stop();
		setTime(0);
	}

	private String to2digit(int digit) {
		if (digit < 10) {
			return "0" + digit;
		} else {
			return String.valueOf(digit);
		}
	}

	private void setTime(float time) {
		seconds = time;
		int tmp = (int) seconds;
		int minute = tmp / 60;
		int se = tmp % 60;
		String text = to2digit(minute) + ":" + to2digit(se);
		label.setText(text);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (!pause)
			setTime(seconds + delta);
	}
}
