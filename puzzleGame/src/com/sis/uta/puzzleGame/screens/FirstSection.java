package com.sis.uta.puzzleGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.controller.PlayerController;
import com.sis.uta.puzzleGame.model.Player;

public class FirstSection extends MapScreen {

	public FirstSection(puzzleGame game) {
		super();
		this.game = game;
		game.Screen=game.FIRSTSECTION;
	}

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private puzzleGame game;
	
	private Player player;

	/* Variables when we have bigger maps and need to check scrolling */
	private MapProperties prop;
	private int tilePixelWidth;
	private int tilePixelHeight;
	
	private Stage stage;
	private SpriteBatch spriteBatch;
	private Sprite buttonup;
	private Sprite buttondown;
	private Sprite buttonleft;
	private Sprite buttonright;
	private PlayerController controller;
	protected TiledMapTileLayer collisionlayer;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/* Set up the camera so it follows the player */
		camera.position.set(player.getX() + player.getWidth()/2f, player.getY() + player.getHeight()/2f, 0);
		
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		
		/* draw player */
		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("frontlayer"));
		renderer.getSpriteBatch().end();
		
		/* draw buttons */
		spriteBatch.begin();
		game.drawPoints(spriteBatch);
		buttondown.draw(spriteBatch);
		buttonup.draw(spriteBatch);
		buttonleft.draw(spriteBatch);
		buttonright.draw(spriteBatch);
		((puzzleGame)game).getMenubutton().draw(spriteBatch);
		spriteBatch.end();
		

		stage.act(delta);
		stage.draw();
	
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

		/* Load map, created by Tiled */
		map = new TmxMapLoader().load("maps/linnabuilding.tmx");

		/* create new renderer with the map */
		renderer = new OrthogonalTiledMapRenderer(map);
		
		/* camera */
		camera = new OrthographicCamera();
		
		/* Create stage for dialog */
		stage = new Stage();	
		spriteBatch = new SpriteBatch();
				
		/* Stuff that is needed if our map expands so we need scrolling */
		prop = map.getProperties();
		
		tilePixelWidth = prop.get("tilewidth", Integer.class);
		tilePixelHeight = prop.get("tileheight", Integer.class);

		// collisionlayer for player
		collisionlayer = (TiledMapTileLayer)map.getLayers().get("collisionlayer");
		
		/* PLayer */
		player = Player.getInstance(collisionlayer, game);
		controller = new PlayerController(player, game);
		
		/* Buttons to move player around */
		buttonup = new Sprite(new Texture("maps/movebutton.png"));
		buttondown = new Sprite(new Texture("maps/movebutton.png"));
		buttonleft = new Sprite(new Texture("maps/movebutton.png"));
		buttonright = new Sprite(new Texture("maps/movebutton.png"));
		
		/* Variables to create spritebounds */
		float stepX = Gdx.graphics.getWidth()/8;
		float stepY = Gdx.graphics.getHeight()/6; 
		
		float middleX = Gdx.graphics.getWidth()/2;
		float middleY = Gdx.graphics.getHeight()/2;
		
		/* These are normal set positioned on the screen left on left side and up at top etc. */
//		buttonleft.setBounds(0, middleY-stepY, stepX,stepY*2);
//		buttonup.setBounds(middleX-stepX, Gdx.graphics.getHeight()-stepY, stepX*2,stepY);
//		buttonright.setBounds(Gdx.graphics.getWidth()-stepX, middleY-stepY, stepX, stepY*2);
//		buttondown.setBounds(middleX-stepX, 0, stepX*2, stepY);
		
		/* REMEMBER TO CHANGE CONTROLMODE TO 1 /\ OR 2 || IF YOU CHANGE THESE
		 * AND THIS IS AT PLAYERCONTROLLER 	   ||      \/ 
		  CONSTRUCTOR */
		
		/* these are more easily to access, left and up on the left side of the screen
		 * and down and right on the other side */
		buttonup.setBounds(0, middleY, stepX, stepY*2); // up
		buttondown.setBounds(Gdx.graphics.getWidth()-stepX, middleY-stepY*2, stepX, stepY*2); // down
		buttonleft.setBounds(0, middleY-stepY*2, stepX, stepY*2); // left
		buttonright.setBounds(Gdx.graphics.getWidth()-stepX, middleY, stepX, stepY*2); // right

		buttondown.rotate90(false);
		buttonup.rotate90(true);
		buttonright.rotate90(true);
		buttonright.rotate90(true);
		
		/* Set inputprocessor to playercontroller to move player around */
		//Gdx.input.setInputProcessor(controller);
		InputMultiplexer multiplexer=new InputMultiplexer();
		multiplexer.addProcessor(controller);
		multiplexer.addProcessor(game);
		Gdx.input.setInputProcessor(multiplexer);
		
		// Set up the camera
		camera.viewportHeight = tilePixelHeight*20f;
		camera.viewportWidth = tilePixelWidth*20f;
		
		if(game.sectionCompleted(1))
		{
			startDialog("Congratulations!\nYou have completed the first section of this game!\nTake a look at other sections or try " +
					"old puzzles again.");
		}
		else
		{
			startDialog("I think you should start your journey with seeing\n a librarian and see if he has anything to say");
		}
		
	}

	@Override
	public void hide() {
		game.saveGame();
		dispose();
	}

	@Override
	public void pause() {
		game.saveGame();
	}

	@Override
	public void resume() {
		show();
	}

	@Override
	public void dispose() {
		map.dispose();
		player.getTexture().dispose();
		buttondown.getTexture().dispose();
		buttonup.getTexture().dispose();
		buttonleft.getTexture().dispose();
		buttonright.getTexture().dispose();
	}
	
	public void startDialog(String string) {
		Gdx.input.setInputProcessor(stage);
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		Dialog dialog = new Dialog("Information", skin)
		{
			protected void result (Object object)
			{
				Gdx.input.setInputProcessor(new PlayerController(Player.getInstance(collisionlayer, game), game));
			}
		}
		.text(string).button("  OK  ").show(stage);
		
	}

}
