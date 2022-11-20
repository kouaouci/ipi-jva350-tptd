package com.ipi.jva350.Service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.service.SalarieAideADomicileService;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class SalarieAideADomicileServiceIntegrationTest {
	
	 @Autowired
	    private SalarieAideADomicileService salarieAideADomicileService;

	    @Test
	    void testClotureMois() throws SalarieException {
	        // Given (aurait aussi pu être mis en méthode @BeforeEach pour toutes les autres méthodes de test)
	        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
	                LocalDate.of(2021, 7, 1), LocalDate.now(),
	                0, 0, 9,
	                1, 0);

	        // When
	        salarieAideADomicileService.clotureMois(aide, 20);
	        // Then
	        Assertions.assertEquals(20, aide.getJoursTravaillesAnneeN());
	    }
	}


