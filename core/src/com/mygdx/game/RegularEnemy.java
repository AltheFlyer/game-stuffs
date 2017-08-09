package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class RegularEnemy extends Enemy {
	
	public RegularEnemy(float startX, float startY, float toX, float toY, int hp, int height,
			int width, int speed) {
		super(startX, startY, toX, toY, 35, height, width, speed, 1);
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
				200,
				false,
				0
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
