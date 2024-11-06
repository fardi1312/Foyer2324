package tn.esprit.spring.BlocChambreTests.Bloc;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocServiceTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    private Bloc bloc;
    private Chambre chambre;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        bloc = new Bloc(1L, "Bloc A", 100, null, null);
        chambre = new Chambre(1L, 101L, TypeChambre.SIMPLE, bloc, null);
        foyer = new Foyer(1L, "Foyer A", 200, null, null);
    }

    @Test
    void testGreet() {
        String message = blocService.greet();
        assertEquals("Hello, World!", message);
    }


    @Test
    void testFindAll() {
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc));

        List<Bloc> blocs = blocService.findAll();

        assertNotNull(blocs);
        assertEquals(1, blocs.size());
        assertEquals(bloc, blocs.get(0));
    }

    @Test
    void testFindById() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));  // Correct: Returning Optional of bloc

        Bloc foundBloc = blocService.findById(1L);

        assertNotNull(foundBloc);
        assertEquals(bloc.getIdBloc(), foundBloc.getIdBloc());
    }

    @Test
    void testFindByIdNotFound() {
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());  // Correct: Returning Optional.empty()

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            blocService.findById(1L);
        });

        assertEquals("Bloc with id 1 not found", thrown.getMessage());
    }

    @Test
    void testDeleteById() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.deleteById(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAffecterChambresABloc() {
        List<Long> chambreNumbers = Arrays.asList(101L);
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);  // Bloc should be returned correctly
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);  // Directly return the Chambre object
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Bloc updatedBloc = blocService.affecterChambresABloc(chambreNumbers, "Bloc A");

        assertNotNull(updatedBloc);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testAffecterBlocAFoyer() {
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer("Foyer A")).thenReturn(foyer);  // Return the Foyer object directly, not Optional
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc updatedBloc = blocService.affecterBlocAFoyer("Bloc A", "Foyer A");

        assertNotNull(updatedBloc);
        assertEquals(foyer, updatedBloc.getFoyer());
        verify(blocRepository, times(1)).save(bloc);
    }
}
