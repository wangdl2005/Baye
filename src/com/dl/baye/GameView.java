package com.dl.baye;

import java.util.HashMap;

import android.R.color;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.*;

public class GameView extends SurfaceView implements Callback,OnTouchListener{
	//状态机状态
	/*
	 * 
	 */
	private int status = 0;
	BayeActivity activity;
	
	static Bitmap dialogBack;
	static Bitmap dialogButton;
	
	static Bitmap[] smallGameMenuOptions = new Bitmap[6];
	boolean showMiniMap;
	//屏幕在大地图中的行列数
	int startRow = 0;
	int startCol = 0;
	//....
	//修改数据的后台线程
	GameViewThread gvt;
	//子View
	MapView mapView;
	CityView cityView;
	PersonChooseView personChooseView;
	PersonAtrrView personAttrView;
	//声音
	MediaPlayer mMediaPlayer;
	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap;
	//alert
	private GameAlert currentGameAlert;
	
	Paint paint;
	public static Resources resources;
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public GameView(BayeActivity bayeActivity) {
		super(bayeActivity);
		//TODO 初始化资源
		this.activity = bayeActivity;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int) event.getX();
			int y = (int) event.getY();
			switch(this.status){
			
			}
		}
		return  true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		switch(status){
			case 0:
			{
				//主界面绘制
				break;
			}
		}
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		//super.onDraw(canvas);
	}
	//类中变量读取
	public void setStatus(int status){
		this.status = status;
	}
	//返回当前的Alert
	public GameAlert getCurrentGameAlert(){
		return currentGameAlert;
	}
	public void setCurrentGameAlert(GameAlert gameAlert){
		this.currentGameAlert = gameAlert;
	}
	
	//initial
	//初始化子view
	public void initClass(){
		
	}	
	//地图
	public void initMap(){
		
	}
	//图片资源
	public void initBitmap(Resources r){
		
	}
	
	//数码管显示数字？
	//TODO
	
	
	
	//刷新帧线程
	class DrawThread extends Thread{
		
	}
}
