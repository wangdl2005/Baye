package com.dl.baye.battle;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;

import com.dl.baye.BattleView;
import com.dl.baye.util.General;

import static com.dl.baye.util.Constant.*;

public class StateNone implements GameState {
	BattleView bv;
	public StateNone(BattleView bv){
		this.bv = bv;
	}
	@Override
	public boolean KeyDown(int keyCode) {
		switch(keyCode){
		case KeyEvent.KEYCODE_W:
			bv.curRow = (bv.curRow-1 < 0) ? 0 : (bv.curRow-1);
			if(bv.curRow < bv.startRow && bv.startRow > 0){
				--bv.startRow;
			}
			break;
		case KeyEvent.KEYCODE_A:
			bv.curCol = (bv.curCol-1 < 0) ? 0 : (bv.curCol -1);
			if(bv.curCol < bv.startCol && bv.startCol > 0){
				--bv.startCol;
			}
			break;
		case KeyEvent.KEYCODE_S:
			bv.curRow = (bv.curRow + 1 > MAP_ROWS-1) ? (MAP_ROWS-1):(bv.curRow + 1);
			if(bv.curRow > bv.startRow + SCREEN_ROWS -1 && bv.startRow + SCREEN_ROWS -1 < MAP_ROWS-1){
				++bv.startRow;
			}
			break;
		case KeyEvent.KEYCODE_D:
			bv.curCol = (bv.curCol + 1 > MAP_COLS-1) ? (MAP_COLS-1):(bv.curCol + 1);
			if(bv.curCol > bv.startCol + SCREEN_COLS -1 && bv.startCol + SCREEN_COLS-1  < MAP_COLS-1){
				++bv.startCol;
			}
			break;
		case KeyEvent.KEYCODE_ENTER:
			General tmp = null;
			if((tmp = bv.getGeneral(bv.curCol, bv.curRow))!=null){
				if(tmp.getTeam() == 0 && tmp.getAction() != 1){
					bv.gAction = tmp;
					BattleView.stateManager.FightMove();
				}
			}
			else{
				BattleView.stateManager.SysMenu();
			}
			break;
		}
		if((bv.gSel = bv.getGeneral(bv.curCol, bv.curRow))  !=  null){
			
		}
		return true;
	}

	@Override
	public void Draw(Canvas canvas, int startCol, int startRow) {
		//绘制光标
		canvas.drawBitmap(BattleView.iconBmp[0], (bv.curCol-startCol) * TILE ,(bv.curRow-startRow) * TILE, null);
	}

}
