package com.dl.baye.util;

public class GameFormula {
	public static int getPersonAssart(Person person){
		
		return 100;
	}
	public static int getPersonAccractBusiness(Person person){
		
		return 100;
	}
	public static int getPersonSearchMoney(Person person){
		return 1000;
	}
	
	public static int getPersonFather(Person person){
		return 10;
	}
	
	public static int getPersonInspection(Person person){
		return 10;
	}
	
	public static int getPersonConscription(Person person){
		return 1000;
	}
	//int[0]:money,int[1]:food,int[3]:-devotion;
	public static int[] getPersonDesperate(Person person)
	{
		return new int[]{500,500,30};
	}
	
	public static boolean getPersonCanvass(Person person,Person personTo){
		return true;
	}
	public static boolean getPersonCounterespionage(Person person,Person personTo){
		return true;
	}
	public static boolean getPersonSurrender(Person person,Person personTo){
		return true;
	}
}
