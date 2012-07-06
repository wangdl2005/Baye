package com.dl.baye.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import static com.dl.baye.util.Constant.*;
public class Map {

	//地图矩阵
	private int[][] mapMat;
	//地图不可通过矩阵
	private int[][] notInMat;
		
	public int[][] getNotInMat() {
		return notInMat;
	}

	public Map(int[][] mapMat,int[][] notInMat){
		this.mapMat = mapMat;
		this.notInMat = notInMat;
	}	
	
	//是否可以通过
	public boolean isReach(int row,int col)
	{
		boolean flag = false;
		if(notInMat[row][col] == 1)
			flag = true;
		return flag;
	}
	
	public int getMapCost(int col,int row){
		int cost = 0;
		switch(mapMat[row][col]){
		case 0:
			cost = 1;
			break;
		case 1:
			cost = 2;
			break;
		case 2:
			cost = 3;
			break;
		case 3:
			cost = 3;
			break;
		}
		return cost;
	}
	
	public void onDraw(Canvas canvas,Bitmap[] bmp,int startCol,int startRow ,int offsetX,int offsetY)
	{
		for(int i=-1;i<=SCREEN_ROWS;++i){
			if(startRow+i<0||startRow+i >=MAP_ROWS)
				continue;
			for(int j=-1;j<=SCREEN_COLS;++j)
			{
				if(startCol+j<0||startCol+j>=MAP_COLS)
					continue;
				if(bmp[mapMat[startRow+i][startCol+j]]!=null){
					canvas.drawBitmap(bmp[mapMat[startRow+i][startCol+j]], j*TILE-offsetX, i*TILE -offsetY,null);
				}
			}
		}
	}
	
	public void drawSelf(Canvas canvas,Bitmap[] bmp){
		int m = mapMat.length;
		int n = mapMat[0].length;
		for(int i=0;i<m;++i)
			for(int j=0;j<n;++j)
			{
				switch(mapMat[i][j]){
				case 0:
					canvas.drawBitmap(bmp[0], j * TILE, i*TILE, null);
					break;
				case 1:
					canvas.drawBitmap(bmp[1], j * TILE, i*TILE, null);
					break;
				case 2:
					canvas.drawBitmap(bmp[2], j * TILE, i*TILE, null);
					break;
				case 3:
					canvas.drawBitmap(bmp[3], j * TILE, i*TILE, null);
					break;
				}
			}
	}
}
