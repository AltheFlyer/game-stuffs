package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
	
	public Rectangle hitbox;
	public float x;
	public float y;
	public Vector2 move;
	public boolean friendly;
	public int speed;
	
	
	public Bullet(float startX, float startY, float moveX, float moveY, int moved, boolean isFriendly, float variance){
		x = startX;
		y = startY;
		hitbox = new Rectangle();
		hitbox.x = startX;
		hitbox.y = startY;
		hitbox.width = 5;
		hitbox.height = 5;
		move = new Vector2();
		move.x = (float) Math.cos(Math.atan2(moveY, moveX) + variance);
		move.y = (float) Math.sin(Math.atan2(moveY, moveX) + variance);
		friendly = isFriendly;
		speed = moved;
	}
	
	/**
	 * float time should always be Gdx.graphics.getDeltaTime();
	 * 
	 */
	public void moveBullet(float time){
		x += move.x * speed * time;
		y += move.y * speed * time;
		hitbox.x += move.x * speed * time;
		hitbox.y += move.y * speed * time;
	}

}
