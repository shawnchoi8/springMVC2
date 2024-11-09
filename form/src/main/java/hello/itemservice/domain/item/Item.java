package hello.itemservice.domain.item;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean open; //판매 여부 (Sale status)
    private List<String> regions; //판매 지역 (Sales regions)
    private ItemType itemType; //상품 종류 (Item type)
    private String deliveryCode; //배송 방식 (Delivery method)

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
