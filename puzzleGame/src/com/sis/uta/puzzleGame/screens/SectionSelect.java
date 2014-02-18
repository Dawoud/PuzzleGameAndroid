package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.controller.SectionSelectController;

public class SectionSelect extends MapScreen {

	
	public final static int LINNA = 0, PAATALO = 1, PINNI = 2;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	/* */
	private Sprite[] buildings;
	private int currentBuilding;

	/* Variables when we have bigger maps and need to check scrolling */
	private MapProperties prop;
	private int mapWidth;
	private int mapHeight;
	private int tilePixelWidth;
	private int tilePixelHeight;

	private int mapPixelWidth;
	private int mapPixelHeight;
	private SpriteBatch batch;

	private Stage stage;
	
	private puzzleGame game;
	
	public SectionSelect(puzzleGame game)
	{
		this.game = game;
		game.Screen=game.SECTIONSELECT;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		batch.begin();
		buildings[currentBuilding].draw(batch);
		((puzzleGame)game).getMenubutton().draw(batch);
		batch.end();
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		
		batch=new SpriteBatch();

		/* Load map, created by Tiled */
		map = new TmxMapLoader().load("maps/puzzleselectionbetter.tmx");

		/* create new renderer with the map */
		renderer = new OrthogonalTiledMapRenderer(map);
		
		stage = new Stage();
		
		/* camera */
		camera = new OrthographicCamera();
		
//		Gdx.input.setInputProcessor(new TiledMapInputProcesser(game));
		
		/* Stuff that is needed if our map expands so we need scrolling */
		prop = map.getProperties();

		mapWidth = prop.get("width", Integer.class);
		mapHeight = prop.get("height", Integer.class);
		tilePixelWidth = prop.get("tilewidth", Integer.class);
		tilePixelHeight = prop.get("tileheight", Integer.class);

		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
		
		camera.viewportHeight = mapPixelHeight;
		camera.viewportWidth = mapPixelWidth;
		
		camera.position.set(mapPixelWidth / 2f, mapPixelHeight / 2f ,0);
		
		camera.update();
		
		buildings = new Sprite[] {new Sprite(new Texture("maps/linna2.png")),
			new Sprite(new Texture("maps/paatalo2.png")), 
			new Sprite(new Texture("maps/pinni2.png"))};

		for(int i = 0; i < buildings.length; ++i)
		{
			buildings[i].setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		Gdx.input.setInputProcessor(new GestureDetector(new SectionSelectController(game, this)));
	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();

	}
	
	public void changeScreen(int input)
	{
		switch(input)
		{
			/* empty space, continue */
			case -1:
				break;
				
			/* Main menu area clicked, return */
			case 0:
				((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
				break;
			
			/* Puzzle area clicked, start first puzzle */
			case 1:
				((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
				break;
			
			case 2:
				
				Gdx.app.log("SectionSelect", "Second section not implemented yet");
				break;
			case 3:
				Gdx.app.log("SectionSelect", "Third section not implemented yet");
				break;
		}
	}
	

	@Override
	public void startDialog(String string) {
		final SectionSelect a = this;
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		Dialog dialog = new Dialog("Information", skin)
		{
			protected void result (Object object)
			{
				Gdx.input.setInputProcessor(new GestureDetector(new SectionSelectController(game, a)));
			}
		}
		.text(string).button("  OK  ").show(stage);
				
	}

	public void nextBuilding() {
	
		if(currentBuilding == buildings.length - 1)
		{
			currentBuilding = 0;
		}
		else
		{
			++currentBuilding;
		}
	}

	public void previousBuilding() {
		if(currentBuilding == 0)
		{
			currentBuilding = buildings.length-1;
		}
		else
		{
			--currentBuilding;
		}
		
	}

	public int getCurrentBuilding() {
		
		return currentBuilding;
	}

}
