package com.ipi.jva350.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import com.ipi.jva350.model.Entreprise;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;


@ExtendWith(MockitoExtension.class)
 class SalairieAideADomicileServiceTest {
	
	  @InjectMocks
	    SalarieAideADomicileService salarieAideADomicileService;
	    @Mock
	    SalarieAideADomicileRepository salarieAideADomicileRepository;
	  @ParameterizedTest(name = "le nombre de congé de N-1 est {0}, le premier poids est {1} le second poinds est {2}")
	    @CsvSource({
	            "0,0.5,0.8 ,0", // Si aucun jour de congés disp
	            "100,0.5,0.8 ,80", // Si jour dspo et poids sur 2nd plus important
	            "100,0.8,0.5 ,80", // Si jour dspo et poids sur 1er plus important
	    })
	    //Testing static methods is interesting but its an argument or a refactor of those
	    void TestPonderaionDesCongésAcquisenNmoins1(double congesPayesAcquisAnneeNMoins1, double poids_1, double poids_2, double expected){
	        try(MockedStatic<Entreprise> mocky = Mockito.mockStatic(Entreprise.class))
	        {
	            mocky.when(() -> Entreprise.proportionPondereeDuMois(LocalDate.now())).thenReturn(poids_1);
	            mocky.when(() -> Entreprise.proportionPondereeDuMois(LocalDate.of(2020, 10,10))).thenReturn(poids_2);

	            double result = salarieAideADomicileService.PonderaionDesCongésAcquisenNmoins1(congesPayesAcquisAnneeNMoins1, LocalDate.now(), LocalDate.of(2020, 10,10));

	            assertEquals(expected, result);
	        }
	    }

	    @ParameterizedTest (name = "mois en cours {0}, premier jour de congé {1}, congé de n-1 {2}, part de congé pris N- 1 {3}, expected {4}")
	    @CsvSource({
	            "'2022-01-03', '2022-01-03', 0, 0, 0",
	            "'2022-01-03', '2022-08-03', 0, 0, 0", // quelque soit la durée du congé si il n'y pas de congé on a 0
	            "'2022-01-03', '2022-01-03', 100, 0, 0", // Even if days are take if there is no difference then we get 0
	            "'2022-02-03', '2022-09-03', 100, 0.4, -1",//Cas où on a un écart valide
	            "'2022-02-03', '2022-09-03', 100, 0.3, 1",//Cas où on a un écart valide
	    })
	    void TestDivevergenceDesCongeDePLusDe20Pourcent(LocalDate moisEnCours, LocalDate premierJourDeConge, double congesPayesAcquisAnneeNMoins1, double partCongesPrisTotauxAnneeNMoins1, double expected){
	        double result = salarieAideADomicileService.DivevergenceDesCongeDePLusDe20Pourcent(moisEnCours,congesPayesAcquisAnneeNMoins1, premierJourDeConge, partCongesPrisTotauxAnneeNMoins1);
	        if (expected == 0){
	            assertEquals(expected, result);
	        } else if (expected > 0) {
	            assertTrue(result > 0);
	        } else if (expected < 0) {
	            assertTrue(result < 0);
	        }
	    }
	    @ParameterizedTest(name = "limites conges {0} , mois en cours {1}, der jour de congé {2}")
	    @CsvSource({
	        "0, '2022-01-03', '2022-01-03', 0", // pas de marge quand pas de jour
	            "0, '2022-01-03', '2022-06-03', 0", // 0 even with a gap
	            "100, '2022-01-03', '2022-06-03', 4", // 0 even with a gap
	    })
	    void TESTMargeDeCongés(double limiteConges, LocalDate moisEnCours, LocalDate dernierJourDeConge, double expected){
	        double result = salarieAideADomicileService.MargeDeCongés(limiteConges, moisEnCours, dernierJourDeConge);
	        if (expected > 0){
	            assertTrue(result > expected);
	        }else{
	            assertEquals(expected,result);
	        }
	    }
	    @ParameterizedTest(name = " mois en cours {1}, der jour de congé {2}")
	    @CsvSource({
	            "'2022-01-03', '2022-01-03', 0", // pas d'ancièneté
	            "'2022-01-03', '2021-01-03', 1", // 1 ans dancieneté
	            "'2022-01-03', '2012-01-03', 10", // 10 ans
	            "'2022-01-03', '2011-01-03', 10", // 11 ans
	    })
	    void TestJourDeCongeAncienneteBonus(LocalDate moisEnCours, LocalDate moisDebutContrat, double expected){
	        double result = salarieAideADomicileService.JourDeCongeAncienneteBonus(moisEnCours, moisDebutContrat);
	        assertEquals(expected, result);
	    }

	    @Test
	    void TESTArrondieJourDeCongeDispo(){
	        assertEquals(4, salarieAideADomicileService.ArrondieJourDeCongeDispo(4.3));
	    }

	    //Du coup tester la fonction est maintenant juste un test d'intégration
	    @ParameterizedTest(name = "il  a déjà pris {0} jour et il en a" +
	                            " {1} dispo de l'an dernier" +
	                            "le mois en cours est {2}" +
	                            "le contrat à commencé le {3}"+
	                            "le premier jour de congé est {4}" +
	                            "le dernier jour de congé est {5}" +
	                             ":: the expected result is {6} ")
	    @CsvSource
	            ({
	                    "0.2, 50, '2022-01-03', '2012-01-03', '2022-07-03', '2022-12-03', 35",
	            })
	    void TestCalculeLimiteEntrepriseCongesPermis(double congePrisAnneeNmoins1,
	                                                 double congePayerAcquisNmoins1,
	                                                 LocalDate currentMonth,
	                                                 LocalDate contractStart,
	                                                 LocalDate firstDayOff,
	                                                 LocalDate lastDayOff,
	                                                 double expected)
	    {
	        when(salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1()).thenReturn(congePrisAnneeNmoins1);
	        long result = salarieAideADomicileService.calculeLimiteEntrepriseCongesPermis(currentMonth,
	                                                                                    congePayerAcquisNmoins1,
	                                                                                    contractStart,
	                                                                                    firstDayOff,
	                                                                                    lastDayOff
	        );

	        assertEquals(expected,result);
	    }

}
