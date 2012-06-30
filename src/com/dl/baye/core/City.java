package com.dl.baye.core;

import java.util.ArrayList;

//城市属性
public class City {
	//编号
	private int id;
	//归属
	//TODO
	private int belong;
	//太守编号
	private int satrapId;
	//农业上限
	private int farmingLimit;
	//农业开发度
	private int farming;
	//商业上限
	private int commerceLimit;
	//商业开发度
	private int commerce;
	//民心忠诚
	private int peopleDevotion;
	//防灾
	private int avoidCalamity;
	//人口上限
	private int populationLimit;
	//人口
	private int population;
	//金钱
	private int money;
	//粮食
	private int food;
	//后备兵力
	private int mothballArmsNum;
	//人才队列
	private ArrayList<Person> personQueue;
	//人才数
	private int personsNum;
	//道具队列
	private ArrayList<GOODS> toolQueue;
	//道具数
	private int toolsNum;
	//名称
	private String name;
	//城市连接（北、东北、东、东南、南、西南、西、西北)
	private ArrayList<Integer> links = new ArrayList<Integer>(8);
	//城市间距离
	private ArrayList<Integer> distances = new ArrayList<Integer>(8);
}
