package com.dl.baye.core;

public class Common {
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
		TEMERITY,
		DREAD,
		AVARICE,
		IDEAL,
		LOYALISM
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
	public enum ARMS_TYPE{
		
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
}
