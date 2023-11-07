package ru.liga.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.commons.exception.ServerException;

/**
 * Класс, который помогает определять район доставки по координатам (тестовый вариант)
 */
@Service
@Slf4j
public class LocalityDeterminantImpl implements LocalityDeterminant {

    public String getLocation(String coordinate) {
        double latitudeCoordinate, longitudeCoordinate;
        try {
            latitudeCoordinate = Double.parseDouble(coordinate
                    .substring(1, coordinate.indexOf(',')));
            longitudeCoordinate = Double.parseDouble(coordinate
                    .substring(coordinate.indexOf(',') + 1, coordinate.length() - 1));
        } catch (NumberFormatException numberFormatException) {
            throw new ServerException();
        }
        log.info("LocalityDeterminant: longitudeCoordinate = " + longitudeCoordinate
                + " latitudeCoordinate = " + latitudeCoordinate);
        if (longitudeCoordinate - latitudeCoordinate <= 0) {
            log.info("LocalityDeterminant: district Nizhegorodsky");
            return "Nizhegorodsky";
        } else if (longitudeCoordinate - latitudeCoordinate >= 20) {
            log.info("LocalityDeterminant: district Sovetsky");
            return "Sovetsky";
        } else {
            log.info("LocalityDeterminant: district Prioksky");
            return "Prioksky";
        }
    }

}
