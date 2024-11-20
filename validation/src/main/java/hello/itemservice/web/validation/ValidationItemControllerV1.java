package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    /**
     * 1. Type Validation: Ensures that price and quantity are numeric.
     *
     * 2. Field Validation: Product name: Required and must not be blank.
     *                      Price: Must be between 1,000 and 1,000,000 KRW.
     *                      Quantity: Must not exceed 9,999
     *
     * 3. Combined Field Validation: The total (price * quantity) must be at least 10,000 KRW.
     */

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과를 보관(Store validation error results)
        Map<String, String> errors = new HashMap<>();

        //검증 로직(Validation logic)
        if(!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "Item name is required");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "Price must be between 1000 won and 1000000 won");
        }
        if(item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.put("quantity", "Quantity must not exceed 9999");
        }

        //특정 필드가 아닌 복합 룰 검증(Validation for combined rules, not specific fields)
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
                errors.put("globalError", "The total of price × quantity must be at least 10,000 KRW. Current total = "+ resultPrice);
            }
        }

        //검증 실패하면 다시 입력폼으로 돌아감(If validation fails, return to the addForm.
        if(!errors.isEmpty()) {
            log.info("errors: {}", errors);
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }

        //검증 성공(Validation succeeded)
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

