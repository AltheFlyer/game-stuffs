package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class TripleShot extends Enemy {
	
	public TripleShot(float startX, float startY, float toX, float toY, int hp, int height,
			int width, int moved) {
		super(startX, startY, toX, toY, 70, height, width, moved, 3);
		// TODO Auto-generated constructor stub
	}
	
	public Array<Bullet> attack(float charX, float charY){
		Array<Bullet> bullets = new Array<Bullet>();
		if (cooldown > 0) {
			cooldown -= Gdx.graphics.getDeltaTime();
		} else {
			Bullet bullet1 = new Bullet(x + 3,
				y + 3,
				charX - x,
				charY - y,
				200,
				false,
				MathUtils.degreesToRadians * -25
			);
			Bullet bullet2 = new Bullet(x + 3,
				y + 3,
				charX - x,
				charY - y,
				200,
				false,
				0
			);
			Bullet bullet3 = new Bullet(x + 3,
				y + 3,
				charX - x,
				charY - y,
				200,
				false,
				MathUtils.degreesToRadians * 25
			);
		
			bullets.add(bullet1);
			bullets.add(bullet2);
			bullets.add(bullet3);
			cooldown = defaultCooldown;
		}
		return bullets;
	}
}
