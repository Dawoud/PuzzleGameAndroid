package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InfoScreen implements Screen {
	
	private Game game;
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonBack;
	private BitmapFont white, black;

	public InfoScreen(Game game)
	{
		this.game=game;
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas=new TextureAtlas("ui/button.pack");
		skin=new Skin(atlas);
		
		table=new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		white=new BitmapFont(Gdx.files.internal("font/white_normal.fnt"), false);
		black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
		
		TextButtonStyle textButtonStyle=new TextButtonStyle();
		textButtonStyle.up=skin.getDrawable("button.up");
		textButtonStyle.down=skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX=1;
		textButtonStyle.pressedOffsetY=-1;
		textButtonStyle.font=black;
		
		buttonBack=new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
			}
		});
		
		LabelStyle labelStyle=new LabelStyle(white, Color.WHITE);
		Label team=new Label("Team members",labelStyle);
		
		Label members=new Label("Ahmed Dawoud\nEashan Salhotra\nElena Betekhtina\nHang Do Minh\nJere Vahlman\nJuho Ylipoti\nMengyuan Yang", labelStyle);
		members.setAlignment(Align.center);
		
		Label imageHead=new Label("Images", labelStyle);
		Label images=new Label("http://www.corepics.com/", labelStyle);
		
		table.add(team).pad(0, 0, 5, 0).row();
		table.add(members).pad(0, 0, 5, 0).row();
		table.add(imageHead).pad(0, 0, 5, 0).row();
		table.add(images).pad(0, 0, 5, 0).row();
		table.add(buttonBack).row();
		
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
