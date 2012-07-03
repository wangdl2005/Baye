package com.dl.baye.util;

import java.util.ArrayList;

import static com.dl.baye.util.Constant.*;
import com.dl.baye.util.Constant.ARMS_TYPE;
import com.dl.baye.util.Constant.CHARACTER;
//武将人才
public class Person {
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public int getIq() {
		return iq;
	}

	public void setIq(int iq) {
		this.iq = iq;
	}

	public int getDevotion() {
		return devotion;
	}

	public void setDevotion(int devotion) {
		this.devotion = devotion;
	}

	public CHARACTER getCharacter() {
		return character;
	}

	public void setCharacter(CHARACTER character) {
		this.character = character;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getThew() {
		return thew;
	}

	public void setThew(int thew) {
		this.thew = thew;
	}

	public ARMS_TYPE getArmsType() {
		return armsType;
	}

	public void setArmsType(ARMS_TYPE armsType) {
		this.armsType = armsType;
	}

	public int getArmsNum() {
		return armsNum;
	}

	public void setArmsNum(int armsNum) {
		this.armsNum = armsNum;
	}

	public int[] getEuips() {
		return Euips;
	}

	public void setEuips(int[] euips) {
		Euips = euips;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

	public int getSearcherId() {
		return searcherId;
	}

	public void setSearcherId(int searcherId) {
		this.searcherId = searcherId;
	}

	public int getAppearCityId() {
		return appearCityId;
	}

	public void setAppearCityId(int appearCityId) {
		this.appearCityId = appearCityId;
	}

	//编号
	private int id;
	//归属
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
	private int[] Euips = new int[2];
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
	
	public Person(int id,int belong,int level,int force,int iq,int devotion,int iCharacter
			,int exper,int thew,int armType,int armsNum,int Euips,int age//,...
			){
		this.id = id;
		this.belong = belong-1;
		this.level = level;
		this.force = force;
		this.iq = iq;
		this.devotion = devotion;
		this.character =toCharacter(iCharacter);
		this.experience = exper;
		this.thew = thew;
		this.armsType = toArmsType(armType);
		this.armsNum = armsNum;
		this.Euips[0] = (Euips & 0xff00) >> 8;
		this.Euips[1]= Euips & 0x00ff;		
		this.age = age;
	}
	
	public Person(int id,int belong,int level,int force,int iq,int devotion,int iCharacter
			,int exper,int thew,int armType,int armsNum,int Euips,int age,String name,int birth,int searchId,int appearId
			){
//		this.id = id;
//		this.level = level;
//		this.force = force;
//		this.iq = iq;
//		this.devotion = devotion;
//		this.character =toCharacter(iCharacter);
//		this.experience = exper;
//		this.thew = thew;
//		this.armsType = toArmsType(armType);
//		this.armsNum = armsNum;
//		this.Euips[0] = (Euips & 0xff00) >> 8;
//		this.Euips[1]= Euips & 0x00ff;		
//		this.age = age;
		this(id,belong,level,force,iq,devotion,iCharacter
				,exper,thew,armType,armsNum,Euips,age);
		this.name = name;
		this.birth = birth;
		this.searcherId = searchId;
		this.appearCityId = appearId;
	}
}