package com.sis.uta.puzzleGame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	private Animation up, down, left, right;
	private float animationTime = 0;
	private TextureRegion currentFrame;
	private Texture animationsheet;
	private TextureRegion[] downFrames, upFrames, leftFrames, rightFrames;
	private static TextureRegion stillFrame;

	
	private Player(TiledMapTileLayer collisionlayer, puzzleGame game) {
		super(new Sprite(new Texture("maps/character.png")));

		this.collisionlayer = collisionlayer;
		
		this.game = game;
		
		collisionKey = "blocked";
		puzzleKey = "puzzle";
		
		loadTextures();
		
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
			instance.loadTextures();
			return instance;
		}
	}
	
	private void loadTextures()
	{
		animationsheet = new Texture("maps/character1.png");
		
		TextureRegion[][] tmp = TextureRegion.split(animationsheet, animationsheet.getWidth()/3, animationsheet.getHeight()/4);
		
		// size of different states in texture
		upFrames = new TextureRegion[] {tmp[3][0], tmp[3][1], tmp[3][2]};
		downFrames = new TextureRegion[] {tmp[2][0], tmp[2][1], tmp[2][2]};
		leftFrames = new TextureRegion[] {tmp[0][0], tmp[0][1], tmp[0][2]};
		rightFrames = new TextureRegion[] {tmp[1][0], tmp[1][1], tmp[1][2]};
		
		stillFrame = downFrames[1];
		
		up = new Animation(1/6f, upFrames);
		down = new Animation(1/6f, downFrames);
		left = new Animation(1/6f, leftFrames);
		right = new Animation(1/6f, rightFrames);
		
		up.setPlayMode(Animation.LOOP);
		down.setPlayMode(Animation.LOOP);
		left.setPlayMode(Animation.LOOP);
		right.setPlayMode(Animation.LOOP);
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
			
		animationTime += deltaTime;
		
		currentFrame = stillFrame;
		// Animations
		if(velocity.x > 0)
		{
			// moving right
			currentFrame = right.getKeyFrame(animationTime);
		}
		else if(velocity.x < 0)
		{
			// moving left
			currentFrame = left.getKeyFrame(animationTime);
		}
		
		if(velocity.y > 0)
		{
			// moving up
			currentFrame = up.getKeyFrame(animationTime);
		}
		else if(velocity.y < 0)
		{
			//moving down
			currentFrame = down.getKeyFrame(animationTime);
		}
		
		setRegion(currentFrame);
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
				startPuzzle(cellType);
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
				startPuzzle(cellType);
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
				startPuzzle(cellType);
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
				startPuzzle(cellType);
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
	
	private void startPuzzle(int cellType)
	{
		switch(cellType)
		{
		
		case PUZZLE1:
			GridPuzzle puzzle = new GridPuzzle(game);
			game.setScreen(puzzle);
			puzzle.startDialog("Can you help me? I need to solve this gridpuzzle\n to open this safe. My boss forgot to"
			+" tell me \nthe right code and now I'm stuck with this \" safety \" puzzle");
			break;
		case PUZZLE2:
			TextPuzzleScreen puzzle2 = new TextPuzzleScreen(game);
			game.setScreen(puzzle2);
			puzzle2.startDialog("Yea I got couple of puzzles to solve for you,\n but I must worn you, even I struggled with" +
					"most of them.");
			
			// after solving jaska's puzzles player is forwarded into cafeteria for graphpuzzle
			break;
		case PUZZLE3:
			GraphPuzzle puzzle3 = new GraphPuzzle(game); 
			game.setScreen(puzzle3);
			puzzle3.startDialog("Thank you for coming! \nI heard that there was someone in the house\nwho's capable to solve puzzles" +
					". \nI need you to solve this graphpuzzle for me.");
			break;
		case PUZZLE4:
			PicturePuzzle puzzle4 = new PicturePuzzle(game);
			game.setScreen(puzzle4);
		}
	}
	
}
