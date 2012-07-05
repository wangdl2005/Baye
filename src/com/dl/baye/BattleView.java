package com.dl.baye;

import java.util.ArrayList;
import static com.dl.baye.util.Constant.*;

import com.dl.baye.util.General;
import com.dl.baye.util.Map;
import com.dl.baye.util.Menu;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;

public class BattleView {
	GameView gameView;
	Resources r;
	
	private int state;
	private Map map;
	private ArrayList<General> playerList = new ArrayList<General>();
	private ArrayList<General> enemyList = new ArrayList<General>();
	private General gSel = null;
	//
	private int moveCount = 0;
	private int[][] moveList = new int[maxX][maxY];
	private int[][] movingList = new int[maxX][maxY];
	private int[][] attackList = new int[maxX][maxY];
	private static Bitmap[] bubingBmp;
	private static Bitmap[] gongbingBmp;
	private static Bitmap[] qibingBmp;
	private static Bitmap[] jibingBmp;
	private static  Bitmap[] xuanbingBmp;
	private static Bitmap[] shuibingBmp;
	private static Bitmap[] gongyongBmp;
	
	private static Bitmap[] iconBmp;
	private static Bitmap[] listBmp;
	static Bitmap[] mapBitmap;
	private Menu menu;
	//可以显示的屏幕最大
	static final int maxX = 25;
	static final int maxY = 15;
	int startRow = 0;			//屏幕左上角在大地图中的行数
	int startCol = 0;			//屏幕左上角在大地图中的行数
	int offsetX = 0;			//屏幕左上角相对于startCol的偏移量
	int offsetY = 0;			//屏幕左上角相对于startRow的偏移量
	// 当前X，Y
	int curX,curY;
	int curRow,curCol;
	
	public BattleView(GameView gameView){
		this.gameView = gameView;
		this.r = gameView.getResources();
		init();
		startGame();
	}
	
	public void init()
	{
		initBitmap();
		initMap();
		initGeneral();
		
		gSel = playerList.get(1);
		menu = new Menu(maxX-1);
		openMenu(2);
		
		startRow = 0;					//将startRow置零
		startCol = 0;					//将startCol置零
		offsetX = 0;					//将offsetX置零
		offsetY = 0;					//将offsetY置零
		for (int y = 0; y <= maxY - 1; y++) {
			for (int x = 0; x <= maxX - 1; x++) {
				moveCount = 0;
				moveList[x][y] = -1;
				movingList[x][y] = -1;
				attackList[x][y] = 0;
			}
		}
	}
	
	public void initGeneral(){
		int[][] animatId = new int[][]
				{
				{1},
				{2},
				{3,4},
				{5,6,7,8},
				{9,10,11,12}
				};
		General player01 = new General(
				gameView.gPersons.get(0), 0, 2, 2);
		player01.makeAnimation(animatId);
		General player02 = new General(
				gameView.gPersons.get(1), 0, 3, 2);
		player02.makeAnimation(animatId);
		playerList.add(player01);
		playerList.add(player02);
		General enemy01 = new General(
				gameView.gPersons.get(2), 1, 4, 9);
		enemy01.makeAnimation(animatId);
		General enemy02 = new General(
				gameView.gPersons.get(3), 1, 5, 9);
		enemy02.makeAnimation(animatId);
		enemyList.add(enemy01);
		enemyList.add(enemy02);
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
	}
	
	public void initMap(){
		int[][] mapMat =
			{
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,1,1,1,1,1,2,1,1,1,1,1,1,0},
				{0,1,2,2,2,2,1,1,1,2,2,2,2,1,0},
				{0,1,1,1,2,1,1,2,1,1,2,1,1,1,0},
				{0,1,2,1,2,1,2,2,2,1,2,1,2,1,0},
				{0,1,2,1,1,1,1,2,1,1,1,1,2,1,0},
				{0,1,2,2,1,2,1,1,1,2,1,2,2,1,0},
				{0,1,1,1,1,2,1,2,1,2,1,1,1,1,0},
				{0,1,2,2,2,2,3,3,3,3,3,3,3,1,0},
				{0,1,1,1,1,2,1,2,1,2,1,1,1,1,0},
				{0,0,0,0,1,2,1,2,1,2,1,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,1,1,1,1,1,1,2,1,1,1,1,1,1,0},
				{0,1,2,2,2,2,1,1,1,2,2,2,2,1,0},
				{0,1,1,1,2,1,1,2,1,1,2,1,1,1,0},
				{0,1,2,1,2,1,2,2,2,1,2,1,2,1,0},
				{0,1,2,1,1,1,1,2,1,1,1,1,2,1,0},
				{0,1,2,2,1,2,1,1,1,2,1,2,2,1,0},
				{0,1,1,1,1,2,1,2,1,2,1,1,1,1,0},
				{0,1,2,2,2,2,3,3,3,3,3,3,3,1,0},
				{0,1,1,1,1,2,1,2,1,2,1,1,1,1,0},
				{0,0,0,0,1,2,1,2,1,2,1,0,0,0,0},
				{0,1,2,2,2,2,3,3,3,3,3,3,3,1,0},
				{0,1,1,1,1,2,1,2,1,2,1,1,1,1,0},
				{0,0,0,0,1,2,1,2,1,2,1,0,0,0,0}
			};
		int[][] notInMat = 
			{
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,1,1,1,1,0,0,0,1,1,1,1,0,0},
				{0,0,0,0,1,0,0,1,0,0,1,0,0,0,0},
				{0,0,1,0,1,0,1,1,1,0,1,0,1,0,0},
				{0,0,1,0,0,0,0,1,0,0,0,0,1,0,0},
				{0,0,1,1,0,1,0,0,0,1,0,1,1,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,1,1,1,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
				{0,0,1,1,1,1,0,0,0,1,1,1,1,0,0},
				{0,0,0,0,1,0,0,1,0,0,1,0,0,0,0},
				{0,0,1,0,1,0,1,1,1,0,1,0,1,0,0},
				{0,0,1,0,0,0,0,1,0,0,0,0,1,0,0},
				{0,0,1,1,0,1,0,0,0,1,0,1,1,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,1,1,1,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,1,1,1,1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0}
			};
		map = new Map(mapMat, notInMat);
	}
	
	public void startGame(){
		for(General g:playerList){
			g.startAnimation();
			g.setAnimationSegment(3);
		}
		for(General g:enemyList){
			g.startAnimation();
		}
	}
	
	public void onDraw(Canvas canvas){
		//清空
		canvas.drawColor(Color.WHITE);
		
		int tempStartRow = this.startRow;	//获取绘制起始行
		int tempStartCol = this.startCol;	//获取绘制起始列
		int tempOffsetX = this.offsetX;		//获取相对于tempStartRow的偏移量
		int tempOffsetY = this.offsetY;		//获取相对于tempStartCol的偏移量
		//draw Map
		//map.drawSelf(canvas, mapBitmap)
		map.onDraw(canvas,mapBitmap,tempStartCol,tempStartRow,tempOffsetX,tempOffsetY);
		//draw Hero
		for(General g : playerList){
			int row = g.getRow();
			int col = g.getCol();
			if((col-tempStartCol)*TILE - tempOffsetX <0||(col-tempStartCol)*TILE - tempOffsetX >SCREEN_WIDTH)
				break;
			g.drawSelf(canvas, bubingBmp,(col-tempStartCol)*TILE - tempOffsetX, (row-tempStartRow)*TILE -tempOffsetY);
			// 已行动完毕			
		}
		//移动范围绘制
		// 攻击范围绘制
		// 绘制移动路线
		//draw enemy
		for(General g : enemyList){
			int row = g.getRow();
			int col = g.getCol();
			if((col-tempStartCol)*TILE - tempOffsetX <0||(col-tempStartCol)*TILE - tempOffsetX >SCREEN_WIDTH)
				break;
			g.drawSelf(canvas, bubingBmp, (col-tempStartCol)*TILE -tempOffsetX, (row-tempStartRow)*TILE -tempOffsetY);
		}
		// 菜单
		if(menu.isVisible()){
			menu.drawSelf(canvas, listBmp, iconBmp, (4-tempStartCol)  , (9-tempStartRow) ,tempOffsetX,tempOffsetY);
		}
		// 右边显示状态
		// 右边小地图
		// 显示角色数据
		
		// 战斗回合
			//移动			
	}
	
	public boolean OnKeyDown(int keyCode, KeyEvent event){
		switch(event.getKeyCode()){
		case KeyEvent.KEYCODE_W:
			CheckIfRollScreen(0);
			break;
		case KeyEvent.KEYCODE_S:
			CheckIfRollScreen(1);
			break;
		case KeyEvent.KEYCODE_A:
			CheckIfRollScreen(2);
			break;
		case KeyEvent.KEYCODE_D:
			CheckIfRollScreen(3);
			break;
		}
		return true;
	}
	
	public synchronized void CheckIfRollScreen(int direction){
//		int hx = gSel.getX();
//		int hy = gSel.getY();
		switch(direction){
		case 0:				//只向上检查滚屏与否
//			if(hy - startRow*TILE - offsetY <= SPACE_FOR_ROLL)
			{//检查是否需要左滚屏
				if(startRow >0){//startRow 还能借
					offsetY -= SPAN_TO_ROLL;
					if(offsetY <= 0){
						startRow -=1;
						offsetY += TILE;	//
					}
				}
				else if(offsetY >= SPAN_TO_ROLL){//格子数不够减了，但是偏移量还有
					offsetY -= SPAN_TO_ROLL;
				}
			}
			break;
		case 1:				//只向下检查滚屏与否
//			if(hy - startRow*TILE -offsetY + SPACE_FOR_ROLL >= SCREEN_HEIGHT)
			{//检查是否需要下滚
				if(startRow + SCREEN_ROWS <= MAP_ROWS ){//可以接受进位就加
					offsetY += SPAN_TO_ROLL;
					if(offsetY >= TILE){//需要进位
						startRow += 1;
						offsetY -=TILE;
					}
				}
			}
			break;
		case 2:				//只向左检查滚屏与否
//			if(hx - startCol*TILE - offsetX <= SPACE_FOR_ROLL)
			{//检查是否需要左滚屏
				if(startCol > 0){//startCol还够减
					offsetX -= SPAN_TO_ROLL;//向左偏移英雄步进的像素数
					if(offsetX <= 0){					
						startCol -=1;//
						offsetX += TILE;//有待商议		
					}
				}
				else if(offsetX >= SPAN_TO_ROLL){//如果格子数不够减，但是偏移量还有
					offsetX -= SPAN_TO_ROLL;//向左偏移英雄步进的像素数
				}
			}
			break;
		case 3:				//只向右检查滚屏与否
//			if(hx - startCol*TILE - offsetX + SPACE_FOR_ROLL >= SCREEN_WIDTH)
			{//检查是否需要右滚屏
				if(startCol + SCREEN_COLS <= MAP_COLS ){//startCol还能加
					offsetX += SPAN_TO_ROLL;//向右偏移英雄步进的像素数
					if(offsetX >= TILE){//需要进位
						startCol += 1;
						offsetX -= TILE;
					}
				}
			}
			break;
		}
	}
	
	/**
	 * 设定菜单
	 * 
	 * @param menuType
	 */
	public synchronized void openMenu(int menuType) {
		menu.visible = true;
		menu.setMenuType(menuType);
		menu.cur = 0;
	}

	/**
	 * 关闭菜单
	 * 
	 */
	public synchronized void closeMenu() {
		menu.visible = false;
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
}
