package com.sis.uta.puzzleGame.screens;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;

public class ThirdPuzzle implements Screen {

	public ThirdPuzzle(Game game) {
		super();
		this.game = game;
	}

	private Game game;
	
	private Triangle[] trianglesclasses;
	
	private ShapeRenderer renderer;
	
	private OrthographicCamera camera;

	private float middleX;
	private float middleY;

	private float[] triangles;

	private int trianglescount;

	private Color[] colours;

	private float yunit;

	private float xunit;

	private int trianglecounter;
	
	@Override
	public void render(float delta) {


		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// render first filled triangles
		renderer.begin(ShapeType.Filled);
		int n = 0;
		for(int i = 0; i < trianglescount; ++i)
		{	
			
			Triangle triangled = trianglesclasses[i];
			renderer.setColor(triangled.getColour());
			float[] xn = triangled.getVertices();
			renderer.triangle(xn[0], xn[1], xn[2], xn[3], xn[4], xn[5]);
		}
		renderer.end();

		// render outlines to triangles
		renderer.begin(ShapeType.Line);
		n = 0;
		for(int i = 0; i < trianglescount; ++i)
		{
			renderer.setColor(Color.BLACK);
		
			Triangle triangled = trianglesclasses[i];
			float[] xn = triangled.getVertices();
			renderer.triangle(xn[0], xn[1], xn[2], xn[3], xn[4], xn[5]);
		}
		renderer.end();
		
		// currently just get back when screen is touched
		if(Gdx.input.justTouched())
		{

			Gdx.app.log("Thirdpuzzle", "checking puzzles, total of "+ trianglesclasses.length);
			for(int i = 0; i < trianglesclasses.length; ++i)
			{

				Gdx.app.log("Thirdpuzzle", "checking " + i + " triangle with coordinates x: "+ Gdx.input.getX() + " y: " +Gdx.input.getY() );
				
				if(trianglesclasses[i].contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()))
				{
					Gdx.app.log("Thirdpuzzle", "found one!");
					trianglesclasses[i].setColour(new Color(Color.GREEN));
					break;
				}
			}
			
//			((Game)Gdx.app.getApplicationListener()).setScreen(new FirstSection(game));
		}
		
	}

	
	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		
		renderer = new ShapeRenderer();
		
		camera = new OrthographicCamera();
		
		camera.viewportHeight = Gdx.graphics.getHeight();
		camera.viewportWidth = Gdx.graphics.getWidth();
		
		middleX = Gdx.graphics.getWidth() / 2f;
		
		middleY = Gdx.graphics.getHeight() / 2f;
		
		camera.position.set( middleX, middleY ,0);
		
		camera.update();
		
		renderer.setProjectionMatrix(camera.combined);
	
		Gdx.input.setInputProcessor(null);
		
		createTriangles();
		
		trianglesclasses = new Triangle[triangles.length/6];
		
		int n = 0;
		for(int i = 0; i < triangles.length;)
		{
			Triangle triangle = new Triangle(new float[] {triangles[i], triangles[i+1], triangles[i+2], triangles[i+3], triangles[i+4], triangles[i+5]}, true);
			trianglesclasses[n] = triangle;
			i += 6;
			n++;
		}
		
//		Create random colours to triangles, just because
		Random rand = new Random();
		
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		
		Color randomColor = new Color(r, g, b, 1 );
		
		colours = new Color[trianglescount];
		
		colours[0] = randomColor;
		
		for(int i = 1; i < trianglescount; ++i)
		{
			r = rand.nextFloat();
			g = rand.nextFloat();
			b = rand.nextFloat();
		
			randomColor = new Color(r, g, b, 1 );
			
			colours[i] = randomColor;
		}
		
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
		//renderer.dispose();

	}
	
	
	/**
	 * Creates triangles to graph puzzle map. 
	 */
	private void createTriangles()
	{
		Gdx.app.log("ThirdPuzzle", "creating triangles");
		trianglescount = 24;
		
		trianglecounter = 0;
		
		// units which declares how big triangles are going to be
		yunit = Gdx.graphics.getHeight()/12;
		xunit = Gdx.graphics.getWidth()/16;
		
		triangles = new float[trianglescount* 3 * 2];
		
		// first of the first row, placement of whole structure is based on these values
		triangles[0] = (middleX - xunit*2);
		triangles[1] = (middleY + yunit*4);
		
		triangles[2] = ((middleX - xunit*2) - xunit);
		triangles[3] = ((middleY + yunit*4) - (yunit*2));
		
		triangles[4] = ((middleX - xunit*2) + xunit);
		triangles[5] = ((middleY + yunit*4) - (yunit*2));
		
		int i = 6;
		
		
//		rest of the first row 
		for(trianglecounter = 1; trianglecounter < 5; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
		
		// first of the second row
		
		triangles[i] = triangles[2];
		i++;
		triangles[i] = triangles[3];
		i++;
		
		triangles[i] = triangles[2] - xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		triangles[i] = triangles[2] + xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		// rest of the second row
		for(trianglecounter = 1; trianglecounter < 7; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
		
		// first triangle of the third row
		triangles[i] = triangles[2] - xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		triangles[i] = triangles[2] +xunit;
		i++;
		triangles[i] = triangles[3] - yunit*2;
		i++;
		
		triangles[i] = triangles[2];
		i++;
		triangles[i] = triangles[3] - yunit*4;
		i++;
		
		// rest of the third row
		for(trianglecounter = 0; trianglecounter < 6; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
		
		// Create fourth rows first triangle
		triangles[i] = triangles[2];
		i++;
		triangles[i] = triangles[3] - yunit*4;
		i++;
		
		triangles[i] = triangles[2] +xunit*2;
		i++;
		triangles[i] = triangles[3] - yunit*4;
		i++;
		
		triangles[i] = triangles[2] + xunit;
		i++;
		triangles[i] = triangles[3] - yunit*6;
		i++;
	
		// Rest of the fourth row
		for(trianglecounter = 0; trianglecounter < 4; ++trianglecounter)
		{
			if(trianglecounter % 2 == 0)
			{
				i = createEvenTriangle(i);				
			}
			else
			{
				i = createOddTriangle(i);
			}
		}
	}
	
	private int createEvenTriangle(int i)
	{
		triangles[i] = triangles[i-4];
		i++;
		triangles[i] = triangles[i-4];
		i++;
		 
		triangles[i] = triangles[i-2] - xunit;
		i++;
		triangles[i] = triangles[i-2] - yunit*2;
		i++;
		
		triangles[i] = triangles[i-2] + xunit*2;
		i++;	
		triangles[i] = triangles[i-2];
		i++; 
		
		return i;
	}

	private int createOddTriangle(int i)
	{
		triangles[i] = triangles[i-6];
		i++;
		triangles[i] = triangles[i-6]; 
		i++;
		
		triangles[i] = triangles[i-2] + xunit*2;
		i++;
		triangles[i] = triangles[i-2];
		i++;
		
		triangles[i] = triangles[i-2] - xunit;
		i++;	
		triangles[i] = triangles[i-2] - yunit*2;
		i++;
		
		return i;
	}
	
	private class Triangle extends Polygon {

		
		
		private Color colour;
		private boolean changeable;
		private float[] vertices;
		
		public Triangle(float[] vertices, boolean changeable)
		{
			super(vertices);
			colour = new Color(Color.WHITE);
			this.vertices = vertices;
			this.changeable = changeable;
			
			if(!changeable)
			{
				colour = new Color(Color.BLUE);
			}
			
		}


		public Color getColour() {
			return colour;
		}


		public void setColour(Color colour) {
			if(changeable)
				this.colour = colour;
		}


		public boolean isChangeable() {
			return changeable;
		}
		
		public float[] getVertices() {
			return vertices;
		}
		
	}

}
