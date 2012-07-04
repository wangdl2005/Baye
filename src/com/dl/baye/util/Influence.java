package com.dl.baye.util;

import java.util.ArrayList;

public class Influence {
		//归属君主ID
		private String belong;
		public String getBelong() {
			return belong;
		}

		public int getFarming() {
			return farming;
		}

		public int getCommerce() {
			return commerce;
		}

		public int getPopulation() {
			return population;
		}

		public int getMoney() {
			return money;
		}

		public int getFood() {
			return food;
		}

		public int getMothballArmsNum() {
			return mothballArmsNum;
		}

		public int getPersonsNum() {
			return personsNum;
		}

		public int getToolsNum() {
			return toolsNum;
		}
		public int getCityNum(){
			return cityNum;
		}
		
		private int cityNum;
		
		//农业开发度
		private int farming;
		//商业开发度
		private int commerce;
		//人口
		private int population;
		//金钱
		private int money;
		//粮食
		private int food;
		//后备兵力
		private int mothballArmsNum;
		//人才数
		private int personsNum;
		//道具数
		private int toolsNum;
		
		public Influence(String belong,int cityNum,int totalFarming,int totalCommerce,int totalPopulatin
				,int totalMoney,int totalFood,int totalArmsNum,int totalPersonNum,int totalToolsNum)
		{
			this.cityNum = cityNum;
			this.belong = belong;
			this.farming = totalFarming;
			this.commerce = totalCommerce;
			this.population = totalPopulatin;
			this.money = totalMoney;
			this.food = totalFood;
			this.mothballArmsNum = totalArmsNum;
			this.personsNum = totalPersonNum;
			this.toolsNum = totalToolsNum;
		}
}
