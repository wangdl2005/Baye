package com.dl.baye;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BayeActivity extends Activity {
	LoadingView loadingView;//进度条界面的引用
	MenuView menuView;//开始游戏的主菜单界面   
	GameView gameView;//游戏主界面
	HelpView helpView;//帮助界面
//	SoundView soundView;//刚开始时选择是否开启声音的界面
//	SoundManageView soundManageView;//声音设置界面
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
  		GameView gv = new GameView(this);
  		setContentView(gv);
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