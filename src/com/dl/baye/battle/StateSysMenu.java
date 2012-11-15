package com.dl.baye.battle;

import android.graphics.Canvas;

import com.dl.baye.BattleView;

public class StateSysMenu implements GameState {
	BattleView bv;
	public StateSysMenu(BattleView bv){
		this.bv = bv;
	}
	@Override
	public boolean KeyDown(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Draw(Canvas canvas) {
		// TODO Auto-generated method stub

	}

}
