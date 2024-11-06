package tn.esprit.spring.BlocChambreTests.Bloc;

import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Foyer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;


    @Test
    void testSaveBloc() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc 1", 50, foyer, null);
        bloc = blocRepository.save(bloc);

        assertNotNull(bloc);
        assertNotNull(bloc.getIdBloc());
        assertEquals("Bloc 1", bloc.getNomBloc());
        assertEquals(50, bloc.getCapaciteBloc());
        assertNotNull(bloc.getFoyer());
        assertEquals(foyer.getIdFoyer(), bloc.getFoyer().getIdFoyer());
    }

    @Test
    void testFindByNomBloc() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc A", 100, foyer, null);
        blocRepository.save(bloc);

        Bloc foundBloc = blocRepository.findByNomBloc("Bloc A");

        assertNotNull(foundBloc);
        assertEquals("Bloc A", foundBloc.getNomBloc());
    }

    @Test
    void testFindAllBlocs() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc1 = new Bloc(0, "Bloc 1", 50, foyer, null);
        Bloc bloc2 = new Bloc(0, "Bloc 2", 100, foyer, null);
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocRepository.findAll();

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
    }

    @Test
    void testDeleteBloc() {
        Foyer foyer = new Foyer(0, "Foyer A", 200, null, null);
        foyer = foyerRepository.save(foyer);

        Bloc bloc = new Bloc(0, "Bloc to Delete", 50, foyer, null);
        bloc = blocRepository.save(bloc);

        blocRepository.delete(bloc);

        Optional<Bloc> deletedBloc = blocRepository.findById(bloc.getIdBloc());
        assertFalse(deletedBloc.isPresent());
    }
}
