package com.sis.uta.puzzleGame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.sis.uta.puzzleGame.puzzleGame;
import com.sis.uta.puzzleGame.screens.GraphPuzzle;
import com.sis.uta.puzzleGame.screens.GridPuzzle;
import com.sis.uta.puzzleGame.screens.PicturePuzzle;
import com.sis.uta.puzzleGame.screens.TextPuzzleScreen;

 
 public class Player extends Sprite{
	
	public final int UP = 1, DOWN = 2, LEFT = 3,  RIGHT = 4;
	public final int NULLTILE = -1, BLOCKED = 0, PUZZLE1 = 1, PUZZLE2 = 2, PUZZLE3 = 3,
			PUZZLE4 = 4;
	
	private puzzleGame game;
	
	private static Player instance = null; 
	
	private int collidecheckstepsize = 2;
	private String collisionKey; 
	private String puzzleKey;
	
	/** movement velocity */
	private Vector2 velocity = new Vector2();
	
	private float speed = 60 * 2;

	private TiledMapTileLayer collisionlayer;

	
	private Player(TiledMapTileLayer collisionlayer, puzzleGame game) {
		super(new Sprite(new Texture("maps/character.png")));

		this.collisionlayer = collisionlayer;
		
		this.game = game;
		
		collisionKey = "blocked";
		puzzleKey = "puzzle";
		
		setX(300);
		setY(100);
	}
	
	public static Player getInstance(TiledMapTileLayer collisionlayer, puzzleGame game)
	{
		if(instance == null)
		{
			return instance = new Player(collisionlayer, game);
		}
		else
		{
			instance.setTexture(new Texture("maps/character.png"));
			return instance;
		}
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	/* Update place */
	private void update(float deltaTime) {
		
		float oldX = getX();
		float oldY = getY();
		
		
		boolean collisionX = false, collisionY = false;
		
		setX(getX()  + velocity.x * deltaTime);
		
		if(velocity.x < 0) // going left
		{
			collisionX = collidesLeft();
		}
		else if(velocity.x > 0) // going right
		{
			collisionX = collidesRight();
		}
		
		setY(getY() + velocity.y * deltaTime);
		
		if(velocity.y < 0) // going down
		{
			collisionY = collidesBottom();
		}
		else if(velocity.y > 0) // going up
		{
			collisionY = collidesTop();
		}
		
		if(collisionX)
		{
			velocity.x = 0f;
			setX(oldX);
		}
		
		if(collisionY)
		{
			velocity.y = 0f;
			setY(oldY);
		}
		
		if(velocity.x > speed)
		{
			velocity.x = speed;
		}
		else if(velocity.y > speed)
		{
			velocity.y = speed;
		}
		
	}

	public void move(int direction) {

		switch(direction)
		{
		case UP:
			velocity.y = speed;
			break;
		case DOWN:
			velocity.y = -speed;
			break;
		case LEFT:
			velocity.x = -speed;
			break;
		case RIGHT:
			velocity.x = speed;
			break;
		}
		
	}

	private int checkCellType(float x, float y)
	{
		Cell cell = collisionlayer.getCell((int) (x / collisionlayer.getTileWidth()), (int) (y / collisionlayer.getTileHeight()));
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey(collisionKey)) {
				return BLOCKED;
			} 
			else {
				for(int i = 1; i < 5; ++i)
				{
					if(cell.getTile().getProperties().containsKey(puzzleKey + i)) {
						Gdx.app.log("Player", "puzzle" + i);
						return i;
					}
					
				}
			}
		}
		return -1;
			
		
	}
	
	
	public boolean collidesTop()
	{
		for(float step = 0; step < getWidth(); step += collisionlayer.getTileWidth() / collidecheckstepsize)
		{
			int cellType = checkCellType(getX() + step, getY() + getHeight());
			
			if(cellType == BLOCKED)
			{
				return true;
			}
			
			if(cellType > BLOCKED)
			{
				Gdx.app.log("Player", "Puzzletile noticed!");
				switch(cellType)
				{
				case PUZZLE1:
					game.setScreen(new GridPuzzle(game));
					break;
				case PUZZLE2:
					game.setScreen(new TextPuzzleScreen(game));
					break;
				case PUZZLE3:
					game.setScreen(new GraphPuzzle(game));
					break;
				case PUZZLE4:
					game.setScreen(new PicturePuzzle(game));
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesBottom()
	{
		for(float step = 0; step < getWidth(); step += collisionlayer.getTileWidth() / collidecheckstepsize)
		{
			int cellType = checkCellType(getX() + step, getY());
			if(cellType == BLOCKED)
			{
				return true;
			}
				
			if(cellType > BLOCKED)
			{
				Gdx.app.log("Player", "Puzzletile noticed!");
				switch(cellType)
				{
				case PUZZLE1:
					game.setScreen(new GridPuzzle(game));
					break;
				case PUZZLE2:
					game.setScreen(new TextPuzzleScreen(game));
					break;
				case PUZZLE3:
					game.setScreen(new GraphPuzzle(game));
					break;
				case PUZZLE4:
					game.setScreen(new PicturePuzzle(game));
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesLeft()
	{
		for(float step = 0; step < getHeight(); step += collisionlayer.getTileHeight() / collidecheckstepsize)
		{
			int cellType = checkCellType(getX(), getY() + step);
			if(cellType == BLOCKED)
			{
				return true;
			}
			
			if(cellType > BLOCKED)
			{
				Gdx.app.log("Player", "Puzzletile noticed!");
				switch(cellType)
				{
				case PUZZLE1:
					game.setScreen(new GridPuzzle(game));
					break;
				case PUZZLE2:
					game.setScreen(new TextPuzzleScreen(game));
					break;
				case PUZZLE3:
					game.setScreen(new GraphPuzzle(game));
					break;
				case PUZZLE4:
					game.setScreen(new PicturePuzzle(game));
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesRight()
	{
		for(float step = 0; step < getHeight(); step += collisionlayer.getTileHeight() / collidecheckstepsize)
		{
			int cellType = checkCellType(getX() + getWidth(), getY() + step); 
			if(cellType == BLOCKED)
			{
				return true;
			}
			
			if(cellType > BLOCKED)
			{
				Gdx.app.log("Player", "Puzzletile noticed!");
				switch(cellType)
				{
				
				case PUZZLE1:
					game.setScreen(new GridPuzzle(game));
					break;
				case PUZZLE2:
					game.setScreen(new TextPuzzleScreen(game));
					break;
				case PUZZLE3:
					game.setScreen(new GraphPuzzle(game));
					break;
				case PUZZLE4:
					game.setScreen(new PicturePuzzle(game));
				}
				return true;
			}
		}
		return false;
	}
	
	public void setVelocityX(float x) {
		velocity.x = x;
	}
	
	public void setVelocityY(float y) {
		velocity.y = y;
	}
	
}
