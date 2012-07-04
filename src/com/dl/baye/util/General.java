package com.dl.baye.util;

import java.util.ArrayList;

public class General  extends Sprite{
	private Person person;
	//0：我方,1：敌方
	private int team;
	//0：未行动;1：已行动
	private int action;
	private boolean isAttack = false;
	public General(Person person,int team, int row, int col) {
		super(row, col);
		this.person = person;
		this.team = team;
	}

}
