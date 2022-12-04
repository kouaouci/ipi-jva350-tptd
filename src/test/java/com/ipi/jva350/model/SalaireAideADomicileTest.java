package com.ipi.jva350.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class SalaireAideADomicileTest {
	// //LEGALEMENT DROIT CONGES PAYES
	@Test
	public final void testaLegalementDroitADesCongesPayesVide() {
		// Given
		SalarieAideADomicile aide = new SalarieAideADomicile();
		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();
		// Then
		assertFalse(res);
	}

	@Test
	public final void testaLegalementDroitADesCongesPayesjoursTravaillesAnneeZero() {
		// Given
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				0, 0, 0);
		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();
		// Then
		assertFalse(res);
	}

	@Test
	public final void testaLegalementDroitADesCongesPayesjoursTravaillesAnneeMoinsDeNeuf() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				5, 1, 0);
		boolean res = aide.aLegalementDroitADesCongesPayes();
		assertFalse(res);
	}

	@Test
	public final void testaLegalementDroitADesCongesPayesjoursTravaillesAnneeLimite() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				9, 0, 0);
		boolean res = aide.aLegalementDroitADesCongesPayes();
		assertFalse(res);
	}

	@Test
	public final void testaLegalementDroitADesCongesPayesjoursTravaillesAnneeVirgule() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				9.5, 0, 0);
		boolean res = aide.aLegalementDroitADesCongesPayes();
		assertFalse(res);
	}

	@Test
	public final void testaLegalementDroitADesCongesPayesjoursTravaillesAnneePlusDeNeuf() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				10, 0, 0);
		boolean res = aide.aLegalementDroitADesCongesPayes();
		assertTrue(res);
	}

	// CALCUL JOURS
	@Test
	public final void calculeJoursDeCongeDecomptesPourPlageEmptyHashSet() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				0, 0, 0);
		LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate.now(),
				LocalDate.now().minusDays(1));
		assertEquals(res, new LinkedHashSet<>());
	}

	@Test
	public final void calculeJoursDeCongeDecomptesPourPlageNotEmptyHashSet() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				0, 0, 0);
		LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate.now(),
				LocalDate.now().minusDays(1));
		assertNotEquals(res, res.isEmpty());
	}

	@Test
	public final void calculeJoursDeCongeDecomptesPourPlagHashSetFilled() {
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				0, 0, 0);
		LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate.now(),
				LocalDate.now().plusDays(20));
		assertEquals(res.size() > 1, true);
	}

	@Test
	public final void testCalculJourDeCongeDecomptesPourPlage() {
		// Given
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				0, 0, 0);

		// When
		LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate.of(2022, 7, 1),
				LocalDate.of(2022, 7, 2));
		LinkedHashSet<LocalDate> expected = new LinkedHashSet<>();
		expected.add(LocalDate.parse("2022-07-01"));
		expected.add(LocalDate.parse("2022-07-02"));

		// Then
		assertEquals(expected, res);
	}

	@ParameterizedTest(name = "Entre {0} et {1}, Nombre de jour de congés decomptes devrait être : {2}")
	@CsvSource({ "'2022-07-01','2022-07-02',2", "'2022-07-01','2022-07-03',2", "'2022-07-02','2022-07-04',1",
			"'2022-07-02','2022-07-02',0" })
	public final void TailleCalculJourDeCongeDecomptesPourPlage(String debut, String fin,
			int expectedNbJourDeCongeDecomptes) {
		// Given
		SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0,
				0, 0, 0);

		// When
		LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate.parse(debut),
				LocalDate.parse(fin));

		// Then
		assertEquals(expectedNbJourDeCongeDecomptes, res.size());
	}

	@ParameterizedTest(name = "jour {0}, expectedValue {1}")
	@CsvSource({ "'2022-11-07',true", "'2022-11-08',true", "'2022-11-09',true", "'2022-11-10',true",
			"'2022-11-11',true", // Jour ferié ne sont pas forcement prit en comptedans la
									// methode('Habituellement') se basant sur le jour de la semaine et c'est tout
			"'2022-11-18',true", // On test le vendredi de la semaine d'après
			"'2022-11-12',false", // Le samedi n'est habituellement pas travaillé
			"'2022-11-13',false",

	})
	void estHabituellementTravaille(LocalDate jour, boolean expectedValue) {
		SalarieAideADomicile salarie = new SalarieAideADomicile();
		assertEquals(salarie.estHabituellementTravaille(jour), expectedValue);

	}

	@ParameterizedTest(name = "jour {0}, expectedValue {1}")
	@CsvSource({ "'2022-11-07',true", "'2022-11-08',true", "'2022-11-09',true", "'2022-11-10',true",
			"'2022-11-11',false", // Jour ferié
			"'2022-11-18',true", // On test le vendredi de la semaine d'après
			"'2022-11-12',true", "'2022-11-13',false",

	})
	void estJourOuvrable(LocalDate jour, boolean expectedValue) {
		// Given
		SalarieAideADomicile salarie = new SalarieAideADomicile();
		// When :
		boolean res = salarie.estJourOuvrable(jour);
		// Then:
		assertEquals(res, expectedValue);

	}

	@ParameterizedTest(name = "dateDebut {0}, dateFin {1}, expectedValue {2}")
	@CsvSource({ "'2022-07-02', '2022-07-27', 20",// Un jour férié dans cette tranche de jour - 14 Juillet + Cas tordu
													// du 1er samedi qui n'est pas compté comme ouvrable
	})
	void calculeJoursDeCongeDecomptesPourPlageTestBeginSamedi(LocalDate dateDebut, LocalDate dateFin,
			int expectedValue) {
		// Given:
		SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
		// When :
		int joursDecomptes = salarieAideADomicile.calculeJoursDeCongeDecomptesPourPlage(dateDebut, dateFin).size();
		// Then:
		// Sans les dimanches par rapport au calendrier il y a {expectedValue} jours de
		// congés à payé
		assertEquals(joursDecomptes, expectedValue);

	}

}
