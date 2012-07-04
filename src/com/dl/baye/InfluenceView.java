package com.dl.baye;

import static com.dl.baye.util.Constant.formatArmsType;

import java.util.ArrayList;

import com.dl.baye.util.Goods;
import com.dl.baye.util.Influence;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class InfluenceView {
	GameView gameView;

	Paint paint;
	static Bitmap menuTitle;//表格的标题背景
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	
	static final int threeBitmapX = 650;
	static final int threeBitmapY = 15;
	static final int smallBtnWidth = 25;
	static final int smallBtnHeight = 25;
	static final int personTitleX = 50;
	static final int personTitleY = 42;
	static final int attrWidth = 52;
	static final int attrStartX = 30;
	static final int attrStartY = 100;
	static final int upDownX = 390;
	static final int upBitmapY = 70;
	static final int downBitmapY = 440;
	static final int btnOKX = 660;
	static final int btnOKY = 430;
	static final int btnWidth = 60;
	static final int btnHeight = 25;
	//系数
	static final double coefficient = 1.3;
	
	static final int numOfSpan = 10;
	int currentI = 0;//当前绘制的屏幕最上边的元素下标
	int selectI = 0;
	
	ArrayList<Influence> influences;

	public InfluenceView(GameView gameView,ArrayList<Influence> influence) {
		this.gameView = gameView;
		this.influences = influence;
		paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		initBitmaps(gameView.getResources());
	}
	
	
	private void initBitmaps(Resources r){
		Bitmap menu_item = BitmapFactory.decodeResource(r, R.drawable.buttons); 
		threeBitmap = Bitmap.createBitmap(menu_item, 60, 0, 90, 30);
		buttonBackGround = Bitmap.createBitmap(menu_item, 0, 0, 60, 30);
		upBitmap = Bitmap.createBitmap(menu_item, 210, 15, 15, 15);
		downBitmap = Bitmap.createBitmap(menu_item, 210, 0, 15, 15);
		menu_item = null;//释放掉大图
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		//logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);//标题背景
	}
	
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, threeBitmapX, threeBitmapY, paint);
		//canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(23);//设置文字大小
		canvas.drawText("势力情报", personTitleX, personTitleY, paint);
		paint.setTextSize(18);//设置文字大小
		
		canvas.drawBitmap(menuTitle, 10, attrStartY-30, paint);
		int attrX = attrStartX;
		canvas.drawText("序号", attrX, attrStartY-10, paint);
		attrX += attrWidth * coefficient;
		canvas.drawText("君主", attrX, attrStartY-10, paint);
		attrX += attrWidth * coefficient;
		canvas.drawText("城市", attrX , attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("武将", attrX ,attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("道具", attrX, attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("农业", attrX, attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("商业", attrX, attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("人口", attrX, attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("金钱", attrX, attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("粮食", attrX, attrStartY-10, paint);
		attrX += attrWidth*coefficient;
		canvas.drawText("兵力", attrX, attrStartY-10, paint);
		int tempCurrentI = currentI;
		paint.setTextSize(16);//设置文字大小

		if(influences != null){
			if(influences.size() < numOfSpan){
				int n = influences.size() ;
				Influence influ= null;
				for(int i=0;i<n;++i){
					influ = influences.get(i);
					String belong = influ.getBelong();
					int totalFarming = influ.getFarming();
					int totalCommerce = influ.getCommerce();
					int totalPopulation = influ.getPopulation();
					int totalMoney = influ.getMoney();
					int totalFood = influ.getMoney();
					int totalArmsNum = influ.getMothballArmsNum();
					int totalPersonNum = influ.getPersonsNum();
					int totalToolsNum = influ.getToolsNum();
					int cityNum = influ.getCityNum();
					
					attrX = attrStartX;
					canvas.drawText(" "+i, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(belong, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+cityNum, attrX , attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalPersonNum, attrX ,attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalToolsNum, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalFarming, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+ totalCommerce, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+ totalPopulation, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalMoney, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalFood, attrX, attrStartY + 35 + 30*i, paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalArmsNum, attrX, attrStartY + 35 + 30*i, paint);
				}
			}
			else{//一个屏幕显示不下时
				int n = influences.size() ;
				Influence influ = null;
				for(int i=tempCurrentI;i<tempCurrentI + numOfSpan;++i){
					influ = influences.get(i);
					String belong = influ.getBelong();
					int totalFarming = influ.getFarming();
					int totalCommerce = influ.getCommerce();
					int totalPopulation = influ.getPopulation();
					int totalMoney = influ.getMoney();
					int totalFood = influ.getMoney();
					int totalArmsNum = influ.getMothballArmsNum();
					int totalPersonNum = influ.getPersonsNum();
					int totalToolsNum = influ.getToolsNum();
					int cityNum = influ.getCityNum();
					
					attrX = attrStartX;
					canvas.drawText(" "+i, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(belong, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+cityNum, attrX , attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalPersonNum, attrX ,attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalToolsNum, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalFarming, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+ totalCommerce, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+ totalPopulation, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalMoney, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalFood, attrX, attrStartY + 35 + 30*(i-currentI), paint);
					attrX += attrWidth * coefficient;
					canvas.drawText(" "+totalArmsNum, attrX, attrStartY + 35 + 30*(i-currentI), paint);
				}
			}
			//select back
			canvas.drawBitmap(selectBackground, 10, attrStartY +35 + 30*selectI - 22, paint);//绘制选择效果
			
			if(currentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, upDownX, upDownX, paint);
			}
			if(influences.size() >numOfSpan && (currentI+numOfSpan) < influences.size() ){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, upDownX, downBitmapY, paint);
			}
		}
		paint.setTextSize(18);
		//绘制确定Button
		canvas.drawBitmap(buttonBackGround, btnOKX, btnOKY, paint);
		canvas.drawText("确定", btnOKX + 10, btnOKY + 20, paint);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(influences==null || influences.size() == 0){
				return true;
			}
			//确定按钮
			if(new Rect(btnOKX,btnOKY,btnOKX+btnWidth,btnOKY+btnHeight).contains(x,y))
			{
				if(influences==null || influences.size() == 0){
					return true;
				}
				//gameView.gPersonSel = personsInCity.get(currentI + selectI);
				this.selectI = 0;
				this.currentI = 0;
				gameView.setStatus(GameView.STATUS_NORMAL);
			}
			//向下翻页按钮
			if(new Rect(threeBitmapX,threeBitmapY,threeBitmapX + smallBtnWidth
					,threeBitmapY +smallBtnHeight).contains(x,y))
			{
				if(influences==null || influences.size() == 0){
					return true;
				}
				selectI++;
				int n = influences.size();
				if(n < numOfSpan){//当一个屏幕可以全部显示时，即不需要滚屏 
					if(selectI > n-1){ 
						selectI = n-1;
					}
				}
				else {//当一屏显示不全，需要滚屏时
					if(selectI > numOfSpan-1){ 
						selectI = numOfSpan-1;
						currentI++;
						if((currentI+numOfSpan) > n){
							currentI--;
						}
					}						
				}		
			}
			//点击了向上翻页按钮
			if(new Rect(threeBitmapX+ smallBtnWidth,threeBitmapY,threeBitmapX +2* smallBtnWidth
					,threeBitmapY +smallBtnHeight).contains(x,y))
			{
				if(influences==null || influences.size() == 0){
					return true;
				}
				selectI--;
				if(selectI < 0){
					selectI = 0;
					currentI--;
					if(currentI < 0){
						currentI = 0;
					}
				}	
			}
			//点击关闭
			if(new Rect(threeBitmapX+ 2 *smallBtnWidth,threeBitmapY,threeBitmapX +3* smallBtnWidth
					,threeBitmapY +smallBtnHeight).contains(x,y))
			{
				this.selectI = 0;
				this.currentI = 0;
				gameView.setStatus(GameView.STATUS_NORMAL);
			}
		}
		return true;
	}
}
