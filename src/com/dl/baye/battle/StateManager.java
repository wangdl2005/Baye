package com.dl.baye.battle;

import android.graphics.Canvas;
import android.util.Log;
import static com.dl.baye.util.Constant.*;
import com.dl.baye.BattleView;


public class StateManager {
	static BattleView bv;
	
	static StateNone stateNone;
	static StateFightMove stateFightMove ;
	static StateFightMenu stateFightMenu ;
	static StateFightAttack stateFightAttack ;
	static StateFightAtkSel stateFightAtkSel ;
	static StateFightAnime stateFightAnime ;
	static StateSysMenu stateSysMenu ;
	
	GameState gameState;
	
	public StateManager(BattleView bv) {
		StateManager.bv = bv;
		stateNone = new StateNone(bv);
		stateFightMove = new StateFightMove(bv);
		stateFightMenu = new StateFightMenu(bv);
		stateFightAttack = new StateFightAttack(bv);
		stateFightAtkSel = new StateFightAtkSel(bv);
		stateFightAnime = new StateFightAnime(bv);
		stateSysMenu = new StateSysMenu(bv);
		gameState = stateNone;
	}
	
	public boolean KeyDown(int keyCode){
		return gameState.KeyDown(keyCode);
	}
	
	public void Draw(Canvas canvas){
		gameState.Draw(canvas);
	}
	
	public void FightMove(){
		if(stateFightMove.isMoved){
			bv.gAction.moveTo(stateFightMove.oriCol, stateFightMove.oriRow);
		}
		stateFightMove.setMoveRange();	
		this.gameState = stateFightMove;	
		Log.d(TAG, "进入Move");
	}
	public void FightMenu(){
		this.gameState = stateFightMenu; 
		Log.d(TAG, "进入Menu");
	}
	public void FightAttack(){this.gameState = stateFightAttack;	Log.d(TAG, "进入Attack"); }
	public void FightAtkSel(){
		stateFightAtkSel.setAttackRange();
		this.gameState = stateFightAtkSel; 	
		Log.d(TAG, "进入AtkSel");
	}
	public void FightAnime(){this.gameState = stateFightAnime;	Log.d(TAG, "进入Anime"); }
	public void None(){this.gameState = stateNone;						Log.d(TAG, "进入None");}
	public void SysMenu(){this.gameState = stateSysMenu;			Log.d(TAG, "进入SysMenu");}
}
