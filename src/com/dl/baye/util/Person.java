package com.dl.baye.util;

import java.util.ArrayList;

import com.dl.baye.util.Constant.ARMS_TYPE;
import com.dl.baye.util.Constant.CHARACTER;
//武将人才
public class Person {
	//编号
	private int id;
	//归属
	//TODO
	private int belong;
	//等级
	private int level;
	//武力
	private int force;
	//智力
	private int iq;
	//忠诚
	private int devotion;
	//性格
	private CHARACTER character;
	//经验
	private int experience;
	//体力
	private int thew;
	//兵种
	private ARMS_TYPE armsType;
	//兵力
	private int armsNum;
	//装备
	private ArrayList<Goods> Euips;
	//年龄
	private int age;
	//名称
	private String name;
	//出生年
	private int birth;
	//搜索者编号
	private int searcherId;
	//出现城市编号
	private int appearCityId;
}