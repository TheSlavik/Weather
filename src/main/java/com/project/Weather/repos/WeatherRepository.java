package com.project.Weather.repos;

import com.project.Weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    @Query(value = "select * from weather order by id desc limit 1", nativeQuery = true)
    Weather findRecent();

    @Query(value = "select round(avg(temp)), round(avg(wind)), round(avg(pressure)), round(avg(humidity)) " +
            "from weather where date(last_updated) between date(?) and date(?)", nativeQuery = true)
    String findAverage(String start, String end);
}
