package com.dl.baye;

import com.dl.baye.util.City;

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
			//主线程
			if(IsWin()){
				//胜利;
			}
			if(IsLoss()){
				//失败
			}
			{
				//玩家操作
				PlayTactic();
			}
			{
				//电脑操作
			}
			{
				//执行命令队列
			}
			{
				//更新环境、变量
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
	private void PlayTactic() {
		gv.setStatus(GameView.STATUS_NORMAL);
		while(true){
			//玩家操作完成
			if(gv.getStatus() == GameView.STATUS_ENDTURN){
				break;
			}
			//玩家操作返回：			
			int cityId = 1;
			int cityToDoId = 1;
			if(gv.gCitySet != null ) {cityId = gv.gCitySet.getId();}
			if(gv.gCitySetToDo != null ) {cityToDoId = gv.gCitySetToDo.getId();}
			City city = gv.gCities.get(cityId);
			City cityToDo = gv.gCities.get(cityToDoId);
			switch(gv.getStatus()){
			case GameView.STATUS_ASSART:
				gv.makeAssart(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_ACCRACTBUSINESS:
				gv.makeAccractBusiness(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_SEARCH:
				gv.makeSearch(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_FATHER:
				gv.makeFather(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_INSPECTION:
				gv.makeInspection(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_EXCHANGE:
				gv.makeExchange(city,gv.gPersonSel);
				resetVar();
				break;
				
			case GameView.STATUS_RECONNOITRE:
				gv.makeReconnoitre(city,cityToDo, gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_BATTLE:
				gv.makeBattle(city, gv.gPersonSel);
				break;
			case GameView.STATUS_CONSCRIPTION:
				gv.makeConscriotion(city, gv.gPersonSel);
				break;
			case GameView.STATUS_DISTRIBUTE:
				gv.makeDistribute(city,gv.gPersonSel);
				break;
			case GameView.STATUS_DEPREDATE:
				gv.makeDepredate(city,gv.gPersonSel);
				break;
			case GameView.STATUS_TRANSPORTATION:
				gv.makeTransportation(city,gv.gPersonSel);
				break;
				
			case GameView.STATUS_ALIENATE:
				gv.makeAlienate(city,gv.gPersonSel);
				break;
			case GameView.STATUS_CANVASS:
				gv.makeCanvass(city,gv.gPersonSel);
				break;
			case GameView.STATUS_COUNTERESPIONAGE:
				gv.makeCounterespionage(city,gv.gPersonSel);
				break;
			case GameView.STATUS_REALIENATE:
				gv.makeRealienate(city,gv.gPersonSel);
				break;
			case GameView.STATUS_INDUCE:
				gv.makeInduce(city,gv.gPersonSel);
				break;
			case GameView.STATUS_TRIBUTE:
				gv.makeTribute(city,gv.gPersonSel);
				break;
				
			case GameView.STATUS_SURRENDER:
				gv.makeSurrender(city,cityToDo,gv.gPersonSel,gv.gPersonSelToDo);
				break;
			case GameView.STATUS_KILL:
				gv.makeKill(city,gv.gPersonSel);
				break;
			case GameView.STATUS_BANISH:
				gv.makeBanish(city,gv.gPersonSel);
				break;
			case GameView.STATUS_MOVE:
				gv.makeMove(city,gv.gPersonSel);
				break;
				
			case GameView.STATUS_LARGESS:
				gv.makeLargess(city,gv.gPersonSel);
				break;
			case GameView.STATUS_CONFISCATE:
				gv.makeConfiscate(city,gv.gPersonSel);
				break;
			case GameView.STATUS_TREAT:
				gv.makeTreat(city,gv.gPersonSel);
				break;	
			}		

			//睡眠
			try{
				Thread.sleep(200);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void resetVar(){
		gv.gPersonSel = null;
		gv.gCitySetToDo = null;
		gv.gPersonSelToDo = null;
	}
	
	private boolean IsWin(){
		return false;
	}
	
	private boolean IsLoss(){
		return false;
	}
}
