package com.ipi.jva350.model;

import com.google.type.DateTime;
import com.ipi.jva350.exception.EntrepriseException;
import org.hibernate.annotations.Source;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

	// région est dans la plage
	@ParameterizedTest(name = "date{0} est dans [debut{1} fin{2}]")
	@CsvSource({ "'2022-10-31', '2022-10-31' , '2022-11-04'", "'2022-11-02', '2022-10-31' , '2022-11-04'",
			"'2022-11-04', '2022-10-31' , '2022-11-04'", })
	void testEstDansPlage(LocalDate d, LocalDate dateDebut, LocalDate dateFin) throws EntrepriseException {
		// DONNÉ QUAND
		boolean res = Entreprise.estDansPlage(d, dateDebut, dateFin);
		// ALORS
		assertEquals(true, res);
	}

	@ParameterizedTest(name = "date{0} est dans [debut{1} fin{2}]")
	@CsvSource({ "'2022-10-30', '2022-10-31' , '2022-11-04'", "'2022-10-05', '2022-10-31' , '2022-11-04'",

	})
	void testPasDansPlage(LocalDate d, LocalDate dateDebut, LocalDate dateFin) throws EntrepriseException {
		// GIVEN WHEN
		boolean res = Entreprise.estDansPlage(d, dateDebut, dateFin);
		// then
		assertEquals(false, res);
	}

	// endregion
	// région estJourFerrié
	@Test
	void estJourFerieFalse() {
		LocalDate date = LocalDate.parse("2022-05-28"); // Samedi
		assertEquals(false, Entreprise.estJourFerie(date));
	}

	@Test
	void estJourFerieTrue() {
		LocalDate date = LocalDate.parse("2022-11-11"); // Vendrédi
		assertEquals(true, Entreprise.estJourFerie(date));
	}

	@Test
	void estJourFerie29Fevrier() {
		LocalDate date = LocalDate.parse("2020-02-29");// Vendredi
		assertEquals(false, Entreprise.estJourFerie(date));
	}

	@Test
	void getPremierJourAnneeDeCongesNULL() {
		assertEquals(null, Entreprise.getPremierJourAnneeDeConges(null));
	}

	@Test
	void getPremierJourAnneeDeCongesOk() {
		LocalDate date = LocalDate.parse("2020-02-29");// Vendredi
		assertEquals(2021, Entreprise.getPremierJourAnneeDeConges(date).getYear());
	}
	// endregion

	// proportionPondereeDuMois
	@ParameterizedTest(name = "date{0}")

	@CsvSource({ "'2022-01-01'", "'2022-02-01'", "'2022-03-01'", "'2022-04-01'", "'2022-05-01'", "'2022-06-01'",
			"'2022-07-01'", "'2022-08-01'", "'2022-09-01'", "'2022-10-01'", "'2022-11-01'", "'2022-12-01'", })
	void TESTproportionPondereeDuMois01(LocalDate date) {
		double result = Entreprise.proportionPondereeDuMois(date);
		assertTrue((result >= 0 || result <= 1));
	}
	// endregion
}
