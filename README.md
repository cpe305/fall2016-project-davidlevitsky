# fall2016-project-davidlevitsky

See Architecture(1).png and ModelViewPresenterOverview.png files for a visualization of the project's architecture. Please read this entire document prior to doing so.

This project allows a user to easily schedule events with contacts on their phone. A few background notes about Android which will help explain the architecture of this project:

1. Android has a concept of Activities. Each Activity essentially represents a screen, and it has two parts associated with it - the UI, which is defined by an XML file, as well as the Java code inside, which is the middle-man between the UI and logic present in other classes.
2. Activity is built into Android. Each Activity has an onCreate function, which defines the view that is inflated onto the screen. This can be manipulated with XML files.

Because an Activity acts as the middleman of communication between the screen (defined by XML files) and Java logic, the most fitting model to use as an architecture is model-view-presenter. The core logic of the application is defined by various Java files, such as Event, Contact, etc, and this composes the model. The Activity is the presenter - it processes user interaction, feeds data into the model, and then displays whatever output is generated. The XML files, which compose what the user will see on the screen, make up the view in this architecture model. They receive their data from the presenter.
