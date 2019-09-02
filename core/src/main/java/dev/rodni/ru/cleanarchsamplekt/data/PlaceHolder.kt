package dev.rodni.ru.cleanarchsamplekt.data

/*
This layer provides abstract definitions for accessing data sources like a database or the internet.
I’ll use Repository pattern in this layer.

The main purpose of the repository pattern is to abstract away the concrete implementation of data access.
To achieve this, I’ll add one interface and one class for each model.


Using the repository pattern is a good example of the Dependency Inversion Principle because:

A Data layer which is of a higher, more abstract level doesn’t depend on a framework, lower-level layer.
The repository is an abstraction of Data Access and it does not depend on details. It depends on abstraction.
 */