package com.dl.baye;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.dl.baye.util.Constant.*;

import com.dl.baye.battle.StateManager;
import com.dl.baye.util.General;
import com.dl.baye.util.Map;
import com.dl.baye.util.Menu;
import com.dl.baye.util.TextUtil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class BattleView {
	GameView gameView;
	Resources r;
	BattleViewThread battleViewThread;
	
	int state;//0 : 选中武将，显示信息,1,显示移动范围2显示菜单,3,显示攻击范围4选择攻击目标
	static final int STATE_MSG = 0;
	static final int STATE_ENEMY  = 7;
	public static StateManager stateManager;
	
	public Map map;
	private ArrayList<General> playerList = new ArrayList<General>();
	private ArrayList<General> enemyList = new ArrayList<General>();
	public General gAction = null;//当前行动武将
	public General gSel  = null;//当前选中武将
	//
	private int moveCount = 0;
	// 攻击范围
	public int[][] moveList = new int[maxY][maxX];
	// 移动路径
	public int[][] movingList = new int[maxY][maxX];
	// 攻击
	//武将初始位置
	private int[][][] posOri = new int[2][5][2];
	public int[][] attackList = new int[maxY][maxX];
	public static Bitmap[] bubingBmp;
	public static Bitmap[] gongbingBmp;
	public static Bitmap[] qibingBmp;
	public static Bitmap[] jibingBmp;
	public static  Bitmap[] xuanbingBmp;
	public static Bitmap[] shuibingBmp;
	public static Bitmap[] gongyongBmp;	
	public static Bitmap[] iconBmp;
	public static Bitmap[] listBmp;
	public static Bitmap[] mapBitmap;
	public static Bitmap actionEndBmp;
	//双缓冲
//	private Bitmap bufferBmp;
//	private Canvas bufferCanvas;
	public Menu menu;
	//可以显示的屏幕最大
	static final int maxX = MAP_COLS;
	static final int maxY = MAP_ROWS;
	public int startRow = 0;			//屏幕左上角在大地图中的行数
	public int startCol = 0;			//屏幕左上角在大地图中的行数
	int offsetX = 0;			//屏幕左上角相对于startCol的偏移量
	int offsetY = 0;			//屏幕左上角相对于startRow的偏移量
	// 当前光标X，Y
	public int curRow,curCol;
	
	public BattleView(GameView gameView){
		this.gameView = gameView;
		this.r = gameView.getResources();
		init();
	}
	
	public void start(){
		this.battleViewThread.start();
	}
	
	public void exit(){
		
	}
	
	public void init()
	{
		initBitmap();
		initMap();
		initGeneral();		
		
		startRow = 0;					//将startRow置零
		startCol = 0;					//将startCol置零
		offsetX = 0;					//将offsetX置零
		offsetY = 0;					//将offsetY置零
		curRow = 3;
		curCol  = 3;
		//
		stateManager = new StateManager(this);
		//范围等地图数据清零
		for (int y = 0; y <= maxY - 1; y++) {
			for (int x = 0; x <= maxX - 1; x++) {
				moveCount = 0;
				moveList[y][x] = -1;
				movingList[y][x] = -1;
				attackList[y][x] = 0;
			}
		}
		//设置双缓冲,每次现在bufferCanvas上把图片绘制完，最后将bufferBmp绘制到canvas上
		//		比屏幕多出周围一圈，用于滚屏	暂时不用，等需要使用滚屏技术时再使用
//		bufferBmp = Bitmap.createBitmap(MAP_COLS *TILE,MAP_ROWS*TILE,Config.ARGB_8888);
//		bufferCanvas = new Canvas(bufferBmp);		
		battleViewThread = new BattleViewThread(this);
		//menu
		menu = new Menu(2);//先设定为只有两个菜单项
		menu.setMenuType(2);
		//初始化
		gAction = null;
		gSel = getGeneral(curCol, curRow);
	}

	public void initGeneral(){
		int[][] animatId = new int[][]
				{
				{0},
				{1},
				{2,3},
				{4,5,6,7},
				{8,9,10,11}
				};
		General player01 = new General(
				gameView.gPersons.get(0), 0, posOri[0][1][0], posOri[0][1][1]);
		player01.makeAnimation(animatId);
		General player00 = new General(
				gameView.gPersons.get(1), 0, posOri[0][0][0], posOri[0][0][1]);
		player00.makeAnimation(animatId);
		playerList.add(player01);
		playerList.add(player00);
		General enemy01 = new General(
				gameView.gPersons.get(2), 1, posOri[1][1][0], posOri[1][1][1]);
		enemy01.makeAnimation(animatId);
		General enemy00 = new General(
				gameView.gPersons.get(3), 1, posOri[1][0][0], posOri[1][0][1]);
		enemy00.makeAnimation(animatId);
		enemyList.add(enemy01);
		enemyList.add(enemy00);
	}
	
	public void initBitmap(){
		Bitmap bmpMap = BitmapFactory.decodeResource(r, R.drawable.map);
		mapBitmap = splitBitmap(bmpMap,2,2);
		bmpMap = null;
		
		Bitmap bmpBing =  BitmapFactory.decodeResource(r, R.drawable.bubing);
		bubingBmp = splitBitmap(bmpBing,3,4);
		bmpBing = null;
		bmpBing =  BitmapFactory.decodeResource(r, R.drawable.gongbing);
		gongbingBmp = splitBitmap(bmpBing,3,4);
		bmpBing = null;
		bmpBing =  BitmapFactory.decodeResource(r, R.drawable.jibing);
		jibingBmp = splitBitmap(bmpBing,3,4);
		bmpBing = null;
		bmpBing =  BitmapFactory.decodeResource(r, R.drawable.qibing);
		qibingBmp = splitBitmap(bmpBing,3,4);
		bmpBing = null;
		bmpBing =  BitmapFactory.decodeResource(r, R.drawable.shuibing);
		shuibingBmp = splitBitmap(bmpBing,3,4);
		bmpBing = null;
		bmpBing =  BitmapFactory.decodeResource(r, R.drawable.xuanbing);
		xuanbingBmp = splitBitmap(bmpBing,3,4);
		bmpBing =  BitmapFactory.decodeResource(r, R.drawable.gongyong);
		gongyongBmp = splitBitmap(bmpBing,3,4);
		bmpBing = null;
		bmpBing=  BitmapFactory.decodeResource(r, R.drawable.icon);
		iconBmp = splitBitmap(bmpBing,4,5);
		bmpBing = null;
		bmpBing=  BitmapFactory.decodeResource(r, R.drawable.list);
		listBmp = splitBitmap(bmpBing,3,3);
		bmpBing = null;
		
		bmpBing=  BitmapFactory.decodeResource(r, R.drawable.unit);
		actionEndBmp = splitBitmap(bmpBing,2,2)[3];
		bmpBing = null;
		
	}
	
	public void initMap(){
		try {
			map = new Map(r.getAssets().open("map.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		posOri[0][0][0] = 3;posOri[0][0][1] = 4;		//team:0,general:0,position:3,4(col,row)
		posOri[0][1][0] = 4;posOri[0][1][1] = 4;
		posOri[0][2][0] = 4;posOri[0][2][1] = 3;
		posOri[0][3][0] = 5;posOri[0][3][1] = 5;
		posOri[0][4][0] = 4;posOri[0][4][1] = 5;
		posOri[1][0][0] = 6;posOri[1][0][1] = 6;
		posOri[1][1][0] = 9;posOri[1][1][1] = 10;
		posOri[1][2][0] = 24;posOri[1][2][1] = 23;
		posOri[1][3][0] = 25;posOri[1][3][1] = 25;
		posOri[1][4][0] = 24;posOri[1][4][1] = 25;
		
	}
	
	public void drawBuffer(Canvas canvas){
		//涂白
		canvas.drawColor(Color.WHITE);
		int tempStartRow = this.startRow;	//获取绘制起始行
		int tempStartCol = this.startCol;	//获取绘制起始列
		//绘制地图
		map.onDraw(canvas,mapBitmap,tempStartCol,tempStartRow,0,0);
		//绘制移动范围
		//绘制英雄
		for(General g : playerList){
			int row = g.getRow();
			int col = g.getCol();
			if((col-tempStartCol)*TILE  <0||(col-tempStartCol)*TILE >SCREEN_WIDTH)
				continue;
			g.drawSelf(canvas, getArmTypeBmp(g.getPerson().getArmsType()),actionEndBmp
					,(col-tempStartCol)*TILE , (row-tempStartRow)*TILE );
				
		}	
		//绘制攻击范围
		//绘制移动路线
		//绘制敌人
		for(General g : enemyList){
			int row = g.getRow();
			int col = g.getCol();
			if((col-tempStartCol)*TILE  <0||(col-tempStartCol)*TILE  >SCREEN_WIDTH)
				continue;
			g.drawSelf(canvas, getArmTypeBmp(g.getPerson().getArmsType()),actionEndBmp
					, (col-tempStartCol)*TILE , (row-tempStartRow)*TILE );
		}
		//绘制菜单
//		if(menu.isVisible()){
//			menu.drawSelf(bufferCanvas, listBmp, iconBmp, (4-tempStartCol)  , (9-tempStartRow) ,0,0);
//		}
		//根据状态绘制
		stateManager.Draw(canvas,tempStartCol,tempStartRow);
	}

	public boolean OnKeyDown(int keyCode){
		switch(state){
		case STATE_ENEMY:
			break;
		default:
				stateManager.KeyDown(keyCode);
				break;
		}
		return true;
	}
	public void OnDraw(Canvas canvas){
		//绘制中央屏幕
		drawBuffer(canvas);
		//绘制小屏幕
		drawMiniMap(canvas);
	}
	
	//方法：绘制迷你地图
	public void drawMiniMap(Canvas canvas){
		Paint paint = new Paint();			//创建画笔对象
		paint.setARGB(255, 250, 250,250);
		canvas.drawRect(
				new Rect(MINI_MAP_START_X,0,MINI_MAP_START_X + 160,0 +15*32)
				, paint);
		//绘制坐标，地形
		String text = "";
		TextUtil txtUtil = null;
		
		text = "(" + curCol +"," + curRow + ")" + "         场地:" + map.getMapMat()[curRow][curCol];
	    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 18, 180, 20, Color.WHITE, Color.BLACK, 0, 15);
	    txtUtil.InitText();
	    txtUtil.DrawText(canvas);
		
		for(int i=0;i<MAP_ROWS;i++){
			for(int j=0;j<MAP_COLS;j++){
				switch(map.getNotInMat()[i][j]){
				case 0:		//绘制一个浅浅的矩形
					paint.setARGB(128, 128, 128, 128);
					break;
				case 1:		//绘制一个深深的矩形
					paint.setARGB(128, 0, 0, 0);
					break;
				}
				canvas.drawRect(
						MINI_MAP_START_X+j*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_Y+i*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_X+(j+1)*MINI_MAP_TILE_SIZE,
						MINI_MAP_START_Y+(i+1)*MINI_MAP_TILE_SIZE,
						paint);
			}
		}
		//画敌人
		paint.setARGB(128, 255, 0, 0);
		for(General m:enemyList){
			canvas.drawOval(
					new RectF(
						MINI_MAP_START_X+m.getCol()*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_Y+m.getRow()*MINI_MAP_TILE_SIZE, 
						MINI_MAP_START_X+(m.getCol()+1)*MINI_MAP_TILE_SIZE,
						MINI_MAP_START_Y+(m.getRow()+1)*MINI_MAP_TILE_SIZE
					), paint);
		}
		//画自己
		paint.setARGB(128, 0, 255, 255);
		for(General hero:playerList){
		canvas.drawOval(
				new RectF(
					MINI_MAP_START_X+hero.getCol()*MINI_MAP_TILE_SIZE, 
					MINI_MAP_START_Y+hero.getRow()*MINI_MAP_TILE_SIZE, 
					MINI_MAP_START_X+(hero.getCol()+1)*MINI_MAP_TILE_SIZE,
					MINI_MAP_START_Y+(hero.getRow()+1)*MINI_MAP_TILE_SIZE
				), paint);
		}
		//画光标
		paint.setARGB(64, 255, 255,255);
		canvas.drawRect(
				MINI_MAP_START_X+curCol*MINI_MAP_TILE_SIZE, 
				MINI_MAP_START_Y+curRow*MINI_MAP_TILE_SIZE, 
				MINI_MAP_START_X+(curCol+1)*MINI_MAP_TILE_SIZE,
				MINI_MAP_START_Y+(curRow+1)*MINI_MAP_TILE_SIZE,
				paint);
		//画家
		paint.setARGB(128, 0, 0, 255);
		
		//绘制武将信息
		if(gSel != null){
			String name =  gSel.getPerson().getName();
			int force = gSel.getPerson().getForce();
			int iq = gSel.getPerson().getIq();
			String armType = formatArmsType(gSel.getPerson().getArmsType());
			int team = gSel.getTeam();
			text = "武将 : " + name;
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 200, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "武力 : " + force;
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 240, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "智力 : " + iq;
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 280, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "兵力 : " + "0";
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 320, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "兵种 : " + armType;
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 360, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "粮食 : " + "0";
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 400, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "Team : " + team;
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 440, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		    
		    text = "场地加成 : " + "0";
		    txtUtil = new TextUtil(text, MINI_MAP_START_X + 20, 480, 
		    		180, 30, Color.WHITE, Color.BLACK, 0, 20);
		    txtUtil.InitText();
		    txtUtil.DrawText(canvas);
		}
	}
	
	public static Bitmap[] splitBitmap(Bitmap bmp,int row,int col){
		int wlength = bmp.getWidth() / col;
		int hlength = bmp.getHeight() / row;
		Bitmap[] bmps = new Bitmap[row*col];
		for(int i=0;i<row;++i)
			for(int j=0;j<col;++j)
			{
				bmps[j + i*col] = Bitmap.createBitmap(bmp, j * wlength, i * hlength, wlength, hlength);
			}
		return bmps;
	}
	
	public Bitmap[] getArmTypeBmp(ARMS_TYPE at){
		Bitmap[] bmps = gongyongBmp;
		switch(at){
		case BuBing:
			bmps=bubingBmp;
			break;
		case GongBing:
			bmps = gongbingBmp;
			break;
		case JiBing:
			bmps = jibingBmp;
			break;
		case QiBing:
			bmps = qibingBmp;
			break;
		case ShuiBing:
			bmps = shuibingBmp;
			break;
		case XuanBing:
			bmps = xuanbingBmp;
			break;
		}
		
		return bmps;
	}
	
	public General getGeneral(int team,int col,int row){
		if(team == 0){				
		for(General g:playerList){
			if(g.getCol() == col && g.getRow() == row){
				return g;
			}
		}
		}
		else if(team == 1){
		for(General g:enemyList){
			if(g.getCol() == col && g.getRow() == row){
				return g;
			}
		}
		}
		return null;
	}
	public General getGeneral(int col,int row){
		for(General g:playerList){
			if(g.getCol() == col && g.getRow() == row){
				return g;
			}
		}
		for(General g:enemyList){
			if(g.getCol() == col && g.getRow() == row){
				return g;
			}
		}
		return null;
	}
}


