package com.mycrawler.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {

	public static boolean collectionEquals(Collection<?> c1, Collection<?> c2){
		if(c1 == null &&  c2 == null)
			return true;
		if(c1 == null && c2 != null)
			return false;
		if(c1 != null && c2 == null)
			return false;
		if(c1.isEmpty() && c2.isEmpty())
			return true;
		if(c1.isEmpty() && !c2.isEmpty())
			return false;
		if(!c1.isEmpty() && c2.isEmpty())
			return false;
		if(c1.size() != c2.size())
			return false;
		Iterator<?> iterator  = c1.iterator();
		while(iterator.hasNext()){
			Object e = iterator.next();
			if(!c2.contains(e)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotEmpty(Collection<?> c1){
		if(c1 == null || c1.size() < 1){
			return false;
		}
		return true;
	}
	

	public static boolean isEmpty(Map<?,?> c1){
		if(c1 == null || c1.size() < 1){
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> c1){
		if(c1 == null || c1.size() < 1){
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String[] buckets){
		if(buckets == null || buckets.length < 1){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Object[] buckets){
		if(buckets == null || buckets.length < 1){
			return true;
		}
		return false;
	}
	
	public static <T> void randSortList(List<T> list){
		/*if(list == null || list.size() < 1)
			return list;
		List<T> tmpList =  new ArrayList<>();
		int size = list.size();
		int temp = size;
		for (int j = 0; j < size; j++) {
			int r = (int) (Math.random() * temp);
			System.out.println(r);
        	tmpList.add(list.get(r));
        	temp--;
    	}
		return tmpList;*/
		Collections.shuffle(list);
	}
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List intersection(List c1 , List c2){
		if(isEmpty(c1) || isEmpty(c2))
			return new ArrayList<>();
		List list = new ArrayList<>();
		list.addAll(c1);
		list.retainAll(c2);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Set intersection(Set c1 , Set c2){
		if(isEmpty(c1) || isEmpty(c2))
			return new HashSet<>();
		Set list = new HashSet<>();
		list.addAll(c1);
		list.retainAll(c2);
		return list;
	}
	 
	@SuppressWarnings("rawtypes")
	public static boolean hasIntersection(List c1 , List c2){
		return isNotEmpty(intersection(c1,c2));
	}
	
	public static void main(String[] args) {
		List<String> c1 = new ArrayList<>();
		c1.add("1");
		c1.add("2");
		c1.add("4");
		List<String> c2 = new ArrayList<>();
		c2.add("1");
		c2.add("4");
		System.out.println(intersection(c1,c2));
		
	}
	
	
}
