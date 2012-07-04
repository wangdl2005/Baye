package com.dl.baye.util;

import com.dl.baye.util.Constant.ARMS_TYPE;
import static com.dl.baye.util.Constant.*;

public class Goods {
	//序号
	private int idx;
	public int getIdx() {
		return idx;
	}

	public int getUserflag() {
		return userflag;
	}

	public String getInform() {
		return inform;
	}

	public int getAddForce() {
		return addForce;
	}

	public int getAddIq() {
		return addIq;
	}

	public int getAddMove() {
		return addMove;
	}

	public ARMS_TYPE getArm() {
		return arm;
	}

	//使用标志：是被使用还是被装备
	private int userflag;
	//道具名字
	private String name;
	public String getName() {
		return name;
	}

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
	
	public Goods(int idx,int uerflag,String name,String inform,int addForce,int addIq,int addMove,int arm){
		this.idx = idx;
		this.userflag = uerflag;
		this.name = name;
		this.inform = inform;
		this.addForce = addForce;
		this.addIq = addIq;
		this.addMove = addMove;
		this.arm = toArmsType(arm);
	}
}
