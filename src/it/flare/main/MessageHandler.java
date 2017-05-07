package it.flare.main;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.flare.domain.Item;
import it.flare.domain.Message1;
import it.flare.domain.Message2;
import it.flare.domain.Message3;
import it.flare.domain.Message3.Operation;


public class MessageHandler {

	final public static Integer LOG=10;
	final public static Integer LOGADJ=50;
	
	private Map<String, List<Item>> map=new LinkedHashMap<>();
    private LinkedList<Object> queue=new LinkedList<>();
	boolean suspended=false;

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public void handleMessage(Object message){
		
		queue.add(message);
		
		if(suspended){
			System.out.println("application suspended ...");
			return;
		}
		
		
		
		if (message instanceof Message1){
			Message1 m=(Message1)message;
			addItem(m.getItem());
		}
		if (message instanceof Message2){
			Message2 m=(Message2)message;
			for(int n=0;n<m.getNum();n++)
				addItem(m.getItem());
		}
		if (message instanceof Message3){
			Message3 m=(Message3)message;
			queue.add(m);
			adjust(m.getOperation(),m.getItem());
		}



	}

	private void addItem(Item i){
		if (map.get(i.getType())==null){
			List<Item> l=new LinkedList<>();
			l.add(i);
			map.put(i.getType(),  l);
		} else {
			map.get(i.getType()).add(i);
		}    	
	}

	private void log(){
		
		for(String key:map.keySet()){
			System.out.println("----------------------------------------------");
			BigDecimal val=new BigDecimal("0");
			List<Item> list=map.get(key);
			for(Item each:list)
				val=val.add(each.getValue());
		
			System.out.println("Product  "+ key + " sales "+ list.size() + " amount "+val);
		}
		System.out.println("----------------------------------------------");
	}
	
private void logAdjust(){
		
	if(suspended){
		return;
	}
	
		for(Object each:queue){
			if(!(each instanceof Message3)) continue;
			Message3 msg=(Message3)each;
 			System.out.println("adjustment "+ msg.getOperation() + " item" +msg.getItem().getType() + "  value" + msg.getItem().getValue());
			System.out.println("----------------------------------------------");
				
	}
	
}
	
	private void adjust(Operation op,Item i){
		List<Item> l=map.get(i.getType());
		if(l!=null){
			for(Item each:l){
				BigDecimal value=each.getValue();
				if(op==Operation.ADD){
				 each.setValue(value.add(i.getValue()));
				}
				if(op==Operation.SUBTRACT){
					each.setValue(value.subtract(i.getValue()));
				}
				if(op==Operation.MULTIPLY){
					each.setValue(value.multiply(i.getValue()));
				}


			}
		}
	}

	public static void main(String[] args) {
		MessageHandler handler=new MessageHandler();
		Scanner sc = new Scanner(System.in);
	try{
		for(int k=0;;k++){
			if(k%LOG==0){
				handler.log();
			}
			if(k>=LOGADJ){
				handler.logAdjust();
				handler.setSuspended(true);
				
			}

			
		System.out.println("1) message 1");
		System.out.println("2) message 2");
		System.out.println("3) message 3");
		System.out.println("4) exit");
		System.out.print("> ");
		
		
			
	
		int n=sc.nextInt();
		if(n==1){
			System.out.println("insert type");
			System.out.print("> ");
				String t=sc.next();
			System.out.println("insert value");
			System.out.print("> ");
			String v=sc.next();
			handler.handleMessage(new Message1(t,v));	
		}
		if(n==2){
			System.out.println("insert type");
			System.out.print("> ");
			String t=sc.next();
			System.out.println("insert value");
			System.out.print("> ");
			String v=sc.next();
			System.out.println("insert num");
			System.out.print("> ");
			Integer i=sc.nextInt();
			handler.handleMessage(new Message2(t,v,i));	
		}
		if(n==3){
			System.out.println("insert type");
			System.out.print("> ");
			String t=sc.next();
			System.out.println("insert value");
			System.out.print("> ");
			String v=sc.next();
			System.out.println("insert op [ADD] [SUBTRACT] [MULTIPLY]");
			System.out.print("> ");
			String o=sc.next();
			handler.handleMessage(new Message3(t,v,o.toUpperCase()));	
		} if(n==4){
			System.out.println("exiting...");						
			System.exit(0);
		}
		}
		
		
	} catch(Throwable e){
		System.out.println("proper error handling not implemented due to lack of time ("+e.getMessage()+")");
	}
}
}





