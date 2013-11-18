package main.HuffmanProcessing;

import java.util.Map;

public class HuffmannEncoder {
	private HuffmannBean bean;
	
	public HuffmannEncoder(Map<String,Integer> originalValues) {
		this.bean = new HuffmannBean(originalValues);
	}
	
	/**
	 * Méthode permettant d'encoder selon la méthode de Huffmann
	 */
	public void encode() {
		while(bean.stillHasNodesToProcess()) {
			bean.processingOneMoreStep();
		}
	}
	
	/**
	 * Méthode permettant d'encoder selon la méthode de Huffmann (avec affichage de chaque étape)
	 */
	@Deprecated
	public void encodeWithDebugInfo() {
		while(bean.stillHasNodesToProcess()) {
			bean.processingOneMoreStep();
			bean.displayContent();
		}
	}
	
}
