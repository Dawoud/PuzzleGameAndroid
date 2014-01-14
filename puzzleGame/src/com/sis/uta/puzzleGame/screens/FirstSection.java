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
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sis.uta.puzzleGame.controller.PlayerController;
import com.sis.uta.puzzleGame.model.Player;

public class FirstSection extends MapScreen {

	public FirstSection(Game game) {
		super();
		this.game = game;
	}

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Game game;
	
	private Player player;

	/* Variables when we have bigger maps and need to check scrolling */
	private MapProperties prop;
	private int mapWidth;
	private int mapHeight;
	private int tilePixelWidth;
	private int tilePixelHeight;

	private int mapPixelWidth;
	private int mapPixelHeight;
	
	private Stage stage;
	private SpriteBatch spriteBatch;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(camera);
		renderer.render();
		
		stage.act();
		stage.draw();
		
		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();
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
		
		/* Player */ 
		
		
		spriteBatch = new SpriteBatch();
		
	
		
		/* Stuff that is needed if our map expands so we need scrolling */
		prop = map.getProperties();

		stage = new Stage();
		
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
		
		player = new Player(new Sprite(new Texture("maps/character.png")), mapPixelWidth - tilePixelWidth, mapPixelHeight - tilePixelHeight, (TiledMapTileLayer)map.getLayers().get("collisionlayer"), game);
		
		Gdx.input.setInputProcessor(new TiledMapInputProcesser(game));
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
		player.getTexture().dispose();

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
