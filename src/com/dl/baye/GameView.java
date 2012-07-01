package com.dl.baye;

import java.util.HashMap;

import static com.dl.baye.util.Constant.*;

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
	 * 0:正常；
	 */
	private int status = 0;
	BayeActivity activity;
	DrawThread drawThread;//刷帧的线程
	
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
		resources = this.getResources();
		if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 30;
        }
		
		getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
        this.gvt = new GameViewThread(this);//初始化后台数据修改线程        
        
        initMap();//初始化地图
        initClass();//初始化所有用到的类
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.drawThread.setFlag(true);
		drawThread.setIsViewOn(true);
        if(! drawThread.isAlive()){//如果后台重绘线程没起来,就启动它
        	try
        	{
        		drawThread.start();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}        	
        }
        
        this.setOnTouchListener(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.drawThread.setIsViewOn(false);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int) event.getX();
			int y = (int) event.getY();
			switch(this.status){
				case 0:
				{
					//
					mapView.onTouchEvent(event);
				}
				break;
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
				mapView.onDraw(canvas);
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
		mapView = new MapView(activity);
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

		private int sleepSpan = GAME_VIEW_SLEEP_SPAN;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, GameView gameView) {//构造器
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
        
        public void setIsViewOn(boolean isViewOn){
        	this.isViewOn = isViewOn;
        }
        
		public void run() {
			Canvas c;
			while(flag){
	            while (isViewOn) {
	                c = null;
	                try {
	                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) {
	                    	gameView.onDraw(c);
	                    }
	                } finally {
	                    if (c != null) {
	                    	//更新屏幕显示内容
	                        this.surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	                try{
	                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	                }
	                catch(Exception e){
	                	e.printStackTrace();
	                }
	            }
	            try{
	            	Thread.sleep(1500);//睡眠指定毫秒数
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
			}
		}
	}
}
