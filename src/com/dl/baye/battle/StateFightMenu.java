package com.dl.baye.battle;

import static com.dl.baye.util.Constant.*;
import android.graphics.Canvas;
import android.view.KeyEvent;

import com.dl.baye.BattleView;

public class StateFightMenu implements GameState {
	BattleView bv;
	public StateFightMenu(BattleView bv){
		this.bv = bv;
	}
	@Override
	public boolean KeyDown(int keyCode) {
		switch(keyCode){
		case KeyEvent.KEYCODE_W:
			bv.menu.setCur(bv.menu.getCur() - 1);
			break;
		case KeyEvent.KEYCODE_A:

			break;
		case KeyEvent.KEYCODE_S:
			bv.menu.setCur(bv.menu.getCur() + 1);
			break;
		case KeyEvent.KEYCODE_D:

			break;
		case KeyEvent.KEYCODE_ENTER:
			if(bv.menu.getCurMenuItem().equals("攻击")){
				BattleView.stateManager.FightAtkSel();
				bv.menu.close();
			}
			else if(bv.menu.getCurMenuItem().equals("待机")){
				bv.gAction.setAction(BE_ACTION);
				BattleView.stateManager.None();
				bv.menu.close();
			}
			break;
		case KeyEvent.KEYCODE_DEL:
			//返回上一个状态
			BattleView.stateManager.FightMove();
			bv.menu.close();
			break;
		}
		return true;
	}

	@Override
	public void Draw(Canvas canvas) {
		// 绘制菜单
		bv.menu.drawSelf(canvas, BattleView.listBmp, BattleView.iconBmp
				, bv.curCol, bv.curRow, 0, 0);
	}

}
