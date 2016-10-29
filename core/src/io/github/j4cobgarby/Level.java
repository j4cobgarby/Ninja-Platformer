package io.github.j4cobgarby;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Level {
	public int[][] layout;
	private int rows;
	private int cols;
	public ArrayList<Rectangle> colliders = new ArrayList<Rectangle>(); // Only rectangles which collide, to speed up collision detection.
	
	public static int tileSize = 30;
	
	public Level(int[][] layout) {
		this.layout = layout;
		this.rows = layout.length;
		this.cols = layout[0].length;
		
		for (int r = 0; r < layout.length; r++) { // Populate colliders
			for (int c = 0; c < layout[r].length; c++) {
				if (layout[r][c] != 0) {
					colliders.add(new Rectangle(c*tileSize, (layout.length-r)*tileSize, tileSize, tileSize));
				}
			}
		}
		System.out.println(String.format("DEBUG: Amount of colliders: %1$d", colliders.size()));
	}
	
	public void draw(ShapeRenderer rend) {
		for (Rectangle r : colliders) {
			rend.rect(r.x, r.y, r.width, r.height);
		}
	}
}
