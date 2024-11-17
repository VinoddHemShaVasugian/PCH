package com.pch.kenofrontend.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class LotteryUtil {


	public static ArrayList<Integer> randomPicks(int pickNumber, int pickFrom, boolean isAscending) {
		ArrayList<Integer> pickingList = new ArrayList<Integer>();
		ArrayList<Integer> randomLottery = new ArrayList<Integer>();
		for(int i = 1; i <= pickFrom; i++)
			pickingList.add(i);
		Random ran = new Random();

		for(int i = 0; i < pickNumber; i++) {
			int x = pickingList.remove(ran.nextInt(pickingList.size()));
			randomLottery.add(x);
		}
		if(isAscending){
			Collections.sort(randomLottery);	
		}
		return randomLottery;
	}


	public static ArrayList<Integer> lastSpecificPicks(int pickNumber, int pickFrom){
		ArrayList<Integer> specificPicks = new ArrayList<Integer>();
		for(int j=0;j<pickNumber;j++){
			specificPicks.add(pickFrom-j);
		}
		Collections.sort(specificPicks);
		return specificPicks;
	}


	public static ArrayList<Integer> firstSpecificPicks(int pickNumber, int pickFrom){
		ArrayList<Integer> specificPicks = new ArrayList<Integer>();
		for(int i = 1; i <= pickNumber; i++) {			
			specificPicks.add(i);
		}		
		Collections.sort(specificPicks);
		return specificPicks;
	}
}
