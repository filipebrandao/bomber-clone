package com.bomber.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class Drawable extends GameObject {
	public int mAnimationTicks = 0;
	/**
	 * Se TRUE ent�o obtem a sprite baseado no numero de ticks, caso contr�rio
	 * obtem a 1�
	 */
	public boolean mLoopAnimation;
	public Animation mCurrentAnimation;

	/**
	 * Actualiza o animation ticks. mas apenas se
	 */
	public abstract void update();

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