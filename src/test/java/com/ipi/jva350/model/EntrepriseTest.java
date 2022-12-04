package com.ipi.jva350.model;

import java.time.LocalDate;
import static com.ipi.jva350.model.Entreprise.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipi.jva350.exception.EntrepriseException;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

	// Entreprise.estDansPlage()
	// date est plage
	@ParameterizedTest(name = "{0} doit être compris entre {1} et {2}")
	@CsvSource({ "'2022-07-07', '2022-07-01', '2022-07-10'", "'2022-06-01', '2022-06-01', '2022-07-03'",
			" '2022-07-04', '2022-07-02', '2022-07-04'", " '2022-07-03', '2022-07-02', '2022-07-04'", })
	void testEstDansPlage(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
		// GIVEN WHEN
		boolean res = Entreprise.estDansPlage(d, debut, fin);
		// THEN
		assertEquals(true, estDansPlage(d, debut, fin));
	}

	// date pas dans le plage
	@ParameterizedTest(name = "{0} doit être compris entre {1} et {2}")
	@CsvSource({ "'2022-07-20', '2022-07-01', '2022-07-10'", "'2022-05-30', '2022-06-01', '2022-07-03'",
			" '2022-07-05', '2022-07-02', '2022-07-04'", " '2022-07-06', '2022-07-02', '2022-07-04'", })
	void testNEstPasDansPlage(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
		// GIVEN WHEN
		boolean res = Entreprise.estDansPlage(d, debut, fin);
		// THEN
		assertEquals(false, estDansPlage(d, debut, fin));
	}

	// Gerer les exception

	@ParameterizedTest(name = "date :  {1}   avant la date :  {2}")
	@CsvSource({ "'2022-01-01','2022-02-02','2022-01-01'", "'2023-03-01','2023-02-02','2022-01-01'",
			"'2023-02-01','2023-02-02','2023-02-01'",

	})
	void estDansPlageWhenDebutBeforeFinThrowExceptionTest(LocalDate d, LocalDate debut, LocalDate fin)
			throws EntrepriseException {
		EntrepriseException exception = assertThrows(EntrepriseException.class,
				() -> Entreprise.estDansPlage(d, debut, fin));
		System.out.print(exception.getMessage());
	}

	// est un jours ferie

	@ParameterizedTest(name = "jour {0} est férié")
	@CsvSource({ "'2022-11-01'", "'2022-11-11'", "'2022-01-01'", "'2022-05-01'", "'2022-07-14'", "'2022-12-25'",
			"'2021-11-01'", "'2021-11-11'", "'2021-01-01'", "'2021-05-01'", "'2021-07-14'", "'2021-12-25'",
			"'2020-11-01'", "'2020-11-11'", "'2020-01-01'", "'2020-05-01'", "'2020-07-14'", "'2020-12-25'",
			"'2019-11-01'", "'2019-11-11'", "'2019-01-01'", "'2019-05-01'", "'2019-07-14'", "'2019-12-25'",
			"'2018-11-01'", "'2018-11-11'", "'2018-01-01'", "'2018-05-01'", "'2018-07-14'", "'2018-12-25'",
			"'2017-11-01'", "'2017-11-11'", "'2017-01-01'", "'2017-05-01'", "'2017-07-14'", "'2017-12-25'", })
	void estJourFerieTest(LocalDate now) {
		assertTrue(Entreprise.estJourFerie(now));
	}

	@ParameterizedTest(name = "jour {0} est férié")
	@CsvSource({ "'2022-10-01'", "'2022-12-11'", "'2022-02-01'", "'2022-05-02'", "'2022-07-13'", "'2022-12-28'",
			"'2021-10-01'", "'2021-12-11'", "'2021-02-01'", "'2021-05-02'", "'2021-07-13'", "'2021-12-28'",
			"'2020-10-01'", "'2020-12-11'", "'2020-02-01'", "'2020-05-02'", "'2020-07-13'", "'2020-12-28'",
			"'2019-10-01'", "'2019-12-11'", "'2019-02-01'", "'2019-05-02'", "'2019-07-13'", "'2019-12-28'",
			"'2018-10-01'", "'2018-12-11'", "'2018-02-01'", "'2018-05-02'", "'2018-07-13'", "'2018-12-28'",
			"'2017-10-01'", "'2017-12-11'", "'2017-02-01'", "'2017-05-02'", "'2017-07-13'", "'2017-12-28'", })
	void estPasJourFerieTest(LocalDate now) {
		assertFalse(Entreprise.estJourFerie(now));
	}

	// Entreprise.proportionPondereeDuMois()
	// Lea
	@ParameterizedTest(name = "La date {0} a comme proportion pondérée du mois : {1}")
	@CsvSource({ "'2012-12-02',28", "'2020-10-10',16", "'2020-08-31',16", "'2050-01-01',0" })
	public final void proportionPondereeDuMois(String date, double expectedProportionPondereeMois) {
		LocalDate d = LocalDate.parse(date);

		double ponderedumois = Entreprise.proportionPondereeDuMois(d);
		assertNotEquals(expectedProportionPondereeMois, ponderedumois);
	}

	// Entreprise.getPremierJourAnneeDeConges()
	// Lea
	@ParameterizedTest(name = "La date {0} a comme pour premier jour d'annee de conges: {1}")
	@CsvSource({ "'2022-04-09','2021-06-01'", "'2022-06-09','2022-06-01'" })
	public final void getPremierJourAnneeDeConges(String date, String dateResultat) {
		LocalDate d = LocalDate.parse(date);

		LocalDate dResu = LocalDate.parse(dateResultat);
		LocalDate dTest = Entreprise.getPremierJourAnneeDeConges(d);

		assertEquals(dTest, dResu);
	}

}
