CREATE SEQUENCE IF NOT EXISTS invermo.assets_asset_id_seq
    INCREMENT 1
    START 13
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS invermo.assets
(
    asset_id integer NOT NULL DEFAULT nextval('invermo.assets_asset_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    symbol text COLLATE pg_catalog."default",
    type text COLLATE pg_catalog."default",
    currency text COLLATE pg_catalog."default",
    CONSTRAINT assets_pkey PRIMARY KEY (asset_id)
)