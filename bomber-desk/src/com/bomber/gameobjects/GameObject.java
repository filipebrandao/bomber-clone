package com.bomber.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomber.common.PoolObject;


public abstract class GameObject extends PoolObject {
	public Vector2 mPosition;
	public Rectangle mBounds;
	private int mUUID;

	public abstract void update();

	/**
	 * Tem de devolver o mUUID.
	 */
	@Override
	public final int hashCode()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public abstract boolean equals(Object _rhs);

	/**
	 * � utilizado pela ObjectPool quando o objecto � marcado como disponivel.
	 * Este m�todo deve ser sempre chamar o seu super antes/depois de efectuar
	 * mudan�as numa classe derivada
	 * 
	 * mIsBeingDestroyed = false;
	 */
	public void reset()
	{
		throw new UnsupportedOperationException();
	}
}