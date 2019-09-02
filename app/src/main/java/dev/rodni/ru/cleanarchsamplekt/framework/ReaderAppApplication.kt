package dev.rodni.ru.cleanarchsamplekt.framework

import android.app.Application

class ReaderAppApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    ReaderAppViewModelFactory.inject(
        this,
        Interactors()
    )
  }
}

//notes from the internet. just to remember the rules

/*
I’ll use five layers:

1. Presentation: A layer that interacts with the UI.
2. Use cases: Sometimes called interactors. Defines actions the user can trigger.
3. Domain: Contains the business logic of the app.
4. Data: Abstract definition of all the data sources.
5. Framework: Implements interaction with the Android SDK and provides concrete implementations for the data layer.
 */

/*NOTE CLEAN
The center circle is the most abstract, and the outer circle is the most concrete.
This is called the Abstraction Principle.
The Abstraction Principle specifies that inner circles should contain business logic,
and outer circles should contain implementation details.

Another principle of Clean Architecture is the Dependency Rule.
This rule specifies that each circle can depend only on the nearest inward circle —
this is what makes the architecture work.

Parts of the code get decoupled, and easier to reuse and test.

There’s a method to the madness.
When someone else works on your code, they can learn the app’s architecture and will understand it better.
 */

/*
SOLID Principles
Five design principles make software design more understandable, flexible and maintainable. Those principles are:

Single Responsibility:
Each software component should have only one reason to change – one responsibility.

Open-closed:
You should be able to extend the behavior of a component, without breaking its usage, or modifying its extensions.

Liskov Substitution:
If you have a class of one type, and any subclasses of that class,
you should be able to represent the base class usage with the subclass, without breaking the app.

Interface Segregation:
It’s better to have many smaller interfaces than a large one,
to prevent the class from implementing the methods that it doesn’t need.

Dependency Inversion:
Components should depend on abstractions rather than concrete implementations.
Also higher level modules shouldn’t depend on lower level modules.
 */