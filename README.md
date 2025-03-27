[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vlo9idtn)
# lab1-wa2025 - Group 20

## External Libraries Used

- [`H3`](https://h3geo.org/): used to manage waypoints more efficiently.
- [`SnakeYAML`](https://github.com/snakeyaml/snakeyaml): used for processing YAML input files.
- [`KotlinX Serialization`](https://github.com/Kotlin/kotlinx.serialization): used to create JSON output files, utilizing the `@Serializable` annotation (linked to the `plugin.serialization` Gradle plugin).
- [`MockK`](https://github.com/mockk/mockk): used for testing the application.
- [`JUnit`](https://junit.org/junit5/): used for testing the application.

## Gradle Plugins Used

- `plugin.serialization`: used to create JSON output files, utilizing the `@Serializable` annotation (linked to the `KotlinX Serialization` library).
- `com.gradleup.shadow`: used to create the JAR file required to run the application on Docker.

## Implemented Features

### Required

- `maxDistanceFromStart`:  Calculates the farthest distance from the starting point of the route.
- `mostFrequentedArea`:  Identifies the most frequently visited area (e.g., a region where the user spends the most time or frequently passes through).
- `waypointsOutsideGeofence`: Checks how many waypoints fall outside a specified geo-fence (defined by a center point and radius).

### Extra

- `accurateMostFrequentedArea`: Identifies the most frequently visited area using a more accurate method based on the H3 grid system (the center of the area may not necessarily be a waypoint).
- `totalDistance`: Calculates the total distance of the route by summing the distances between consecutive waypoints.
- `distanceForRootType`: Calculates the distance traveled for each type of road (highway, county road, city road).
- `fuelConsumption`: Calculates the fuel consumption of a car for a specific distance, given its fuel efficiency.
- `averageSpeed`: Calculates the average speed along the route.
- `averageDirection`: Calculates the average direction of the route (North, North-West, West, South-West, South, South-East, East, North-East).

## How to run the application

### On Linux-based systems

```
    docker build -t lab1-g20 .
    docker run -it -v ./evaluation:/app/data lab1-g20
```

### On Windows systems

```
    docker build -t lab1-g20 .
    docker run -it -v .\evaluation:/app/data lab1-g20
```

### Input and output data

The input files `custom-parameters.yaml` and `waypoints.yaml` should be placed in the `evaluation` directory.
The output files will be generated in the same directory.