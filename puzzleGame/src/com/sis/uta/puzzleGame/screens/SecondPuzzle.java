package com.sis.uta.puzzleGame.screens;

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
		int[] results = null;
		int required;
		
		//parsing
		try {
		      XmlReader xmlReader = new XmlReader();
		      FileHandle file = Gdx.files.internal("Age.xml");
		      XmlReader.Element root = xmlReader.parse(file);
		      XmlReader.Element taskElement = root.getChildByName("task");

		      	//String taskName = taskElement.getAttribute("name");
		      	taskName = taskElement.getAttribute("name");
		        required = taskElement.getInt("required", 1);

		        XmlReader.Element resultsElement = taskElement.getChildByName("results");
		        final int resultCount = resultsElement.getChildCount();
		        results = new int[resultCount];

		        for (int j = 0; resultCount > j; ++j) {
		          XmlReader.Element result = resultsElement.getChild(j);
		          int value = result.getInt("value");
		          results[j] = value;
		        }

		        //Task task = new Task(taskName, results);

		        //this.tasks = task;
		        //int requiredTaskCount = required;
		        
		        
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
				((Game)Gdx.app.getApplicationListener()).setScreen(new SectionSelect(game));
			}
		});
		
		LabelStyle headingStyle=new LabelStyle(white, Color.WHITE);
		
		heading=new Label(taskName, headingStyle);
		//heading=new Label("dgdfh", headingStyle);
		heading.setFontScale(0.6f);
		heading.setWrap(true);
		heading.setColor(Color.WHITE);
		
		table.add(heading);
//		int resultCount = results.length;
//		for (int j = 0; resultCount > j; ++j) {
//			table.row();
//			String str = Integer.toString(results[j]);
//			table.add(str);
//		}
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

