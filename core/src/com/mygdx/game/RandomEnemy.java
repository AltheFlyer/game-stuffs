package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RandomEnemy extends Enemy {

	public RandomEnemy(float startX, float startY, float toX, float toY, int hp, int height, int width) {
		super(startX, startY, toX, toY, 70, height, width, 75, 0.3f, "regular enemy.png");
		// TODO Auto-generated constructor stub
	}
	
	public Array<Bullet> attack(float charX, float charY){
		Array<Bullet> bullets = new Array<Bullet>();
		if (cooldown > 0) {
			cooldown -= Gdx.graphics.getDeltaTime();
		} else {
			Bullet bullet = new Bullet(x + 3,
				y + 3,
				charX - x,
				charY - y,
				MathUtils.random(150, 250),
				false,
				MathUtils.degreesToRadians * MathUtils.random(0, 360)
			);
			bullets.add(bullet);
			cooldown = defaultCooldown;
		}
		return bullets;
	}
	
	public void move(float toX, float toY){
		double movement = Math.atan2((toY) - (y + 7), (toX ) - (x + 7));
		x += Math.cos(movement)  * speed * Gdx.graphics.getDeltaTime();
		y += Math.sin(movement)  * speed * Gdx.graphics.getDeltaTime();
		hitbox.x += Math.cos(movement)  * speed * Gdx.graphics.getDeltaTime();
		hitbox.y += Math.sin(movement)  * speed * Gdx.graphics.getDeltaTime();
	}
}
