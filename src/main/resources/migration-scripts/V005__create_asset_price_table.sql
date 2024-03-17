CREATE SEQUENCE IF NOT EXISTS invermo.asset_price_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS invermo.asset_price
(
    id bigint NOT NULL DEFAULT nextval('invermo.asset_price_id_seq'::regclass),
    asset_id bigint NOT NULL,
    price numeric NOT NULL,
    date timestamp without time zone NOT NULL,
    CONSTRAINT asset_price_pkey PRIMARY KEY (id)
)