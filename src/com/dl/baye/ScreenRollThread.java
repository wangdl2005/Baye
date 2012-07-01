package com.dl.baye;



public class ScreenRollThread extends Thread {
	boolean flag;//循环控制变量
	int sleepSpan = 100;
	ScreenRollView screenRoll;
	int characterControl;//控制文字显示速度变量
	int charNumber;//记录显示文字的个数，包括显示了后备滚屏掩盖的
	
	public ScreenRollThread(ScreenRollView screenRoll){
		super.setName("==ScreenRollThread");
		this.screenRoll = screenRoll;
		flag = true;
	}
	//线程执行方法
	public void run(){
		while(flag){//循环标志位为真
			switch(screenRoll.status){
			case 1://竹简划入
				screenRoll.scrollStartX -= 5;
				if(screenRoll.scrollStartX == 20){
					screenRoll.status = 2;//进入文字显示阶段//停住不走了
				}				
				break;
			case 2://文字显示
				characterControl ++;
				if(characterControl == 3){
					screenRoll.characterNumber ++;//每次显示的文字都多一个
					characterControl =0;
					charNumber ++;
					if(charNumber == screenRoll.msg.length()){//显示结束
						screenRoll.status = 3;//3是待命,一会要在这发Handler
					}
				}				
				break;
			case 3://渐隐状态
				screenRoll.alpha -=5;
				if(screenRoll.alpha == 0){
					screenRoll.status = 4;///切屏
					
					
					screenRoll.activity.myHandler.sendEmptyMessage(11);
					this.flag = false;
				}
				break;
			}
			try{
				Thread.sleep(sleepSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
