package com.dl.baye.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Map {

	public static final int TILE = 32;
	//地图矩阵
	private int[][] mapMat;
	//地图不可通过矩阵
	private int[][] notInMat;
		
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
