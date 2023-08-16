package com.example.itemservice.web.item.basic;

import com.example.itemservice.domain.item.Item;
import com.example.itemservice.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/items")
@Slf4j
public class BasicItemController {
    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(Item.builder()
            .itemName("itemA")
            .price(10000)
            .quantity(10)
            .build());

        itemRepository.save(Item.builder()
            .itemName("itemB")
            .price(20000)
            .quantity(20)
            .build());
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addFormView() {
        return "basic/addForm";
    }

    @PostMapping("/add/v1")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam int quantity, Model model) {
        log.info("/addItemV1");

        Item item = Item.builder()
            .itemName(itemName)
            .price(price)
            .quantity(quantity)
            .build();

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/add/v2")
    public String addItemV2(@ModelAttribute("item") Item item) {
        log.info("/addItemV2");

        itemRepository.save(item);

        // @ModelAttribute 를 통해서 자동적으로 Model이 추가됨

        return "basic/item";
    }

    @PostMapping("/add/v3")
    public String addItemV3(@ModelAttribute Item item) {
        log.info("/addItemV3");

        itemRepository.save(item);

        // @ModelAttribute.name 을 생략했을 경우, 클래스의 첫글자만 소문자로 변경해서 등록한다.

        return "basic/item";
    }

    @PostMapping("/add/v4")
    public String addItemV4(Item item) {
        log.info("/addItemV4");

        itemRepository.save(item);

        // @ModelAttribute 생략 가능, 생략해도 자동으로 Model에 등록됨

        return "basic/item";
    }

    @PostMapping("/add/v5")
    public String addItemV5(Item item) {
        log.info("/addItemV5");

        itemRepository.save(item);

        // POST - Redirect - GET (PRG)
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        log.info("/addItemV6");

        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);

        // 나머지 redirectAttributes 는 쿼리 파라미터로 전달된다.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);


        // Controller 에 매핑된 @PathVariable 값은 redirect 에도 사용할 수 있다.
        return "redirect:/basic/items/{itemId}";
    }
}
