package com.dl.baye;

public class GameViewThread extends Thread {
	GameView gv;
	int sleepSpan = 300;
	int waitSpan = 1500;
	boolean flag = true;
	boolean isChanging = false;//用于触发动画
	public GameViewThread(GameView gv){
		this.gv = gv;
	}
	@Override
	public void run() {
		while(flag){
			//动画
			while(isChanging){				
			}
			//睡眠
			try{
				Thread.sleep(sleepSpan);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			//线程空转
			try{
				Thread.sleep(waitSpan);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
