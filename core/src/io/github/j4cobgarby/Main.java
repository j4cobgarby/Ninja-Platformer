package io.github.j4cobgarby;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Main extends ApplicationAdapter {
	public ShapeRenderer rend;
	public OrthographicCamera cam;
	private final Color bgcolor = new Color(0xc1c0e8ff);
	public static Vector2 gravity = new Vector2(0, -20);

	Ninja ninja = new Ninja(new Vector2(0, 0));
	
	public static Level currentLevel;
	
	@Override
	public void create() {
		rend = new ShapeRenderer();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.zoom = 0.6f;
		
		currentLevel = Levels.Layouts.l1;
	}

	@Override
	public void render() {
		ninja.handleInput();
		ninja.update();

		Gdx.gl.glClearColor(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		rend.setProjectionMatrix(cam.combined);
		rend.begin(ShapeType.Filled);
		ninja.draw(rend);
		currentLevel.draw(rend);
		rend.end();

		cam.update();

		if (Gdx.input.isKeyPressed(Keys.UP))
			cam.translate(0, 5);
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			cam.translate(-5, 0);

		cam.position.x = MathUtils.lerp(cam.position.x, ninja.getPos().x, 0.1f);
		cam.position.y = MathUtils.lerp(cam.position.y, ninja.getPos().y, 0.1f);
		
		float camViewportHalfX = cam.viewportWidth/2;
		float camViewportHalfY = cam.viewportHeight/2;
	}

	@Override
	public void dispose() {
		rend.dispose();
	}
}
