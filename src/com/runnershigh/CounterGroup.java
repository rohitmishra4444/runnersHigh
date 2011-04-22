package com.runnershigh;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class CounterGroup extends CounterDigit{
	private long LastFrameChangeTime;
	private int FrameUpdateTime;
	private int lastCounterValueOnes;
	private int lastCounterValueTens;
	private int lastCounterValueHundreds;
	private int lastCounterValueThousands;
	
	
	public CounterGroup(int _x, int _y, int _z, int _width, int _height, int _FrameUpdateTime){
		super((int)_x, (int)_y, (int)_z, (int)_width, (int)_height);
		x=_x;
		y=_y;
		width=_width;
		height=_height;
		FrameUpdateTime = _FrameUpdateTime;
		LastFrameChangeTime = System.currentTimeMillis();
	}
	private final Vector<CounterDigit> mChildren = new Vector<CounterDigit>();

	@Override
	public void draw(GL10 gl) {
		int size = mChildren.size();
		for (int i = 0; i < size; i++)
			mChildren.get(i).draw(gl);
	}

	/**
	 * @param location
	 * @param object
	 * @see java.util.Vector#add(int, java.lang.Object)
	 */
	public void add(int location, CounterDigit object) {
		mChildren.add(location, object);
	}

	/**
	 * @param object
	 * @return
	 * @see java.util.Vector#add(java.lang.Object)
	 */
	public boolean add(CounterDigit object) {
		return mChildren.add(object);
	}

	/**
	 * 
	 * @see java.util.Vector#clear()
	 */
	public void clear() {
		mChildren.clear();
	}

	/**
	 * @param location
	 * @return
	 * @see java.util.Vector#get(int)
	 */
	public Mesh get(int location) {
		return mChildren.get(location);
	}

	/**
	 * @param location
	 * @return
	 * @see java.util.Vector#remove(int)
	 */
	public Mesh remove(int location) {
		return mChildren.remove(location);
	}

	/**
	 * @param object
	 * @return
	 * @see java.util.Vector#remove(java.lang.Object)
	 */
	public boolean remove(Object object) {
		return mChildren.remove(object);
	}

	/**
	 * @return
	 * @see java.util.Vector#size()
	 */
	public int size() {
		return mChildren.size();
	}
	
	public void resetCounter(){
		int size = mChildren.size();
		for (int i = 0; i < size; i++)
			mChildren.get(i).setDigitToZero();
	}
	public void tryoToSetCounterTo(int counterValue) {
		if(  System.currentTimeMillis() > (LastFrameChangeTime+FrameUpdateTime) ){
			LastFrameChangeTime=System.currentTimeMillis();
			
			int counterValueOnes = counterValue % 10; 
			if(lastCounterValueOnes != counterValueOnes)
				mChildren.get(3).setDigitTo(counterValueOnes);
			
			int counterValueTens = (counterValue % 100) / 10 ;
			if(lastCounterValueTens != counterValueTens)
				mChildren.get(2).setDigitTo(counterValueTens);
			
			int counterValueHundreds = (counterValue % 1000 ) / 100;
			if(lastCounterValueHundreds != counterValueHundreds)
				mChildren.get(1).setDigitTo(counterValueHundreds);
			
			int counterValueThousands = counterValue / 1000;
			if(lastCounterValueThousands != counterValueThousands)
				mChildren.get(0).setDigitTo(counterValueThousands);
		}
	}
	
}
