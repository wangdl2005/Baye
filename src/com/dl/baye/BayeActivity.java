package com.dl.baye;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BayeActivity extends Activity {
	LoadingView loadingView;//进度条界面的引用
	MenuView menuView;//开始游戏的主菜单界面   
	GameView gameView;//游戏主界面
	HelpView helpView;//帮助界面
//	SoundView soundView;//刚开始时选择是否开启声音的界面
	SoundManageView soundManageView;//声音设置界面
	ScreenRollView screenRoll;//竹简滚屏界面
	AboutView aboutView;//关于界面
	
	boolean isBackSound = true;//是否有背景声音
	boolean isStartSound = true;//开场声音
	boolean isBattleSound = true;//战斗声音
	boolean isEnvironmentSound = true;//环境音
	View currentView = null;
	//更新UI线程
	Handler myHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
	    		setContentView(R.layout.main);
				break;
			case 1:
				//返回到menu
				if(loadingView != null){
					loadingView = null;
				}
				toMenuView();
				break;
			case 2:
				//screenRollView
				if(loadingView != null){
					loadingView = null;
				}
				if(menuView != null){
					menuView = null;
				}
				screenRoll = new ScreenRollView(BayeActivity.this);
				BayeActivity.this.setContentView(screenRoll);
				break;
			case 3:
				//收到LoadingView的消息，切到GameView
				if(loadingView != null){
    				loadingView = null;//释放LoadingView
    			}
    			toGameView();//切换到游戏的主界面
    			break; 
    		case 4://收到MenuView界面的消息，切换到HelpView
    	    	helpView = new HelpView(BayeActivity.this,5);
    	    	BayeActivity.this.currentView = helpView;//设置当前的View为HelpView
    	    	BayeActivity.this.setContentView(helpView);
    			break;
    		case 5:
    			menuView = new MenuView(BayeActivity.this);
    			toMenuView();
    			break;
    		case 6:
    			toGameView();//切换到游戏的主界面
    			break;
    		case 7://开始时的声音选择界面传来的消息
    	    	toLoadingView(1);
    	    	new Thread(){//后台启动加载需要加载的View
    	    		public void run(){
    	    			Looper.prepare();
    	    			menuView = new MenuView(BayeActivity.this);//初始化MenuView
    	    		}
    	    	}.start();
    			break;
    		case 8:
    	    	helpView = new HelpView(BayeActivity.this,6);
    	    	BayeActivity.this.currentView = helpView;//设置当前的View为HelpView
    	    	BayeActivity.this.setContentView(helpView);
    			break;
    		case 9:
    			soundManageView = new SoundManageView(BayeActivity.this,5);
    	    	BayeActivity.this.currentView = soundManageView; 
    	    	BayeActivity.this.setContentView(soundManageView);
    			break;
    		case 10:
    			soundManageView = new SoundManageView(BayeActivity.this,6);
    	    	BayeActivity.this.currentView = soundManageView;
    	    	BayeActivity.this.setContentView(soundManageView);
    			break;
    		case 11:
    			//重新loading 初始化GameView
    			if(loadingView != null){
    				loadingView = null;
    			}
    			if(screenRoll != null){//释放
    				screenRoll = null;
    			}  
    			toLoadingView(3);
            	new Thread(){ 
            		public void run(){
            			Looper.prepare();
            			gameView = new GameView(BayeActivity.this);//初始化GameView
            		}
            	}.start();
            	break;
    		case 12://从AboutView传来的消息，需要到MenuView
    			menuView = new MenuView(BayeActivity.this);        		
    			BayeActivity.this.setContentView(menuView);
    			BayeActivity.this.currentView = menuView;
    			break;
    		case 13://从MenuView传来的消息，要到AboutView
    			if(aboutView == null){
    				aboutView = new AboutView(BayeActivity.this);
    			}
    			BayeActivity.this.setContentView(aboutView);
    			BayeActivity.this.currentView = aboutView;
    			break;
    		case 14://从GameView传来的消息，要到MenuView
    			menuView = new MenuView(BayeActivity.this);
    			BayeActivity.this.setContentView(menuView);
    			BayeActivity.this.currentView = menuView;
    			break;                	
    		case 99:
    			//读档
    			if(loadingView != null){
    				loadingView = null;
    			}
    			currentView = null;
    			if(GameRecordManager.check(BayeActivity.this)){
        			toLoadingView(101);
					new Thread(){
						public void run(){
							try{
								Thread.sleep(2000);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							Looper.prepare();
							if(gameView==null)
							{
								gameView = new GameView(BayeActivity.this);
							}
							GameRecordManager.loadGameStatus(gameView);
						}
					}.start();
    			}
    			break;
    		case 100:
    			if(gameView != null){
    				BayeActivity.this.setContentView(gameView);
    			}
    			break;
			}			
		};
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	//强制为横屏
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //全屏
  		requestWindowFeature(Window.FEATURE_NO_TITLE); 
  		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
  		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
  		//MyHandle type设置
  		//toLoadingView(11);
  		GameView gv = new GameView(this);
  		setContentView(gv);
  		currentView = gv;
  		//初始化资源
    }
    
    
    public void toLoadingView(int toViewID){
 		loadingView = new LoadingView(this,toViewID);//初始化进度条
 		this.currentView = loadingView;
     	this.setContentView(loadingView);//切换到进度条View
     }
  //切换到开始游戏的主菜单
    public void toMenuView(){
    	if(this.menuView != null){//当menuView不为空时切屏
    		this.currentView = menuView;
    		this.setContentView(menuView);
    	}
    	else{//当为空时打印并退出
    		System.exit(0);
    	}
    }
    
    //切换到游戏主界面
    public void toGameView(){ 
    	if(this.gameView != null){//当gameView不为空时
    		this.currentView = gameView;
    		this.setContentView(gameView);
    	}
    	else{//当为空时打印并退出
    		System.exit(0);
    	}
    }
    //ScollView
    public void toScreenRollView(){
    	if(this.screenRoll != null){//当screenRoll不为空时
    		this.currentView = screenRoll;
    		this.setContentView(screenRoll);
    	}
    	else{//当为空时打印并退出
    		System.exit(0);
    	}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(currentView != null){
			currentView.onKeyDown(keyCode, event);
		}
		
		//test
		if(loadingView != null){
			loadingView.process += 30;
		}
		return super.onKeyDown(keyCode, event);
	}    
}