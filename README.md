# Connectivity Services Development (CSD)

## Getting started

### Requirements

- Android SDK
- JDK 17

### Build

- Android Application (all configurations)

  ```shell
  ./gradlew :mobile:assemble
  ```

- Android Automotive Application (all configurations)

  ```shell
  ./gradlew :automotive:assemble
  ```

## Project structure

- `automotive` module

  The module contains an implementation of the Android Automotive Application.

- `mobile` module

  The module contains an implementation of the Android Application.

- `shared` module

  The module contains shared components such as colors, fonts, use-cases, etc.

## Architecture

The implementation meets the principles of the Clean Architecture. The architecture is described in the following diagram:

![architecture-overview.png](docs/architecture-overview.png)
