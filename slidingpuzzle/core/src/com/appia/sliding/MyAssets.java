package com.appia.sliding;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MyAssets {
	
	private static final MyAssets _inst = new MyAssets();
	public static MyAssets instance()
	{
		return _inst;
	}
	
	private MyAssets() {
	}
	
	private AssetManager manager;
	private TextureAtlas atlas;
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public AssetManager getManager() {
		return manager;
	}

	public void create() {
		Resolution[] resolutions = { new Resolution(480, 800, "480800") };
		ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);
		manager = new AssetManager(resolver);
		Texture.setAssetManager(manager);
		
		manager.load("badlogic.jpg", Texture.class);
		manager.load("img/pack.atlas", TextureAtlas.class);
		
		manager.load("font/font.fnt", BitmapFont.class);
		
		manager.finishLoading();
		atlas = manager.get("img/pack.atlas", TextureAtlas.class);		
	}
	
	public void dispose() {
		manager.dispose();

	}
}
