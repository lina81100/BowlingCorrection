package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {
	private List<String> joueurs;
	private List<PartieMonoJoueur> partiesMonoJoueurs;
	private int joueurCourantIndex;

	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
		if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("Aucun joueur dans la partie");
		}

		joueurs = new ArrayList<>();
		partiesMonoJoueurs = new ArrayList<>();
		joueurCourantIndex = 0;

		// Ajouter chaque joueur dans la partie
		for (String nom : nomsDesJoueurs) {
			joueurs.add(nom);
			partiesMonoJoueurs.add(new PartieMonoJoueur());
		}

		return getProchainLanceEtBoulePour(joueurs.get(joueurCourantIndex));
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (partiesMonoJoueurs == null) {
			throw new IllegalStateException("La partie n'est pas démarrée");
		}

		if (lesPartiesSontTerminee()) {
			return "Partie terminée";
		}

		PartieMonoJoueur partieCourante = partiesMonoJoueurs.get(joueurCourantIndex);
		partieCourante.enregistreLancer(nombreDeQuillesAbattues);

		joueurCourantIndex = (joueurCourantIndex + 1) % joueurs.size();

		return getProchainLanceEtBoulePour(joueurs.get(joueurCourantIndex));
	}

	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		int index = joueurs.indexOf(nomDuJoueur);
		if (index == -1) {
			throw new IllegalArgumentException("Le joueur n'existe pas");
		}
		return partiesMonoJoueurs.get(index).score();
	}

	

	public String getProchainLanceEtBoulePour(String joueur) {
		int index = joueurs.indexOf(joueur);
		PartieMonoJoueur partie = partiesMonoJoueurs.get(index);
		return "Prochain tir : joueur " + joueur
			+ ", tour n° " + partie.numeroTourCourant()
			+ ", boule n° " + partie.numeroProchainLancer();
	}

	public Boolean lesPartiesSontTerminee() {
		for (PartieMonoJoueur partie : partiesMonoJoueurs) {
			if (!partie.estTerminee()) {
				return false;
			}
		}
		return true;
	}
}
