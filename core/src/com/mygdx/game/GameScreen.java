package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen{
	
	final ArenaGame game;
	
	private Texture characterImage;
	private Texture enemyImage;
	private Texture bulletImage;

	private Texture backgroundImage;
	private OrthographicCamera camera;
	private ShapeRenderer render;
	private Rectangle character;
	private Array<Enemy> enemies;
	private Array<Bullet> bullets;
	private Vector3 mousePos;
	private int randomValue;
	private int spawnX;
	private int spawnY;
	private int playerHealth;
	private long lastSpawn;
	private long cooldown;

	
	public GameScreen(final ArenaGame game) {
		this.game = game;
		characterImage = new Texture("Character.png");
		enemyImage = new Texture("regular enemy.png");
		bulletImage = new Texture("FriendlyBullet.png");
		backgroundImage = new Texture("BG.png");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
		
		character = new Rectangle();
		character.x = 100;
		character.y = 100;
		character.width = 10;
		character.height = 10;
		
		cooldown = 0;
		playerHealth = 100;
		
		enemies = new Array<Enemy>();
		bullets = new Array<Bullet>();
		spawnEnemy();
		
		mousePos = new Vector3();
		render = new ShapeRenderer();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//System.out.println(Gdx.graphics.getDeltaTime());
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		
		
				
		game.batch.setProjectionMatrix(camera.combined);
		render.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		//Background
		game.batch.draw(backgroundImage, 0, 0);
		game.batch.end();
		
		render.begin(ShapeType.Filled);
		//Enemy Spawners
		render.setColor(0.6f, 0.6f, 0.6f, 1);
		render.box(0, 225, 0, 100, 150, 0);
		render.box(750, 225, 0, 100, 150, 0);
		render.box(350, 0, 0, 150, 100, 0);
		render.box(350, 500, 0, 150, 100, 0);
		//Draw sidebar
		render.setColor(0.3f, 0.3f, 0.3f, 1);
		render.box(850, 0, 0, 150, 600, 0);	
		render.end();
		
		
		//Draw game objects
		game.batch.begin();
		//Enemies
		for(Enemy enemy: enemies){
			game.batch.draw(enemyImage, enemy.x ,enemy.y);
		}
		//Bullets
		for (Bullet bullet: bullets){
			game.batch.draw(bulletImage, bullet.x, bullet.y);
		}
		//Character
		game.batch.draw(characterImage, character.x, character.y);
		game.batch.end();
		
		//Character movement
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			character.y += 175 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			character.x -= 175 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			character.y -= 175 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			character.x += 175 * Gdx.graphics.getDeltaTime();
		}
		
		//Keep character onscreen
		if (character.x < 0) {
			character.x = 0;
		} else if (character.x > 840) {
			character.x = 840;
		}
		if (character.y < 0) {
			character.y = 0;
		} else if (character.y > 590) {
			character.y = 590;
		}
		
		//Spawn new enemy
		if (TimeUtils.nanoTime() - lastSpawn > 700000000) {
			spawnEnemy();
		}
		
		Iterator<Enemy> iter = enemies.iterator();
		Iterator<Bullet> iter1 = bullets.iterator();
		
		//Enemy cycles
		while(iter.hasNext()){
			Enemy enemy = iter.next();
			//Enemy movement
			enemy.move(character.x + 5, character.y + 5);
			//Enemy Shooting
			bullets.addAll(enemy.attack(character.x, character.y));
			
		}
		
		//bullet movement
		while(iter1.hasNext()){
			Bullet bullet = iter1.next();
			bullet.moveBullet(Gdx.graphics.getDeltaTime());
			if (bullet.x < 0 || bullet.x > 850 || bullet.y < 0 || bullet.y > 600) {
				//Remove if out of bounds
				iter1.remove();
			}	
		}
		
		//Bullet Collisions
		Iterator<Enemy> iterEnemy = enemies.iterator();
		
		while (iterEnemy.hasNext()) {
			try {
				Enemy enemy = iterEnemy.next();
				Iterator<Bullet> iterBullet = bullets.iterator();
				while (iterBullet.hasNext()) {
					Bullet bullet = iterBullet.next();
					if (enemy.hitbox.overlaps(bullet.hitbox) && bullet.friendly) {	
						//Enemy collisions
						iterBullet.remove();
						iterEnemy.remove();
					} else if (bullet.hitbox.overlaps(character) && !bullet.friendly) {
						//Player collisions
						playerHealth -= 10;
						System.out.println(playerHealth);
						iterBullet.remove();
						
						if (playerHealth <= 0) {
							game.setScreen(new MainMenuScreen(game));
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Something went wrong with the arrays");
			}
		}
		
		//Shooting
		if (Gdx.input.isTouched() && TimeUtils.nanoTime() - cooldown > 300000000) {
			spawnBullet(character.x, character.y, true, 0);
			spawnBullet(character.x, character.y, true, MathUtils.degreesToRadians * MathUtils.random(-15,15));
			spawnBullet(character.x, character.y, true, MathUtils.degreesToRadians * MathUtils.random(-15,15));
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
		characterImage.dispose();
		enemyImage.dispose();
		bulletImage.dispose();
		backgroundImage.dispose();
		render.dispose();
	}
	
	//Generate a random enemy
	private void spawnEnemy() {
		//Generate spawn area first
		randomValue = MathUtils.random(1, 4);
		switch (randomValue) {
			case 1: 
				//Left spawner
				spawnX = MathUtils.random(10, 90);
				spawnY = MathUtils.random(235, 365);
				break;
			case 2:
				//Top spawner
				spawnX = MathUtils.random(360, 490);
				spawnY = MathUtils.random(510, 590);
				break;
			case 3:
				//Right spawner
				spawnX = MathUtils.random(750, 840);
				spawnY = MathUtils.random(235, 365);
				break;
			case 4:
				//Bottom spawner
				spawnX = MathUtils.random(360, 490);
				spawnY = MathUtils.random(10, 90);
				break;
		}
				
		randomValue = MathUtils.random(1, 3);
		
		switch (randomValue) {
			case 1: RegularEnemy enemy = new RegularEnemy(spawnX, spawnY, 0, 0, 0, 13, 13, 100);
					enemies.add(enemy);
					break;
			case 2: TripleShot tri = new TripleShot(spawnX, spawnY, 0, 0, 0, 13, 13, 0);
					enemies.add(tri);
					break;
			case 3: MachineGun mach = new MachineGun(spawnX, spawnY, 0, 0, 0, 13, 13, 0);
					enemies.add(mach);
					break;
		}
		lastSpawn = TimeUtils.nanoTime();
	}
	
	//Generates player bullets
	private void spawnBullet(float x, float y, boolean friendly, float offset) {
		camera.unproject(mousePos.set(Gdx.input.getX(),Gdx.input.getY(),0));
		
		Bullet bullet = new Bullet(
				x + 3,
				y + 3,
				mousePos.x - character.x,
				mousePos.y - character.y,
				350,
				friendly,
				MathUtils.degreesToRadians * MathUtils.random(-2, 2) + offset
				);
	
		cooldown = TimeUtils.nanoTime();
		bullets.add(bullet);
		 
	}
	
}
