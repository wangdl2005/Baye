package com.dl.baye.battle;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;

import com.dl.baye.BattleView;
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
			//+1 是因为地图有一部分在屏幕外
			bv.curRow = (bv.curRow-1 <= bv.startRow) ?(bv.startRow+1) : (bv.curRow-1);
//			if(bv.curRow < bv.startRow + 3 && bv.startRow > 0){
//				--bv.startRow;
//			}
			break;
		case KeyEvent.KEYCODE_A:
			bv.curCol = (bv.curCol-1 <= bv.startCol) ?(bv.startCol+1) : (bv.curCol-1);
//			if(bv.curCol < bv.startCol + 5 && bv.startCol > 0){
//				--bv.startCol;
//			}
			break;
		case KeyEvent.KEYCODE_S:
			bv.curRow = (bv.curRow+1 > bv.startRow + SCREEN_ROWS) ? (bv.startRow + SCREEN_ROWS ): (bv.curRow + 1);
//			if(bv.curRow > bv.startRow + 11 && bv.startRow + SCREEN_ROWS < MAP_ROWS){
//				++bv.startRow;
//			}
			break;
		case KeyEvent.KEYCODE_D:
			bv.curCol = (bv.curCol+1 > bv.startCol + SCREEN_COLS) ?(bv.startCol + SCREEN_COLS ): (bv.curCol+1);
//			if(bv.curCol > bv.startCol + 15 && bv.startCol + SCREEN_COLS < MAP_COLS){
//				++bv.startCol;
//			}
			break;
		case KeyEvent.KEYCODE_ENTER:
			if((bv.gAction = bv.getGeneral(0, bv.curCol, bv.curRow))  !=  null && bv.gAction.getAction() != 1){
				BattleView.stateManager.FightMove();
			}
			break;
		}
		if((bv.gSel = bv.getGeneral(bv.curCol, bv.curRow))  !=  null){
			
		}
		return true;
	}

	@Override
	public void Draw(Canvas canvas) {
		//绘制光标
		canvas.drawBitmap(BattleView.iconBmp[0], bv.curCol * TILE ,bv.curRow * TILE, null);
	}

}
