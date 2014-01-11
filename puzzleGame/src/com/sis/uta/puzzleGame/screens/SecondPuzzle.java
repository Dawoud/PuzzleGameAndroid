package com.sis.uta.puzzleGame.screens;

import java.awt.Checkbox;
import java.io.IOException;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.XmlReader;

public class SecondPuzzle implements Screen{
	
	private Game game;
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonBack, buttonCheck;
	private BitmapFont white, black;
	private Label heading;
	
	
	public SecondPuzzle(Game game) {
		this.game = game;
	}

	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		
		CharSequence taskName = null;
		String value1 = null, value2 = null, value3 = null, value4 = null;
		//String[] results = null;
		int required;
		
		//parsing
		try {
		      XmlReader xmlReader = new XmlReader();
		      FileHandle file = Gdx.files.internal("data/x.xml");
		      XmlReader.Element root = xmlReader.parse(file);
		      XmlReader.Element taskElement = root.getChildByName("task");

		      	//taskName = taskElement.getAttribute("name");
		      	taskName = root.getAttribute("name");
		        required = root.getInt("required", 1);
//		        XmlReader.Element resultsElement = root.getChildByName("results");
//		        final int resultCount = resultsElement.getChildCount();
//		        results = new String[resultCount];
//
//		        for (int j = 0; resultCount < j; ++j) {
//		          XmlReader.Element result = resultsElement.getChild(j);
//		          String value = result.getAttribute("value");
//		          results[j] = value;
//		        }
		        
		        XmlReader.Element resultsElement = root.getChildByName("result");
		        value1 = resultsElement.getAttribute("value1");
		        value2 = resultsElement.getAttribute("value2");
		        value3 = resultsElement.getAttribute("value3");
		        value4 = resultsElement.getAttribute("value4");		        
		        
		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		
		atlas=new TextureAtlas("ui/button.pack");
		skin=new Skin(atlas);
		
		table=new Table(skin);
		
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
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
		
		buttonCheck=new TextButton("Check", textButtonStyle);
		buttonCheck.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				//check the result
				boolean correctanswer;
				correctanswer = true;
				if(correctanswer) {
					((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
				}
			}
		});
		
		buttonBack=new TextButton("Back", textButtonStyle);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
			}
		});
		
		LabelStyle headingStyle=new LabelStyle(white, Color.WHITE);
		heading = new Label(taskName, headingStyle);
		heading.setFontScale(0.6f);
		heading.setWrap(true);
		heading.setColor(Color.WHITE);
		heading.setWidth(0);
		
		TextButton button1 = new TextButton("1", textButtonStyle);
		TextButton button2 = new TextButton("2", textButtonStyle);
		ButtonGroup answers = new ButtonGroup(button1, button2);
		answers.setChecked("1");
				
		table.add(heading);
//		int resultCount = results.length;
//		for (int j = 0; resultCount > j; ++j) {
//			table.row();
//			String str = Integer.toString(results[j]);
//			table.add(str);
//		}
		//table.add(results[0]);
		//table.add(result1);
//		table.row();
//		table.add(button1);
//		table.add(button2);
		table.row();
		table.add(buttonCheck).minWidth(Gdx.graphics.getWidth()/4).minHeight(Gdx.graphics.getHeight()/11).row();
		table.add(buttonBack).minWidth(Gdx.graphics.getWidth()/4).minHeight(Gdx.graphics.getHeight()/11).row();
		
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

