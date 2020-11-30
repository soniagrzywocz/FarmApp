package project.models;

public class PriceVisitor implements Visitor {

    @Override
    public int visit(FarmItem item) {
        int currentPrice = item.getPrice();
        return currentPrice;
    }
}
