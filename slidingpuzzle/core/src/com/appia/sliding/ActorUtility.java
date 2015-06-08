package com.appia.sliding;

import com.badlogic.gdx.scenes.scene2d.Actor;

class ActorUtility {
	public static void setCenter(Actor actor, float x, float y) {
		setRatio(actor, 0.5f, 0.5f, x, y);
	}
	
	public static void setCenter(Actor actor1, Actor actor2){
		float x = actor2.getX() + actor2.getWidth() * actor2.getScaleX() * 0.5f;
		float y = actor2.getY() + actor2.getHeight() * actor2.getScaleY() * 0.5f;
		setCenter(actor1, x, y);
	}

	public static void setRatio(Actor actor, float ratioWidth, float ratioHeight, float x, float y) {
		actor.setX(x - actor.getWidth() * actor.getScaleX() * ratioWidth);
		actor.setY(y - actor.getHeight()* actor.getScaleY() * ratioHeight);
	}	
	
	public static void setRatio(Actor actor, float ratioWidth, float ratioHeight, Actor actor2, float ratioWidth2, float ratioHeight2) {
		float x = actor2.getX() + actor2.getWidth() * actor2.getScaleX() * ratioWidth2;
		float y = actor2.getY() + actor2.getHeight() * actor2.getScaleY() * ratioHeight2;
		setRatio(actor, ratioWidth, ratioHeight, x, y);
	}	
}
