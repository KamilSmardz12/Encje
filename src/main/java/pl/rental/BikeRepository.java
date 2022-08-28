package pl.rental;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

interface BikeRepository extends CrudRepository<Bike, Long> {

    List<Bike> findAllByDayPriceIsGreaterThan(double minDayPrice);

    @Query(value = "select * from BIKE where day_price > :minDayPrice", nativeQuery = true)
    List<Bike> pobierz(double minDayPrice);
    @Query(value = "select 1=1 from BIKE where day_price > :minDayPrice", nativeQuery = true)
    boolean exist(Double minDayPrice);
    @Query(value = "select 1 from BIKE where day_price > :minDayPrice", nativeQuery = true)
    int existInt(double minDayPrice);

    @Transactional
    boolean existsAllByDayPriceIsGreaterThanEqual(Double minPrice);

    @Modifying
    @Transactional
    @Query(value = "update BIKE set DAY_PRICE = DAY_PRICE + 2", nativeQuery = true)
    void updateDayPrice();

}
