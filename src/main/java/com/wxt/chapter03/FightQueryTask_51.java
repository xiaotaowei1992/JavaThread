package com.wxt.chapter03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FightQueryTask_51 extends Thread implements FightQuery_51 {
    private final String origin;
    private final String destination;
    private final List<String> flightList = new ArrayList<String>();
    
	public FightQueryTask_51(String airLine, String origin, String destination) {
		super("[" + airLine +"]");
		this.origin = origin;
		this.destination = destination;
	}
	@Override
	public void run() {
		System.out.printf("%s-query from %s to %s \n",getName(),origin,destination);
		int randomVal = ThreadLocalRandom.current().nextInt(10);
		
		try {
			TimeUnit.SECONDS.sleep(randomVal);
			this.flightList.add(getName() + "-" + randomVal);
			System.out.printf("The fight:%s list query successful \n",getName() );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<String> get() {
		return this.flightList;
	}

}
