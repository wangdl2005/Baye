package com.dl.baye;

import static com.dl.baye.util.Constant.*;

import java.util.ArrayList;

import com.dl.baye.LoadingView.DrawThread;
import com.dl.baye.util.City;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;;

public class MapView{
	BayeActivity activity;
	static Bitmap bmpBtnBack;
	static Bitmap bmpMap;
	static Bitmap bmpBtnSelected;
	
	static final int SEAM_BUTTON_TEXT = 9;
	static final int mapStartX = 380;
	static final int mapStartY = 20;
	static final int cityPosWidth = 7;
	int mapX;
	int mapY; 
	int mapWidth = 400;
	int mapHeight = 400;

	//menu 0:无子菜单;1-6：内政、军事、外交、人才、君主、信息的子菜单
	static int menuPos = 0;
	static City selCity;
	
	//内政、军事、外交、人才、君主、信息
	Rect rInterior = new Rect();
	Rect rArmament = new  Rect();
	Rect rDiplomatism = new Rect();
	Rect rPersons = new Rect();
	Rect rKing = new Rect();
	Rect rInfo = new Rect();
	//子菜单1-6；
	Rect rChild_1 = new Rect();
	Rect rChild_2 = new Rect();
	Rect rChild_3 = new Rect();
	Rect rChild_4 = new Rect();
	Rect rChild_5 = new Rect();
	Rect rChild_6 = new Rect();
	Rect rChild_7 = new Rect();
	ArrayList<Rect> rCities = new ArrayList<Rect>();
	Rect rMap = new Rect();
	private DrawThread drawThread;
	
	public MapView(BayeActivity activity) {
		this.activity = activity;
        
		initBitmap(activity.getResources());
		initRect();
	}

	public boolean onTouchEvent( MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int) event.getX();
			int y = (int) event.getY();
			//处理地图
			for(Rect r : rCities){
				if(r.contains(x, y)){
					menuPos = 0;
					//TODO set city
					selCity = new City();
				}
			}
			
			//******处理Menu******
			//生成子菜单
			if(rInterior.contains(x, y)){
				menuPos = 1;
			}else if(rArmament.contains(x,y)){
				menuPos = 2;
			}else if(rDiplomatism.contains(x,y)){
				menuPos = 3;
			}else if(rPersons.contains(x,y)){
				menuPos = 4;
			}else if(rKing.contains(x,y)){
				menuPos = 5;
			}else if(rInfo.contains(x,y)){
				menuPos = 6;
			}
			//处理子菜单
			switch(menuPos){
			case 0:
				break;
			case 1:
			{
				if(rChild_1.contains(x, y)){
					//开垦
					Log.d(TAG, "开垦");
				}else if(rChild_2.contains(x, y)){
					Log.d(TAG, "招商");
				}else if(rChild_3.contains(x, y)){
					Log.d(TAG, "搜寻");
				}else if(rChild_4.contains(x, y)){
					Log.d(TAG, "治理");
				}else if(rChild_5.contains(x, y)){
					Log.d(TAG, "出巡");
				}else if(rChild_6.contains(x, y)){
					Log.d(TAG, "交易");
				}
			}
				break;
			case 2:
			{
				if(rChild_1.contains(x, y)){
					Log.d(TAG, "侦察");
				}else if(rChild_2.contains(x, y)){
					Log.d(TAG, "出兵");
				}else if(rChild_3.contains(x, y)){
					Log.d(TAG, "征兵");
				}else if(rChild_4.contains(x, y)){
					Log.d(TAG, "分配");
				}else if(rChild_5.contains(x, y)){
					Log.d(TAG, "掠夺");
				}else if(rChild_6.contains(x, y)){
					Log.d(TAG, "输送");
				}
			}
				break;
			case 3:
			{
				if(rChild_1.contains(x, y)){
					Log.d(TAG, "离间");
				}else if(rChild_2.contains(x, y)){
					Log.d(TAG, "招揽");
				}else if(rChild_3.contains(x, y)){
					Log.d(TAG, "策反");
				}else if(rChild_4.contains(x, y)){
					Log.d(TAG, "反间");
				}else if(rChild_5.contains(x, y)){
					Log.d(TAG, "劝降");
				}else if(rChild_6.contains(x, y)){
					Log.d(TAG, "朝贡");
				}
			}
				break;
			case 4:
			{
				if(rChild_1.contains(x, y)){
					Log.d(TAG, "招降");
				}else if(rChild_2.contains(x, y)){
					Log.d(TAG, "处斩");
				}else if(rChild_3.contains(x, y)){
					Log.d(TAG, "流放");
				}else if(rChild_4.contains(x, y)){
					Log.d(TAG, "移动");
				}
			}
				break;
			case 5:
			{
				if(rChild_1.contains(x, y)){
					Log.d(TAG, "赏赐");
				}else if(rChild_2.contains(x, y)){
					Log.d(TAG, "没收");
				}else if(rChild_3.contains(x, y)){
					Log.d(TAG, "宴请");
				}
			}
				break;
			case 6:
			{
				if(rChild_1.contains(x, y)){
					Log.d(TAG, "武将");
				}else if(rChild_2.contains(x, y)){
					Log.d(TAG, "城市");
				}else if(rChild_3.contains(x, y)){
					Log.d(TAG, "势力");
				}else if(rChild_4.contains(x, y)){
					Log.d(TAG, "宝物");
				}
			}
				break;
			}
			
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		drawMap(canvas);
		if(selCity != null){
			createMenu(canvas, menuPos);
		}
	}
	
	public void initBitmap(Resources r){
		bmpMap =  BitmapFactory.decodeResource(r, R.drawable.map192);
		bmpBtnBack = BitmapFactory.decodeResource(r, R.drawable.btn_back);
		bmpBtnSelected = BitmapFactory.decodeResource(r, R.drawable.btn_selected);		
	}
	
	public void initRect(){
		rInterior.set(20, 20, 80, 50);
		rArmament.set(20,50,80,80);
		rDiplomatism.set(20,80,80,110);
		rPersons.set(20,110,80,140);
		rKing.set(20,140,80,170);
		rInfo.set(20,170,80,200);
		
		rChild_1.set(80,20,140, 50);
		rChild_2.set(80,50,140,80);
		rChild_3.set(80,80,140,110);
		rChild_4.set(80,110,140,140);
		rChild_5.set(80,140,140,170);
		rChild_6.set(80,170,140,200);
		rChild_7.set(80,200,140,230);
		
		rMap = new Rect(mapStartX, mapStartY, mapStartX + mapWidth, mapStartY + mapHeight);
		
		mapX = 100;
		mapY = 100;
		rCities.add(new Rect(mapStartX + mapX -cityPosWidth,mapStartY + mapY -cityPosWidth,
				mapStartX + mapX + cityPosWidth,mapStartY + mapY +cityPosWidth));
	}
	
	public void drawMap(Canvas canvas){
		//draw map
		canvas.drawBitmap(bmpMap, null, rMap, null);
		//draw cities
		Paint paint = new Paint();						//创建画笔对象
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);						//设置抗锯齿
		for(Rect r : rCities){
			canvas.drawRect(r, paint);
		}
	}
	
	public void createMenu(Canvas canvas,int menuPos){
		
		//drawButtonBackground
		canvas.drawBitmap(bmpBtnBack, null, rInterior, null);
		canvas.drawBitmap(bmpBtnBack, null, rArmament, null);
		canvas.drawBitmap(bmpBtnBack, null, rDiplomatism, null);
		canvas.drawBitmap(bmpBtnBack, null, rPersons, null);
		canvas.drawBitmap(bmpBtnBack, null, rKing, null);
		canvas.drawBitmap(bmpBtnBack, null, rInfo, null);
		//drawText
		Paint paint = new Paint();						//创建画笔对象
		paint.setARGB(255, 42, 48, 103);				//设置画笔颜色
		paint.setAntiAlias(true);						//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//设置字体
		paint.setTextSize(20);							//设置字号
		
		canvas.drawText("内政", rInterior.left+SEAM_BUTTON_TEXT, rInterior.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("军事", rArmament.left+SEAM_BUTTON_TEXT, rArmament.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("外交", rDiplomatism.left+SEAM_BUTTON_TEXT, rDiplomatism.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("人才", rPersons.left+SEAM_BUTTON_TEXT, rPersons.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("君主", rKing.left+SEAM_BUTTON_TEXT, rKing.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("信息", rInfo.left+SEAM_BUTTON_TEXT, rInfo.bottom-SEAM_BUTTON_TEXT, paint);
		switch(menuPos){
			//没有子菜单
			case 0:
				break;
			case 1:
			{
				//内政
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rInterior, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_5, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_6, null);
				canvas.drawText("开垦", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("招商", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("搜寻", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("治理", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("出巡", rChild_5.left+SEAM_BUTTON_TEXT, rChild_5.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("交易", rChild_6.left+SEAM_BUTTON_TEXT, rChild_6.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			case 2:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rArmament, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_5, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_6, null);
				canvas.drawText("侦察", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("出兵", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("征兵", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("分配", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("掠夺", rChild_5.left+SEAM_BUTTON_TEXT, rChild_5.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("输送", rChild_6.left+SEAM_BUTTON_TEXT, rChild_6.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			//外交
			case 3:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rDiplomatism, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_5, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_6, null);
				canvas.drawText("离间", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("招揽", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("策反", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("反间", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("劝降", rChild_5.left+SEAM_BUTTON_TEXT, rChild_5.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("朝贡", rChild_6.left+SEAM_BUTTON_TEXT, rChild_6.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}

			//人才
			case 4:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rPersons, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawText("招降", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("处斩", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("流放", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("移动", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			//君主
			case 5:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rKing, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawText("赏赐", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("没收", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("宴请", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				//canvas.drawText("赏赐", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			//Info
			case 6:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rInfo, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawText("武将", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("城市", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("势力", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("宝物", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
		}
	}
}
