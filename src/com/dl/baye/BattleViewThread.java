package com.dl.baye;
class BattleViewThread extends Thread{
	BattleView battleView;
	boolean flag = false;//战斗开始标志
	int sleepSpan = 0;
	int waitSpan = 0;
	public BattleViewThread(BattleView bv) {
		this.battleView = bv;
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	@Override
	public void run() {
		while(flag){
			//战斗
			{
				switch(battleView.state){
					
					case BattleView.STATE_ENEMY:
						break;
				}
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