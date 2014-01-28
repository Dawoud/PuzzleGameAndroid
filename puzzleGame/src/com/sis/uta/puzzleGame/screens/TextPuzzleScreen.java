package com.sis.uta.puzzleGame.screens;


import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;




public class TextPuzzleScreen implements Screen{
	
	private Game game;
	
	public TextPuzzleScreen(Game game) {
		this.game = game;
	}
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonBack;
	private BitmapFont white, black;
	private Label heading,answer1,answer2,answer3,rightanswer;
	
	List<TextPuzzle> list = null;

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
		
		try {
			
			list = new ReadXMLFile().getTextPuzzlesByParseXml();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas=new TextureAtlas("ui/button.pack");
		skin=new Skin(atlas);
		
		table=new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.layout();
		table.left();
		
		white=new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
		black=new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
		
		TextButtonStyle textButtonStyle=new TextButtonStyle();
		textButtonStyle.up=skin.getDrawable("button.up");
		textButtonStyle.down=skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX=1;
		textButtonStyle.pressedOffsetY=-1;
		textButtonStyle.font=black;
		
		TextFieldStyle textFieldStyle= new TextFieldStyle();
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.focusedFontColor = Color.PINK;
		textFieldStyle.background = skin.getDrawable("button.up");
		textFieldStyle.font = black;
		
		buttonBack=new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
			}
		});
		
		LabelStyle headingStyle=new LabelStyle(white, Color.WHITE);
		heading = new Label(list.get(0).getQuestion(), headingStyle);
		heading.setFontScale(0.6f);
		heading.setWrap(true);
		heading.setColor(Color.WHITE);
		heading.setWidth(0);
		
		TextButton button1 = new TextButton("A", textButtonStyle);
		button1.left();
		button1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//add checking
				//((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
			}
		});  
				
		
		TextButton button2 = new TextButton("B", textButtonStyle);
		button2.left();
		button2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//add checking
				//((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
			}
		});  
		
		TextButton button3 = new TextButton("C", textButtonStyle);
		button3.left();
		button3.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//add checking
				//((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
			}
		});  
		
		TextButton button4 = new TextButton("D", textButtonStyle);
		button4.left();
		button4.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//add checking
				((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
			}
		});  
		
		ButtonGroup bg= new ButtonGroup(button1,button2,button3,button4);
		bg.setChecked("A");
		
		answer1 = new Label(list.get(0).getAnsewer1(), headingStyle);
		answer2 = new Label(list.get(0).getAnsewer2(), headingStyle);
		answer3 = new Label(list.get(0).getAnsewer3(), headingStyle);
		rightanswer = new Label(list.get(0).getRightanswer(), headingStyle);
		
				
		table.row().minWidth(Gdx.graphics.getWidth()).colspan(2);
		table.add(heading).colspan(5);		
		table.row().minWidth(Gdx.graphics.getWidth()/6).minHeight(Gdx.graphics.getHeight()/24);
		table.add(button1);
		table.add(answer1);
		table.row().minWidth(Gdx.graphics.getWidth()/6).minHeight(Gdx.graphics.getHeight()/24);
		table.add(button2);
		table.add(answer2);
		table.row().minWidth(Gdx.graphics.getWidth()/6).minHeight(Gdx.graphics.getHeight()/24);
		table.add(button3);
		table.add(answer3);
		table.row().minWidth(Gdx.graphics.getWidth()/6).minHeight(Gdx.graphics.getHeight()/24);
		table.add(button4);
		table.add(rightanswer);
		table.add(buttonBack);
		
		
		
		
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
