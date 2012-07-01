package com.dl.baye.util;

import com.dl.baye.util.Constant.ARMS_TYPE;

public class Goods {
	//序号
	private int idx;
	//使用标志：是被使用还是被装备
	private int userflag;
	//道具名字
	private String name;
	//道具说明
	private String inform;
	//对武力加成
	private int addForce;
	//对智力的加成
	private int addIq;
	//对移动力的加成
	private int addMove;
	//兵种的变化
	private ARMS_TYPE arm;
}
