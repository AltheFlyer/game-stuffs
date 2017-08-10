package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class SwirlEnemy extends Enemy {
	
	public int direction;
	
	public SwirlEnemy(float startX, float startY, float toX, float toY, int hp, int height, 
			int width) {
		super(startX, startY, toX, toY, 70, height, width, 50, 0.15f, "regular enemy.png");
		// TODO Auto-generated constructor stub
	}
	
	public Array<Bullet> attack(float charX, float charY){
		Array<Bullet> bullets = new Array<Bullet>();
		if (cooldown > 0) {
			cooldown -= Gdx.graphics.getDeltaTime();
		} else {
			direction += 15;
			Bullet bullet1 = new Bullet(
					x + 3, 
					y + 3, 
					x + 4, 
					y + 4, 
					200, 
					false,
					MathUtils.degreesToRadians * direction	
			);
			Bullet bullet2 = new Bullet(
					x + 3, 
					y + 3, 
					x + 4, 
					y + 4, 
					200, 
					false,
					MathUtils.degreesToRadians * (direction + 90)	
			);
			Bullet bullet3 = new Bullet(
					x + 3, 
					y + 3, 
					x + 4, 
					y + 4, 
					200, 
					false,
					MathUtils.degreesToRadians * (direction + 180)	
			);
			Bullet bullet4 = new Bullet(
					x + 3, 
					y + 3, 
					x + 4, 
					y + 4, 
					200, 
					false,
					MathUtils.degreesToRadians * (direction	- 90)
			);
			bullets.add(bullet1);
			bullets.add(bullet2);
			bullets.add(bullet3);
			bullets.add(bullet4);
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
