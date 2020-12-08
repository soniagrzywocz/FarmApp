# Software Engineering Project

## **Description**
Build a dashboard to setup items on the farm. For each item users should be able to provide basic details such as location coordinates, dimensions, price and others. Setting up these items will allow 1) drones to create automated flight paths; and 2) machine learning algorithms to make a variety of predictions. Although implementing machine learning is not the focus of this course we are building the dashboard interface assuming that machine learning algorithms are implemented by a different team in the company. The dashboard should implement singleton, composite design patterns and create a visualization of the items added to the farm.

## **Workflow**

Everything stems from our MainApp javafx class located inside the first package called, "project". We have three files with fxml extensions that allow us to create this farm interface application. Together with their controller classes and real-world model classes, the application with function as specified in the requirements.

### **Setting Up Development Environment**

- Clone the repo and `cd` into it.
- You'll need to decide on the IDE you're working with and open the project up with the cloned repo.
- **Installs**
    - **`Java SDK 8`**: You can get away with having a higher version than 8 but some features may not be backwards compatible
    - **`Java FX`**: Make sure to install javafx in your environment
- **Configuration (IntelliJ)**
 - Point the Main class to be `project.MainApp`.
 - Point the working directory to the `Software-Engineering` directory (root directory of this repo). Also allow for `Use classpath of module` to point to the same directory.
 - Point the JRE to your preferred SDK (we chose 8).
 - Ensure you also provide a library path for your SDK in file -> project structure -> libraries

### **Running**

- Ensure everything specific to your editor is set up correctly. Then, you should be able to point the MainApp javafx class as the entry point and click `Run`. An interface should pop-up allowing you to use this application.

## Authors

 Sonia Grzywocz (grzywocz@uab.edu), Cameron Wood (cwood11@uab.edu), Tom El Safadi (ksafadi@uab.edu), Meet Patel (meet19@uab.edu), Brittany Latham (ladyb28@uab.edu)
