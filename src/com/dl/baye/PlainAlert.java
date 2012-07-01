package com.dl.baye;

import static com.dl.baye.util.Constant.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
//只有一个确定按钮的alert
public class PlainAlert extends GameAlert {
	String alertMessage;
	public PlainAlert(GameView gv,String alertMessage, Bitmap bmpBack, Bitmap bmpButton) {
		super(gv, bmpBack, bmpButton);
		this.alertMessage = alertMessage;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int)event.getX();			//获得点击处的X坐标
		int y = (int)event.getY();			//获得点击处的Y坐标
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
				gameView.setStatus(0);					//设置游戏状态为0(待命态)	
				gameView.setOnTouchListener(gameView);	//将监听器重新设置为GameView
				gameView.setCurrentGameAlert(null);		//置空当前游戏提示
			}			
		}
		return true;
	}

	@Override
	public void drawDialog(Canvas canvas) {
		//背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y,null);
		drawString(canvas, alertMessage);
		//画按钮确定按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();						//创建画笔对象
		paint.setARGB(255, 42, 48, 103);				//设置画笔颜色
		paint.setAntiAlias(true);						//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//设置字体
		paint.setTextSize(18);							//设置字号
		canvas.drawText("确定",							//绘制确定按钮
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);		
	}

}
