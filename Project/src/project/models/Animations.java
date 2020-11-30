package project.models;

import java.io.IOException;

public interface Animations {

    void visitItem(FarmItem item) throws IOException;

    void scanFarm() throws IOException;
}
