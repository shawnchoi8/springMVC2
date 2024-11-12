package hello.itemservice.domain.item;

public enum ItemType {

    BOOK("BOOK"), FOOD("FOOD"), ETC("ETC");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
