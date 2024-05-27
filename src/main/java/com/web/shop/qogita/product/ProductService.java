package com.web.shop.qogita.product;

import com.web.shop.qogita.product.dto.ItemCustomerDTO;
import com.web.shop.qogita.product.dto.RandomCartDTO;
import com.web.shop.qogita.product.dto.SearchDTO;
import com.web.shop.qogita.product.dto.ShowcaseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Page<ShowcaseDTO> getShowcasePage(SearchDTO dto){
        Page<Object[]> result = productRepository.findAllByNameLikeShowcaseDTO(dto.getSearchLine(), dto.getPageable());
        ShowcaseDTO showcaseDTO = new ShowcaseDTO();
        return result.map(showcaseDTO::toDto);
    }

    public ItemCustomerDTO getItemPageById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
            return null;

        ItemCustomerDTO itemCustomerDTO = new ItemCustomerDTO();
        itemCustomerDTO = itemCustomerDTO.toDto(product.get());

        return itemCustomerDTO;
    }

    public List<RandomCartDTO> getRandomCards(){
        List<Product> products = productRepository.findRandomProducts();
        RandomCartDTO dto = new RandomCartDTO();
        return dto.toDtoList(products);
    }
}
