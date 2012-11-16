package com.dl.baye.battle;

import android.graphics.Canvas;
import android.view.KeyEvent;
import static com.dl.baye.util.Constant.*;

import com.dl.baye.BattleView;

public class StateFightAtkSel implements GameState {
	BattleView bv;
	public StateFightAtkSel(BattleView bv){
		this.bv = bv;
	}
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
			if(bv.attackList[bv.curRow][bv.curCol] == 1 && bv.gSel != null 
				&& bv.gSel.getTeam() != bv.gAction.getTeam()){
				//进入攻击状态
				BattleView.stateManager.FightAttack();
			}
			break;
		case KeyEvent.KEYCODE_DEL:
			//返回上一个状态
			BattleView.stateManager.FightMenu();
			bv.menu.open(2);
			break;
		}
		if((bv.gSel = bv.getGeneral(bv.curCol, bv.curRow))  !=  null){
			
		}
		return true;
	}

	@Override
	public void Draw(Canvas canvas) {
		//攻击范围
		for (int j = 0; j <= MAP_ROWS - 1; j++) {
			for (int i = 0; i <= MAP_COLS - 1; i++) {
				if(bv.attackList[j][i] == 1){
					canvas.drawBitmap(BattleView.iconBmp[3], i * TILE, j * TILE,null);
				}
			}
		}
		//绘制攻击标志
		if(bv.attackList[bv.curRow][bv.curCol] == 1 
				&& bv.gSel != null && bv.gAction !=null && bv.gSel.getTeam() != bv.gAction.getTeam()){
			canvas.drawBitmap(BattleView.iconBmp[4], bv.curCol * TILE, bv.curRow * TILE,null);
		}
		//光标
		canvas.drawBitmap(BattleView.iconBmp[0], bv.curCol * TILE ,bv.curRow * TILE, null);		
	}
	
	public void setAttackRange(){
		if(bv.gAction != null){
			int row = bv.gAction.getRow();
			int col = bv.gAction.getCol();
			int range = bv.gAction.getAttackRange();
			for(int i=0;i<MAP_ROWS;++i)
				for(int j=0;j<MAP_COLS;++j)
				{
					bv.attackList[i][j] = 0;
				}
			for(int i=row-range;i<row;++i)
			{
				if(i<0 || i>MAP_ROWS)
					continue;
				if(i-(row-range) + col < MAP_COLS&&i-(row-range) + col >0)
				{
					bv.attackList[i][i-(row-range) + col] = 1;
				}
				if(col-(i-(row-range)) >0 &&col-(i-(row-range)) <MAP_COLS)
				{
					bv.attackList[i][col-(i-(row-range))] = 1;
				}
			}
			for(int i=row;i<=row+range;++i)
			{
				if(i<0 || i>MAP_ROWS)
					continue;
				if(range-(i-row) + col < MAP_COLS&&range-(i-row) + col >0)
				{
					bv.attackList[i][range-(i-row) + col] = 1;
				}
				if(col-(range-(i-row) ) >0 &&col-(range-(i-row)) <MAP_COLS)
				{
					bv.attackList[i][col-(range-(i-row))] = 1;
				}
			}
		}
	}
}

