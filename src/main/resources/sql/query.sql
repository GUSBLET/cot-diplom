CREATE SEQUENCE roles_seq;
CREATE SEQUENCE tokens_seq;


CREATE TABLE ACCOUNTS
(
    ID            UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    EMAIL         VARCHAR(150) NOT NULL UNIQUE,
    PASSWORD      VARCHAR,
    IS_ENABLED    BOOLEAN DEFAULT TRUE,
    IS_NON_LOCKED BOOLEAN DEFAULT TRUE,
    CREATED_ON    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_ON    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ROLES
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    role VARCHAR(50) NOT NULL
);


INSERT INTO ROLES (id, role)
VALUES (gen_random_uuid(), 'ADMIN'),
       (gen_random_uuid(), 'CUSTOMER');



CREATE TABLE ROLE_ACCOUNTS
(
    account_id UUID,
    role_id    UUID,
    PRIMARY KEY (account_id, role_id),
    FOREIGN KEY (account_id) REFERENCES ACCOUNTS (id),
    FOREIGN KEY (role_id) REFERENCES ROLES (id)
);

INSERT INTO ACCOUNTS (EMAIL, PASSWORD, IS_ENABLED, IS_NON_LOCKED)
VALUES ('admin@gmail.com', '123', TRUE, TRUE);

INSERT INTO ROLE_ACCOUNTS (ACCOUNT_ID, ROLE_ID)
SELECT a.ID, r.ID
FROM ACCOUNTS a
         CROSS JOIN ROLES r
WHERE a.EMAIL = 'admin@gmail.com' AND r.role = 'ADMIN';

CREATE TABLE PRODUCTS (
                          ID              BIGSERIAL PRIMARY KEY,
                          VENDOR          VARCHAR NOT NULL,
                          BRAND           VARCHAR NOT NULL,
                          GTIN            VARCHAR NOT NULL,
                          VARIANT_ID      INTEGER,
                          NAME            VARCHAR NOT NULL,
                          DESCRIPTION     JSONB,
                          PRICES          JSONB NOT NULL,
                          CHARACTERISTICS JSONB,
                          PROPERTIES      JSONB,
                          IN_STOCK        BOOLEAN NOT NULL,
                          IMAGES          JSONB NOT NULL,
                          CATEGORIES      JSONB,
                          PRODUCT_STATE   JSONB NOT NULL
);

CREATE TABLE TOKENS
(
    ID             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    TOKEN          VARCHAR(36) NOT NULL,
    CREATION_TIME  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LIFETIME       TIMESTAMP,
    OPERATION_TYPE VARCHAR(50) NOT NULL,
    ACCOUNT_ID     UUID,
    FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS (id)
);

CREATE TABLE ORDERS
(
    ID            BIGSERIAL PRIMARY KEY,
    STATUS        VARCHAR(60) NOT NULL,
    CREATION_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ARRIVING_TIME TIMESTAMP,
    COMMON_PRICE  DECIMAL(10, 2),
    FINAL_PRICE   DECIMAL(10, 2),
    ACCOUNT_ID    UUID REFERENCES ACCOUNTS (ID)
);

CREATE TABLE ORDERED_PRODUCTS
(
    ID         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    AMOUNT     INTEGER,
    PRICE      DECIMAL(10, 2),
    PRODUCT_ID BIGINT REFERENCES PRODUCTS (ID),
    ORDER_ID   BIGINT REFERENCES ORDERS (ID)
);


INSERT INTO PRODUCTS (VENDOR, BRAND, GTIN, NAME, DESCRIPTION, PRICES, CHARACTERISTICS, PROPERTIES, IN_STOCK, IMAGES, CATEGORIES, PRODUCT_STATE)
VALUES
    ('QOGITA', 'Clinique', '0020714999346', 'Aromatics Elixir by Clinique Eau de Parfum For Women 100ml', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "28.03"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/ewtaZYerwnQdZiBvwXyKbq.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Clinique', '0020714156893', 'Clinique Happy for Women EDP Spray 3.4 Fl Oz', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "20.32"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/CHMijwCen9aWUHwuTAyQCg.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Issey Miyake', '3423470311365', 'L''Eau d''Issey For Men Eau De Toilette 125ml', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "38.22"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/2YjSWKZGs8Dtpaff7KmpdR.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Versace', '8011003858545', 'Versace Dylan Turquoise Eau De Toilette Spray 50ml', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/BoY8V5RMa3UvLWFee7jBp5.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }');


INSERT INTO PRODUCTS (VENDOR, BRAND, GTIN, NAME, DESCRIPTION, PRICES, CHARACTERISTICS, PROPERTIES, IN_STOCK, IMAGES, CATEGORIES, PRODUCT_STATE)
VALUES
    ('QOGITA', 'Aramis', 'No GTIN', 'Aramis Eau de Toilette Aramis 110 ml for Men', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/RWtZw9jbUoGoGw4sW4KpEb.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Clinique', 'No GTIN', 'Clinique Aromatics Elixir 45 ml Eau de Parfum Women''s Perfume', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/DyYku5FydF5PWhxdan8isb.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Gloria Vanderbilt', 'No GTIN', 'Gloria Vanderbilt 100 ml Eau de Toilette Women''s Perfume', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/5yQ3kvzun633kVhvtrpzKt.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Revlon', 'No GTIN', 'Revlon Colorstay Liquid Foundation Makeup For Combination/Oily Skin SPF 15 Medium Full Coverage with Matte Finish 30ml 250 Fresh Beige', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/mj4Q3S3cEHdGvWCWpnUSsd.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Elizabeth Arden', 'No GTIN', 'Elizabeth Arden 5th Avenue 125 ml Eau de Parfum Women''s Perfume', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/gBaVqXfLCx5mxtAKQrxdgS.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Lanvin', 'No GTIN', 'Lanvin Eclat d''Arpege Eau de Parfum Spray for Women 100ml', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/RYwNZCmvBCwLPh7Y8dLbvk.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Clinique', 'No GTIN', 'Clinique Happy for Women 50ml EDP Floral', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/J7ogDBFxrruAMVe6Mapagn.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Clinique', 'No GTIN', 'Cliniqu Happy Men EDT M', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/jngu6xPCHPXFuoAVzY9o8X.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Hermes', 'No GTIN', 'Hermes Un Jardin Sur Le Nil EDT 50 ml Spray Bottle', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/Fv8ns8B3vmxj9yi87fXNHc.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }'),

    ('QOGITA', 'Issey Miyake', 'No GTIN', 'Issey Miyake L''Eau d''Issey Pour Homme Eau de Toilette Vapo', NULL,
     '{
       "PURCHASE_PRICE": 0,
       "RETAIL_PRICE": 0,
       "SALES_PRICE": "29.19"
     }', NULL, NULL, true,
     '["https://static.prod.qogita.com/files/images/variants/Cywc3TmkEvybncg6LHgoGF.jpg"]',
     NULL,
     '{
       "CREATED_ON": "2024-03-12 10:52:30.276",
       "UPDATED_ON": "2024-03-12 10:52:30.276"
     }');