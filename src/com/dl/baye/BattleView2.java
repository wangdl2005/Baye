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
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class BattleView2 {
	GameView gameView;
	Resources r;
	
	private int state;//0 : 选中武将，显示信息,1,显示移动范围2显示菜单,3,显示攻击范围4选择攻击目标
	static final int STATE_MSG = 0;
	static final int STATE_SHOW_MOVE = 1;
	static final int STATE_SHOW_MENU = 2;
	static final int STATE_SHOW_ATK = 3;
	static final int STATE_SEL_ATK = 4;
	static final int STATE_NONE = -1;
	private Map map;
	private ArrayList<General> playerList = new ArrayList<General>();
	private ArrayList<General> enemyList = new ArrayList<General>();
	private General gAction = null;//当前行动武将
	//
	private int moveCount = 0;
	// 攻击范围
	private int[][] moveList = new int[maxY][maxX];
	// 移动路径
	private int[][] movingList = new int[maxY][maxX];
	// 攻击
	private int[][] attackList = new int[maxY][maxX];
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
	static final int maxX = MAP_COLS;
	static final int maxY = MAP_ROWS;
	int startRow = 0;			//屏幕左上角在大地图中的行数
	int startCol = 0;			//屏幕左上角在大地图中的行数
	int offsetX = 0;			//屏幕左上角相对于startCol的偏移量
	int offsetY = 0;			//屏幕左上角相对于startRow的偏移量
	// 当前光标X，Y
	int curRow,curCol;
	boolean flagDrawMove = false;
	boolean flagDrawAttack = false;
	
	public BattleView2(GameView gameView){
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
		
//		gAction = playerList.get(0);
//		curCol = gAction.getCol();
//		curRow = gAction.getRow();
		
		startRow = 0;					//将startRow置零
		startCol = 0;					//将startCol置零
		offsetX = 0;					//将offsetX置零
		offsetY = 0;					//将offsetY置零
		initVar();
		
		state = STATE_NONE;
		setMoveRange();
		setAttackRange(true);
	}

	private void initVar() {
		for (int y = 0; y <= maxY - 1; y++) {
			for (int x = 0; x <= maxX - 1; x++) {
				moveCount = 0;
				moveList[y][x] = -1;
				movingList[y][x] = -1;
				attackList[y][x] = 0;
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
				gameView.gPersons.get(0), 0, 5, 5);
		player01.makeAnimation(animatId);
		General player02 = new General(
				gameView.gPersons.get(1), 0, 6, 7);
		player02.makeAnimation(animatId);
		playerList.add(player01);
		playerList.add(player02);
		General enemy01 = new General(
				gameView.gPersons.get(2), 1, 4, 10);
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
				{0,1,1,1,1,2,1,2,1,2,1,1,1,1,0}
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
				{0,0,0,0,0,1,0,1,0,1,0,0,0,0,0}
			};
		map = new Map(mapMat, notInMat);
	}
	
	public void startGame(){
//		for(General g:playerList){
//			g.startAnimation();
//			g.setAnimationSegment(3);
//		}
//		for(General g:enemyList){
//			g.startAnimation();
//		}
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
		//移动范围绘制
		if(flagDrawMove/*条件*/)
		{
			for(int i=0;i<maxY;++i)
			{
				for(int j=0;j<maxX;++j)
				{
					if (moveList[i][j] > -1) {
						//canvas.drawBitmap(iconBmp[2], j * TILE-tempOffsetX, i * TILE-tempOffsetY,null);
						drawSelf(canvas,iconBmp[2],i,j,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
					} else if (attackList[i][j] > 0) {
						//canvas.drawBitmap(iconBmp[3], j * TILE-tempOffsetX, i * TILE-tempOffsetY,null);
						drawSelf(canvas,iconBmp[3],i,j,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
					}
				}
			}
		}
		//draw Hero
		for(General g : playerList){
			int row = g.getRow();
			int col = g.getCol();
			if((col-tempStartCol)*TILE - tempOffsetX <0||(col-tempStartCol)*TILE - tempOffsetX >SCREEN_WIDTH)
				continue;
			g.drawSelf(canvas, bubingBmp,(col-tempStartCol)*TILE - tempOffsetX, (row-tempStartRow)*TILE -tempOffsetY);
			// 已行动完毕			
		}		
		// 攻击范围绘制
		if(flagDrawAttack/*条件*/)
		{
			for (int j = 0; j <= maxY - 1; j++) {
				for (int i = 0; i <= maxX - 1; i++) {
					int result = attackList[j][i];
					if (result == 2) {
						//canvas.drawBitmap(iconBmp[3], i * TILE, j * TILE,null);
						drawSelf(canvas,iconBmp[3],j,i,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
					}
					// 标注选中的攻击对象 team TODO
					if (result == 2 && getGeneral(1, i, j) != null
//							&& curX == i
//							&& curY == j
							) {

						drawSelf(canvas,iconBmp[3],j,i,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
						//canvas.drawBitmap(iconBmp[4], i * TILE-tempOffsetX, j * TILE-tempOffsetY,null);
					}
				}
			}
		}
		// 		绘制移动路线 暂不绘制
		if(false/*条件*/)
		{
			int count = 0;
			for (int j = 0; j <= maxY - 1; j++) {
				for (int i = 0; i <= maxX - 1; i++) {
					if (movingList[j][i] == -1) {
						continue;
					}
					count = 0;
					if ((movingList[j][i] == 0)
							|| (movingList[j][i] == moveCount)) {
						if ((i > 0)
								&& (movingList[j][i-1] > -1)
								&& ((movingList[j][i-1]
										- map.getMapCost(i - 1, j) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j][i-1]))) {
							count = 1;
						}
						if ((j > 0)
								&& (movingList[j-1][i] > -1)
								&& ((movingList[j-1][i]
										- map.getMapCost(i, j - 1) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j-1][i]))) {
							count = 2;
						}
						if ((i < maxX - 1)
								&& (movingList[j][i+1]> -1)
								&& ((movingList[j][i+1]
										- map.getMapCost(i + 1, j) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j][i+1]))) {
							count = 3;
						}
						if ((j < maxY - 1)
								&& (movingList[j+1][i]> -1)
								&& ((movingList[j+1][i]
										- map.getMapCost(i, j + 1) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j+1][i]))) {
							count = 4;
						}
						if (movingList[j][i] != 0) {
							count = count + 4;
						}
					} else {
						count = 6;
						if ((i > 0)
								&& (movingList[j][i-1] > -1)
								&& ((movingList[j][i-1]
										- map.getMapCost(i - 1, j) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j][i-1]))) {
							count = count + 1;
						}
						if ((j > 0)
								&& (movingList[j-1][i] > -1)
								&& ((movingList[j-1][i]
										- map.getMapCost(i, j - 1) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j-1][i]))) {
							count = count + 2;
						}
						if ((i < maxX - 1)
								&& (movingList[j][i+1]> -1)
								&& ((movingList[j][i+1]
										- map.getMapCost(i + 1, j) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j][i+1]))) {
							count = count + 3;
						}
						if ((j < maxY - 1)
								&& (movingList[j+1][i]> -1)
								&& ((movingList[j+1][i]
										- map.getMapCost(i, j + 1) == movingList[j][i]) || (movingList[j][i]
										- map.getMapCost(i, j) == movingList[j+1][i]))) {
							count = count + 5;
						}
					}
					if (count > 0) {
						if(i * TILE-tempOffsetX <0||i * TILE-tempOffsetX>SCREEN_WIDTH||
								j * TILE-tempOffsetY<0||j * TILE-tempOffsetY>SCREEN_HEIGHT)
							continue;

						drawSelf(canvas,iconBmp[count + 4],j,i,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
						//canvas.drawBitmap(iconBmp[count + 4], i * TILE-tempOffsetX, j * TILE-tempOffsetY,null);
					}
				}
			}
		}
		//draw enemy
		for(General g : enemyList){
			int row = g.getRow();
			int col = g.getCol();
			if((col-tempStartCol)*TILE - tempOffsetX <0||(col-tempStartCol)*TILE - tempOffsetX >SCREEN_WIDTH)
				continue;
			g.drawSelf(canvas, bubingBmp, (col-tempStartCol)*TILE -tempOffsetX, (row-tempStartRow)*TILE -tempOffsetY);
		}
		// 菜单
		if(menu.isVisible()){
			menu.drawSelf(canvas, listBmp, iconBmp, (4-tempStartCol)  , (9-tempStartRow) ,tempOffsetX,tempOffsetY);
		}
		// 右边显示状态
		// 右边小地图
		drawMiniMap(canvas);
		// 显示角色数据
		
		// 战斗回合
			//移动			
	}
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			curRow = (int) event.getY()/TILE + startRow;
			curCol = (int)event.getX()/TILE + startCol;
			Log.d(TAG, "row:" + curRow +" col:" + curCol);
			General gSelect = getGeneral(curCol,curRow);
			switch(state){
			case STATE_NONE:
				if(gSelect != null){
					gAction = gSelect;
					//show message
					state = STATE_MSG;
				}
				break;
			case STATE_MSG:
				if(gSelect != null && gAction!= null && gSelect.isEqual(gAction)){
					state = STATE_SHOW_MOVE;
					initVar();
					setMoveRange();
					setAttackRange(true);
					//绘制移动范围
					flagDrawMove = true;
				}
				break;
			case STATE_SHOW_MOVE:
				if(gSelect != null && gAction!= null  ){
					if(gSelect.isEqual(gAction)){
						state = STATE_SHOW_MENU;
						openMenu(2);
					}
					else{
						//sel move
					}
				}
				break;
			case STATE_SHOW_MENU:
				break;
			case STATE_SHOW_ATK:
				break;
			case STATE_SEL_ATK:
				break;
			}
			if(gSelect != null){
				Log.d(TAG, "name:" + gSelect.getPerson().getName());
				initVar();
				setMoveRange();
				setAttackRange(true);
				//绘制移动范围
				flagDrawMove = true;
				//开启菜单
				openMenu(2);
			}
			if(menu.visible == true){
			}
		}
		return true;
	}
	public boolean OnKeyDown(int keyCode, KeyEvent event){
		 /*
		switch(event.getKeyCode()){
		case KeyEvent.KEYCODE_W:
			--curRow;
			if(curRow <0){
				curRow = 0;
			}
			setMovingCourse();
			CheckIfRollScreen(0);
			break;
		case KeyEvent.KEYCODE_S:
			++curRow;
			if(curRow >= maxY){
				curRow = maxY-1;
			}
			setMovingCourse();
			CheckIfRollScreen(1);
			break;
		case KeyEvent.KEYCODE_A:
			--curCol;
			if(curCol <0){
				curCol = 0;
			}
			setMovingCourse();
			CheckIfRollScreen(2);
			break;
		case KeyEvent.KEYCODE_D:
			++curCol;
			if(curCol >= maxX){
				curCol = maxX-1;
			}
			setMovingCourse();
			CheckIfRollScreen(3);
			break;
		}*/
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
	
	//方法：绘制迷你地图
		public void drawMiniMap(Canvas canvas){
			Paint paint = new Paint();			//创建画笔对象
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
			//画家
			paint.setARGB(128, 0, 0, 255);
		}
		public void setMovingCourse(){
			if (moveList[curRow][curCol] == -1) {
				return;
			}
			if (movingList[curRow][curCol] == moveCount) {
				return;
			}

			// 选择可行的最短路径
			if ((movingList[curRow][redressCol(curCol - 1)] != moveCount)
					&& (movingList[redressRow(curRow - 1)][curCol] != moveCount)
					&& (movingList[curRow][redressCol(curCol + 1)] != moveCount)
					&& (movingList[redressRow(curRow + 1)][curCol] != moveCount)
					|| (moveCount + map.getMapCost(curCol, curRow) > gAction.getMove())) {

				for (int j = 0; j <= maxY - 1; j++) {
					for (int i = 0; i <= maxX - 1; i++) {
						movingList[j][i] = -1;
					}
				}
				int x = curCol;
				int y = curRow;
				moveCount = moveList[curRow][curCol];
				movingList[y][x] = moveCount;
				// 获得移动路径
				for (int i = moveCount; i > 0; i--) {
					switch (setMoveCouse(x, y)) {
					case 0:
						x = x - 1;
						break;
					case 1:
						y = y - 1;
						break;
					case 2:
						x = x + 1;
						break;
					case 3:
						y = y + 1;
						break;
					case 4:
						break;
					}

				}
				moveCount = moveList[curRow][curCol];
				movingList[y][x] = 0;
				return;
			}
			// 获得矫正的移动步数
			moveCount = moveCount + map.getMapCost(curCol, curRow);

			if (movingList[curRow][curCol] > -1) {
				moveCount = movingList[curRow][curCol];
				for (int j = 0; j <= maxY - 1; j++) {
					for (int i = 0; i <= maxX - 1; i++) {
						if (movingList[j][i] > movingList[curRow][curCol]) {
							movingList[j][i] = -1;
						}
					}
				}
			}
			movingList[curRow][curCol] = moveCount;
		}
		/**
		 * 设定最短移动路径
		 * 
		 * @param x
		 * @param y
		 * @return
		 */
		public synchronized int setMoveCouse(int x, int y) {
			// 判定左方最短路径
			if ((x > 0) && (moveList[y][x - 1] > -1)
					&& (moveList[y][x - 1] < moveList[y][x])
					&& (moveList[y][x - 1] == moveCount - map.getMapCost(x, y))) {

				moveCount = moveCount - map.getMapCost(x, y);
				movingList[y][x - 1] = moveCount;
				return 0;
			}
			// 判定上方最短路径
			if ((y > 0) && (moveList[y - 1][x] > -1)
					&& (moveList[y - 1][x] < moveList[y][x])
					&& (moveList[y - 1][x] == moveCount - map.getMapCost(x, y))) {
				moveCount = moveCount - map.getMapCost(x, y);
				movingList[y - 1][x] = moveCount;
				return 1;
			}

			// 判定右方最短路径
			if ((x < maxX - 1) && (moveList[y][x + 1] > -1)
					&& (moveList[y][x + 1] < moveList[y][x])
					&& (moveList[y][x + 1] == moveCount - map.getMapCost(x, y))) {
				moveCount = moveCount - map.getMapCost(x, y);
				movingList[y][x + 1] = moveCount;
				return 2;

			}

			// 判定下方最短路径
			if ((y < maxY - 1) && (moveList[y + 1][x] > -1)
					&& (moveList[y + 1][x] < moveList[y][x])
					&& (moveList[y + 1][x] == moveCount - map.getMapCost(x, y))) {

				moveCount = moveCount - map.getMapCost(x, y);
				movingList[y + 1][x] = moveCount;
				return 3;
			}
			return 4;
		}
		
		//设定移动范围
		public  void setMoveRange(){
			if(gAction != null){
				int area = gAction.getMove();
				int col = gAction.getCol();
				int row = gAction.getRow();
				moveList[row][col] = 0;
				for(int count = 0; count<=area;++count){
					for(int j=redressRow(row-area);j<=redressRow(row+area);++j)
						for(int i=redressCol(col-Math.abs(j-(row-area)));
								i<=redressCol(col+Math.abs(j-(row-area)));
								++i
								)
						{
							// 如果能够移动指定步数
							if ((getMoveCount(i - 1, j) == count)
									|| (getMoveCount(i, j - 1) == count)
									|| (getMoveCount(i + 1, j) == count)
									|| (getMoveCount(i, j + 1) == count)) {
								setMoveCount(i, j, count);
							}
						}
				}
			}
		}
		
		/**
		 * getMoveCount
		 */
		public int getMoveCount(int col,int row){
			if ((row < 0) || (row >maxY  - 1) || (col < 0) || (col > maxX - 1)) {
				// 无法移动返回-1
				return -1;
			}
			return moveList[row][col];
		}
		
		/*
		 * 
		 */
		public void setMoveCount(int col,int row,int count){
			// 当为我军时
			if (gAction.getTeam() == 0) {
				if (getGeneral(1, col, row) != null) {
					return;
				}
			} else {
				if (getGeneral(0,col, row) != null) {
					return;
				}
			}
			int cost = map.getMapCost(col,row);
			// 指定位置无法进入
			if (cost < 0) {
				return;
			}
			count = count + cost;
			// 移动步数超过移动能力
			if (count > gAction.getMove()) {
				return;
			}
			// 获得移动所需步数
			if ((moveList[row][col] == -1) || (count < moveList[row][col])) {
				moveList[row][col] = count;
			}
		}
		/*
		 * 设定攻击范围
		 */
		public void setAttackRange(boolean isDraw){
			if(isDraw && gAction != null){
				int row = gAction.getRow();
				int col = gAction.getCol();
				int range = gAction.getAttackRange();
				int point = isDraw?2:1;
				for(int i=row-range;i<row;++i)
				{
					if(i<0 || i>maxY)
						continue;
					if(i-(row-range) + col < maxX&&i-(row-range) + col >0)
					{
						attackList[i][i-(row-range) + col] = point;
					}
					if(col-(i-(row-range)) >0 &&col-(i-(row-range)) <maxX)
					{
						attackList[i][col-(i-(row-range))] = point;
					}
				}
				for(int i=row;i<=row+range;++i)
				{
					if(i<0 || i>maxY)
						continue;
					if(range-(i-row) + col < maxX&&range-(i-row) + col >0)
					{
						attackList[i][range-(i-row) + col] = point;
					}
					if(col-(range-(i-row) ) >0 &&col-(range-(i-row)) <maxX)
					{
						attackList[i][col-(range-(i-row))] = point;
					}
				}
			}
		}
		
		public boolean isAttackable(int team,int col,int row){
			General  g = getGeneral(col, row);
			if(team != g.getTeam()){
				if(attackList[row][col] ==2){
					return true;
				}
			}
			return false;
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
		
		/**
		 * 矫正x坐标
		 * 
		 * @param col
		 * @return
		 */
		public int redressCol(int col) {
			if (col < 0)
				col = 0;
			if (col > maxX - 1)
				col = maxX - 1;
			return col;
		}

		/**
		 * 矫正row坐标
		 * 
		 * @param row
		 * @return
		 */
		public int redressRow(int row) {
			if (row < 0)
				row = 0;
			if (row > maxY - 1)
				row = maxY - 1;
			return row;
		}
	/**
	 * 设定菜单
	 * 
	 * @param menuType
	 */
	public void openMenu(int menuType) {
		menu.visible = true;
		menu.setMenuType(menuType);
		menu.cur = 0;
	}

	/**
	 * 关闭菜单
	 * 
	 */
	public void closeMenu() {
		menu.visible = false;
	}	
	
	public void drawSelf(Canvas canvas,Bitmap bmp,int row,int col,int startRow,int startCol,int offsetX,int offsetY){
		canvas.drawBitmap(bmp,  (col-startCol)*TILE - offsetX,
				(row-startRow)*TILE - offsetY, null);
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
