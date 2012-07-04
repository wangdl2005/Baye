package com.dl.baye;

import android.util.Log;
import static com.dl.baye.util.Constant.*;
import static com.dl.baye.util.GameFormula.*;

import com.dl.baye.util.City;
import com.dl.baye.util.Order;
import com.dl.baye.util.Person;

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
				break;
			}
			if(IsLoss()){
				//失败
				break;
			}
			{
				//玩家操作
				PlayTactic();
			}
			{
				//电脑操作
				ComputerTactic();
			}
			{
				//执行命令队列
				OrdersExec();
			}
			{
				//更新环境、变量
				ConditionUpdate();
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
				gv.makeBattle(city,cityToDo, gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_CONSCRIPTION:
				gv.makeConscriotion(city, gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_DISTRIBUTE:
				//gv.makeDistribute(city,gv.gPersonSel);
				break;
			case GameView.STATUS_DEPREDATE:
				gv.makeDepredate(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_TRANSPORTATION:
				gv.makeTransportation(city,cityToDo,gv.gPersonSel);
				resetVar();
				break;
				
			case GameView.STATUS_ALIENATE:
				gv.makeAlienate(city,cityToDo,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_CANVASS:
				gv.makeCanvass(city,cityToDo,gv.gPersonSel,gv.gPersonSelToDo);
				resetVar();
				break;
			case GameView.STATUS_COUNTERESPIONAGE:
				gv.makeCounterespionage(city,cityToDo,gv.gPersonSel,gv.gPersonSelToDo);
				resetVar();
				break;
			case GameView.STATUS_REALIENATE:
				gv.makeRealienate(city,cityToDo,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_INDUCE:
				gv.makeInduce(city,cityToDo,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_TRIBUTE:
				gv.makeTribute(city,gv.gPersonSel);
				resetVar();
				break;
				
			case GameView.STATUS_SURRENDER:
				gv.makeSurrender(city, gv.gPersonSel, gv.gPersonSelToDo);
				resetVar();
				break;
			case GameView.STATUS_KILL:
				gv.makeKill(city, gv.gPersonSel, gv.gPersonSelToDo);
				resetVar();
				break;
			case GameView.STATUS_BANISH:
				gv.makeBanish(city, gv.gPersonSel, gv.gPersonSelToDo);
				resetVar();
				break;
			case GameView.STATUS_MOVE:
				gv.makeMove(city,cityToDo,gv.gPersonSel);
				resetVar();
				break;
				
			case GameView.STATUS_LARGESS:
				gv.makeLargess(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_CONFISCATE:
				gv.makeConfiscate(city,gv.gPersonSel);
				resetVar();
				break;
			case GameView.STATUS_TREAT:
				gv.makeTreat(city);
				resetVar();
				break;	
			}		
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
	
	private void ComputerTactic(){
		Log.d(TAG,"computer doing");
	}
	
	private void OrdersExec(){
		int n = gv.orderQueue.size();
		while(n>0){			
			n--;
			Order order = gv.orderQueue.poll();
			int timeCount = order.getTimeCount()-1;
			if(order!=null){
				City city = null;
				Person person = null;
				City cityTo = null;
				Person personTo = null;
				switch(order.getId()){
				case GameView.STATUS_ASSART:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						city.addFarming(getPersonAssart(person));
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"开垦");
					}
					break;
				case GameView.STATUS_ACCRACTBUSINESS:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						city.addCommerce(getPersonAccractBusiness(person));
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"招商");
					}
					break;
				case GameView.STATUS_SEARCH:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						//TODO
						city.addMoney(getPersonSearchMoney(person));
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"搜寻");
					}
					break;
				case GameView.STATUS_FATHER:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						city.addAvoidCalamity(getPersonFather(person));
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"治理");
					}
					break;
				case GameView.STATUS_INSPECTION:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						city.addPeopleDevotion(getPersonInspection(person));
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"巡视");
					}
					break;
				case GameView.STATUS_EXCHANGE:
					break;
					
				case GameView.STATUS_RECONNOITRE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_RECONNOITRE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//侦察
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"侦察" +cityTo.getName());
					}
					break;
				case GameView.STATUS_BATTLE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_BATTLE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//战争
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"与" +cityTo.getName() +"开战");
					}
					break;
				case GameView.STATUS_CONSCRIPTION:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						city.addMothballArmsNum(getPersonConscription(person));
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":征兵");
					}
					break;
				case GameView.STATUS_DISTRIBUTE:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){					
						Log.d(TAG,city.getName() + ":分配");
					}
					break;
				case GameView.STATUS_DEPREDATE:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){			
						int[] result = getPersonDesperate(person);
						city.addMoney(result[0]);
						city.addFood(result[1]);
						city.minPeopleDevotion(result[2]);
						//TODO 掠夺
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":掠夺");
					}
					break;
				case GameView.STATUS_TRANSPORTATION:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_TRANSPORTATION);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//输送
						gv.addPerson(cityTo, person);
						cityTo.addMoney(100);
						Log.d(TAG,city.getName() + ":" +"输送" +cityTo.getName() +"100");
					}
					break;
					
				case GameView.STATUS_ALIENATE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_ALIENATE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//离间，忠诚下降
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"离间" +cityTo.getName());
					}
					break;
				case GameView.STATUS_CANVASS:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					personTo = order.getPersonTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_CANVASS);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setPersonTo(personTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//招揽成功
						if(getPersonCanvass(person,personTo)){						
							gv.delPerson(cityTo, personTo);
							gv.addPerson(city, personTo);
						}
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"招揽" +cityTo.getName() +"de" + personTo.getName());
					}
					break;
				case GameView.STATUS_COUNTERESPIONAGE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					personTo = order.getPersonTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_COUNTERESPIONAGE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setPersonTo(personTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//策反成功
						if(getPersonCounterespionage(person,personTo)){						
							//
						}
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"策反" +cityTo.getName() +"de" + personTo.getName());
					}
					break;
				case GameView.STATUS_REALIENATE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_REALIENATE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//反间
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"反间" +cityTo.getName());
					}
					break;
				case GameView.STATUS_INDUCE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_INDUCE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//劝降
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"劝降" +cityTo.getName());
					}
					break;
				case GameView.STATUS_TRIBUTE:
					city = order.getCity();
					person = order.getPerson();
					if(timeCount < 0){
						//朝贡
						city.addPeopleDevotion(10);
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"朝贡");
					}
					break;
					
				case GameView.STATUS_SURRENDER:
					city = order.getCity();
					person = order.getPerson();
					personTo = order.getPersonTo();
					if(timeCount < 0){
						//劝降
						if(getPersonSurrender(person,personTo))
						{
							gv.addPerson(city, personTo);
						}
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"劝降");
					}
					break;
				case GameView.STATUS_KILL:
					city = order.getCity();
					person = order.getPerson();
					personTo = order.getPersonTo();
					if(timeCount < 0){
						//斩
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"斩");
					}
					break;
				case GameView.STATUS_BANISH:
					city = order.getCity();
					person = order.getPerson();
					personTo = order.getPersonTo();
					if(timeCount < 0){
						//流放						
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"流放");
					}
					break;
				case GameView.STATUS_MOVE:
					city = order.getCity();
					person = order.getPerson();
					cityTo = order.getCityTo();
					if(timeCount >= 0){
						Order o = new Order();
						o.setId(GameView.STATUS_MOVE);
						o.setPerson(person);
						o.setCity(city);
						o.setCityTo(cityTo);
						o.setTimeCount(timeCount);
						gv.orderQueue.offer(o);
					}
					if(timeCount < 0){
						//移动
						gv.addPerson(cityTo, person);
						Log.d(TAG,city.getName() + ":" +"移动" +cityTo.getName() );
					}
					break;
					
				case GameView.STATUS_LARGESS:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						//忠诚提高
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"赏赐");
					}
					break;
				case GameView.STATUS_CONFISCATE:
					city = order.getCity();
					person = order.getPerson();
					
					if(timeCount < 0){
						//忠诚降低、宝物丢失
						gv.addPerson(city, person);
						Log.d(TAG,city.getName() + ":" +"没收");
					}
					break;
				case GameView.STATUS_TREAT:
					city = order.getCity();
					
					if(timeCount < 0){
						//忠诚提高
						Log.d(TAG,city.getName() + ":" +"宴请");
					}
					break;	
				}
			}
		}
	}
	
	private void ConditionUpdate(){
		gv.gMonthDate += 1;
		if(gv.gMonthDate > 12){
			gv.gYearDate +=1;
			gv.gMonthDate = 1;
			//物品信息更新--出现
			//人物信息更新--人物出现，年龄增长
		}
		//城市信息更新--包括城市人物更新
		//随机事件产生--饥荒。。
	}
}
