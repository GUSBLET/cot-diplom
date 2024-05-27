package com.web.shop.qogita.technical.model.attribute;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModelPageAttributes<T> {

    String title;
    String content;
    T entity;
}
