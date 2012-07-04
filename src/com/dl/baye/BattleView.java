package com.dl.baye;

import java.util.ArrayList;

import com.dl.baye.util.General;
import com.dl.baye.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BattleView {
	GameView gameView;
	Resources r;
	
	private int state;
	private Map map;
	private ArrayList<General> playerList;
	private ArrayList<General> enemyList;
	//
	private int[][] moveList;
	private int[][] movingList;
	private int[][] attackList;
	
	static Bitmap[] mapBitmap;
	public BattleView(GameView gameView){
		this.gameView = gameView;
		this.r = gameView.getResources();
		init();
	}
	
	public void init()
	{
		initBitmap();
		initMap();
	}
	
	public void initBitmap(){
		Bitmap bmpMap = BitmapFactory.decodeResource(r, R.drawable.map);
		mapBitmap = splitBitmap(bmpMap,2,2);
		bmpMap = null;
		
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
			};
		map = new Map(mapMat, notInMat);
	}
	
	public void onDraw(Canvas canvas){
		//draw Map
		map.drawSelf(canvas, mapBitmap);
	}
	
	public static void  drawGamePublic(int imgId,Canvas canvas,int screenX,int screenY){
		
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
