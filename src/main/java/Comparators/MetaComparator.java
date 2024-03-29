package Comparators;

import java.util.Comparator;

import ajude.entities.Campanha;

public class MetaComparator implements Comparator<Campanha> {

	@Override
	public int compare(Campanha camp1, Campanha camp2) {
		return (int)((camp2.getDoacoes() - camp2.getMeta())*100 - (camp1.getDoacoes() - camp1.getMeta())*100);
	}

}
