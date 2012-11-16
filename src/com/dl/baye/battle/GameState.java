package com.dl.baye.battle;


import android.graphics.Canvas;

interface GameState{
	boolean KeyDown(int keyCode);
	void Draw(Canvas canvas, int startCol, int startRow);
}