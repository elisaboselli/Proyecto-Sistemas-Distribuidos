package utils;

import java.util.Map;

public class PromisesSent {
	
	private Map<Integer,Integer> promisesSent;
	
	public PromisesSent() {}
	
	public void addPromise(int _id, int _round) {
		promisesSent.put(_id, _round);
	}
	
	public int getPromiseRound(int _id) {
		Integer round = promisesSent.get(_id);
		if (round == null)
			return -1;
		else
			return round.intValue();
	}

}
