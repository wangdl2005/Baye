package com.dl.baye.util;

import java.util.ArrayList;
import static com.dl.baye.util.Constant.*;
import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class Menu {	
	// 是否可见
	 public boolean visible;

	// 菜单纵幅
	 int height;

	// 菜单横幅
	 int width;

	// 光标位置
	 public int cur=0;

	// 菜单类型
	 int menuType;

	// 菜单选项
	 ArrayList<String> menuItem;
	 
	 int size=0;

	public Menu(int size) {
		this.menuItem = new ArrayList<String>(10);
		this.setVisible(false);
		this.setMenuType(0);
		this.setCur(0);
		this.size=size;
	}

	public void free() {
		this.menuItem.clear();
	}

	public int getCur() {
		return cur;
	}

	/**
	 * 设定光标
	 * @param cur
	 */
	public void setCur(int cur) {
		if (cur < 0) {
			cur = this.height - 1;
		}
		if (cur > this.height - 1) {
			cur = 0;
		}
		this.cur = cur;
	}

	/**
	 * 获得指定索引位置菜单
	 * 
	 * @param index
	 * @return
	 */
	public String getMenuItem(int index) {
		return (String) menuItem.get(index);

	}

	/**
	 * 设定菜单
	 *
	 */
	public void setMenuItem() {
		switch (this.menuType) {
		case 0:
			this.width = 3;
			this.height = 1;
			menuItem.add("结束");
			break;
		case 1:
			this.width = 3;
			this.height = 1;
			menuItem.add("待机");
			break;
		case 2:
			this.width = 3;
			this.height = 2;
			menuItem.add("攻击");
			menuItem.add("待机");
			break;
		}
	}
	
	public void drawSelf(Canvas canvas,Bitmap[] listBmp,Bitmap[] iconBmp,int col,int row,int offsetX,int offsetY){
		int screenX = (col-1)*TILE -offsetX;
		int screenY = (row-1)*TILE-offsetY;
		Paint paint = new Paint();
		//半透明
		paint.setAlpha(128);
		//第一行
		canvas.drawBitmap(listBmp[0], screenX,screenY, paint);
		for(int i=0;i<this.width;++i){
			canvas.drawBitmap(listBmp[1], screenX + TILE*(i+1),screenY, paint);
		}
		canvas.drawBitmap(listBmp[2], screenX +TILE *(this.width+1),screenY, paint);
		//第一列、最后一列
		for(int j=1;j<=this.height;++j){
			canvas.drawBitmap(listBmp[3], screenX,screenY+TILE*j, paint);
		}
		for(int j=1;j<=this.height;++j){
			canvas.drawBitmap(listBmp[5], screenX+TILE *(this.width+1),screenY+TILE*j, paint);
		}
		//中间空白
		for(int i=1;i<=this.width;++i)
			for(int j=1;j<=this.height;++j)
			{
				canvas.drawBitmap(listBmp[4], screenX+TILE *i,screenY+TILE*j, paint);
			}
		//最后一行
		canvas.drawBitmap(listBmp[6], screenX,screenY + TILE * (this.height+1), paint);
		for(int i=0;i<this.width;++i){
			canvas.drawBitmap(listBmp[7], screenX + TILE*(i+1),screenY+ TILE * (this.height+1), paint);
		}
		canvas.drawBitmap(listBmp[8], screenX +TILE *(this.width+1),screenY+ TILE * (this.height+1), paint);
		//draw cur
		canvas.drawBitmap(iconBmp[1], screenX + TILE,screenY + (this.cur+1)*TILE , paint);
		//drawText
		paint.setColor(Color. WHITE);
		paint.setAntiAlias(true);						//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//设置字体
		paint.setTextSize(18);//设置字号
		for(int i=0;i<this.height;++i){
			canvas.drawText(menuItem.get(i), screenX + 2*TILE, screenY + TILE *(i+1)+24, paint);
		}
	}
	
	public void onTouch(MotionEvent event){
		
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.free();
		this.menuType = menuType;
		this.setMenuItem();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
