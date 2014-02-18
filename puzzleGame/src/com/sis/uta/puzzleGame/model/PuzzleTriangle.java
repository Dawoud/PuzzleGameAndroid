package com.sis.uta.puzzleGame.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;

public class PuzzleTriangle extends Polygon {

	private PuzzleTriangle[] adjacentTriangles;
	private Color colour;
	private boolean changeable;
	private float[] vertices;
	
	public PuzzleTriangle(float[] vertices, boolean changeable)
	{
		super(vertices);
		colour = new Color(Color.WHITE);
		this.vertices = vertices;
		this.changeable = changeable;
		
		if(!changeable)
		{
			Color[] colors = new Color[] {new Color(Color.BLUE), new Color(Color.MAGENTA)};
			
			if(Math.random() < 0.5)
			{
				this.colour = colors[1];
			}
			else
			{
				this.colour = colors[0];
			}
		
		}
	}

	
	/** Method to check if adjacent triangles are same color as this one.
	 * @return true if adjacent triangles were other color than this triangle
	 */
	public boolean checkTriangles()
	{
		if(this.colour.equals(Color.WHITE))
		{
			return false;
		}
		for(int i = 0; i < adjacentTriangles.length; ++i)
		{
			if(adjacentTriangles[i] != null)
			{
				if(adjacentTriangles[i].getColour().equals(this.colour))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Color getColour() {
		return colour;
	}


	public void setColour(Color colour) {
		if(changeable)
			this.colour = colour;
	}

	
	public float[] getVertices() {
		return vertices;
	}
	
	public void setAdjacentTriangles(PuzzleTriangle a, PuzzleTriangle b, PuzzleTriangle c) {
		this.adjacentTriangles = new PuzzleTriangle[3];
		adjacentTriangles[0] = a;
		adjacentTriangles[1] = b;
		adjacentTriangles[2] = c;
	}

}
