package Comparators;

import java.util.Comparator;

import ajude.entities.Campanha;

public class LikesComparator implements Comparator<Campanha> {

	@Override
	public int compare(Campanha camp1, Campanha camp2) {
		return camp2.getLikes() - camp1.getLikes();
	}

}
