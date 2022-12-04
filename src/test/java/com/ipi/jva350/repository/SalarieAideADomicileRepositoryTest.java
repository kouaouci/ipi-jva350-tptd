package com.ipi.jva350.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipi.jva350.model.SalarieAideADomicile;
@ExtendWith(SpringExtension.class)
@DataJpaTest

class SalarieAideADomicileRepositoryTest {
	   @Autowired
	    private SalarieAideADomicileRepository salarieAideADomicileRepository;

	    @Test
	    void partCongesPrisTotauxAnneeNMoins1Ok(){
	        //GIVEN
	        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
	        salarieAideADomicile.setCongesPayesPrisAnneeNMoins1(15.5);
	        salarieAideADomicile.setCongesPayesAcquisAnneeNMoins1(20.0);
	        salarieAideADomicileRepository.save(salarieAideADomicile);
	        double resultAttendu = 15.5 / 20.0;
	        //WHEN
	        double resultatRepository = salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1();
	        //THEN
	        assertEquals(resultAttendu, resultatRepository);
	    }

}
