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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sis.uta.puzzleGame.puzzleAudio;
import com.sis.uta.puzzleGame.puzzleGame;

public class MainMenu implements Screen {

	public MainMenu(puzzleGame game) {
		
		this.game = game;
		game.Screen=game.MAINMENU;
	}

	private puzzleGame game;
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonOption, buttonExit;
	private ImageButton buttonMute, buttonInfo;
	private ImageButtonStyle imageButtonStyleMute, imageButtonStyleUnmute, imageButtonStyleInfo;
//	private TextButton buttonScore, buttonHelp;
	private BitmapFont white, black;
	private Label heading;
	private Texture backgroundTexture, textureMute, textureUnmute, textureInfo;
	private Sprite background;
	private SpriteBatch batch;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
//		Table.drawDebug(stage);
		
		
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
		textureUnmute = new Texture(Gdx.files.internal("data/unmute_s.png"));                
		TextureRegion imageUnmute = new TextureRegion(textureUnmute);
		textureMute = new Texture(Gdx.files.internal("data/mute_s.png"));                
		TextureRegion imageMute = new TextureRegion(textureMute);
		
		textureInfo=new Texture(Gdx.files.internal("data/info.png"));
		TextureRegion imageInfo=new TextureRegion(textureInfo);
		
		TextButtonStyle textButtonStyle=new TextButtonStyle();
		textButtonStyle.up=skin.getDrawable("button.up");
		textButtonStyle.down=skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX=1;
		textButtonStyle.pressedOffsetY=-1;
		textButtonStyle.font=black;
		
		imageButtonStyleUnmute = new ImageButtonStyle();
		imageButtonStyleUnmute.imageUp = new TextureRegionDrawable(imageUnmute);
		imageButtonStyleUnmute.pressedOffsetX=1;
		imageButtonStyleUnmute.pressedOffsetY=-1;
				
		imageButtonStyleMute = new ImageButtonStyle();
		imageButtonStyleMute.imageUp = new TextureRegionDrawable(imageMute);
		imageButtonStyleMute.pressedOffsetX=1;
		imageButtonStyleMute.pressedOffsetY=-1;
		
		imageButtonStyleInfo=new ImageButtonStyle();
		imageButtonStyleInfo.imageUp=new TextureRegionDrawable(imageInfo);
		imageButtonStyleInfo.pressedOffsetX=1;
		imageButtonStyleInfo.pressedOffsetY=-1;
		
		buttonPlay=new TextButton("Back to Game", textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new FirstSection(game));
			}
		});
		
		
		buttonOption=new TextButton("Options", textButtonStyle);
		buttonOption.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new OptionMenu(game));
			}
		});
		
//		buttonScore=new TextButton("Scores", textButtonStyle);
//		
//		buttonHelp=new TextButton("Help!", textButtonStyle);
		
		buttonExit=new TextButton("Quit Game", textButtonStyle);
		buttonExit.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//puzzleAudio.dispose();
				Gdx.app.exit();
			}
		});
		
		
		buttonMute = new ImageButton(imageButtonStyleMute);
		if (!puzzleAudio.isPlaying())
			buttonMute.setStyle(imageButtonStyleUnmute);
		buttonMute.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (puzzleAudio.isPlaying())
				{
					puzzleAudio.pause();
					buttonMute.setStyle(imageButtonStyleUnmute);
				}
				else
				{
					puzzleAudio.playMusic();
					buttonMute.setStyle(imageButtonStyleMute);
				}
				
				//puzzleAudio.playMusic();
			}
		});
		
		buttonInfo=new ImageButton(imageButtonStyleInfo);
		buttonInfo.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new InfoScreen(game));
			}
		});
		
		LabelStyle headingStyle=new LabelStyle(white, Color.WHITE);
		
		heading=new Label("Puzzle Game", headingStyle);
		heading.setFontScale(2);
		
		table.add(heading).row();
		table.add(buttonPlay).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9).row();
//		table.add(buttonScore).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9).row();
		table.add(buttonOption).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9);
//		table.add(buttonHelp).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9);
		table.add(buttonMute).row();
		table.add(buttonExit).minWidth(Gdx.graphics.getWidth()/2).minHeight(Gdx.graphics.getHeight()/9);
		table.add(buttonInfo).row();
				
		table.debug();
		stage.addActor(table);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		//puzzleAudio.dispose();

	}

}