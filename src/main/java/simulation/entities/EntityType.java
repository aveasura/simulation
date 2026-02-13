package simulation.entities;

public enum EntityType {
    RABBIT('R'),
    FOX('F'),
    GRASS('G'),
    MOUNTAIN('M'),
    TREE('T');

    private final char symbol;

    EntityType(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }
}
