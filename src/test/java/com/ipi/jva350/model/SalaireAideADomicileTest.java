package com.ipi.jva350.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SalaireAideADomicileTest {
	@Test // indique qui sera exécutée par Junit

	void aLegalementDroitADesCongesNouvelEmploye() {

		// Given
		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.now(), LocalDate.now(), 0, 0, 0, 0, 0);
		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();
		// then
		Assertions.assertEquals(false, res);

	}

	@Test
	void aLegalementDroitADesCongesPayesNonInitialise() {
		// Given
		SalarieAideADomicile aide = new SalarieAideADomicile();
		// when
		boolean res = aide.aLegalementDroitADesCongesPayes();
		// then
		Assertions.assertEquals(false, res);
	}

	@Test

	void aLegalementDroitADesCongesPayesTrue() {
		// Given

		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.now(), LocalDate.now(), 0, 0, 5, 1, 0);

		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();

		// then
		Assertions.assertEquals(false, res);

	}

	@Test

	void aLegalementDroitADesCongesPayesNettementMoinsDe10() {
		// Given
		// localDate dummyDate =LocalDate.now();

		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.of(2021, 07, 01), LocalDate.now(), 0,
				0, 15, 1, 0);

		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();

		// then
		Assertions.assertEquals(true, res);

	}

	@Test

	void aLegalementDroitADesCongesPayesNettementMoinsDe11() {
		// Given
		// localDate dummyDate =LocalDate.now();

		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.of(2021, 07, 01), LocalDate.now(), 0,
				0, 11, 1, 0);

		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();

		// then
		Assertions.assertEquals(true, res);

	}

	@Test

	void aLegalementDroitADesCongesPayesCasAuxLimite9() {
		// Given
		// localDate dummyDate =LocalDate.now();

		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.of(2021, 07, 01), LocalDate.now(), 0,
				0, 9, 1, 0);

		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();

		// then
		Assertions.assertEquals(false, res);

	}

	@Test

	void aLegalementDroitADesCongesPayesCasAuxLimite() {
		// Given
		// localDate dummyDate =LocalDate.now();

		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.of(2021, 07, 01), LocalDate.now(), 0,
				0, 9, 1, 0);

		// When
		boolean res = aide.aLegalementDroitADesCongesPayes();
		// then
		Assertions.assertEquals(false, res);
		aide.setJoursTravaillesAnneeNMoins1(10);

		// then
		Assertions.assertEquals(true, aide.aLegalementDroitADesCongesPayes());

	}
	   @Test
	    void aLegalementDroitADesCongesPayesNettementPlusDe10() {
	        // Given :
	        //LocalDate dummyDate = LocalDate.now();
	        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
	                LocalDate.of(2021, 7, 1), LocalDate.now(),
	                0, 0, 15,
	                1, 0);
	        // When :
	        boolean res = aide.aLegalementDroitADesCongesPayes();
	        // Then :
	        Assertions.assertEquals(true, res);
	    }

	
     @Test 
     void aLegalementDroitADesCongesPayesLimites() {
    	//Given

 		SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.of(2021, 07, 01), LocalDate.now(), 0,
 				0, 9, 1, 0);

 		// When
 		boolean res = aide.aLegalementDroitADesCongesPayes();
 		// then
 		Assertions.assertEquals(false, res);
 		aide.setJoursTravaillesAnneeNMoins1(10);

 		// then
 		Assertions.assertEquals(true, aide.aLegalementDroitADesCongesPayes());
    	 	 
     }
     
     @Test
     void testCalculeJoursDeCongeDecomptesPourPlage() {
         // When :
         SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                 LocalDate.of(2021, 7, 1), LocalDate.now(),
                 0, 0, 9,
                 1, 0);

         // When :
         LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(
                 LocalDate.of(2022, 7, 1), LocalDate.of(2022, 7, 2));

         // Then :
         LinkedHashSet<LocalDate> expected = new  LinkedHashSet<>();
         expected.add(LocalDate.of(2022, 7, 1));
         expected.add(LocalDate.parse("2022-07-02"));
         Assertions.assertEquals(expected, res);
     }

     
     // test parametrerr
     
     
     @ParameterizedTest(name= "entre {0} et {1}, nombre de JoursDeCongeDeComptes devrait etre{2}")
    		 @CsvSource({
    		"'2022-07-01','2022-07-02',2 "	 ,
    		"'2022-07-01','2022-07-03',2 "	 ,
    		"'2022-07-02','2022-07-04',1 "	 ,
    		"'2022-07-02','2022-07-02',0 "	 ,
    			 
    		 })
    
     
     void testCalculJoursCongesDecomptesPourPlage(String debut, String fin, double exectedNbJoursDeCongeDeComptes) {
    	  //given
    	  SalarieAideADomicile aide = new SalarieAideADomicile("jeanne", LocalDate.of(2021, 07, 01), LocalDate.now(), 0,
   				0, 10, 1, 0);
    	  //when
    	 // LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(
                  //LocalDate.of(2022,7,1), LocalDate.of(2022,7,27));
    	  LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(
                  LocalDate.parse(debut), LocalDate.parse(fin));

    	  
    	  /* then
    	  LinkedHashSet<LocalDate>expected =  new LinkedHashSet<>();
          expected.add(LocalDate.of(2022,7,11));
          Assertions.assertEquals(expected,res);
    	// Assertions.assertEquals(expectedNbJourDeCongeDeComptes,res.size(), null);
          //Then*/
    	  Assertions.assertEquals(exectedNbJoursDeCongeDeComptes, res.size());
          
         /*LinkedHashSet<LocalDate> res =aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate(debut),LocalDate(fin) );
          Assertions.assertEquals(expectedNbJourDeCongeDecomptes, res.size());*/
     }
     @ParameterizedTest(name = "Entre {0} et {1}, nombre de JoursDeCongeDecomptes devrait être {2}")
     @CsvSource({
             "'2022-07-01', '2022-07-02', 2",
             "'2022-07-01', '2022-07-03', 2",
             "'2022-07-02', '2022-07-04', 1",
             "'2022-07-02', '2022-07-02', 0"
     })
     
  
     public final void TailleCalculJourDeCongeDecomptesPourPlage(String debut, String fin, double expectedNbJourDeCongeDecomptes) {
         //Given
         SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne", LocalDate.of(2021,  7, 1), LocalDate.now(), 0,0,0, 0, 0);
          
         //When
         LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(LocalDate.parse(debut), LocalDate.parse(fin));

          //Then
          Assertions.assertEquals(expectedNbJourDeCongeDecomptes, res.size());
 
		  
    			
    			  

    	  
      }}
     
   
     

