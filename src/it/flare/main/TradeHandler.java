package it.flare.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.flare.domain.Rank;
import it.flare.domain.Trade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class TradeHandler {

	
	public void report() throws Exception{
		Parser parser=new Parser();
		List<Trade> trades=parser.parse();
		List<Rank> ranks=new ArrayList<Rank>();
		//--- trades 
		Map<LocalDate,Map<String,List<Trade>>> normalizedMap=parser.normalize(trades);
		for(LocalDate each:normalizedMap.keySet()){

			Map<String,List<Trade>> map=normalizedMap.get(each);
			
			System.out.println("SettlementDate="+each+" -------------------");
			for(String entity:map.keySet()){

				System.out.println("== Entity:"+entity);

				List<Trade> list = map.get(entity);
				
				BigDecimal totalIn=new BigDecimal("0");
				BigDecimal totalOut=new BigDecimal("0");
							
				for(Trade item:list){
					
					if(item.getBs().equals("B"))
					  totalOut=totalOut.add(item.getPrice().multiply(new BigDecimal(item.getUnits()).multiply(item.getFx())));

					if(item.getBs().equals("S"))
						  totalIn=totalIn.add(item.getPrice().multiply(new BigDecimal(item.getUnits()).multiply(item.getFx())));
				}
			
				System.out.println("In="+totalIn);
				System.out.println("Out="+totalOut);
				parser.addRank(ranks, entity, totalIn, totalOut);
			}
	  }
		//ranks 
		
		//---------- rank in -----------------
		System.out.println("---- ranks ------------------------");
		
		// lambda this time
		Collections.sort(ranks,(a, b) -> a.getAmountIn().compareTo(b.getAmountIn()));
		for(int n=0;n<ranks.size();n++){
			System.out.println("In rank n "+(n+1)+" "+ranks.get(n).getEntity() + " "+ranks.get(n).getAmountIn());
		}
		//---------- rank out -----------------
		Collections.sort(ranks,(a, b) -> a.getAmountOut().compareTo(b.getAmountOut()));
		for(int n=0;n<ranks.size();n++){
			System.out.println("Out rank n "+(n+1)+" "+ranks.get(n).getEntity()+ " "+ranks.get(n).getAmountOut());
		}

	}

		
	
	public static void main(String[] args) {
	try{	
			new TradeHandler().report();
		
	}catch(Throwable e){
		System.out.println("proper error handling not implemented due to lack of time ("+e.getMessage()+")");
	}
	}
	
	public static class SettlementComparator implements Comparator<Trade>{

		@Override
		public int compare(Trade o1, Trade o2) {
			// TODO Auto-generated method stub
			return o1.getSettlement().compareTo(o2.getSettlement());
		}
		
	}
	
	public static class Parser {
		
		//not very efficient 
		public  void addRank(List<Rank> ranks,String entity,BigDecimal in,BigDecimal out){
			
			//only need the entity so far
			Rank rank=new Rank().setEntity(entity).setAmountIn(in).setAmountOut(out);
			int k=ranks.indexOf(rank);
			if(k>=0){
				Rank r=ranks.get(k);
				r.setAmountIn(r.getAmountIn().add(in));
				r.setAmountOut(r.getAmountOut().add(out));
				
			} else{
				
				ranks.add(rank);
			}
			}
		
		public Map<LocalDate,Map<String,List<Trade>>> normalize(List<Trade> trades){
			
			 Map<LocalDate,Map<String,List<Trade>>> map=new LinkedHashMap<>(); 
			
			 Collections.sort(trades,new SettlementComparator());
			 
			for(Trade each:trades){
				Map<String,List<Trade>> key=map.get(each.getSettlement());
				if(key==null){
					Map<String,List<Trade>> innerMap = new  HashMap<>();
					List<Trade> innerList = new  ArrayList<>();
					innerList.add(each);
					innerMap.put(each.getEntity(), innerList);
					map.put(each.getSettlement(), innerMap);
				} else { //settlement found
				 
					List<Trade> innerKey=key.get(each.getEntity());
					if(innerKey==null){
						List<Trade> innerList = new  ArrayList<>();
						innerList.add(each);
						key.put(each.getEntity(), innerList);
					} else {//entity found 
						
						innerKey.add(each);
					}
					
					
				}
			}
			 
			 
			 return map;
		}
		
		
		public List<Trade> parse(){
		//read file into stream, try-with-resources
		List<Trade> trades=null;
		try (Stream<String> stream = Files.lines(
				Paths.get(ClassLoader.getSystemResource("it/flare/resource/trade.xls").toURI())
				)){
				trades=stream
						.map(line->line.split(","))
						.map(array->new Trade(array[0],array[1],array[2],array[3],array[4],array[5],array[6],array[7]))
						.collect(Collectors.toList());
								
			
		} catch (Exception e) {
			//real log error here
			e.printStackTrace();
		}
		return trades;
		}
	}
	
}
