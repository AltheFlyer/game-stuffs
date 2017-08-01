package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class Enemy {
	
	public Rectangle hitbox;
	public float x;
	public float y;
	Vector2 movement;
	public int health;
	public float cooldown;
	public int speed;
	public float defaultCooldown;
	
	public Enemy(float startX,
				 float startY, 
				 float toX, 
				 float toY, 
				 int hp,
				 int height,
				 int width,
				 int moved,
				 float shotDelay) {
					movement = new Vector2();
					hitbox = new Rectangle();
					x = startX;
					y = startY;
					movement.x = toX;
					movement.y = toY;
					hitbox.x = startX;
					hitbox.y = startY;
					hitbox.width = width;
					hitbox.height = height;
					health = hp;
					cooldown = 2;
					speed = moved;
					defaultCooldown = shotDelay;
	}
	
	public Array<Bullet> attack(float charX, float charY){
		Array<Bullet> x = new Array<Bullet>();
		return x;
	}
	
	public void move(float toX, float toY){
		
	}
	
}
