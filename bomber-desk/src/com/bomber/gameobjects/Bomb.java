package com.bomber.gameobjects;

import com.bomber.world.GameWorld;

/**
 * A bomba quando rebenta chama o metodo respectivo no GameWorld, que faz todas
 * as verifica��es e s� depois cria a explos�o.
 */
public abstract class Bomb extends MovableObject {
	private int mTicksUntilExplosion;
	private short mBombPower = 3;
	public GameWorld mWorld;

	public abstract void update();
}