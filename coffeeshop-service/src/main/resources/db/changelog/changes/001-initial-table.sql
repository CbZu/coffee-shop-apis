DROP TABLE IF EXISTS coffeeshop.coffeeshops CASCADE;

CREATE TABLE coffeeshop.coffeeshops
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT coffeeshops_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS approval_status;

CREATE TYPE approval_status AS ENUM ('APPROVED', 'REJECTED');

DROP TABLE IF EXISTS coffeeshop.order_approval CASCADE;

CREATE TABLE coffeeshop.order_approval
(
    id uuid NOT NULL,
    coffeeshop_id uuid NOT NULL,
    order_id uuid NOT NULL,
    status approval_status NOT NULL,
    CONSTRAINT order_approval_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS coffeeshop.products CASCADE;

CREATE TABLE coffeeshop.products
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    price numeric(10,2) NOT NULL,
    available boolean NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS coffeeshop.coffeeshop_products CASCADE;

CREATE TABLE coffeeshop.coffeeshop_products
(
    id uuid NOT NULL,
    coffeeshop_id uuid NOT NULL,
    product_id uuid NOT NULL,
    CONSTRAINT coffeeshop_products_pkey PRIMARY KEY (id)
);

ALTER TABLE coffeeshop.coffeeshop_products
    ADD CONSTRAINT "FK_COFFEESHOP_ID" FOREIGN KEY (coffeeshop_id)
    REFERENCES coffeeshop.coffeeshops (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE coffeeshop.coffeeshop_products
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
    REFERENCES coffeeshop.products (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

DROP MATERIALIZED VIEW IF EXISTS coffeeshop.order_coffeeshop_m_view;

CREATE MATERIALIZED VIEW coffeeshop.order_coffeeshop_m_view
TABLESPACE pg_default
AS
 SELECT r.id AS coffeeshop_id,
    r.name AS coffeeshop_name,
    r.active AS coffeeshop_active,
    p.id AS product_id,
    p.name AS product_name,
    p.price AS product_price,
    p.available AS product_available
   FROM coffeeshop.coffeeshops r,
    coffeeshop.products p,
    coffeeshop.coffeeshop_products rp
  WHERE r.id = rp.coffeeshop_id AND p.id = rp.product_id
WITH DATA;

refresh materialized VIEW coffeeshop.order_coffeeshop_m_view;

DROP function IF EXISTS coffeeshop.refresh_order_coffeeshop_m_view;

CREATE OR replace function coffeeshop.refresh_order_coffeeshop_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW coffeeshop.order_coffeeshop_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_order_coffeeshop_m_view ON coffeeshop.coffeeshop_products;

CREATE trigger refresh_order_coffeeshop_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON coffeeshop.coffeeshop_products FOR each statement
EXECUTE PROCEDURE coffeeshop.refresh_order_coffeeshop_m_view();