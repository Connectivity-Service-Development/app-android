# Connectivity Services Development (CSD)

## Getting started

### Requirements

- Android SDK
- JDK 17

### Build

- Android Application (all configuration)
  `./gradlew :mobile:assemble`

- Android Automotive Application (all configuration)
  `./gradlew :automotive:assemble`

## Project structure

- `automotive` module
  The module contains implementation of the Android Automotive Application

- `mobile` module
  The module contains implementation of the Android Application

- `shared` module
  The module contains shared components like colors, fonts, use-cases etc.

## Architecture

The implementation meets the principles of the Clean Architecture. The architecture is described in the following diagram:

![architecture-overview.png](docs/architecture-overview.png)
