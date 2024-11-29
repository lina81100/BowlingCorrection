package bowling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PartieMultiJoueursTest {

	

	@Test
	public void testPartieTerminee() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[]{"Pierre"});

		for (int i = 0; i < 12; i++) {
			partie.enregistreLancer(10); // Strike à chaque tour
		}

		assertEquals("Partie terminée", partie.enregistreLancer(0));  // La partie est terminée après les 12 lancers
		assertEquals(300, partie.scorePour("Pierre"));  // Score maximum après 12 strikes
	}

	@Test
	public void testJoueurInconnu() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();
		partie.demarreNouvellePartie(new String[]{"Pierre", "Paul"});

		// Vérifier que la tentative de demander un score pour un joueur inexistant génère une exception
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			partie.scorePour("Jacques");
		});

		assertEquals("Le joueur n'existe pas", exception.getMessage());
	}

	@Test
	public void testDemarreNouvellePartieAvecJoueursVides() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();

		// Vérifier qu'une exception est levée si aucun joueur n'est fourni
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(new String[]{});
		});

		assertEquals("Aucun joueur dans la partie", exception.getMessage());
	}

	@Test
	public void testEnregistreLancerQuandPartieNonDemarree() {
		PartieMultiJoueurs partie = new PartieMultiJoueurs();

		// Vérifier qu'une exception est levée si on essaie d'enregistrer un lancer avant de démarrer la partie
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			partie.enregistreLancer(5);
		});

		assertEquals("La partie n'est pas démarrée", exception.getMessage());
	}

}
