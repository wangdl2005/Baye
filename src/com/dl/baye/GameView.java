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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.*;

public class GameView extends SurfaceView implements Callback,OnTouchListener{
	//状态机状态
	/*
	 * 0:正常；1:personView;2:EnemyView;100:结束回合
	 * Alert:
	 * 
	 * Order:
	 * 901:开垦-1-0
	 * 902:招商-1-0
	 * 903:搜寻-1-0
	 * 904:治理-1-0
	 * 905:出巡-1-0
	 * 906:交易-1-0
	 * 
	 * 911:侦察-1-0
	 * 912:出兵-1-0
	 * 913:征兵-1-0
	 * 914:分配-1-0
	 * 915:掠夺-1-0
	 * 916:输送-1-0
	 * 
	 * 921:离间-1-0
	 * 922:招揽-1-0
	 * 923:策反-1-0
	 * 924:反间-1-0
	 * 925:劝降-1-0
	 * 926:朝贡-1-0
	 * 
	 * 931:招降-1-0
	 * 932:处斩-1-0
	 * 933:流放-1-0
	 * 934:移动-1-0
	 * 
	 * 941:赏赐-1-0
	 * 942:没收-1-0
	 * 943:宴请-1-0
	 */
	
	static final int STATUS_NORMAL = 0;
	static final int STATUS_PERSONVIEW = 1;
	static final int STATUS_ENEMYVIEW = 2;
	static final int STATUS_ENDTURN =100;
	//以下命令status 也可以作为命令ID
	static final int STATUS_ASSART = 901;
	static final int STATUS_ACCRACTBUSINESS = 902;
	static final int STATUS_SEARCH = 903;
	static final int STATUS_FATHER = 904;
	static final int STATUS_INSPECTION = 905;
	static final int STATUS_EXCHANGE = 906;
	
	static final int STATUS_RECONNOITRE = 911;
	static final int STATUS_BATTLE = 912;	
	static final int STATUS_CONSCRIPTION = 913;
	static final int STATUS_DISTRIBUTE = 914;
	static final int STATUS_DEPREDATE = 915;
	static final int STATUS_TRANSPORTATION = 916;
	
	static final int STATUS_ALIENATE = 921;
	static final int STATUS_CANVASS = 922;
	static final int STATUS_COUNTERESPIONAGE = 923;
	static final int STATUS_REALIENATE = 924;
	static final int STATUS_INDUCE = 925;
	static final int STATUS_TRIBUTE = 926;
	
	static final int STATUS_SURRENDER = 931;
	static final int STATUS_KILL = 932;
	static final int STATUS_BANISH= 933;
	static final int STATUS_MOVE = 934;
	
	static final int STATUS_LARGESS = 941;
	static final int STATUS_CONFISCATE = 942;
	static final int STATUS_TREAT = 943;
	
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
	//欲操作的城市
	CitySet gCitySetToDo;
	//当前选中武将
	Person gPersonSel;
	//欲操作的武将
	Person gPersonSelToDo;
	
	
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
	PersonView personView;
	EnemyView enemyView;
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
        gvt.start();
        
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
//			int x = (int) event.getX();
//			int y = (int) event.getY();
			switch(this.getStatus()){
				case 0:
				{
					//
					mapView.onTouchEvent(event);
					break;
				}
				case 1:
				{
					personView.onTouchEvent(event);
					break;
				}
				case STATUS_ENEMYVIEW:
				{
					enemyView.onTouchEvent(event);
					break;
				}
			}
		}
		return  true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(getStatus() > 900){
			mapView.onDraw(canvas);
		}
		else{
			switch(getStatus()){
				case STATUS_NORMAL:
				{
					//主界面绘制
					mapView.onDraw(canvas);
					break;
				}
				case STATUS_PERSONVIEW:
				{
					personView.onDraw(canvas);
					break;
				}
				case STATUS_ENEMYVIEW:
				{
					enemyView.onDraw(canvas);
					break;
				}
			}
		}
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
		initGoods();
		initPersons();
		initPeriodKings();
		initCityList();
		
		//
		
		mapView = new MapView(this);
		personView = new PersonView(this,null);
		enemyView = new EnemyView(this, null);
	}	
	//地图
	public void initMap(){
		
	}
	//图片资源
	public void initBitmap(Resources r){
		
	}
	
	
	public void initPersons(){
		//董卓：00 01 01 56 24 64 00 00 00 00 0000 0200 33
		this.gPersons.put(0x00, new Person(
				0x00,0x01,0x01,0x56,0x24,0x64,0x00,0x00,0x64,0x00,0x0000,0x0200,0x33
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
		this.gGoods.put(0x02, new Goods(0x02,0x00,"七星刀","王兵器"
				,5,0,0,0));
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
		ArrayList<Person> personsTemp = new ArrayList<Person>();
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		personsTemp.add(gPersons.get(0));
		ArrayList<Goods> goodsTmep = new ArrayList<Goods>();
		goodsTmep.add(gGoods.get(2));
		this.gCities.put(1, new City(1, 0, 0, 5000, 1000, 5000, 1000, 80, 50
				, 10000, 3000, 1000, 1000, 1000, personsTemp, 1, goodsTmep, 1, "西凉", null, null));
		this.rCities.put(1, new CitySet(110,75));
	}
	
	public String getGoods(int idx){
		if(gGoods.get(idx)!=null){
			return gGoods.get(idx).getName();
		}
		return "无";
	}	
	//
	public void toPersonView(int statusReturn,ArrayList<Person> persons){
		if(personView == null){
			personView = new PersonView(this,persons);
		}
		personView.setPersonsInCity(persons);
		personView.setStatusReturn(statusReturn);
		setStatus(STATUS_PERSONVIEW);
	}
	public void toEnemyView(int statusReturn,ArrayList<Person> persons){
		if(enemyView == null){
			enemyView = new EnemyView(this,persons);
		}
		enemyView.setPersonsInCity(persons);
		enemyView.setStatusReturn(statusReturn);
		setStatus(STATUS_ENEMYVIEW);
	}
	
	//开垦
	public boolean canAssart(City city){
		if(city.getFarming() == city.getFarmingLimit()){
			//达到上限
			return false;
		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
		return true;
	}
	
	public void makeAssart(City city,Person person){
		if(isPersonManual(person, STATUS_ASSART) == true){
			orderConsumeMoney(city, STATUS_ASSART);
			orderConsumeThew(person, STATUS_ASSART);
			
			Order order = new Order();
			order.setId(STATUS_ASSART);
			order.setPerson(person);
			order.setCity(city);
			order.setTimeCount(0);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_ASSART);
		setStatus(STATUS_NORMAL);
	}
	
	//招商
	public boolean canAccractBusiness(City city){
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
		if(city.getCommerce() == city.getCommerceLimit()){
			//达到上限
			return false;
		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		return true;
	}
	
	public void makeAccractBusiness(City city,Person person){
		if(isPersonManual(person, STATUS_ACCRACTBUSINESS) == true){
			orderConsumeMoney(city, STATUS_ACCRACTBUSINESS);
			orderConsumeThew(person, STATUS_ACCRACTBUSINESS);
			
			Order order = new Order();
			order.setId(STATUS_ACCRACTBUSINESS);
			order.setPerson(person);
			order.setCity(city);
			order.setTimeCount(0);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_ACCRACTBUSINESS);
		setStatus(STATUS_NORMAL);
	}
	//搜寻
	public boolean canSearch(City city){
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		return true;
	}
	
	public void makeSearch(City city,Person person){
		if(isPersonManual(person, STATUS_SEARCH) == true){
			orderConsumeMoney(city, STATUS_SEARCH);
			orderConsumeThew(person, STATUS_SEARCH);
			
			Order order = new Order();
			order.setId(STATUS_SEARCH);
			order.setPerson(person);
			order.setCity(city);
			order.setTimeCount(0);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_SEARCH);
		setStatus(STATUS_NORMAL);
	}
	//治理
	public boolean canFather(City city){
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
//		if(city.getAvoidCalamity() == 100){
//			//达到上限
//			return false;
//		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		return true;
	}
	
	public void makeFather(City city,Person person){
		if(isPersonManual(person, STATUS_FATHER) == true){
			orderConsumeMoney(city, STATUS_FATHER);
			orderConsumeThew(person, STATUS_FATHER);
			
			Order order = new Order();
			order.setId(STATUS_FATHER);
			order.setPerson(person);
			order.setCity(city);
			order.setTimeCount(0);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_FATHER);
		setStatus(STATUS_NORMAL);
	}
	//出巡
	public boolean canInspection(City city){
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
//		if(city.getAvoidCalamity() == 100){
//			//达到上限
//			return false;
//		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		return true;
	}
	
	public void makeInspection(City city,Person person){
		if(isPersonManual(person, STATUS_INSPECTION) == true){
			orderConsumeMoney(city, STATUS_INSPECTION);
			orderConsumeThew(person, STATUS_INSPECTION);
			
			Order order = new Order();
			order.setId(STATUS_INSPECTION);
			order.setPerson(person);
			order.setCity(city);
			order.setTimeCount(0);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_INSPECTION);
		setStatus(STATUS_NORMAL);
	}
	//交易
	public boolean canExchange(City city){
		return true;
	}
	
	public void makeExchange(City city,Person person){
		Log.d(TAG, ""+STATUS_EXCHANGE);
		setStatus(STATUS_NORMAL);
	}
	//侦察
	public boolean canReconnoitre(City city){
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		return true;
	}
	
	public void makeReconnoitre(City city,City cityTo,Person person){
		if(isPersonManual(person, STATUS_RECONNOITRE) == true){
			orderConsumeMoney(city, STATUS_RECONNOITRE);
			orderConsumeThew(person, STATUS_RECONNOITRE);
			
			Order order = new Order();
			order.setId(STATUS_RECONNOITRE);
			order.setPerson(person);
			order.setCity(city);
			order.setCityTo(cityTo);
			order.setTimeCount(1);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_RECONNOITRE);
		setStatus(STATUS_NORMAL);
	}
	//出兵
	public boolean canBattle(City city,Person person){
		return true;
	}
	
	public void makeBattle(City city,Person person){
		Log.d(TAG, ""+STATUS_BATTLE);
		setStatus(STATUS_NORMAL);
	}
	//征兵
	public boolean canConscriotion(City city,Person person){
		return true;
	}
	
	public void makeConscriotion(City city,Person person){
		Log.d(TAG, ""+STATUS_CONSCRIPTION);
		setStatus(STATUS_NORMAL);
	}
	//分配
	public boolean canDistribute(City city,Person person){
		return true;
	}
	
	public void makeDistribute(City city,Person person){
		Log.d(TAG, ""+STATUS_DISTRIBUTE);
		setStatus(STATUS_NORMAL);
	}
	//掠夺
	public boolean canDepredate(City city,Person person){
		return true;
	}
	
	public void makeDepredate(City city,Person person){
		Log.d(TAG, ""+STATUS_DEPREDATE);
		setStatus(STATUS_NORMAL);
	}
	//输送
	public boolean canTransportation(City city,Person person){
		return true;
	}
	
	public void makeTransportation(City city,Person person){
		Log.d(TAG, ""+STATUS_TRANSPORTATION);
		setStatus(STATUS_NORMAL);
	}
	
	//离间
	public boolean canAlienate(City city,Person person){
		return true;
	}
	
	public void makeAlienate(City city,Person person){
		Log.d(TAG, ""+STATUS_ALIENATE);
		setStatus(STATUS_NORMAL);
	}
	//招揽
	public boolean canCanvass(City city,Person person){
		return true;
	}
	
	public void makeCanvass(City city,Person person){
		Log.d(TAG, ""+STATUS_CANVASS);
		setStatus(STATUS_NORMAL);
	}
	//策反
	public boolean canCounterespionage(City city,Person person){
		return true;
	}
	
	public void makeCounterespionage(City city,Person person){
		Log.d(TAG, ""+STATUS_COUNTERESPIONAGE);
		setStatus(STATUS_NORMAL);
	}
	//反间
	public boolean canRealienate(City city,Person person){
		return true;
	}
	
	public void makeRealienate(City city,Person person){
		Log.d(TAG, ""+STATUS_REALIENATE);
		setStatus(STATUS_NORMAL);
	}
	//劝降
	public boolean canInduce(City city,Person person){
		return true;
	}
	
	public void makeInduce(City city,Person person){
		Log.d(TAG, ""+STATUS_INDUCE);
		setStatus(STATUS_NORMAL);
	}
	//朝贡
	public boolean canTribute(City city,Person person){
		return true;
	}
	
	public void makeTribute(City city,Person person){
		Log.d(TAG, ""+STATUS_TRIBUTE);
		setStatus(STATUS_NORMAL);
	}
	
	//招降
	public boolean canSurrender(City city){
		if(city.getPersonsNum() <= 0){
			// 人手不足
			return false;
		}
		if(city.getMoney() < 100){
			// 金钱不足
			return false;
		}
		return true;
	}
	
	public void makeSurrender(City city,City cityTo,Person person,Person personTo){
		if(isPersonManual(person, STATUS_SURRENDER) == true){
			orderConsumeMoney(city, STATUS_SURRENDER);
			orderConsumeThew(person, STATUS_SURRENDER);
			
			Order order = new Order();
			order.setId(STATUS_SURRENDER);
			order.setPerson(person);
			order.setCity(city);
			order.setPersonTo(personTo);
			order.setCityTo(cityTo);
			order.setTimeCount(1);
			orderQueue.offer(order);
			
			delPerson(city, person);
		}
		Log.d(TAG, ""+STATUS_SURRENDER);
		setStatus(STATUS_NORMAL);
	}
	//处斩
	public boolean canKill(City city,Person person){
		return true;
	}
	
	public void makeKill(City city,Person person){
		Log.d(TAG, ""+STATUS_KILL);
		setStatus(STATUS_NORMAL);
	}
	//流放
	public boolean canBanish(City city,Person person){
		return true;
	}
	
	public void makeBanish(City city,Person person){
		Log.d(TAG, ""+STATUS_BANISH);
		setStatus(STATUS_NORMAL);
	}
	//移动
	public boolean canMove(City city,Person person){
		return true;
	}
	
	public void makeMove(City city,Person person){
		Log.d(TAG, ""+STATUS_MOVE);
		setStatus(STATUS_NORMAL);
	}
	
	//赏赐
	public boolean canLargess(City city,Person person){
		return true;
	}
	
	public void makeLargess(City city,Person person){
		Log.d(TAG, ""+STATUS_LARGESS);
		setStatus(STATUS_NORMAL);
	}
	//没收
	public boolean canConfiscate(City city,Person person){
		return true;
	}
	
	public void makeConfiscate(City city,Person person){
		Log.d(TAG, ""+STATUS_CONFISCATE);
		setStatus(STATUS_NORMAL);
	}
	//宴请
	public boolean canTreat(City city,Person person){
		return true;
	}
	
	public void makeTreat(City city,Person person){
		Log.d(TAG, ""+STATUS_TREAT);
		setStatus(STATUS_NORMAL);
	}
	
	public ArrayList<Person> getGPersons(){
		ArrayList<Person> persons = new ArrayList<Person>();
		for(int i : gPersons.keySet()){
			persons.add(gPersons.get(i));
		}
		return persons;
	}
	
	public void delPerson(City city,Person person){
		city.getPersonQueue().remove(person);
	}
	
	public void orderConsumeMoney(City city,int orderId){
		switch(orderId){
		case STATUS_ASSART:
			city.setMoney(city.getMoney() - 100);
			break;
		default:
			city.setMoney(city.getMoney() - 100);
			break;
		}
	}
	
	public void orderConsumeThew(Person person,int orderId){
		switch(orderId){
		case STATUS_ASSART:
			person.setThew(person.getThew() - 10);
			break;
		default:
			person.setThew(person.getThew() - 10);
			break;
		}
	}
	
	//武将是否有足够体力进行命令
	public boolean isPersonManual(Person person,int orderId){
		return person.getThew() > 10;
	}
	
	public int getStatus() {
		return status;
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
