package com.dl.baye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.dl.baye.util.City;
import com.dl.baye.util.CitySet;
import com.dl.baye.util.Goods;
import com.dl.baye.util.Order;
import com.dl.baye.util.Person;

import static com.dl.baye.util.Constant.*;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	//命令队列，用于在一个周期结束时，对所有命令执行
	Queue<Order> orderQueue = new LinkedList<Order>() ;
	//君主ID
	int gPlayerKingId;
	//当前日期
	int gYearDate;
	int gMonthDate;
	//当前时期
	int gPhaseIndex;
	//当前选中武将
	Person gPersonSel;
	HashMap<Integer,CitySet> rCities = new HashMap<Integer,CitySet>(CITY_MAX);
	HashMap<Integer,City> gCities = new HashMap<Integer,City>(CITY_MAX);
	//武将/道具
	HashMap<Integer,Person> gPersons = new HashMap<Integer,Person>(PERSON_MAX);
	HashMap<Integer,Goods> gGoods = new HashMap<Integer,Goods>(GOODS_MAX);
	//君主
	ArrayList<Integer> gKings = new ArrayList<Integer>(KING_MAX);
	//ArrayList<CitySet> gCitySets = new ArrayList<CitySet>(CITY_MAX);
	//当前显示城市位置
	CitySet gCitySet;
	
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
		//initial cities		
		initCityList();
		initPersons();
		initPeriodKings();
		initGoods();
		
		mapView = new MapView(this);
	}	
	//地图
	public void initMap(){
		
	}
	//图片资源
	public void initBitmap(Resources r){
		
	}
	
	//数码管显示数字？
	//TODO
	
	public void initPersons(){
		//董卓：00 01 01 56 24 64 00 00 00 00 0000 0200 33
		this.gPersons.put(0x00, new Person(
				0x00,0x01,0x01,0x56,0x24,0x64,0x00,0x00,0x00,0x00,0x0000,0x0200,0x33
				,"董卓",180,0,0
				));
	}
	
	public void initGoods(){
//		01 方天画戟 
//		02 七星刀 
//		03 青龙刀
//		04 杖八矛 
//		05 双股剑 
//		06 三尖刀 
//		07 双铁戟 
//		08 倚天剑 
//		09 青虹刀 
//		0a 望月枪 
//		0b 古淀刀 
//		0c 六韬
//		0d 司马法 
//		0e 孙子兵法
//		0f 范蠡兵法
//		10 墨子
//		11 吴子兵法 
//		12 鬼谷子
//		13 孙膑兵法
//		14 尉缭子
//		15 商君书
//		16 三略
//		17 赤兔 
//		18 的卢 
//		19 绝影 
//		1a 爪黄飞电 
//		1b 王追 
//		1c 惊帆 
//		1d 白鸽 
//		1e 快航 
//		1f 铁骑兵符 
//		20 太玄兵符 
//		21 水战兵符
		this.gGoods.put(0x01, new Goods(0x01,0x00,"方天画戟","吕布兵器"
				,10,0,0,0));
	}
	
	//获取君主列表
	public void initPeriodKings(){
		this.gKings.add(0x00);
	}
	//获得玩家君主
	public void getPlayerKing(int idx){
		
	}
	//获取城市列表
	public void initCityList(){
		this.gCities.put(1, new City(1, 0, 0, 5000, 1000, 5000, 1000, 80, 50
				, 10000, 3000, 1000, 1000, 1000, null, 1, null, 0, "西凉", null, null));
		this.rCities.put(1, new CitySet(110,75));
	}
	
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
