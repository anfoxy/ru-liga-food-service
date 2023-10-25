package ru.liga.kitchen_service.service;

public class DistanceCalculator {

    private static final double earthRadius = 6371.0;

    public static double calculator(String coordinateCourier,String coordinateLocation ){
        double latitudeCoordinateCourier = Double.parseDouble(coordinateCourier.substring(1, coordinateCourier.indexOf(',')));
        double longitudeCoordinateCourier = Double.parseDouble(coordinateCourier.substring(coordinateCourier.indexOf(',') + 1, coordinateCourier.length() - 1));

        double latitudeCoordinateLocation = Double.parseDouble(coordinateLocation.substring(1, coordinateLocation.indexOf(',')));
        double longitudeCoordinateLocation = Double.parseDouble(coordinateLocation.substring(coordinateLocation.indexOf(',') + 1, coordinateLocation.length() - 1));

        double distance = calculator(latitudeCoordinateCourier, longitudeCoordinateCourier, latitudeCoordinateLocation, longitudeCoordinateLocation);
        return distance;
    }

    public static double calculator(double latitudeCoordinateCourier,
                                   double longitudeCoordinateCourier,
                                   double latitudeCoordinateLocation,
                                   double longitudeCoordinateLocation) {
        double radiansLatitude = Math.toRadians(latitudeCoordinateLocation - latitudeCoordinateCourier);
        double radiansLongitude = Math.toRadians(longitudeCoordinateLocation - longitudeCoordinateCourier);

        double radiansLatitudeCoordinateCourier = Math.toRadians(latitudeCoordinateCourier);
        double radiansLatitudeCoordinateLocation = Math.toRadians(latitudeCoordinateLocation);

        double formula = Math.pow(Math.sin(radiansLatitude / 2), 2)
                + Math.pow(Math.sin(radiansLongitude / 2), 2)
                * Math.cos(radiansLatitudeCoordinateCourier)
                * Math.cos(radiansLatitudeCoordinateLocation);
        double haversinusFormula = 2 * Math.atan2(Math.sqrt(formula), Math.sqrt(1 - formula));
        double distance = earthRadius * haversinusFormula;
        return distance;
    }
}
