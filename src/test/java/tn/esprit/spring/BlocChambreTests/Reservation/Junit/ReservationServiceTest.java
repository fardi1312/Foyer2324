package tn.esprit.spring.BlocChambreTests.Reservation.Junit;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;
import tn.esprit.spring.Services.Reservation.ReservationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    private Reservation testReservation;
    private Chambre testChambre;
    private Etudiant testEtudiant;

    @BeforeEach
    public void setUp() {
        // Initialize and save a Chambre for testing
        testChambre = new Chambre();
        testChambre.setNumeroChambre(101L);
        testChambre.setTypeC(TypeChambre.SIMPLE);
        chambreRepository.save(testChambre);

        // Initialize and save an Etudiant for testing
        testEtudiant = new Etudiant();
        testEtudiant.setCin(123456L);
        etudiantRepository.save(testEtudiant);

        // Initialize a Reservation entity
        testReservation = new Reservation();
        testReservation.setIdReservation("2023/2024-Test");
        testReservation.setAnneeUniversitaire(LocalDate.now());
        testReservation.setEstValide(false);
        reservationRepository.save(testReservation);
    }

    @Test
    void testAddOrUpdateReservation() {
        Reservation newReservation = new Reservation();
        newReservation.setIdReservation("2023/2024-New");
        newReservation.setAnneeUniversitaire(LocalDate.now());
        newReservation.setEstValide(true);

        Reservation savedReservation = reservationService.addOrUpdate(newReservation);

        assertNotNull(savedReservation);
        assertEquals("2023/2024-New", savedReservation.getIdReservation());
        assertTrue(reservationRepository.existsById(savedReservation.getIdReservation()));
    }

    @Test
    void testFindById() {
        Reservation foundReservation = reservationService.findById(testReservation.getIdReservation());

        assertNotNull(foundReservation);
        assertEquals("2023/2024-Test", foundReservation.getIdReservation());
    }

    @Test
    void testDeleteById() {
        reservationService.deleteById(testReservation.getIdReservation());

        assertThrows(EntityNotFoundException.class, () -> reservationService.findById(testReservation.getIdReservation()));
    }


    @Test
    void testAnnulerReservation() {
        // Assume a reservation exists and is valid
        testReservation.setEstValide(true);
        testReservation.getEtudiants().add(testEtudiant);
        reservationRepository.save(testReservation);

        String cancelMessage = reservationService.annulerReservation(testEtudiant.getCin());

        assertEquals("La réservation 2023/2024-Test est annulée avec succès", cancelMessage);
        assertFalse(reservationRepository.existsById(testReservation.getIdReservation()));
    }
}
