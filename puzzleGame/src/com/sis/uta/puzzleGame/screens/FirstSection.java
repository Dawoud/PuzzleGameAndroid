package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class FirstSection implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	/* Arrays for checking what was clicked, untill I figure out how to use
	 * objects in tiledmaps
	 */

	/* Variables when we have bigger maps and need to check scrolling */
	private MapProperties prop;
	private int mapWidth;
	private int mapHeight;
	private int tilePixelWidth;
	private int tilePixelHeight;

	private int mapPixelWidth;
	private int mapPixelHeight;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(camera);
		renderer.render();
		
		if(Gdx.input.justTouched())
		{
			Gdx.app.log("Map", "Map clicked");
			checkInputLocation();
		}
	}

	@Override
	public void resize(int width, int height) {
		/* reset camera when resized */
		
		
	}

	@Override
	public void show() {

		/* Load map, created by Tiled */
		map = new TmxMapLoader().load("maps/first.tmx");

		/* create new renderer with the map */
		renderer = new OrthogonalTiledMapRenderer(map);
		
		/* camera */
		camera = new OrthographicCamera();
		
		Gdx.input.setInputProcessor(null);
		
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
	
	private void checkInputLocation()
	{
		/* Resolve where the user clicked */
		switch(checkTile(Gdx.input.getX() , Gdx.input.getY()))
		{
			/* empty space, continue */
			case -1:
			break;
				
			/* Main menu area clicked, return */
			case 0:
			((Game)Gdx.app.getApplicationListener()).setScreen(new SectionSelect());
			break;
			
			/* Puzzle area clicked, start first puzzle */
			case 1:
			((Game)Gdx.app.getApplicationListener()).setScreen(new FirstPuzzle());
			break;
		}
	}
	
	/* Method to check if given coordinates are access points to menus/puzzles */
	private int checkTile(float x, float y)
	{
		Gdx.app.log("1.Map", "Checking tile: X " + x + " Y " + y);
		
		int answer = -1;
		
		
		MapLayer clickableLayers = (MapLayer) map.getLayers().get("Object Layer 1");
		
		RectangleMapObject obj;
		for(int i = 0; i < 2; ++i)
		{
			Gdx.app.log("1.Map", "checking object " + i);
			obj = (RectangleMapObject) clickableLayers.getObjects().get("puzzleHouse" + i);
			if(obj.getRectangle().contains(x, mapPixelHeight - y))
			{
				answer = i;
				break;
			}
		}
		
		
		Gdx.app.log("1.map", "Got ID prop: " + answer);

		return answer;
	}

}
