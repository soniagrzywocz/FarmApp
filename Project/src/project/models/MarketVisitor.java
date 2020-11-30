package project.models;

public class MarketVisitor implements Visitor {

    @Override
    public int visit(FarmItem item) {
        int currentValue = item.getMarketValue();
        return currentValue;
    }
}
