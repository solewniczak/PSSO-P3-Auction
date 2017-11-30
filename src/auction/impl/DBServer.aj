package auction.impl;

import auction.support.ItemsWriter;

public aspect DBServer {
	
	static ItemsWriter StandardServerImpl.itemsWriter = new ItemsWriter("items.db");
	
	pointcut serverLoadItems(StandardServerImpl c):
    	target(c) && call(void *.loadItems());
	
	void around(StandardServerImpl c) : serverLoadItems(c) {
		//load items
		c.items = StandardServerImpl.itemsWriter.load();
		System.err.println("items loaded from DB");
	}
	
	pointcut serverNotifyAllObservers(StandardServerImpl c):
    	target(c) && call(void *.notifyAllObservers());
	
	before(StandardServerImpl c) : serverNotifyAllObservers(c) {
		//save items to DB
		StandardServerImpl.itemsWriter.save(c.items);
	}
}
