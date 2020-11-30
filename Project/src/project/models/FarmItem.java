package project.models;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class FarmItem extends TreeItem<FarmItem> implements Visitable {

    private String itemName;
    private int positionX;
    private int positionY;
    private int length;
    private int width;
    private int height;
    private int price;
    private int priceSum;
    private int marketValue;
    private int marketValueSum;

    private VBox element;

    public FarmItem(String name, int positionX, int positionY, int length, int width, int height, int price, int marketValue) {
        this.itemName = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.length = length;
        this.price = price;
        this.priceSum = price;
        this.marketValue = marketValue;
        this.marketValueSum = marketValue;

        // Create element
        createElement();
    }

    /**
     * Creates the element.
     *
     */
    private void createElement() {
        // Create text
        Text text = new Text(this.getItemName());
        text.setFont(Font.font(12));

        if (!(this instanceof Drone)) {
            // Create rectangle
            Rectangle rectangle = new Rectangle(this.getWidth(), this.getLength());
            rectangle.setFill(null);
            setItemBorder(rectangle);
            // Group rectangle and text
            VBox group = new VBox();
            group.getChildren().addAll(text, rectangle);
            group.setLayoutX(this.getPositionX());
            group.setLayoutY(this.getPositionY()-15); // Correct position due to text that is above
            // Assign element
            setElement(group);
        }
        else {
            ImageView droneImg = new ImageView(new Image("drone.png"));
            // Group rectangle and text
            VBox group = new VBox();
            group.getChildren().addAll(text, droneImg);
            group.setLayoutX(this.getPositionX());
            group.setLayoutY(this.getPositionY()-15); // Correct position due to text that is above
            // Assign element
            setElement(group);
        }
    }

    /**
     * Sets the border of a rectangle of a specific type.
     *
     * @param rectangle The rectangle for which the border is set for.
     */
    private void setItemBorder(Rectangle rectangle) {
        // Set border color
        if (this instanceof ItemContainer) rectangle.setStroke(Color.GREEN);
        else if (this instanceof Item) rectangle.setStroke(Color.RED);
        // Set border stroke width
        rectangle.setStrokeWidth(3);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


    /* GETTERS */
    public String getItemName() { return itemName; }
    public int getPositionX() { return positionX;  }
    public int getPositionY() { return positionY; }
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getPrice() { return price; }
    public int getPriceSum() { return priceSum; }
    public int getMarketValue() { return marketValue; }
    public int getMarketValueSum() { return marketValueSum; }
    public VBox getElement() { return element; }

    /* SETTERS */
    public void setItemName(String itemName) {
        this.itemName = itemName;
        ((Text) this.getElement().getChildren().get(0)).setText(itemName);
    }
    public void setPositionX(int pos) {
        this.positionX = pos;
        this.getElement().setLayoutX(pos);
    }
    public void setPositionY(int pos) {
        this.positionY = pos;
        this.getElement().setLayoutY(pos-15); // Correct position due to text that is above
    }
    public void setLength(int length) {
        this.length = length;
        ((Rectangle) this.getElement().getChildren().get(1)).setWidth(length);
    }
    public void setWidth(int width) {
        this.width = width;
        ((Rectangle) this.getElement().getChildren().get(1)).setHeight(width);
    }
    public void setHeight(int height) { this.height = height; }
    public void setPrice(int price) { this.price = price; }
    public void setPriceSum(int priceSum) { this.priceSum = priceSum; }
    public void setMarketValue(int marketValue) { this.marketValue = marketValue; }
    public void setMarketValueSum(int marketValueSum) { this.marketValueSum = marketValueSum; }
    public void setElement(VBox element) { this.element = element; }

    @Override
    public String toString() {
        return getItemName();
    }
}
