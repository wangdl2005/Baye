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
			stateFightMove.isMoved = false;
		}
		stateFightMove.setMoveRange();	
		this.gameState = stateFightMove;	
		Log.d(TAG, "进入Move");
	}
	public void FightMenu(){
		this.gameState = stateFightMenu; 
		Log.d(TAG, "进入Menu");
	}
	public void FightAttack(){
		if(bv.gAction!=null && bv.gSel != null){
			int seg = 0;
			switch(bv.gAction.getDirection()){
			case 0:
				seg = 3;break;
			case 1:
				seg = 4;break;
			}
			bv.gAction.startOnceAnimation(seg);
			bv.gSel.startOnceAnimation(2);
		}
		if(bv.gAction.getSpriteThread().isGameOn())
		{
			this.gameState = stateFightAttack;	
			Log.d(TAG, "进入Attack"); 
			//ATTACK 动画
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(true){
		if(this.gameState.equals(stateFightAttack) && bv.gAction.getSpriteThread().isGameOn() == false)
		{
			FightAnime();
			break;
		}
		}
	}
	public void FightAtkSel(){
		stateFightAtkSel.setAttackRange();
		this.gameState = stateFightAtkSel; 	
		Log.d(TAG, "进入AtkSel");
	}
	public void FightAnime(){
		this.gameState = stateFightAnime;
		Log.d(TAG, "进入Anime");
		//显示战场动画
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//设置待机
		bv.gAction.setAction(BE_ACTION);
		None();
	}
	public void None(){
		stateFightMove.isMoved = false;
		this.gameState = stateNone;		
		Log.d(TAG, "进入None");
	}
	public void SysMenu(){this.gameState = stateSysMenu;			Log.d(TAG, "进入SysMenu");}
}
