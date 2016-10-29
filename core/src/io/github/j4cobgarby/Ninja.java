package io.github.j4cobgarby;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ninja {
	private final int size = 16;
	private final float velMultiplier = 0.8f;
	private final float momMultiplier = 0.2f;
	
	private Vector2 pos;
	private Vector2 velo;
	private Vector2 momentum;
	private Color col;
	private float speed;
	private float jumpForce;
	
	public Rectangle body;
	
	public ArrayList<Vector2> prevs = new ArrayList<Vector2>();
	private final int maxPrevs = 10;
	private final float timeBetweenPrevs = 1;
	private double elapsed = 0;
	private Color trailCol = new Color(0xed4d25ff);
		
	/**
	 * A class for a player, with a size, speed, colour, and a trail.
	 * @param pos The initial position of the player.
	 * @param velo The initial velocity of the player.
	 * @param col The colour of the player.
	 * @param jumpForce The upwards velocity added when the player jumps.
	 */
	public Ninja (Vector2 pos) {
		this.pos = pos;
		
		this.velo = new Vector2(0, 0);
		this.col = new Color(0x353535ff);
		this.speed = 10;
		this.jumpForce = 300;
		this.momentum = new Vector2(0, 0);
		
		body = new Rectangle(pos.x, pos.y, size, size);
	}
	
	/**
	 * This method draws the player at it's current position. It also draws the trail.
	 * @param rend The ShapeRenderer used to draw the rectangles.
	 */
	public void draw(ShapeRenderer rend) {
		if (rend.isDrawing()) {
			for (int i = 0; i < prevs.size(); i++) { // Trail
				rend.rect(prevs.get(i).x, prevs.get(i).y, size, size, 
						trailCol, 
						trailCol, 
						trailCol, 
						trailCol);
			}
			 
			rend.setColor(col);
			rend.rect(body.x, body.y, body.width, body.height);
		}
	}
	
	public void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.A)) {
			momentum.x -= speed;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			momentum.x += speed;	
		}
		if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			momentum.y += jumpForce;
		}
	}
	
	public void update() {
		Gdx.graphics.setTitle(Integer.toString(Gdx.graphics.getFramesPerSecond()));
		
		this.elapsed += Gdx.graphics.getDeltaTime() * 1000;
		
		if (/*elapsed >= timeBetweenPrevs*/ true) {
			this.elapsed = 0;
			if (prevs.size() >= maxPrevs) {
				// remove first element, add to end.
				prevs.remove(0);
				prevs.add(pos.cpy());
			}
			else prevs.add(pos.cpy());
		}
		
		momentum.x *= momMultiplier; // Decrease momentum first
		momentum.y *= momMultiplier;
		
		velo.x *= velMultiplier; // Then decrease the velocity. Both of these are to slow down the player.
		velo.y *= velMultiplier;
		
		velo.x += momentum.x; // Add the momentum to the velocity, to respond to input.
		velo.y += momentum.y;
		
		pos.x += velo.x; // Move the player.
		pos.y += velo.y;
		
		try {
			translate(Main.gravity.x, Main.gravity.y);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		body.x = pos.x;
		body.y = pos.y;
	}
	
	public void translate(float x, float y) throws OverlapException {
		Rectangle supposedRect = new Rectangle(pos.x + x, pos.y + y, size, size);
		for (Rectangle c : Main.currentLevel.colliders) {
			if (c.overlaps(supposedRect)) {
				throw new OverlapException("Rectangle collides.");
			}
		}
		pos.x = supposedRect.x;
		pos.y = supposedRect.y;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public Vector2 getVelo() {
		return velo;
	}

	public void setVelo(Vector2 velo) {
		this.velo = velo;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getJumpForce() {
		return jumpForce;
	}

	public void setJumpForce(float jumpForce) {
		this.jumpForce = jumpForce;
	}

	public int getSize() {
		return size;
	}
}
