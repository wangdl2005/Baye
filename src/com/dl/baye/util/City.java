package com.dl.baye.util;

import java.util.ArrayList;

//城市属性
public class City {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBelong() {
		return belong;
	}

	public void setBelong(int belong) {
		this.belong = belong;
	}

	public int getSatrapId() {
		return satrapId;
	}

	public void setSatrapId(int satrapId) {
		this.satrapId = satrapId;
	}

	public int getFarmingLimit() {
		return farmingLimit;
	}

	public void setFarmingLimit(int farmingLimit) {
		this.farmingLimit = farmingLimit;
	}

	public int getFarming() {
		return farming;
	}

	public void setFarming(int farming) {
		this.farming = farming;
	}

	public int getCommerceLimit() {
		return commerceLimit;
	}

	public void setCommerceLimit(int commerceLimit) {
		this.commerceLimit = commerceLimit;
	}

	public int getCommerce() {
		return commerce;
	}

	public void setCommerce(int commerce) {
		this.commerce = commerce;
	}

	public int getPeopleDevotion() {
		return peopleDevotion;
	}

	public void setPeopleDevotion(int peopleDevotion) {
		this.peopleDevotion = peopleDevotion;
	}

	public int getAvoidCalamity() {
		return avoidCalamity;
	}

	public void setAvoidCalamity(int avoidCalamity) {
		this.avoidCalamity = avoidCalamity;
	}

	public int getPopulationLimit() {
		return populationLimit;
	}

	public void setPopulationLimit(int populationLimit) {
		this.populationLimit = populationLimit;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMothballArmsNum() {
		return mothballArmsNum;
	}

	public void setMothballArmsNum(int mothballArmsNum) {
		this.mothballArmsNum = mothballArmsNum;
	}

	public ArrayList<Person> getPersonQueue() {
		return personQueue;
	}

	public void setPersonQueue(ArrayList<Person> personQueue) {
		this.personQueue = personQueue;
	}

	public int getPersonsNum() {
		return personQueue.size();
	}

	public void setPersonsNum(int personsNum) {
		this.personsNum = personsNum;
	}

	public ArrayList<Goods> getToolQueue() {
		return toolQueue;
	}

	public void setToolQueue(ArrayList<Goods> toolQueue) {
		this.toolQueue = toolQueue;
	}

	public int getToolsNum() {
		return toolQueue.size();
	}

	public void setToolsNum(int toolsNum) {
		this.toolsNum = toolsNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Integer> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Integer> links) {
		this.links = links;
	}

	public ArrayList<Integer> getDistances() {
		return distances;
	}

	public void setDistances(ArrayList<Integer> distances) {
		this.distances = distances;
	}

	//编号
	private int id;
	//归属君主ID
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
	private ArrayList<Goods> toolQueue;
	//道具数
	private int toolsNum;
	//名称
	private String name;
	//城市连接（北、东北、东、东南、南、西南、西、西北)
	private ArrayList<Integer> links = new ArrayList<Integer>(8);
	//城市间距离
	private ArrayList<Integer> distances = new ArrayList<Integer>(8);
	
	public City(int id,int belong,int satrapId,
			int farmingLimit,int farming,int commerceLimit,int commerce,
			int peopleDevotion,int avoidCalamity,int populationLimit,int population,
			int money,int food,int mothballArmsNum, ArrayList<Person> personQueue,int personsNum,
			ArrayList<Goods> toolQueue,int toolsNum,String name,ArrayList<Integer> links,ArrayList<Integer> distances){
			this.id = id;
			this.belong =belong;
			this.satrapId = satrapId;
			this.farming =farming;
			this.farmingLimit = farmingLimit;
			this.commerce = commerce;
			this.commerceLimit = commerceLimit;
			this.peopleDevotion = peopleDevotion;
			this.avoidCalamity = avoidCalamity;
			this.population = population;
			this.populationLimit = populationLimit;
			this.money = money;
			this.food = food;
			this.mothballArmsNum = mothballArmsNum;
			this.personQueue = personQueue;
			this.personsNum = personsNum;
			this.toolQueue = toolQueue;
			this.toolsNum = toolsNum;
			this.name = name;
			this.links = links;
			this.distances =distances;
	}
}
