CREATE SEQUENCE IF NOT EXISTS invermo.asset_category_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS invermo.asset_category
(
    id bigint NOT NULL DEFAULT nextval('invermo.asset_category_id_seq'::regclass),
    asset_id bigint NOT NULL,
    category_id bigint NOT NULL,
    CONSTRAINT asset_category_pkey PRIMARY KEY (id)
)