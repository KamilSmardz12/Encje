package pl.rental;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
class BikeService {

    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Transactional
    public void add(NewBikeDto newBike) {
        Bike bike = new Bike(newBike.getId(),
                newBike.getModel(),
                newBike.getSerialNo(),
                newBike.getHourPrice(),
                newBike.getDayPrice());
        bikeRepository.save(bike);
    }

    @Transactional
    public void deleteById(Long bikeId) {
        bikeRepository.deleteById(bikeId);
    }

    @Transactional
    public double rentForHours(Long bikeId, int hours, String borrowerId) {
        LocalDateTime dateOfReturn = LocalDateTime.now().plusHours(hours);
        double hourPrice = updateBike(bikeId, dateOfReturn, borrowerId).getHourPrice();
        return hourPrice * hours;
    }

    @Transactional
    public double rentForDay(Long bikeId, String borrowerId) {
        LocalDateTime dateOfReturn = LocalDateTime.now().withHour(23).withMinute(59);
        return updateBike(bikeId, dateOfReturn, borrowerId).getDayPrice();
    }

    @Transactional
    public void returnBike(Long bikeId) {
        updateBike(bikeId, null, null);
    }

    private Bike updateBike(Long bikeId, LocalDateTime dateOfReturn, String borrowerId) {
        Bike bike = bikeRepository
                .findById(bikeId)
                .orElseThrow(BikeNotFoundException::new);
        bike.setDateOfReturn(dateOfReturn);
        bike.setBorrowerId(borrowerId);
        return bike;
    }

    @Transactional
    public void pobierzNiewyporzyczoneRowery() {
        System.out.println(bikeRepository.pobierz(100.));

        System.out.println(bikeRepository.findAllByDayPriceIsGreaterThan(100.));

        System.out.println(bikeRepository.exist(100.));

        System.out.println(bikeRepository.existInt(100.));

        System.out.println(bikeRepository.existsAllByDayPriceIsGreaterThanEqual(100.));

        bikeRepository.updateDayPrice();
        bikeRepository.pobierz(-1.).forEach(System.out::println);

        if (1 == 1)
            throw new RuntimeException();
        bikeRepository.updateDayPrice();

    }
}
