package com.dl.baye;

import static com.dl.baye.util.Constant.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View.*;

public abstract class GameAlert implements OnTouchListener{
	Bitmap bmpDialogBack;
	Bitmap bmpDialogButton;
	GameView gameView;
	public GameAlert(GameView gv,Bitmap bmpBack,Bitmap bmpButton){
		this.gameView = gv;
		this.bmpDialogBack = bmpBack;
		this.bmpDialogButton = bmpButton;
	}
	public abstract void drawDialog(Canvas canvas);
	//往对话框上写文字
	public void drawString(Canvas canvas,String string){
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
		paint.setTextSize(DIALOG_WORD_SIZE);//设置文字大小
		int lines = string.length()/DIALOG_WORD_EACH_LINE+(string.length()%DIALOG_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//如果是最后一行那个不太整的汉字
				str = string.substring(i*DIALOG_WORD_EACH_LINE);
			}else{
				str = string.substring(i*DIALOG_WORD_EACH_LINE, (i+1)*DIALOG_WORD_EACH_LINE);
			}
			canvas.drawText(str, DIALOG_WORD_START_X, DIALOG_WORD_START_Y+DIALOG_WORD_SIZE*i, paint);
		}
	}
}
