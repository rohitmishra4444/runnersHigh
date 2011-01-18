package com.runnershigh;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


public class Level {
	
	private int width;
	private int height;
	private int levelPosition;
	private int scoreCounter;
	private boolean play3k;
	private boolean threeKwasplayed;
	private float speed;
	private Vector<Rect> blockData;
	
	public Level(Context context, int width, int heigth) {
		this.width = width;
		this.height = heigth;
		this.levelPosition = 0;
		this.scoreCounter = 0;
		this.speed = 0;
		play3k = false;
		threeKwasplayed = false;
		
		
		blockData = new Vector<Rect>();
		
		generateAndAddBlock();
	}
	
	public void update() {
		
		synchronized (blockData) {
			//Log.d("debug", "in update");
			if (blockData.size() == 0)
			{
				levelPosition = 0;
				generateAndAddBlock();
			}
			//Log.d("debug", "in update after == 0");
			
			if (levelPosition > blockData.get(0).right) {
				blockData.remove(0);	
			}
			//Log.d("debug", "in update after > right; blockData.size() -> " + Integer.toString(blockData.size()) );
			
			if (blockData.get(blockData.size() -1).left < levelPosition + width)
				generateAndAddBlock();
			
			//Log.d("debug", "in update after < levelPosition + width");
			
			if(speed<5)
				speed+=0.001;
			levelPosition += 5 + speed;
			scoreCounter += 1;
			
			if(scoreCounter>=3000 && threeKwasplayed==false){
				threeKwasplayed=true;
				SoundManager.playSound(2, 1);
			}
			//Log.d("debug", "in update after value mod");
		}	
	}
	
	public void draw(Canvas canvas) {
		synchronized (blockData) {
			//Log.d("debug", "in draw");
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.FILL);
			canvas.save();
			
			canvas.translate(-levelPosition, 0);
			canvas.scale(1, -1);
			//canvas.translate(0, height);
			canvas.translate(0, -height+1);
			
		
			for (Rect block : blockData) {				
				canvas.drawRect(block, paint);
			}
			canvas.restore();
			/*canvas.translate(0, height-1);
			canvas.scale(1, -1);
			canvas.translate(levelPosition, 0);*/
		}
	}
	
	private void generateAndAddBlock() {
		//Log.d("debug", "in generate");
		if (blockData.size() == 0) {
			Rect newRect = new Rect(0, 50, width, 0);
			blockData.add(newRect);
		} else {
			int newHeight;
			if (blockData.get(blockData.size() -1 ).top > height/2)
				newHeight = (int)(Math.random()*height/3*2 + height/8);
			else
				newHeight = (int)(Math.random()*height/2 + height/8);
			
			int newWidth = (int)(Math.random()*width/2+width/2);
			int distance = (int)(Math.random()*width/4+width/8);
			Rect lastRect = blockData.get(blockData.size() - 1); 
			int newLeft = lastRect.right + distance;
			int newRight = newLeft + newWidth;
			Rect newRect = new Rect(newLeft, newHeight, newRight, 0);
			blockData.add(newRect);
		}
	}
	
	public Vector<Rect> getBlockData() {
		
		synchronized (blockData) {
			//Log.d("debug", "in getBlockData");
			Vector<Rect> modifiedBlockData = new Vector<Rect>();
			
			for (Rect block : blockData) {				
				Rect current = new Rect(block);
				
				current.left-= levelPosition;
				current.right -= levelPosition;
				
				modifiedBlockData.add(current);
			}
			
			return modifiedBlockData;
		}
		
	}

	public int getScoreCounter() {
		return scoreCounter;
	}
	
	public void reset() {
		scoreCounter=0;
		synchronized (blockData) {
			//Log.d("debug", "in reset");
			levelPosition = 0;
			blockData.clear();
			this.speed = 0;
			generateAndAddBlock();
		}
	}
}
