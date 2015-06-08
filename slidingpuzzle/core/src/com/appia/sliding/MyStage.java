package com.appia.sliding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

class MyStage extends Stage {
	private static final String TAG = MyStage.class.getSimpleName();

	public MyStage(Viewport viewport) {
		super(viewport);
	}

	private CheckedImage lv3Btn;
	private CheckedImage lv4Btn;
	private CheckedImage lv5Btn;

	private CheckedImage startBtn;
	private CheckedImage soundBtn;
	private GameBoard gameBoard;
	private Clock clock;

	private class ChoseLvlListener extends ClickListener {
		private final int lvl;

		public ChoseLvlListener(int lvl) {
			this.lvl = lvl;
		}

		@Override
		public void clicked(InputEvent event, float x, float y) {
            CheckedImage lvlBtn = getLvBtn(lvl);
			if (lvlBtn != null && lvlBtn.isChecked())
				return;
			GameSound.instance().playClick();
			if (gameBoard.isInGame())
				showYesNoDialog("Are you sure to load new level?", new IYesNoListener() {

					@Override
					public void clickYes() {
						newLvl(lvl);
					}

					@Override
					public void clickNo() {
                            Gdx.app.debug(TAG, "click N?");
					}
				});
			else 
				newLvl(lvl);
		}
		
		
	}

	private CheckedImage getLvBtn(int lvl) {
		switch (lvl) {
		case 3:
			return lv3Btn;
		case 4:
			return lv4Btn;
		case 5:
			return lv5Btn;
		default:
			return null;

		}
	}

	private void newLvl(int lvl) {
		Gdx.app.debug(TAG, "select new Lvl : " + lvl);
		lv3Btn.setCheck(false);
		lv4Btn.setCheck(false);
		lv5Btn.setCheck(false);
        CheckedImage lvlBtn = getLvBtn(lvl);
		if (lvlBtn != null)
			lvlBtn.setCheck(true);
		gameBoard.setRowColumn(lvl, lvl);
		init();
		startBtn.setCheck(false);
	}

	public void create() {
		final TextureAtlas atlas = MyAssets.instance().getAtlas();
		Image background = new Image(atlas.findRegion("background"));
		background.setWidth(Gdx.graphics.getWidth());
		background.setHeight(Gdx.graphics.getHeight());
		this.addActor(background);

		Group game = new Group();
		game.setWidth(Gdx.graphics.getWidth());
		game.setHeight(Gdx.graphics.getWidth());
		this.addActor(game);

		Image board = new Image(atlas.findRegion("board"));
		board.setWidth(game.getWidth());
		board.setHeight(game.getHeight());
		game.addActor(board);

		float ratioBorder = 60f / 640;

		game.addActor(board);

		gameBoard = new GameBoard(3, 3, atlas.findRegion("pic2"), atlas.findRegion("border"));
		gameBoard.setWidth(game.getWidth() * (1 - 2 * ratioBorder));
		gameBoard.setHeight(game.getWidth() * (1 - 2 * ratioBorder));
		gameBoard.setX(game.getWidth() * ratioBorder);
		gameBoard.setY(game.getHeight() * ratioBorder);
		gameBoard.setStage(this);
		game.addActor(gameBoard);

		BitmapFont bmFont = MyAssets.instance().getManager().get("font/font.fnt");
		clock = new Clock(bmFont, new Color(1, 0, 0, 1));
		game.addActor(clock);

		init();
		// gameBoard.newGame();

		//game.setCenterPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        setCenterPosition(game);

		lv3Btn = new CheckedImage(atlas.findRegion("level3"), atlas.findRegion("level3_hilite"));
		lv4Btn = new CheckedImage(atlas.findRegion("level4"), atlas.findRegion("level4_hilite"));
		lv5Btn = new CheckedImage(atlas.findRegion("level5"), atlas.findRegion("level5_hilite"));

		lv3Btn.setCheck(true);

		lv3Btn.setWidth(game.getWidth() * ratioBorder * 0.8f);
		lv3Btn.setHeight(game.getHeight() * ratioBorder * 0.8f);
		lv3Btn.addListener(new ChoseLvlListener(3));

		lv3Btn.setX(game.getWidth() / 2 - 2 * lv3Btn.getWidth());
		lv3Btn.setY(game.getHeight() - 1.1f * lv3Btn.getHeight());
		// --------
		lv4Btn.setWidth(game.getWidth() * ratioBorder * 0.8f);
		lv4Btn.setHeight(game.getHeight() * ratioBorder * 0.8f);
		lv4Btn.addListener(new ChoseLvlListener(4));

		lv4Btn.setX(game.getWidth() / 2 - 0.5f * lv3Btn.getWidth());
		lv4Btn.setY(game.getHeight() - 1.1f * lv3Btn.getHeight());
		// -----
		lv5Btn.setWidth(game.getWidth() * ratioBorder * 0.8f);
		lv5Btn.setHeight(game.getHeight() * ratioBorder * 0.8f);
		lv5Btn.addListener(new ChoseLvlListener(5));

		lv5Btn.setX(game.getWidth() / 2 + 1f * lv3Btn.getWidth());
		lv5Btn.setY(game.getHeight() - 1.1f * lv3Btn.getHeight());

		game.addActor(lv3Btn);
		game.addActor(lv4Btn);
		game.addActor(lv5Btn);

		lv3Btn.setCheck(true);

		startBtn = new CheckedImage(atlas.findRegion("start"), atlas.findRegion("stop"));
		float scale = (game.getHeight() * ratioBorder) / startBtn.getHeight();

		startBtn.setWidth(startBtn.getWidth() * scale);
		startBtn.setHeight(startBtn.getHeight() * scale);
		startBtn.setX(game.getWidth() / 2 - startBtn.getWidth() / 2);

		game.addActor(startBtn);
		startBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				if (!startBtn.isChecked()) {
					newGame();
				} else {
					showYesNoDialog("Are you sure to stop this game?", new IYesNoListener() {
						@Override
						public void clickYes() {
							stopGame();	
						}

						@Override
						public void clickNo() {
						}
						
					});					
				}
			}
		});

		soundBtn = new CheckedImage(atlas.findRegion("ic_mute_button"), atlas.findRegion("ic_mute_button_pressed"));
		scale = (game.getHeight() * ratioBorder) / soundBtn.getHeight();
		soundBtn.setSize(soundBtn.getWidth() * scale, soundBtn.getHeight() * scale);
		soundBtn.setY(game.getHeight() - soundBtn.getHeight());
		game.addActor(soundBtn);

		soundBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				soundBtn.setCheck(!soundBtn.isChecked());
				GameSound.instance().enable = !soundBtn.isChecked();
			}
		});

		Image img0Btn = new Image(atlas.findRegion("tn_pic0"));
		scale = (game.getHeight() * ratioBorder * 0.9f) / img0Btn.getHeight();
		img0Btn.setSize(img0Btn.getWidth() * scale, img0Btn.getHeight() * scale);
		img0Btn.setX(game.getWidth() - img0Btn.getWidth() / 0.9f * 0.95f);
		img0Btn.setY(game.getHeight() / 2 + 0.5f * img0Btn.getHeight());
		game.addActor(img0Btn);
		img0Btn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				gameBoard.setTextureRegion(atlas.findRegion("pic0"));
			}
		});


		Image img1Btn = new Image(atlas.findRegion("tn_pic1"));
		img1Btn.setSize(img0Btn.getWidth(), img0Btn.getHeight());
		img1Btn.setX(img0Btn.getX());
		img1Btn.setY(game.getHeight() / 2 - 0.5f * img0Btn.getHeight());				
		game.addActor(img1Btn);
		img1Btn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				gameBoard.setTextureRegion(atlas.findRegion("pic1"));
			}
		});


		Image img2Btn = new Image(atlas.findRegion("tn_pic2"));
		img2Btn.setSize(img0Btn.getWidth(), img0Btn.getHeight());
		img2Btn.setX(img0Btn.getX());
		img2Btn.setY(game.getHeight() / 2 - 1.5f * img0Btn.getHeight());
		img2Btn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				gameBoard.setTextureRegion(atlas.findRegion("pic2"));
			}
		});
		game.addActor(img2Btn);
		
	}

	private void init() {
		gameBoard.init();
		clock.reset();
	}

	private void stopGame() {
		startBtn.setCheck(false);

		gameBoard.stop();
		clock.stop();
	}

	public void newGame() {
		gameBoard.newGame();
		startBtn.setCheck(true);
		clock.reset();
		clock.start();
	}

	interface IYesNoListener {
		void clickYes();

		void clickNo();
	}

	private Group dialogGroup;
	
	private void showOkDialog(final String text) {
		dialogGroup = new Group();

		TextureAtlas atlas = MyAssets.instance().getAtlas();
		Image bg = new Image(atlas.findRegion("bgAlpha"));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		dialogGroup.addActor(bg);

		BitmapFont bmFont = MyAssets.instance().getManager().get("font/font.fnt");
		Image bgDlg = new Image(atlas.findRegion("dialogBg"));
		Image okBtn = new Image(atlas.findRegion("okBtn"));

		okBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				hideDialog();
			}
		});

		GsnCustomDialog dialog = new GsnCustomDialog(text, bmFont, bgDlg, okBtn);
		//dialog.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        setCenterPosition(dialog);

		dialogGroup.addActor(dialog);
		this.addActor(dialogGroup);
	}

	private void showYesNoDialog(final String text, final IYesNoListener listener) {
		dialogGroup = new Group();

		TextureAtlas atlas = MyAssets.instance().getAtlas();
		Image bg = new Image(atlas.findRegion("bgAlpha"));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		dialogGroup.addActor(bg);

		BitmapFont bmFont = MyAssets.instance().getManager().get("font/font.fnt");
		Image bgDlg = new Image(atlas.findRegion("dialogBg"));
		Image okBtn = new Image(atlas.findRegion("okBtn"));
		Image cancelBtn = new Image(atlas.findRegion("cancelBtn"));

		okBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				listener.clickYes();
				hideDialog();
			}
		});

		cancelBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSound.instance().playClick();
				listener.clickNo();
				hideDialog();
			}
		});

		GsnCustomDialog dialog = new GsnCustomDialog(text, bmFont, bgDlg, okBtn, cancelBtn);
		//dialog.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        setCenterPosition(dialog);

		dialogGroup.addActor(dialog);
		this.addActor(dialogGroup);
	}

	private void hideDialog() {
		this.getRoot().removeActor(dialogGroup);
	}

	public void doneGame() {
		GameSound.instance().playApplause();		
		clock.stop();
		gameBoard.stop();
		showOkDialog("Congratulation!!");
	}

	private void setCenterPosition(Actor group){
		group.setPosition(this.getWidth() / 2 - group.getWidth() / 2, getHeight() / 2 - group.getHeight() / 2);
	}
}
