package com.dl.baye.util;

public class Order {
	//命令类型编号
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public int getTimeCount() {
		return timeCount;
	}
	public void setTimeCount(int timeCount) {
		this.timeCount = timeCount;
	}
	//执行武将
	private Person person;
	//所在城市
	private City city;
	public City getCityTo() {
		return cityTo;
	}
	public void setCityTo(City cityTo) {
		this.cityTo = cityTo;
	}
	//
	private Person personTo;
	public Person getPersonTo() {
		return personTo;
	}
	public void setPersonTo(Person personTo) {
		this.personTo = personTo;
	}
	//目标
	private City cityTo;
	//士兵数量
	private int armsNum;
	//粮食数量
	private int foodNum;
	//金钱数量
	private int moneyNum;
	//消耗时间
	private int consumTime;
	//执行累时
	private int timeCount;
}
