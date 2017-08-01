package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {
	
	Vector3 touchPoint;
	private ArenaGame game;
	private Texture play;
	private OrthographicCamera camera;
	Rectangle hitbox;
	
	public MainMenuScreen(final ArenaGame game){
		this.game = game;
		play = new Texture("PlayButton.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
		touchPoint = new Vector3();
		hitbox = new Rectangle();
		hitbox.set(350, 250, 650, 350);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.begin();
		game.batch.draw(play, 350, 250);
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
			if (hitbox.contains(touchPoint.x, touchPoint.y)){
				game.setScreen(new GameScreen(game));
				dispose();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		play.dispose();
	}
	
}
