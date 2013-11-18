package main.HuffmanProcessing;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Classe permettant de récupérer un ensemble trié selon les valeurs à partir d'une MAP
 * @author JoeTheFuckingFrypan
 * @version 0.1
 */
public class Sorter {
	
	/**
	 * Méthode permettant de récupérer un ensemble trié selon les valeurs à partir d'une MAP<String,Integer> --MAP<K,V> en général
	 * @param map Collection ordonnée selon les clés, dont on souhaite un ordonnancement selon les valeurs
	 * @return Collection ordonnée selon les valeurs
	 */
	public static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override 
	            public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                if (e1.getValue().equals(e2.getValue())) {
	                	return 1; //permet d'outrepasser les règles du compareTo pour permettre d'avoir des fréquences identiques
	                } else {
	                	return e1.getValue().compareTo(e2.getValue());
	                }
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
}
