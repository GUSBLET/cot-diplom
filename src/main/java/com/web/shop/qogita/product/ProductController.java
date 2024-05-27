package com.web.shop.qogita.product;

import com.web.shop.qogita.product.dto.ItemCustomerDTO;
import com.web.shop.qogita.product.dto.SearchDTO;
import com.web.shop.qogita.product.enums.Filtering;
import com.web.shop.qogita.technical.model.attribute.ModelAttributeManager;
import com.web.shop.qogita.technical.model.attribute.ModelPageAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/")
    public String getShowcase(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "44") int size,
                              @RequestParam(name = "searchLine", defaultValue = "") String searchLine,
                              @RequestParam(name = "brand", defaultValue = "") String brand,
                              @RequestParam(name = "minPrice", defaultValue = "") Float minPrice,
                              @RequestParam(name = "maxPrice", defaultValue = "") Float maxPrice,
                              @RequestParam(name = "priceSort", defaultValue = "") Filtering priceFiltering,
                              Model model) {
        ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Showcase")
                .content("showcase-page")
                .entity(productService.getShowcasePage(
                        SearchDTO.builder()
                                .searchLine(searchLine)
                                .brand(brand)
                                .minPrice(minPrice)
                                .maxPrice(maxPrice)
                                .priceFiltering(priceFiltering)
                                .pageable(PageRequest.of(page, size))
                                .build()))
                .build());
        return "layout";
    }

    @GetMapping("/item")
    public String search(@RequestParam("id") Long id,
                         Model model) {
        ItemCustomerDTO dto = productService.getItemPageById(id);
        if(dto == null) {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Error")
                    .content("message-page")
                    .entity("Item is not found")
                    .build());
        } else {
            model.addAttribute("randoms", productService.getRandomCards());
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title(dto.getName())
                    .content("item-page")
                    .entity(dto)
                    .build());
        }

        return "layout";
    }
}
