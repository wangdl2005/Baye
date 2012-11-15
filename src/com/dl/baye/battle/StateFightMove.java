package com.dl.baye.battle;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import static com.dl.baye.util.Constant.*;

import com.dl.baye.BattleView;

public class StateFightMove implements GameState {
	BattleView bv;
	public StateFightMove(BattleView bv){
		this.bv = bv;
	}
	public int oriCol;
	public int oriRow;
	public boolean isMoved = false;
	
	@Override
	public boolean KeyDown(int keyCode) {
		switch(keyCode){
		case KeyEvent.KEYCODE_W:
			//+1 是因为地图有一部分在屏幕外
			bv.curRow = (bv.curRow-1 <= bv.startRow) ?(bv.startRow+1) : (bv.curRow-1);

			break;
		case KeyEvent.KEYCODE_A:
			bv.curCol = (bv.curCol-1 <= bv.startCol) ?(bv.startCol+1) : (bv.curCol-1);

			break;
		case KeyEvent.KEYCODE_S:
			bv.curRow = (bv.curRow+1 > bv.startRow + SCREEN_ROWS) ? (bv.startRow + SCREEN_ROWS ): (bv.curRow + 1);

			break;
		case KeyEvent.KEYCODE_D:
			bv.curCol = (bv.curCol+1 > bv.startCol + SCREEN_COLS) ?(bv.startCol + SCREEN_COLS ): (bv.curCol+1);

			break;
		case KeyEvent.KEYCODE_ENTER:
			if(bv.moveList[bv.curRow][bv.curCol] > 0){
				this.oriCol = bv.gAction.getCol();
				this.oriRow = bv.gAction.getRow();
				isMoved = true;
				bv.gAction.moveTo(bv.curCol, bv.curRow);
				BattleView.stateManager.FightMenu();
				bv.menu.open(2);
			}
			else if(bv.moveList[bv.curRow][bv.curCol] == 0){
				this.oriCol = bv.gAction.getCol();
				this.oriRow = bv.gAction.getRow();
				isMoved = true;
				BattleView.stateManager.FightMenu();
				bv.menu.open(2);
			}
			break;
		case KeyEvent.KEYCODE_DEL:
			//返回上一个状态
			BattleView.stateManager.None();
			break;
		}
		if((bv.gSel = bv.getGeneral(bv.curCol, bv.curRow))  !=  null){
			
		}
		return true;
	}

	@Override
	public void Draw(Canvas canvas) {
		//显示移动范围
		for(int i=0;i<MAP_ROWS;++i)
		{
			for(int j=0;j<MAP_COLS;++j)
			{
				if (bv.moveList[i][j] > -1) {
					canvas.drawBitmap(BattleView.iconBmp[2], j * TILE, i * TILE,null);
				} 
			}
		}
		//显示光标
		canvas.drawBitmap(BattleView.iconBmp[0], bv.curCol * TILE ,bv.curRow * TILE, null);
	}
	
	
	public void setMoveRange(){
		if(bv.gAction != null){
			int area = bv.gAction.getMove();
			int col = bv.gAction.getCol();
			int row = bv.gAction.getRow();
			for(int i=0;i<MAP_ROWS;++i)
				for(int j=0;j<MAP_COLS;++j)
				{
					bv.moveList[i][j] = -1;
				}
			bv.moveList[row][col] = 0;
			for(int count = 0; count<=area;++count){
				for(int j=redressRow(row-area);j<=redressRow(row+area);++j)
					for(int i=redressCol(col-Math.abs(j-(row-area)));
							i<=redressCol(col+Math.abs(j-(row-area)));
							++i
							)
					{
						// 如果能够移动指定步数
						if ((getMoveCount(i - 1, j) == count)
								|| (getMoveCount(i, j - 1) == count)
								|| (getMoveCount(i + 1, j) == count)
								|| (getMoveCount(i, j + 1) == count)) {
							setMoveCount(i, j, count);
						}
					}
			}
		}
	}
	/**
	 * 矫正x坐标
	 * 
	 * @param col
	 * @return
	 */
	public int redressCol(int col) {
		if (col < 0)
			col = 0;
		if (col > MAP_COLS - 1)
			col = MAP_COLS - 1;
		return col;
	}

	/**
	 * 矫正row坐标
	 * 
	 * @param row
	 * @return
	 */
	public int redressRow(int row) {
		if (row < 0)
			row = 0;
		if (row > MAP_ROWS - 1)
			row = MAP_ROWS - 1;
		return row;
	}
	
	public int getMoveCount(int col,int row){
		if ((row < 0) || (row >MAP_ROWS  - 1) || (col < 0) || (col > MAP_COLS - 1)) {
			// 无法移动返回-1
			return -1;
		}
		return bv.moveList[row][col];
	}
	
	public void setMoveCount(int col,int row,int count){
		// 当为我军时
		if (bv.gAction.getTeam() == 0) {
			if (bv.getGeneral(1, col, row) != null) {
				return;
			}
		} else {
			if (bv.getGeneral(0,col, row) != null) {
				return;
			}
		}
		int cost = bv.map.getMapCost(col,row);
		// 指定位置无法进入
		if (cost < 0) {
			return;
		}
		count = count + cost;
		// 移动步数超过移动能力
		if (count > bv.gAction.getMove()) {
			return;
		}
		// 获得移动所需步数
		if ((bv.moveList[row][col] == -1) || (count < bv.moveList[row][col])) {
			bv.moveList[row][col] = count;
		}
	}
}
