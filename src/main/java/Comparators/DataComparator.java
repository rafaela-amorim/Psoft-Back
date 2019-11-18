package Comparators;

import java.util.Comparator;

import ajude.entities.Campanha;

public class DataComparator implements Comparator<Campanha> {

	@Override
	public int compare(Campanha camp1, Campanha camp2) {
		return camp1.getDataLimite().compareTo(camp2.getDataLimite());
	}

}
