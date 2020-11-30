package project.models;

import project.controllers.OverviewController;

import java.io.IOException;

public class PhysicalDroneAdapter implements Animations {

    TelloDrone drone;

    public PhysicalDroneAdapter(TelloDrone drone) {
        this.drone = drone;
    }

    @Override
    public void visitItem(FarmItem item) throws IOException {
        drone.activateSDK();
        drone.takeoff();
        drone.increaseAltitude(100);

        //convert x,y to cm
        drone.gotoXY(0, 0, 150);
    }

    @Override
    public void scanFarm() throws IOException {
        drone.activateSDK();
        drone.takeoff();

    }
}