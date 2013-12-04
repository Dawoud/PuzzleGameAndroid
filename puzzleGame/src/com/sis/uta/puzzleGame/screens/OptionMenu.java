package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionMenu implements Screen {
	
	public OptionMenu(Game game) {

		this.game = game;
	}

	private Game game;
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonMusic, buttonSound, buttonLanguage, buttonBack;
	private Slider musicSlider, soundSlider;
	private SelectBox languageBox;
	private BitmapFont white, black, white_normal;
	private Label heading, labelMusic, labelSound;
	private Texture backgroundTexture;
	private Sprite background;
	private SpriteBatch batch;

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		background.draw(batch);
		batch.end();
		
		stage.act(delta);
		
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
batch=new SpriteBatch();
		
		backgroundTexture=new Texture("data/main_bg.png");
		background=new Sprite(backgroundTexture);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage=new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas=new TextureAtlas("ui/button.pack");
		skin=new Skin(atlas);
		
		table=new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
		black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
		white_normal=new BitmapFont(Gdx.files.internal("font/white_normal.fnt"), false);
		
		TextButtonStyle textButtonStyle=new TextButtonStyle();
		textButtonStyle.up=skin.getDrawable("button.up");
		textButtonStyle.down=skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX=1;
		textButtonStyle.pressedOffsetY=-1;
		textButtonStyle.font=black;
		
		buttonMusic=new TextButton("Music", textButtonStyle);
		
		Skin sliderSkin=new Skin(Gdx.files.internal("data/uiskin.json"));
		
		musicSlider=new Slider(0, 20, 1, false, sliderSkin);
		
		soundSlider=new Slider(0, 20, 1, false, sliderSkin);
		
		buttonSound=new TextButton("Sound", textButtonStyle);
		
		buttonLanguage=new TextButton("Language", textButtonStyle);
		
		languageBox=new SelectBox(new String[]{"English", "Suomi"}, sliderSkin);
		
		buttonBack=new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
			}
		});
		
//		if (Gdx.input.isKeyPressed(Keys.BACK))
//		{
//			((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
//		}
		
		LabelStyle headingStyle=new LabelStyle(white, Color.WHITE);
		
		LabelStyle normalStyle=new LabelStyle(white_normal, Color.WHITE);
		
		heading=new Label("Options", headingStyle);
		heading.setFontScale(2);
		
		labelMusic=new Label("Music", normalStyle);
		
		labelSound=new Label("Sound", normalStyle);
		
		table.add(heading).row();
		table.add(labelMusic).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/10).row();
		table.add(musicSlider).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/10).row();
		table.add(labelSound).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/10).row();
		table.add(soundSlider).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/10).row();
//		table.add(buttonSound).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9).row();
		table.add(languageBox).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9).row().pad(0, 0, 20, 0);
		table.add(buttonBack).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9).row();
		
		stage.addActor(table);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
