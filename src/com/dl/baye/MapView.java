package com.dl.baye;

import static com.dl.baye.util.Constant.TAG;
import static com.dl.baye.util.Constant.mapStartX;
import static com.dl.baye.util.Constant.*;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.dl.baye.util.City;
import com.dl.baye.util.CitySet;
import com.dl.baye.util.Person;

public class MapView{
	GameView gameView;
	private int status = STATUS_NORMAL;
	private int orderId = 0;
	static Bitmap bmpBtnBack;
	static Bitmap bmpMap;
	static Bitmap bmpBtnSelected;
	static Bitmap dialogBack;
	
	static final int btnOKX = 660;
	static final int btnOKY = 430;
	static final int btnWidth = 60;
	static final int btnHeight = 25;
	static final int STATUS_NORMAL  = 0;
	static final int STATUS_PICK_CITY = 1;
	static final int SEAM_BUTTON_TEXT = 9;
	static final int cityPosWidth = 7;
	static final int infoStartX = 560;
	static final int infoStartY = 40;
	static final int infoHeight = 30;
	static final int mapWidth = 400;
	static final int mapHeight = 400;
	
	static final int messageSize = 11;
	static final int messageLine = 20;
	static final int messageStartX = 450;
	static final int messageStartY = 289;
	static final int messageWidth  = 330;
	static final int messageHeight = 121;

	//menu 0:无子菜单;1-6：内政、军事、外交、人才、君主、信息的子菜单
	static int menuPos = 0;
	
	//内政、军事、外交、人才、君主、信息
	Rect rInterior = new Rect();
	Rect rArmament = new  Rect();
	Rect rDiplomatism = new Rect();
	Rect rPersons = new Rect();
	Rect rKing = new Rect();
	Rect rInfo = new Rect();
	//子菜单1-7；
	Rect rChild_1 = new Rect();
	Rect rChild_2 = new Rect();
	Rect rChild_3 = new Rect();
	Rect rChild_4 = new Rect();
	Rect rChild_5 = new Rect();
	Rect rChild_6 = new Rect();
	Rect rChild_7 = new Rect();
	Rect rMap = new Rect();
	
	Rect rMessage = new Rect();
	
	public MapView(GameView gv) {
		this.gameView = gv;
        
		initBitmap(gv.getResources());
		initRect();
	}
	
	public void setStatus (int status){
		this.status = status;
	}

	public boolean onTouchEvent( MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(status == STATUS_NORMAL)
			{
				//处理地图
				CitySet cSet = null;
				for(int i :gameView.rCities.keySet()){
					cSet = gameView.rCities.get(i);
					if(getRect(cSet).contains(x, y)){
						menuPos = 0;
						cSet.setId(i);
						gameView.gCitySet = cSet;
					}
				}			
				//******处理Menu******
				//生成子菜单
				if(rInterior.contains(x, y)){
					menuPos = 1;
				}else if(rArmament.contains(x,y)){
					menuPos = 2;
				}else if(rDiplomatism.contains(x,y)){
					menuPos = 3;
				}else if(rPersons.contains(x,y)){
					menuPos = 4;
				}else if(rKing.contains(x,y)){
					menuPos = 5;
				}else if(rInfo.contains(x,y)){
					menuPos = 6;
				}
				//处理子菜单
				switch(menuPos){
				case 0:
					break;
				case 1:
				{
					City city = gameView.gCities.get(gameView.gCitySet.getId());
					ArrayList<Person> personsInCity = city.getPersonQueue();
					if(rChild_1.contains(x, y)){
						//开垦
						Log.d(TAG, "开垦");
						if(gameView.canAssart(city)== true){
							gameView.toPersonView(GameView.STATUS_ASSART,personsInCity);
						}
					}else if(rChild_2.contains(x, y)){
						Log.d(TAG, "招商");
						if(gameView.canAccractBusiness(city)){
							gameView.toPersonView(GameView.STATUS_ACCRACTBUSINESS,personsInCity);
						}
					}else if(rChild_3.contains(x, y)){
						Log.d(TAG, "搜寻");
						if(gameView.canSearch(city)){
							gameView.toPersonView(GameView.STATUS_SEARCH,personsInCity);
						}
					}else if(rChild_4.contains(x, y)){
						Log.d(TAG, "治理");
						if(gameView.canFather(city)){
							gameView.toPersonView(GameView.STATUS_FATHER,personsInCity);
						}
					}else if(rChild_5.contains(x, y)){
						Log.d(TAG, "出巡");
						if(gameView.canInspection(city)){
							gameView.toPersonView(GameView.STATUS_INSPECTION,personsInCity);
						}
					}else if(rChild_6.contains(x, y)){
						Log.d(TAG, "交易");
						if(gameView.canExchange(city)){
							gameView.toPersonView(GameView.STATUS_EXCHANGE,personsInCity);
						}
					}
				}
					break;
				case 2:
				{
					City city = gameView.gCities.get(gameView.gCitySet.getId());
					ArrayList<Person> personsInCity = city.getPersonQueue();
					if(rChild_1.contains(x, y)){
						Log.d(TAG, "侦察");
						if(gameView.canReconnoitre(city)){
							setStatus(STATUS_PICK_CITY);	
							orderId = GameView.STATUS_RECONNOITRE;
						}
					}else if(rChild_2.contains(x, y)){
						if(gameView.canBattle(city)){
							setStatus(STATUS_PICK_CITY);
							orderId = GameView.STATUS_BATTLE;	
						}
						Log.d(TAG, "出兵");
					}else if(rChild_3.contains(x, y)){
						if(gameView.canConscriotion(city))
						{
							gameView.toPersonView(GameView.STATUS_CONSCRIPTION,personsInCity);
						}
						Log.d(TAG, "征兵");
					}else if(rChild_4.contains(x, y)){
						//TODO
						Log.d(TAG, "分配");
					}else if(rChild_5.contains(x, y)){
						if(gameView.canDepredate(city))
						{
							gameView.toPersonView(GameView.STATUS_DEPREDATE,personsInCity);
						}
						Log.d(TAG, "掠夺");
					}else if(rChild_6.contains(x, y)){
						if(gameView.canTransportation(city)){
							setStatus(STATUS_PICK_CITY);
							orderId = GameView.STATUS_TRANSPORTATION;	
						}
						
						Log.d(TAG, "输送");
					}
				}
					break;
				case 3:
				{
					City city = gameView.gCities.get(gameView.gCitySet.getId());
					ArrayList<Person> personsInCity = city.getPersonQueue();
					if(rChild_1.contains(x, y)){
						if(gameView.canTransportation(city)){
						setStatus(STATUS_PICK_CITY);
						orderId = GameView.STATUS_ALIENATE;	
					}
						Log.d(TAG, "离间");
					}else if(rChild_2.contains(x, y)){
						if(gameView.canCanvass(city)){
							setStatus(STATUS_PICK_CITY);	
							orderId = GameView.STATUS_CANVASS;
						}
						Log.d(TAG, "招揽");
					}else if(rChild_3.contains(x, y)){
						if(gameView.canCounterespionage(city)){
							setStatus(STATUS_PICK_CITY);	
							orderId = GameView.STATUS_COUNTERESPIONAGE;
						}
						Log.d(TAG, "策反");
					}else if(rChild_4.contains(x, y)){
						if(gameView.canRealienate(city)){
							setStatus(STATUS_PICK_CITY);
							orderId = GameView.STATUS_REALIENATE;	
						}
						Log.d(TAG, "反间");
					}else if(rChild_5.contains(x, y)){
						if(gameView.canInduce(city)){
							setStatus(STATUS_PICK_CITY);
							orderId = GameView.STATUS_INDUCE;	
						}
						Log.d(TAG, "劝降");
					}else if(rChild_6.contains(x, y)){
						if(gameView.canTribute(city))
						{
							gameView.toPersonView(GameView.STATUS_TRIBUTE,personsInCity);
						}
						Log.d(TAG, "朝贡");
					}
				}
					break;
				case 4:
				{
					City city = gameView.gCities.get(gameView.gCitySet.getId());
					ArrayList<Person> personsInEnemyCity = city.getFuLu();
					if(rChild_1.contains(x, y)){
						Log.d(TAG, "招降");
						if(gameView.canSurrender(city)){
							gameView.toPersonView(GameView.STATUS_SURRENDER,personsInEnemyCity);
						}
					}else if(rChild_2.contains(x, y)){
						if(gameView.canKill(city)){
							gameView.toPersonView(GameView.STATUS_KILL,personsInEnemyCity);
						}
						Log.d(TAG, "处斩");
					}else if(rChild_3.contains(x, y)){
						if(gameView.canBanish(city)){
							gameView.toPersonView(GameView.STATUS_BANISH,personsInEnemyCity);
						}
						Log.d(TAG, "流放");
					}else if(rChild_4.contains(x, y)){
						if(gameView.canMove(city)){
							setStatus(STATUS_PICK_CITY);
							orderId = GameView.STATUS_MOVE;	
						}
						Log.d(TAG, "移动");
					}
				}
					break;
				case 5:
				{
					City city = gameView.gCities.get(gameView.gCitySet.getId());
					ArrayList<Person> personsInCity = city.getPersonQueue();
					if(rChild_1.contains(x, y)){
						if(gameView.canLargess(city)){
							gameView.toPersonView(GameView.STATUS_LARGESS,personsInCity);
						}
						Log.d(TAG, "赏赐");
					}else if(rChild_2.contains(x, y)){
						if(gameView.canConfiscate(city)){
							gameView.toPersonView(GameView.STATUS_CONFISCATE,personsInCity);
						}
						Log.d(TAG, "没收");
					}else if(rChild_3.contains(x, y)){
						if(gameView.canTreat(city)){
							gameView.setStatus(GameView.STATUS_TREAT);
						}
						Log.d(TAG, "宴请");
					}
				}
					break;
				case 6:
				{
					if(rChild_1.contains(x, y)){
						gameView.toPersonView(GameView.STATUS_NORMAL, gameView.getGPersons());
						Log.d(TAG, "武将");
					}else if(rChild_2.contains(x, y)){
						gameView.toCityView(gameView.getGCities());
						Log.d(TAG, "城市");
					}else if(rChild_3.contains(x, y)){
						gameView.toInfluenceView(gameView.getGInfluence());
						Log.d(TAG, "势力");
					}else if(rChild_4.contains(x, y)){
						gameView.toGoodsView(gameView.getGGoods());
						Log.d(TAG, "宝物");
					}
				}
					break;
				}
				
				if(new Rect(btnOKX,btnOKY,btnOKX+btnWidth * 2,btnOKY+btnHeight).contains(x,y))
				{
					gameView.setStatus(GameView.STATUS_ENDTURN);
				}
				
			}

			if(status == STATUS_PICK_CITY){
				CitySet cSet = null;
				for(int i :gameView.rCities.keySet()){
					cSet = gameView.rCities.get(i);	
					if(getRect(cSet).contains(x, y)){
						if(cSet.compareTo(gameView.gCitySet)){
							gameView.alert("请不要选择自己的城市",GameView.STATUS_NORMAL);
							return true;
						}
						cSet.setId(i);
						gameView.gCitySetToDo = cSet;
					}
				}	
				//确定按钮
				if(new Rect(btnOKX,btnOKY,btnOKX+btnWidth,btnOKY+btnHeight).contains(x,y))
				{
					City city = gameView.gCities.get(gameView.gCitySet.getId());
					ArrayList<Person> personsInCity = city.getPersonQueue();

					City enemycity = gameView.gCities.get(gameView.gCitySetToDo.getId());
					ArrayList<Person> personsInEnemyCity = enemycity.getPersonQueue();
					switch(orderId){
					case GameView.STATUS_RECONNOITRE:
						gameView.toPersonView(GameView.STATUS_RECONNOITRE, personsInCity);
						break;
					case GameView.STATUS_BATTLE:
						gameView.toPersonView(GameView.STATUS_BATTLE, personsInCity);
						break;
					case GameView.STATUS_TRANSPORTATION:
						gameView.toPersonView(GameView.STATUS_TRANSPORTATION, personsInCity);
						break;
						
					case GameView.STATUS_ALIENATE:
						gameView.toPersonView(GameView.STATUS_ALIENATE, personsInCity);
						break;
					case GameView.STATUS_CANVASS:
						gameView.toEnemyView(GameView.STATUS_CANVASS,personsInEnemyCity);
						break;
					case GameView.STATUS_COUNTERESPIONAGE:
						gameView.toEnemyView(GameView.STATUS_COUNTERESPIONAGE,personsInEnemyCity);
						break;
					case GameView.STATUS_REALIENATE:
						gameView.toPersonView(GameView.STATUS_REALIENATE, personsInCity);
						break;
					case  GameView.STATUS_INDUCE:
						gameView.toPersonView(GameView.STATUS_INDUCE, personsInCity);
						break;
						
					case GameView.STATUS_MOVE:
						gameView.toPersonView(GameView.STATUS_MOVE, personsInCity);
						break;
					}
					//TODO
					setStatus(STATUS_NORMAL);
				}
			}
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		
		canvas.drawColor(Color.rgb(179, 155, 106));
		drawMap(canvas);
		if(status == STATUS_NORMAL){
			//绘制Message
			canvas.drawBitmap(dialogBack,null,rMessage,null);
			Paint txtPaint = new Paint();
			txtPaint.setColor(Color.BLACK);
			txtPaint.setAntiAlias(true);//抗锯齿
			txtPaint.setTextSize(messageSize);
			
			//绘制结束回合Button
			canvas.drawBitmap(bmpBtnBack,null,new Rect(btnOKX , btnOKY, btnOKX + btnWidth * 2,btnOKY + btnHeight),null);
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);//设置字体颜色
			paint.setAntiAlias(true);//抗锯齿
			paint.setTextSize(18);
			canvas.drawText("结束回合", btnOKX + 30, btnOKY + 20, paint);
			
			if(gameView.gCitySet != null){
				//draw CityInfo
				drawCityInfo(canvas,gameView.gCitySet);
				
				createMenu(canvas, menuPos);		
				
			}
		}
		if(status == STATUS_PICK_CITY )
		{
			if(gameView.gCitySetToDo != null){
				//draw CityInfo
				drawCityInfo(canvas,gameView.gCitySetToDo);
			}

			//绘制确定Button
			canvas.drawBitmap(bmpBtnBack, btnOKX, btnOKY, null);
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);//设置字体颜色
			paint.setAntiAlias(true);//抗锯齿
			paint.setTextSize(18);
			canvas.drawText("确定", btnOKX + 10, btnOKY + 20, paint);
		}
	}
	
	public void initBitmap(Resources r){
		bmpMap =  BitmapFactory.decodeResource(r, R.drawable.map192);
		bmpBtnBack = BitmapFactory.decodeResource(r, R.drawable.btn_back);
		bmpBtnSelected = BitmapFactory.decodeResource(r, R.drawable.btn_selected);		
		dialogBack = BitmapFactory.decodeResource(r, R.drawable.dialog_back);		
	}
	
	public void initRect(){
		rInterior.set(420, 20, 480, 50);
		rArmament.set(420,50,480,80);
		rDiplomatism.set(420,80,480,110);
		rPersons.set(420,110,480,140);
		rKing.set(420,140,480,170);
		rInfo.set(420,170,480,200);
		
		rChild_1.set(490,20,550, 50);
		rChild_2.set(490,50,550,80);
		rChild_3.set(490,80,550,110);
		rChild_4.set(490,110,550,140);
		rChild_5.set(490,140,550,170);
		rChild_6.set(490,170,550,200);
		rChild_7.set(490,200,550,230);
		
		rMap = new Rect(mapStartX, mapStartY, mapStartX + mapWidth, mapStartY + mapHeight);
		
		rMessage = new Rect(messageStartX,messageStartY
				,messageStartX + messageWidth,messageStartY+messageHeight);
	}
	
	public void drawMap(Canvas canvas){
		//draw map
		canvas.drawBitmap(dialogBack,null,new Rect( rMap.left-2,rMap.top-2,rMap.right+2,rMap.bottom+2), null);
		canvas.drawBitmap(bmpMap, null, rMap, null);
		//draw cities
		Paint paint = new Paint();						//创建画笔对象
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);						//设置抗锯齿
		CitySet cSet = null;
		for(int i :gameView.rCities.keySet()){
			cSet = gameView.rCities.get(i);
			canvas.drawRect(getRect(cSet),paint);
		}
	}
	
	public Rect getRect(CitySet cs){
		int selX = cs.getSelX();
		int selY = cs.getSelY();
//		return new Rect(mapStartX + mapX -cityPosWidth,mapStartY + mapY -cityPosWidth,
//				mapStartX + mapX + cityPosWidth,mapStartY + mapY +cityPosWidth);
		return new Rect(mapStartX +selX -cityPosWidth,mapStartY + selY -cityPosWidth,mapStartX +selX +cityPosWidth,mapStartY + selY +cityPosWidth);
	}
	
	public void drawCityInfo(Canvas canvas,CitySet cSet){
		//
		Paint paint = new Paint();						//创建画笔对象
		paint.setARGB(255, 42, 48, 103);				//设置画笔颜色
		paint.setAntiAlias(true);						//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//设置字体
		paint.setTextSize(20);							//设置字号
		
		City city = gameView.gCities.get(cSet.getId());
		int id = city.getId();
		String name = city.getName();
		String king = gameView.gPersons.get(city.getBelong()).getName();
		String Satrap = gameView.gPersons.get(city.getSatrapId()).getName();
		int farmingLimit = city.getFarmingLimit();
		int farming = city.getFarming();
		int commerce = city.getCommerce();
		int commerceLimit = city.getCommerceLimit();
		int peopleDevotion = city.getPeopleDevotion();
		int AvoidCalamity = city.getAvoidCalamity();
		int population = city.getPopulation();
		int populationLimit =city.getPopulationLimit();
		int mothBallArmsNum = city.getMothballArmsNum();
		int money = city.getMoney();
		int food = city.getFood();
		int personsNum = city.getPersonsNum();
		int toolsNum = city.getToolsNum();
		

		int infoX,infoY;
		infoX = infoStartX;
		infoY = infoStartY;
		canvas.drawText("编号:" + id + "    城市:" + name,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("君主:" + king + "    太守:" + Satrap,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("农业:" + farming + "/"  + farmingLimit,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("商业:" + commerce + "/" + commerceLimit,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("人口:" + population + "/"  + populationLimit,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("兵力:" + mothBallArmsNum,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText( "民心:" + peopleDevotion + "  防灾:" + AvoidCalamity,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("金钱:" + money + "    粮食:" + food,infoX,infoY,paint);
		infoY += infoHeight;
		canvas.drawText("人才:" + personsNum + "    道具:" + toolsNum,infoX,infoY,paint);
	}
	
	public void createMenu(Canvas canvas,int menuPos){
		
		//drawButtonBackground
		canvas.drawBitmap(bmpBtnBack, null, rInterior, null);
		canvas.drawBitmap(bmpBtnBack, null, rArmament, null);
		canvas.drawBitmap(bmpBtnBack, null, rDiplomatism, null);
		canvas.drawBitmap(bmpBtnBack, null, rPersons, null);
		canvas.drawBitmap(bmpBtnBack, null, rKing, null);
		canvas.drawBitmap(bmpBtnBack, null, rInfo, null);
		//drawText
		Paint paint = new Paint();						//创建画笔对象
		paint.setARGB(255, 42, 48, 103);				//设置画笔颜色
		paint.setAntiAlias(true);						//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//设置字体
		paint.setTextSize(20);							//设置字号
		
		canvas.drawText("内政", rInterior.left+SEAM_BUTTON_TEXT, rInterior.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("军事", rArmament.left+SEAM_BUTTON_TEXT, rArmament.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("外交", rDiplomatism.left+SEAM_BUTTON_TEXT, rDiplomatism.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("人才", rPersons.left+SEAM_BUTTON_TEXT, rPersons.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("君主", rKing.left+SEAM_BUTTON_TEXT, rKing.bottom-SEAM_BUTTON_TEXT, paint);
		canvas.drawText("信息", rInfo.left+SEAM_BUTTON_TEXT, rInfo.bottom-SEAM_BUTTON_TEXT, paint);
		switch(menuPos){
			//没有子菜单
			case 0:
				break;
			case 1:
			{
				//内政
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rInterior, null);
				canvas.drawLine(rInterior.right, rInterior.top, rInterior.right+10, rInterior.top, paint);
				canvas.drawLine(rInterior.right, rInterior.bottom, rInterior.right+10, rInterior.bottom, paint);
				canvas.drawLine(rChild_1.left,rChild_1.top,rChild_1.left,rChild_1.bottom,paint);
				
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_5, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_6, null);
				canvas.drawText("开垦", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("招商", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("搜寻", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("治理", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("出巡", rChild_5.left+SEAM_BUTTON_TEXT, rChild_5.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("交易", rChild_6.left+SEAM_BUTTON_TEXT, rChild_6.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			case 2:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rArmament, null);
				canvas.drawLine(rArmament.right, rArmament.top, rArmament.right+10, rArmament.top, paint);
				canvas.drawLine(rArmament.right, rArmament.bottom, rArmament.right+10, rArmament.bottom, paint);
				canvas.drawLine(rChild_1.left,rChild_1.top,rChild_2.left,rChild_2.bottom,paint);
				
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_5, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_6, null);
				canvas.drawText("侦察", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("出兵", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("征兵", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("分配", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("掠夺", rChild_5.left+SEAM_BUTTON_TEXT, rChild_5.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("输送", rChild_6.left+SEAM_BUTTON_TEXT, rChild_6.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			//外交
			case 3:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rDiplomatism, null);
				canvas.drawLine(rDiplomatism.right, rDiplomatism.top, rDiplomatism.right+10, rDiplomatism.top, paint);
				canvas.drawLine(rDiplomatism.right, rDiplomatism.bottom, rDiplomatism.right+10, rDiplomatism.bottom, paint);
				canvas.drawLine(rChild_1.left,rChild_1.top,rChild_3.left,rChild_3.bottom,paint);
				
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_5, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_6, null);
				canvas.drawText("离间", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("招揽", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("策反", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("反间", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("劝降", rChild_5.left+SEAM_BUTTON_TEXT, rChild_5.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("朝贡", rChild_6.left+SEAM_BUTTON_TEXT, rChild_6.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}

			//人才
			case 4:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rPersons, null);
				canvas.drawLine(rPersons.right, rPersons.top, rPersons.right+10, rPersons.top, paint);
				canvas.drawLine(rPersons.right, rPersons.bottom, rPersons.right+10, rPersons.bottom, paint);
				canvas.drawLine(rChild_1.left,rChild_1.top,rChild_4.left,rChild_4.bottom,paint);
				
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawText("招降", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("处斩", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("流放", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("移动", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			//君主
			case 5:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rKing, null);
				canvas.drawLine(rKing.right, rKing.top, rKing.right+10, rKing.top, paint);
				canvas.drawLine(rKing.right, rKing.bottom, rKing.right+10, rKing.bottom, paint);
				canvas.drawLine(rChild_1.left,rChild_1.top,rChild_5.left,rChild_5.bottom,paint);
				
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawText("赏赐", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("没收", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("宴请", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				//canvas.drawText("赏赐", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
			//Info
			case 6:
			{
				//选中
				canvas.drawBitmap(bmpBtnSelected, null,rInfo, null);
				canvas.drawLine(rInfo.right, rInfo.top, rInfo.right+10, rInfo.top, paint);
				canvas.drawLine(rInfo.right, rInfo.bottom, rInfo.right+10, rInfo.bottom, paint);
				canvas.drawLine(rChild_1.left,rChild_1.top,rChild_6.left,rChild_6.bottom,paint);
				
				canvas.drawBitmap(bmpBtnBack, null, rChild_1, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_2, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_3, null);
				canvas.drawBitmap(bmpBtnBack, null, rChild_4, null);
				canvas.drawText("武将", rChild_1.left+SEAM_BUTTON_TEXT, rChild_1.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("城市", rChild_2.left+SEAM_BUTTON_TEXT, rChild_2.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("势力", rChild_3.left+SEAM_BUTTON_TEXT, rChild_3.bottom-SEAM_BUTTON_TEXT, paint);
				canvas.drawText("宝物", rChild_4.left+SEAM_BUTTON_TEXT, rChild_4.bottom-SEAM_BUTTON_TEXT, paint);
				break;
			}
		}
	}


}
