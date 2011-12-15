package com.bomber.gameobjects.bonus;

import com.bomber.gameobjects.Player;

public abstract class TemporaryBonus extends Bonus {

	protected Player mAffectedPlayer;
	private int mEffectDuration;
	private int mEffectCurrentDuration;

	public short mType;

	TemporaryBonus(short _type, int _durationTicks) {
		mEffectDuration = _durationTicks;
		mAffectedPlayer = null;
		mType = _type;
	}
	
	
	public <T extends TemporaryBonus> void clone(T _bonus)
	{
		_bonus.mAffectedPlayer =mAffectedPlayer;
		_bonus.mEffectDuration = mEffectDuration;
		_bonus.mEffectCurrentDuration = mEffectCurrentDuration;
		
		_bonus.mType = mType;
	}
	
	
	@Override
	public final void applyEffect(Player _player)
	{
		mAffectedPlayer = _player;
		mEffectCurrentDuration = 0;

		// Retira algum do mesmo tipo que possa existir na lista
		for (TemporaryBonus b : mAffectedPlayer.mActiveBonus)
		{
			if (b.mType == mType)
			{
				mAffectedPlayer.mActiveBonus.releaseObject(b);
				break;
			}
		}

		onApplyEffect();
	}

	@Override
	public void update()
	{
		// Actualiza a anima��o
		super.update();

		if (mAffectedPlayer == null)
			return;

		if (++mEffectCurrentDuration >= mEffectDuration)
		{
			onRemoveEffect();
			mAffectedPlayer.mActiveBonus.releaseObject(this);
		}
	}

	public final void removeEffect()
	{
		onRemoveEffect();
	}
	
	public abstract void onRemoveEffect();
	public abstract void onApplyEffect();
}
