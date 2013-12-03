package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SectionSelect extends MapScreen {

	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	

	/* Variables when we have bigger maps and need to check scrolling */
	private MapProperties prop;
	private int mapWidth;
	private int mapHeight;
	private int tilePixelWidth;
	private int tilePixelHeight;

	private int mapPixelWidth;
	private int mapPixelHeight;
	private Texture backgroundTexture;
	private Sprite background;
	private SpriteBatch batch;
	
	private float scale;
	private Stage stage;
	
	private Game game;
	
	public SectionSelect(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		batch.begin();
		background.draw(batch);
		batch.end();
		
		stage.act();
		stage.draw();
		
		/*renderer.setView(camera);
		renderer.render();*/
		
//		if(Gdx.input.justTouched())
//		{
//			Gdx.app.log("SectionSelect", "Map clicked");
//			checkInputLocation();
//		}
	}

	@Override
	public void resize(int width, int height) {
		/* reset camera when resized */
		
		scale = width/height;
//		
//		camera.viewportHeight = height;
//		camera.viewportWidth = width;
//		
//		camera.position.set(width / 2f, height / 2f ,0);
//		
//		camera.update();
	}

	@Override
	public void show() {
		
		batch=new SpriteBatch();
		
		backgroundTexture=new Texture("maps/ilmakuva450_2.png");
		background=new Sprite(backgroundTexture);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		/* Load map, created by Tiled */
		map = new TmxMapLoader().load("maps/puzzleselectionbetter.tmx");

		/* create new renderer with the map */
		float unitscale = 1/16f;
		renderer = new OrthogonalTiledMapRenderer(map);
		
		stage = new Stage();
		
		/* camera */
		camera = new OrthographicCamera();
		
		Gdx.input.setInputProcessor(new TiledMapInputProcesser(game));
		
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
		
		
		
	}

	@Override
	public void hide() {
		dispose();

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
	
	private void checkInputLocation()
	{
		Gdx.app.log("1. Map", "input x: " + Gdx.input.getX() + " y: " + Gdx.input.getY() );
		/* Resolve where the user clicked */
		switch(checkTile((int)Math.floor(Gdx.input.getX()/16), (int)Math.floor((mapPixelHeight - Gdx.input.getY())/16)))
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
	
	/* Method to check if given coordinates are access points to menus/puzzles */
	private int checkTile(int x, int y)
	{
		Gdx.app.log("1.Map", "Checking tile: X " + x + " Y " + y);
		
		int answer = -1;
		
		
		TiledMapTileLayer clickableLayers = (TiledMapTileLayer) map.getLayers().get("inputlocations");
		
		for(int i = 0; i < 4; ++i)
		{
			Gdx.app.log("SectionSelect", "checking object " + i);
			if(clickableLayers.getCell(x, y) != null)
			{
				if(clickableLayers.getCell( x, y ).getTile().getProperties().containsKey("puzzle" + i) )
				{
					answer = i;
					break;
				}
			}
		}
		
		
		Gdx.app.log("SectionSelect", "Got ID prop: " + answer);

		return answer;
	}

	@Override
	public void startDialog(String string) {
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		Dialog dialog = new Dialog("Information", skin)
		{
			protected void result (Object object)
			{
				Gdx.input.setInputProcessor(new TiledMapInputProcesser(game));
			}
		}
		.text(string).button("  OK  ").show(stage);
		
		
	}

}
