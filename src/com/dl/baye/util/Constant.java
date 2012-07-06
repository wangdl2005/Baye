package com.dl.baye.util;

public class Constant {
	 
	public static final String TAG = "SanGuoBaYe";

	//与缩略地图相关的常量
	public static final int MINI_MAP_TILE_SIZE = 8;		//缩略地图的每块大小
	public static final int MINI_MAP_START_X = 600;		//缩略地图的开始x坐标
	public static final int MINI_MAP_START_Y = 0;		//缩略地图的开始y坐标
	//与地图相关的常量
	public static final int TILE = 32;					//地图图元的大小
	public static final int MAP_ROWS = 25;		//地图行数
	public static final int MAP_COLS = 15;		//地图列数
	public static final int SCREEN_ROWS = 16;	//屏幕能显示的行数
	public static final int SCREEN_COLS = 11;	//屏幕能显示的列数
	public static final int SCREEN_WIDTH = 800;	//屏幕宽度
	public static final int SCREEN_HEIGHT = 442;	//屏幕高度
	public static final int SPACE_FOR_ROLL = 124;	//英雄与边界的距离小于该值进行滚屏
	public static final float AREA_PERCENT = 0.6f;		//重叠面积比例超过该值则判定为发生碰撞
	public static final int SPAN_TO_ROLL = 8;		//每次滚屏像素数
	/**
	 * MapView 使用常量
	 */

	public static final int mapStartX = 10;
	public static final int mapStartY = 20;
	
	/**
	 * MenuView界面中用到的常量
	 */
	public static final int MENU_VIEW_SLEEP_SPAN = 200;//MenuView界面刷帧线程睡眠时间
	public static final int MENU_VIEW_WORD_SPACE = 15;//MenuView界面菜单之间的上下间距
	public static final int MENU_VIEW_UP_SPACE = 110;//MenuView界面菜单距上边沿的距离
	public static final int MENU_VIEW_LEFT_SPACE = 110;//MenuView界面菜单距左边沿的距离
	public static final int PICTURECOUNT = 18;//滚动背景切割数 
	public static final int PICTUREWIDTH = 50;
	public static final int PICTUREHEIGHT = 480;
	
	/**
	 * LoadingView界面中用到的常量
	 */
	public static final int LOADING_VIEW_WORD_SIZE = 12;//进度条界面中“加载中”三个字的大小
	public static final int LOADING_VIEW_START_X = 20;//进度条界面中进度条的x坐标
	public static final int LOADING_VIEW_START_Y = 230;//进度条界面中进度条的y坐标
	public static final int LOADING_VIEW_SLEEP_SPAN = 400;//进度条界面刷帧线程睡眠时间
	/**
	 * GameView界面中用到的常量
	 */
	public static final int GAME_VIEW_SLEEP_SPAN = 100;//GameView界面刷帧线程睡眠时间
	public static final int DIALOG_START_X = 240;
	public static final int DIALOG_START_Y = 300;//游戏中对话框的绘制y坐标，x坐标为零
	//对话框中的文字大小如果为16，那么开始x坐标为24，1行的汉字数为17
	//对话框中的文字大小如果为18，那么开始x坐标为24 ，1行的汉字数为15
	public static final int DIALOG_WORD_SIZE = 16;//对话框中文字的大小
	public static final int DIALOG_WORD_START_X = 270;//对话框中文字开始的x坐标
	public static final int DIALOG_WORD_START_Y = 350;//对话框文字开始的y坐标
	public static final int DIALOG_WORD_EACH_LINE = 16;//对话框每行的文字个数
	public static final int DIALOG_BTN_START_X = 370;//对话框中按钮的开始x坐标
	public static final int DIALOG_BTN_START_Y = 360;//对话框中按钮的开始y坐标
	public static final int DIALOG_BTN_WIDTH = 60;//对话框中按钮的宽度
	public static final int DIALOG_BTN_HEIGHT = 30;//对话框中按钮的高度
	public static final int DIALOG_BTN_SPAN = 160;//对话框中两个按钮的x方向上的间距
	public static final int DIALOG_BTN_WORD_LEFT = 12;//对话框中按钮上文字距按钮左边的距离
	public static final int DIALOG_BTN_WORD_UP = 6;//对话框中按钮上文字距按钮上边沿的距离
	public static final int GAME_VIEW_MEMU_WORD_SPACE = 10;//GameView界面中出现的主菜单之间的上下间距
	public static final int GAME_VIEW_MEMU_LEFT_SPACE = 100;//GameView界面中出现的主菜单距左边距的距离
	public static final int GAME_VIEW_MEMU_UP_SPACE = 90;//GameView界面中出现的主菜单距上边沿的距离	
	public static final int GAME_VIEW_SCREEN_ROWS= 14;//GameView总共的行数
	public static final int GAME_VIEW_SCREEN_COLS = 11;//GameView总共的列数
	public static final int MIN_FOOD = 2000;//城池小于该值则报警
	public static final int HERO_FACE_START_Y = 365;//英雄头像起始的y坐标，x坐标为零。英雄的头像可以被点击从而进行掷骰子
	public static final int HERO_FACE_WIDTH = 60;//英雄头像的宽度
	public static final int HERO_FACE_HEIGHT = 60;//英雄头像的高度
	public static final int DASHBOARD_START_Y = 365;
	public static final int DICE_SIZE = 25;
	public static final int ROLL_SCREEN_SPACE_RIGHT =149;//英雄距屏幕右边界149个像素时就应该滚屏了320-(31*5+16)
	public static final int ROLL_SCREEN_SPACE_DOWN = 216;//英雄距屏幕下边界216个像素时就应该滚屏了480-(31*8+16)
	public static final int ROLL_SCREEN_SPACE_LEFT =140;//英雄距屏幕右边界140个像素时就应该滚屏了31*4+16
	public static final int ROLL_SCREEN_SPACE_UP = 140;//英雄距屏幕下边界140个像素时就应该滚屏了31*4+16
	
	public static final int STRENGTH_COST_DECREMENT = 2;//每次技能升级减小的体力消耗值
	//最大君主数
	public static final int KING_MAX = 100;
	//最大武将数
	public static final int PERSON_MAX = 200;
	//最大城市数
	public static final int CITY_MAX = 38;
	//最大道具数
	public static final int GOODS_MAX = 33;
	//武将寿命
	public static final int PERSON_DEATH_AGE = 90;
	//武将出现年龄
	public static final int PERSON_APPEAR_AGE = 16;
	//每月恢复体力
	public static final int THEW_RENEW = 4;
	//宴请恢复体力
	public static final int THEW_TREAT = 50;
	//最大命令数
	public static final int ORDER_MAX = 100;
	//最大出征命令数
	public static final int FIGHT_ORDER_MAX = 30;
	
	//方向：北、东北、东、东南、南、西南、西、西北
	public enum DIRECTION{
		N,
		EN,
		E,
		ES,
		S,
		WS,
		W,
		WN
	}
	//城市状态:正常、饥荒、旱灾、水灾、暴动
	public enum STATE{
		NORMAL,
		FAMINE,
		DROUGHT,
		FLOOD,
		REBELLION
	}
	
	//性格:鲁莽、怕死、贪财、大志、忠义
	public enum CHARACTER{
		TEMERITY(0),
		DREAD(1),
		AVARICE(2),
		IDEAL(3),
		LOYALISM(4);
		private int value;
		CHARACTER(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	//性格离间几率:鲁莽、怕死、贪财、大志、忠义
	public enum CHARACTER_ALIENATE{
		TEMERITY(50),
		DREAD(30),
		AVARICE(40),
		IDEAL(30),
		LOYALISM(5);
		private int value;
		CHARACTER_ALIENATE(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	//性格招揽几率:鲁莽、怕死、贪财、大志、忠义
	public enum CHARACTER_CANVASS{
		TEMERITY(15),
		DREAD(40),
		AVARICE(30),
		IDEAL(20),
		LOYALISM(5);
		private int value;
		CHARACTER_CANVASS(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	//性格招揽策反:鲁莽、怕死、贪财、大志、忠义
	public enum CHARACTER_COUNTERESPIONGE{
		TEMERITY(30),
		DREAD(10),
		AVARICE(20),
		IDEAL(60),
		LOYALISM(5);
		private int value;
		CHARACTER_COUNTERESPIONGE(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	//性格招降几率:鲁莽、怕死、贪财、大志、忠义
	public enum CHARACTER_SURRENDER{
		TEMERITY(15),
		DREAD(60),
		AVARICE(30),
		IDEAL(20),
		LOYALISM(5);
		private int value;
		CHARACTER_SURRENDER(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	//性格招降几率系数:鲁莽、怕死、贪财、大志、忠义
	public enum CHARACTER_SURRENDER_MOD{
		TEMERITY(2),
		DREAD(5),
		AVARICE(4),
		IDEAL(3),
		LOYALISM(1);
		private int value;
		CHARACTER_SURRENDER_MOD(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	//君主性格:冒进、狂人、奸诈、大义、和平
	public enum LORD_CHARACTER{
		RASH,
		CRAZY,
		DUPLICITY,
		JUSTICE,
		PEACE
	}
	//君主性格劝降几率：冒进、狂人、奸诈、大义、和平
	public enum LORD_PERSUADE_RATE{
		RASH(10),
		CRAZY(1),
		DUPLICITY(20),
		JUSTICE(5),
		PEACE(15);
		private int value;
		LORD_PERSUADE_RATE(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	// 兵种
//	00 骑兵 
//	01 步兵 
//	02 弓兵 
//	03 水兵 
//	04 极兵 
//	05 玄兵 
	public enum ARMS_TYPE{
		QiBing(0),
		BuBing(1),
		GongBing(2),
		ShuiBing(3),
		JiBing(4),
		XuanBing(5);
		private int value;
		private ARMS_TYPE(int val){
			this.value = val;
		}
		public int getVale(){
			return value;
		}
	}
	
	//命令:内政、外交、军备、
	public enum ORDER{
		INTERIOR,
		DIPLOMATISM,
		ARMAMENT
	}
	
	public enum ORDER_INTERIOR{
		//任命 TODO
		//APPOINT
		//开垦
		ASSART,
		//招商
		ACCRACTBUSINESS,
		//搜寻
		SEARCH,
		//治理
		FATHER,
		//出巡
		INSPECTION,
		//招降
		SURRENDER,
		//---处斩
		KILL,
		//---流放
		BANISH,
		//---赏赐
		LARGESS,
		//---没收
		CONFISCATE,
		//交易
		EXCHANGE,
		//---宴请
		TREAT,
		//---输送
		TRANSPORTATION,
		//---移动
		MOVE
	}
	public enum THEW_ORDER_INTERIOR{
		//任命 TODO
		//APPOINT(4),
		//开垦
		ASSART(4),
		//招商
		ACCRACTBUSINESS(4),
		//搜寻
		SEARCH(4),
		//治理
		FATHER(4),
		//出巡
		INSPECTION(4),
		//招降
		SURRENDER(4),
		//---处斩
		KILL(4),
		//---流放
		BANISH(4),
		//---赏赐
		LARGESS(4),
		//---没收
		CONFISCATE(4),
		//交易
		EXCHANGE(4),
		//---宴请
		TREAT(4),
		//---输送
		TRANSPORTATION(4),
		//---移动
		MOVE(4);
		private int value;
		private THEW_ORDER_INTERIOR(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	public enum ORDER_DIPLOMATISM{
		//离间
		ALIENATE,
		//招揽
		CANVASS,
		//策反
		COUNTERESPIONAGE,
		//反间
		REALIENATE,
		//劝降
		INDUCE;
		//朝贡
		//TRIBUTE TODO
	}
	
	public enum THEW_ORDER_DIPLOMATISM{
		//离间
		ALIENATE(4),
		//招揽
		CANVASS(4),
		//策反
		COUNTERESPIONAGE(4),
		//反间
		REALIENATE(4),
		//劝降
		INDUCE(4);
		//朝贡
		//TRIBUTE TODO

		private int value;
		private THEW_ORDER_DIPLOMATISM(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	public enum ORDER_ARMAMENT{
		//侦察
		RECONNOITRE,
		//征兵
		CONSCRIPTION,
		//分配
		DISTRIBUTE,
		//掠夺
		DEPREDATE,
		//出征
		BATTLE
	}
	
	public enum THEW_ORDER_ARMAMENT{
		//侦察
		RECONNOITRE(4),
		//征兵
		CONSCRIPTION(4),
		//分配
		DISTRIBUTE(4),
		//掠夺
		DEPREDATE(4),
		//出征
		BATTLE(4);
		
		private int value;
		private THEW_ORDER_ARMAMENT(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	}
	
	public static CHARACTER toCharacter(int val){
		CHARACTER chara =null;
		switch(val){
		case 0:
			chara = CHARACTER.TEMERITY;
			break;
		case 1 :
			chara = CHARACTER.DREAD;
			break;
		case 2:
			chara = CHARACTER.AVARICE;
			break;
		case 3 :
			chara = CHARACTER.IDEAL;
			break;
		case 4:
			chara = CHARACTER.LOYALISM;
			break;
		}
		return chara;
	}
	public static ARMS_TYPE toArmsType(int val){
		ARMS_TYPE chara =null;
		switch(val){
		case 0:
			chara = ARMS_TYPE.QiBing;
			break;
		case 1 :
			chara = ARMS_TYPE.BuBing;
			break;
		case 2:
			chara = ARMS_TYPE.GongBing;
			break;
		case 3 :
			chara = ARMS_TYPE.ShuiBing;
			break;
		case 4:
			chara = ARMS_TYPE.JiBing;
			break;
		case 5:
			chara = ARMS_TYPE.XuanBing;
			break;
		}
		return chara;
	}
	
	public static String formatArmsType(ARMS_TYPE armsType){
		String str = null;
		switch(armsType){
		case BuBing:
			str = "步兵";
			break;
		case JiBing:
			str = "极兵";
			break;
		case GongBing:
			str = "弓兵";
			break;
		case QiBing:
			str = "骑兵";
			break;
		case ShuiBing:
			str = "水兵";
			break;
		case XuanBing:
			str = "玄兵";
			break;
		}
		return str;
	}
	//鲁莽、怕死、贪财、大志、忠义
	public static String formatCharacter(CHARACTER chara){
		String str= null;
		switch(chara){
		case AVARICE:
			str = "贪财";
			break;
		case DREAD:
			str = "怕死";
			break;
		case IDEAL:
			str = "大志";
			break;
		case LOYALISM:
			str = "忠义";
			break;
		case TEMERITY:
			str = "鲁莽";
			break;
		}
		return str;
	}
}
