package com.ipi.jva350.model;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static com.ipi.jva350.model.Entreprise.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.ipi.jva350.exception.EntrepriseException;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {
	

	
	// Entreprise.estDansPlage()
	 //date est plage
	 @ParameterizedTest(name = "{0} doit être compris entre {1} et {2}")
	    @CsvSource({
	            "'2022-07-07', '2022-07-01', '2022-07-10'",
	            "'2022-06-01', '2022-06-01', '2022-07-03'",
	            " '2022-07-04', '2022-07-02', '2022-07-04'",
	            " '2022-07-03', '2022-07-02', '2022-07-04'",
	    })
	 void testEstDansPlage(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
		 assertEquals(true, estDansPlage(d,debut, fin));
	 }
	 // date pas dans le plage
	  @ParameterizedTest(name = "{0} doit être compris entre {1} et {2}")
	    @CsvSource({
	            "'2022-07-20', '2022-07-01', '2022-07-10'",
	            "'2022-05-30', '2022-06-01', '2022-07-03'",
	            " '2022-07-05', '2022-07-02', '2022-07-04'",
	            " '2022-07-06', '2022-07-02', '2022-07-04'",
	    })
	    void testNEstPasDansPlage(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
	        assertEquals(false, estDansPlage(d, debut, fin));
	    }
	  
// est un jours ferie 
	  
	
	
	//Entreprise.proportionPondereeDuMois()
	//Entreprise.getPremierJourAnneeDeConges()

	
}
