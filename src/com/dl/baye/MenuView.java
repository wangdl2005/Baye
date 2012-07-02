package com.dl.baye;

import static com.dl.baye.util.Constant.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MenuView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {
	BayeActivity activity;
	private DrawThread drawThread;//刷帧的线程
	MenuViewBackgroundThread gameThread;//背景滚动线程
	private int status = 0;//当前选中的状态
	
	Bitmap bigBitmap;//大图元
	Bitmap menuBackground;//背景的大图元
	Bitmap[] menuBackgrounds = new Bitmap[PICTURECOUNT];//装分割以后背景的图片
	Bitmap[] smallBitmaps = new Bitmap[12];//装分割以后的图片
	
	int backGroundIX = 0;//核心图的x坐标
	int i = 0;//核心图的索引
	MediaPlayer mMediaPlayer; 
	Paint paint;
	public MenuView(BayeActivity activity) {//构造器 
		super(activity);
		this.activity = activity;
		mMediaPlayer = MediaPlayer.create(activity, R.raw.startsound);
		mMediaPlayer.setLooping(true);
        if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 20;
        }
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);
        this.gameThread = new MenuViewBackgroundThread(this);//初始化背景滚动线程
        initBitmap();//初始化图片资源
    	if(activity.isStartSound){
    		mMediaPlayer.start();
    	}
    	setOnTouchListener(this);
        if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 30;
        }
	}
	
	public void initBitmap(){//初始化图片资源的方法
		paint = new Paint();
        if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 10;
        }
        bigBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.menu_options);//初始化菜单所用到的文字图元
		for(int i=0; i<smallBitmaps.length; i++){//切成小图片
			smallBitmaps[i] = Bitmap.createBitmap(bigBitmap, 100*(i%6), 25*(i>5?1:0), bigBitmap.getWidth()/6, bigBitmap.getHeight()/2);
		}
		bigBitmap = null;//释放掉大图
        if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 20;
        }
		menuBackground = BitmapFactory.decodeResource(getResources(), R.drawable.menu_back);//大背景图片
		for(int i=0; i<menuBackgrounds.length; i++){//切成小图片
			menuBackgrounds[i] = Bitmap.createBitmap(menuBackground, PICTUREWIDTH*i, 0, PICTUREWIDTH, PICTUREHEIGHT);
		}
		menuBackground = null;
        if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 20;
        }
	}
	
	//绘制方法
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		
		int backGroundIX=this.backGroundIX;
		int i=this.i;
		
		//解决i左侧的问题
		if(backGroundIX>0){
			int n=(backGroundIX/PICTUREWIDTH)+((backGroundIX%PICTUREWIDTH==0)?0:1);//计算i左面有几幅图
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(
						menuBackgrounds[(i-j+PICTURECOUNT)%PICTURECOUNT], 
			      backGroundIX-PICTUREWIDTH*j, 
			      0, 
			      paint
			     );
			}
		}

		//解决i自己
		canvas.drawBitmap(menuBackgrounds[i], backGroundIX, 0, paint);
		
		//解决i右侧的问题
		if(backGroundIX<SCREEN_WIDTH-PICTUREWIDTH){
			int k=SCREEN_WIDTH-(backGroundIX+PICTUREWIDTH);
			int n=(k/PICTUREWIDTH)+((k%PICTUREWIDTH==0)?0:1);//计算i右面有几幅图
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(
						menuBackgrounds[(i+j)%PICTURECOUNT], 
						backGroundIX+PICTUREWIDTH*j, 
						0, 
						paint
				);
			}
		}	
		for(int j=0; j<6; j++){
			if(status == j){
				canvas.drawBitmap(smallBitmaps[j+6], MENU_VIEW_LEFT_SPACE, MENU_VIEW_UP_SPACE+(smallBitmaps[j].getHeight()+MENU_VIEW_WORD_SPACE)*j, paint);
			}
			else{
				canvas.drawBitmap(smallBitmaps[j], MENU_VIEW_LEFT_SPACE, MENU_VIEW_UP_SPACE+(smallBitmaps[j].getHeight()+MENU_VIEW_WORD_SPACE)*j, paint);
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
        this.drawThread.setFlag(true);
        drawThread.setIsViewOn(true);
        if(!drawThread.isAlive()){
        	this.drawThread.start();
        }
        
        this.gameThread.setFlag(true);
        if(!gameThread.isAlive()){
        	this.gameThread.start();//启动背景滚动线程
        }
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {//摧毁时被调用
		drawThread.setIsViewOn(false);
	}
	
	class DrawThread extends Thread{//刷帧线程
		private int sleepSpan = MENU_VIEW_SLEEP_SPAN;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private MenuView menuView;
		private boolean flag = false;
		private boolean isViewOn = false;
        public DrawThread(SurfaceHolder surfaceHolder, MenuView menuView) {//构造器
        	super.setName("==MenuView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.menuView = menuView;
        }
        public void setIsViewOn(boolean isViewOn){
        	this.isViewOn = isViewOn;
        }
        public void setFlag(boolean flag) {
        	this.flag = flag;
        }
        
		public void run() {
			Canvas c;
            while (this.flag) {
            	while (isViewOn) {
                    c = null;
                    try {
                    	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                        c = this.surfaceHolder.lockCanvas(null);
                        synchronized (this.surfaceHolder) {
                        	menuView.onDraw(c);
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
            		Thread.sleep(1500);
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            }
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>MENU_VIEW_LEFT_SPACE && x<MENU_VIEW_LEFT_SPACE+smallBitmaps[0].getWidth()){//x坐标在需要的范围内
				if(y>MENU_VIEW_UP_SPACE && y<MENU_VIEW_UP_SPACE+smallBitmaps[0].getHeight()){
					//点击的是初入江湖菜单
					mMediaPlayer.stop();
		    		activity.myHandler.sendEmptyMessage(2);//向主activity发送Handler消息
					status = 0;
				}
				else if(y>MENU_VIEW_UP_SPACE+1*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE) 
						&& y<MENU_VIEW_UP_SPACE+1*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//点击风云再起
					mMediaPlayer.stop();
					activity.myHandler.sendEmptyMessage(99);
					status = 1;
				}
				else if(y>MENU_VIEW_UP_SPACE+2*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE) 
						&& y<MENU_VIEW_UP_SPACE+2*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//点击音效设置
					activity.myHandler.sendEmptyMessage(9);//向主activity发送Handler消息
					status = 2;
				}
				else if(y>MENU_VIEW_UP_SPACE+3*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE) 
						&& y<MENU_VIEW_UP_SPACE+3*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//点击江湖指引
					mMediaPlayer.stop();
					status = 3;
					activity.myHandler.sendEmptyMessage(4);//向主activity发送Handler消息
				}
				else if(y>MENU_VIEW_UP_SPACE+4*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE) 
						&& y<MENU_VIEW_UP_SPACE+4*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//点击群英荟萃
					mMediaPlayer.stop(); 
					status = 4;
					activity.myHandler.sendEmptyMessage(13);//向主activity发送Handler消息
				}
				else if(y>MENU_VIEW_UP_SPACE+5*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE) 
						&& y<MENU_VIEW_UP_SPACE+5*(smallBitmaps[0].getHeight()+MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//点击了封剑归隐
					mMediaPlayer.stop();
					status = 5;
					System.exit(0);//退出
				}
			}			
		}
		return true;
	}
}
