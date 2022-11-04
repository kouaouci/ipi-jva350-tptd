package com.ipi.jva350.model;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SalaireAideADomicileTest {
	@Test	//indique qui sera exécutée par Junit
	
	void aLegalementDroitADesCongesPayesZero() {
		
		//Given
		SalarieAideADomicile aide =new SalarieAideADomicile("jeanne", LocalDate.now(), LocalDate.now(), 0, 0, 0, 0, 0);
		//When
		 boolean res=aide.aLegalementDroitADesCongesPayes();
		 //then
		 Assertions.assertEquals(false, res);
		 
	}
		 @Test
		 void aLegalementDroitADesCongesPayesNonInitialise() {
			 //Given
			 SalarieAideADomicile aide =new SalarieAideADomicile();
			 //when
			 boolean res = aide.aLegalementDroitADesCongesPayes();
			 //then
			 Assertions.assertEquals(false, res);
		 }
		 
		 @Test
		 
		 void aLegalementDroitADesCongesPayesTrue()
		 {
			 //Given
			 
			 SalarieAideADomicile aide =new SalarieAideADomicile("jeanne", LocalDate.now(), LocalDate.now(), 0, 0, 5, 1, 0);
			
			 
			 //When
			 boolean res = aide.aLegalementDroitADesCongesPayes();
		 
			 // then
			 Assertions.assertEquals(false, res);
			 
		 }
			 @Test
			 
			 void aLegalementDroitADesCongesPayesNettementMoinsDe10()
			 {
				 //Given
				 //localDate dummyDate =LocalDate.now();
				 
				 SalarieAideADomicile aide =new SalarieAideADomicile("jeanne", LocalDate.of(2021,07,01), LocalDate.now(), 0, 0, 15, 1, 0);
				
				 
				 //When
				 boolean res = aide.aLegalementDroitADesCongesPayes();
			 
				 // then
				 Assertions.assertEquals(true, res);
				 
		
			 }
	 @Test
			 
			 void aLegalementDroitADesCongesPayesNettementMoinsDe11()
			 {
				 //Given
				 //localDate dummyDate =LocalDate.now();
				 
				 SalarieAideADomicile aide =new SalarieAideADomicile("jeanne", LocalDate.of(2021,07,01), LocalDate.now(), 0, 0, 11, 1, 0);
				
				 
				 //When
				 boolean res = aide.aLegalementDroitADesCongesPayes();
			 
				 // then
				 Assertions.assertEquals(true, res);
				 
		
			 }
	 @Test
	 
	 void aLegalementDroitADesCongesPayesCasAuxLimite9()
	 {
		 //Given
		 //localDate dummyDate =LocalDate.now();
		 
		 SalarieAideADomicile aide =new SalarieAideADomicile("jeanne", LocalDate.of(2021,07,01), LocalDate.now(), 0, 0, 9, 1, 0);
		
		 
		 //When
		 boolean res = aide.aLegalementDroitADesCongesPayes();
	 
		 // then
		 Assertions.assertEquals(false, res);
		 

	 }
	 @Test
	 
	 void aLegalementDroitADesCongesPayesCasAuxLimite()
	 {
		 //Given
		 //localDate dummyDate =LocalDate.now();
		 
		 SalarieAideADomicile aide =new SalarieAideADomicile("jeanne", LocalDate.of(2021,07,01), LocalDate.now(), 0, 0, 9, 1, 0);
		
		 //When
		 boolean res = aide.aLegalementDroitADesCongesPayes();
		 // then
		 Assertions.assertEquals(false, res);
		 aide.setJoursTravaillesAnneeNMoins1(10);
	
	 
		 // then
		 Assertions.assertEquals(true, aide.aLegalementDroitADesCongesPayes());
		 

	 }

	
	
	
	

}

