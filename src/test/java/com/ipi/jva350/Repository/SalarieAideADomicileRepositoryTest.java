package com.ipi.jva350.Repository;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)

@DataJpaTest
public class SalarieAideADomicileRepositoryTest {

    @Autowired
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    /**
     * Réinitialise la base avant chaque test
     * (en fait utile uniquement si on utilisait SalarieAideADomicileService.creerSalarieAideADomicile()
     * au lieu de salarieAideADomicileRepository.save(new SalarieAideADomicile(...))
     */
    @BeforeEach
    void setUp() {
        salarieAideADomicileRepository.deleteAll();
    }

    @Test
    void findByNom() {
        // Given (aurait aussi pu être mis en méthode @BeforeAll pour toutes les autres méthodes de test)
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 9,
                1, 0);
        salarieAideADomicileRepository.save(aide);

        // When
        SalarieAideADomicile res = salarieAideADomicileRepository.findByNom(aide.getNom());
        // Then
     assertNotNull(res);
    }
}


