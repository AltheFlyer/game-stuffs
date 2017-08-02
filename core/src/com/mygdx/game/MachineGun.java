package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class MachineGun extends Enemy {
	
	public int shotsFired;
	public float shotTime;

	public MachineGun(float startX, float startY, float toX, float toY, int hp, int height,
			int width, int moved) {
		super(startX, startY, toX, toY, hp, height, width, moved, 5);
		shotsFired = 0;
		shotTime = 1;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Array<Bullet> attack(float charX, float charY) {
		Array<Bullet> bullets = new Array<Bullet>();
		if (cooldown > 0) {
			cooldown -= Gdx.graphics.getDeltaTime();
		} else {
			if (shotTime <= 0) {
				Bullet bullet = new Bullet(x + 3,
						y + 3,
						charX - x,
						charY - y,
						200,
						false,
						0
					);
					bullets.add(bullet);
					shotTime = 0.1f;
					shotsFired += 1;
			}
			
			shotTime-=Gdx.graphics.getDeltaTime();
			
			//Only reset cool down once 5 bullets are shot
			if (shotsFired >= 5) {
				cooldown = defaultCooldown;
				shotsFired = 0;
			}
			
		}
		return bullets;
	}
	
	@Override
	public void move(float toX, float toY) {
		//Do nothing
	}
	

}
