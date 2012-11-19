package com.dl.baye;

import java.util.Random;
import static com.dl.baye.util.Constant.*;
import android.view.KeyEvent;

import com.dl.baye.util.General;

class BattleViewThread extends Thread{
	BattleView battleView;
	boolean flag = false;//战斗开始标志
	int sleepSpan = 0;
	int waitSpan = 0;
	public BattleViewThread(BattleView bv) {
		this.battleView = bv;
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	//wait for show
	public void waiting(int time){
		try{
			Thread.sleep(time);
			}
			catch(Exception e){}
	}
	
	@Override
	public void run() {
		while(flag){
			//战斗
			{
				switch(BattleView.state){
					case BattleView.STATE_NORMAL:
						break;		
					case BattleView.STATE_ENEMY:
					{
						for(General g : battleView.enemyList)
						{
							g.setAction(0);
						}
						//AI
						for(General g : battleView.enemyList)
						{
							battleView.gAction = g;
							int row = g.getRow();
							int col = g.getCol();
							battleView.curCol = col;
							battleView.curRow = row;
							BattleView.stateManager.FightMove();
							//random move
							int moveRowCount = 1;//new Random().nextInt(g.getMove());
							int moveColCount  =  1;//new Random().nextInt(g.getMove());
							int i = (new Random().nextBoolean() ? 1 : -1) * moveRowCount + row;
							int j = (new Random().nextBoolean() ? 1 : -1) * moveColCount + col;
							{
								if(i < 0) i = 0;
								if(i > MAP_ROWS-1)  i =MAP_ROWS-1;
								if(j < 0) j = 0;
								if(j > MAP_COLS -1) j = MAP_COLS - 1;
								battleView.curCol = j;
								battleView.curRow = i;
								if(battleView.moveList[i][j] >= 0){
									//TODO
									waiting(2000);
									BattleView.stateManager.KeyDown(KeyEvent.KEYCODE_ENTER);
								}
							}
							//Menu
							//TODO
							waiting(2000);
							BattleView.stateManager.KeyDown(KeyEvent.KEYCODE_S);
							//TODO
							waiting(1000);
							BattleView.stateManager.KeyDown(KeyEvent.KEYCODE_ENTER);
							//TODO
							waiting(2000);
						}
						battleView.curCol = 10;
						battleView.curRow = 10;
						waiting(1000);
						BattleView.stateManager.SysMenu();
						waiting(1000);
						BattleView.stateManager.KeyDown(KeyEvent.KEYCODE_ENTER);
					}
						break;
				}
			}
			//睡眠
			try{
				Thread.sleep(sleepSpan);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			//线程空转
			try{
				Thread.sleep(waitSpan);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}