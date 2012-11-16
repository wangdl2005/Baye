package com.dl.baye.battle;

import static com.dl.baye.util.Constant.BE_ACTION;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import static com.dl.baye.util.Constant.*;
import com.dl.baye.BattleView;

public class StateSysMenu implements GameState {
	BattleView bv;
	public StateSysMenu(BattleView bv){
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
			if(bv.menu.getCurMenuItem().equals("结束")){ 
				Log.d(TAG,"进入Enemy");
				//TODO
				BattleView.stateManager.None();
				bv.menu.close();
			}
			break;
		case KeyEvent.KEYCODE_DEL:
			//返回上一个状态
			BattleView.stateManager.None();
			bv.menu.close();
			break;
		}
		return true;
	}

	@Override
	public void Draw(Canvas canvas, int startCol, int startRow) {
		bv.menu.drawSelf(canvas, BattleView.listBmp, BattleView.iconBmp
				, bv.curCol - startCol, bv.curRow - startRow, 0, 0);

	}

}
