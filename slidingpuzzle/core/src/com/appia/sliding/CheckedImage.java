package com.appia.sliding;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class CheckedImage extends Image {
	private final Drawable uncheckDraw;
	private final Drawable checkDraw;
	private boolean check = false;
	public CheckedImage(TextureRegion uncheckRegion, TextureRegion checkRegion ) {
		super(uncheckRegion);
		this.checkDraw = new TextureRegionDrawable(checkRegion);
		this.uncheckDraw = new TextureRegionDrawable(uncheckRegion);		
	}
	
	public void setCheck(boolean check){
		this.check = check;
		if (check)
			this.setDrawable(checkDraw);
		else
			this.setDrawable(uncheckDraw);
	}
	
	public boolean isChecked(){
		return check;
	}
}
