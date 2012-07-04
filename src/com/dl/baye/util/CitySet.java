package com.dl.baye.util;

import static com.dl.baye.util.Constant.*;

public class CitySet {
	public int getMapX() {
		return mapX;
	}
	public int getMapY() {
		return mapY;
	}
	public int getSelX() {
		return selX;
	}
	public int getSelY() {
		return selY;
	}
	int mapX;
	int mapY;
	//
	int selX;
	int selY;
	//城市ID
	int id;
	
	public boolean compareTo(CitySet citySet){
//		boolean flag = true;
//		if(this.selX != citySet.selX || this.selY != citySet.selY){
//			flag = false;
//		}		
//		return flag;
		return (this.selX == citySet.selX && this.selY == citySet.selY);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CitySet(int selX,int selY){
		this.selX = selX;
		this.selY = selY;
		this.mapX = selX - mapStartX;
		this.mapY = selY - mapStartY;
	}
}
