package com.dl.baye.util;

import java.util.ArrayList;
import com.dl.baye.BattleView;

import android.graphics.Canvas;

public class Sprite {
	public static final int tile = 32;
	ArrayList<int[]> animationSegmentList = new ArrayList<int[]>();	//动画段列表，包括向上，向下等动画段
	//当前的动画段在列表中的索引。静止：0下,1左，2右，3上，移动：4下，5左，6右，7上
	int currentSegment;							//当前动画段的索引						
	int currentFrame;								//当前动画段的动画帧索引
	int x;		//精灵在大地图的坐标
	int y;		//精灵在大地图的坐标
	int col;	//精灵在地图上的列数，通过求靠下31×31方块的中心所在的地方求出
	int row;	//精灵在地图上的行数，通过求靠下31×31方块的中心所在的地方求出
	SpriteThread st;
	public Sprite(int row,int col){
		this.row = row;
		this.col = col;
		this.x = tile * row;
		this.y = tile * col;
		st = new SpriteThread(this);
		//start
	}
	//修改当前动画段的当前动画帧
		public void nextFrame(){
				int [] currSeg = animationSegmentList.get(currentSegment);
				currentFrame = (currentFrame + 1)%currSeg.length;		
		}
		//方法：制作自己的动画段列表
		public void makeAnimation(int [][] imgId){
			for(int [] imgIDs:imgId){
				addAnimationSegment(imgIDs);
			}
		}
		//方法：向动画段列表中添加动画段
		public void addAnimationSegment(int [] ani){
			this.animationSegmentList.add(ani);		//向动画段列表中添加动画段
		}
		//方法：设置动画段
		public void setAnimationSegment(int segment){
			this.currentSegment = segment;		//设置动画段
			this.currentFrame = 0;				//从头开始循环帧
		}
		//方法：将自己在屏幕上画出
		public void drawSelf(Canvas canvas,int screenX,int screenY){
			int imgId = animationSegmentList.get(currentSegment)[currentFrame];	//获取要绘制的那个帧
			BattleView.drawGamePublic(imgId, canvas, screenX, screenY);		//调用BitmapManager的静态方法绘制图片
		}
		//方法：开启动画
		public void startAnimation(){
			st.isGameOn = true;
			if(!st.isAlive()){
				st.start();
			}
		}
		//方法：暂停动画
		public void pauseAnimation(){
			st.isGameOn = false;
		}
		//方法：销毁动画线程
		public void stopAnimation(){
			st.flag = false;
			st.isGameOn = false;
		}
}
