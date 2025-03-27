[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vlo9idtn)
# lab1-wa2025 - Group 20

## External Libraries Used

- `H3`(https://h3geo.org/): used for manage the waypoints in a more efficient way.
- `SnakeYAML`(https://github.com/snakeyaml/snakeyaml): used for processing the yaml input file.
- `KotlinX Serialization`(https://github.com/Kotlin/kotlinx.serialization): used for create the json output file, 
using the `@Serializable` annotation (linked with the `plugin.serialization` Gradle plugin).
- `MockK`(https://github.com/mockk/mockk): used for testing the application.
- `JUnit`(https://junit.org/junit5/): used for testing the application.

## Gradle Plugins Used

- `plugin.serialization`: used for create the json output file, using the `@Serializable` annotation 
(linked with the `KotlinX Serialization` library).
- `com.gradleup.shadow`: used for create the jar file that will be used to run the application on Docker.

## Implemented Features

### Required

- `maxDistanceFromStart`:  Calculate the farthest distance from the starting point of the route.
- `mostFrequentedArea`:  Identify the area most frequently visited (e.g., a region where the user spends the 
most time or passes the most)
- `waypointsOutsideGeofence`: Check how many waypoints fall outside a specified geo-fence (defined by a centre 
point and radius).

### Extra

- `accurateMostFrequentedArea`: Identify the area most frequently visited, using a more accurate method based on the 
H3 grid system (the center of the area could also not be a waypoint).
- `totalDistance`: Calculate the total distance of the route by summing the distance between consecutive waypoints.
- `distanceForRootType`: Calculate the distance travelled for each type of road (highway, county road, city road).
- `fuelConsumption`: Calculate the fuel consumption of a car for a specific distance, given its fuel efficiency.
- `averageSpeed`: Calculate the average speed of the route.
- `averageDirection`: Calculate the average direction of the route (North, North-West, West, South-West, South, 
South-East, East, North-East).

## How to run the application

```
    docker build -t lab1-g20 .
    docker run -it -v ./evaluation:/app/data lab1-g20
```