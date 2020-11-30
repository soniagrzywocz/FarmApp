package project.models;

public class ItemContainer extends FarmItem {

    // Root
    private boolean _root = false;

    public ItemContainer(int positionX, int positionY, int length, int width, int height, int price, int marketValue) {
        super("New_Container", positionX, positionY, length, width, height, price, marketValue);
    }

    public ItemContainer() {
        super("Farm", 0, 0, 800, 600, 0, 0, 0);
        // Flag as root
        this._root = true;
    }

    public boolean getRoot() { return this._root; }
}
