package tn.esprit.spring.BlocChambreTests.Chambre;

import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ChambreRepositoryTest {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    @Test
    void testFindByNumeroChambre() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc A", 100, foyer, null);
        bloc = blocRepository.save(bloc);

        Chambre chambre = new Chambre(0, 101L, TypeChambre.SIMPLE, bloc, null);
        chambre = chambreRepository.save(chambre);

        Chambre foundChambre = chambreRepository.findByNumeroChambre(101L);

        assertNotNull(foundChambre);
        assertEquals(101L, foundChambre.getNumeroChambre());
    }

    @Test
    void testFindByBlocNomBloc() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc1 = new Bloc(0, "Bloc 1", 50, foyer, null);
        Bloc bloc2 = new Bloc(0, "Bloc 2", 100, foyer, null);
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        Chambre chambre1 = new Chambre(0, 101L, TypeChambre.SIMPLE, bloc1, null);
        Chambre chambre2 = new Chambre(0, 102L, TypeChambre.DOUBLE, bloc1, null);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        List<Chambre> chambres = chambreRepository.findByBlocNomBloc("Bloc 1");

        assertNotNull(chambres);
        assertEquals(2, chambres.size());
        assertTrue(chambres.stream().anyMatch(c -> c.getNumeroChambre() == 101L));
    }

    @Test
    void testCountByTypeCAndBlocIdBloc() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc A", 100, foyer, null);
        bloc = blocRepository.save(bloc);

        Chambre chambre1 = new Chambre(0, 101L, TypeChambre.SIMPLE, bloc, null);
        Chambre chambre2 = new Chambre(0, 102L, TypeChambre.SIMPLE, bloc, null);
        Chambre chambre3 = new Chambre(0, 103L, TypeChambre.DOUBLE, bloc, null);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);
        chambreRepository.save(chambre3);

        int count = chambreRepository.countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, bloc.getIdBloc());

        assertEquals(2, count);
    }

    @Test
    void testCountReservationsByIdChambreAndReservationsAnneeUniversitaireBetween() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc A", 100, foyer, null);
        bloc = blocRepository.save(bloc);

        Chambre chambre = new Chambre(0, 101L, TypeChambre.SIMPLE, bloc, null);
        chambre = chambreRepository.save(chambre);

        LocalDate dateDebutAU = LocalDate.of(2024, 9, 1);
        LocalDate dateFinAU = LocalDate.of(2024, 12, 31);

        int count = chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                chambre.getIdChambre(), dateDebutAU, dateFinAU);

        assertEquals(0, count); // Assuming no reservations for the chambre
    }

    @Test
    void testFindAvailableChambres() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc A", 100, foyer, null);
        bloc = blocRepository.save(bloc);

        Chambre chambre1 = new Chambre(0, 101L, TypeChambre.SIMPLE, bloc, null);
        Chambre chambre2 = new Chambre(0, 102L, TypeChambre.SIMPLE, bloc, null);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        LocalDate dateDebutAU = LocalDate.of(2024, 9, 1);
        LocalDate dateFinAU = LocalDate.of(2024, 12, 31);

        List<Chambre> availableChambres = chambreRepository.findAvailableChambres(
                "Foyer A", TypeChambre.SIMPLE, dateDebutAU, dateFinAU);

        assertNotNull(availableChambres);
        assertTrue(availableChambres.stream().anyMatch(c -> c.getNumeroChambre() == 101L));
    }
}

