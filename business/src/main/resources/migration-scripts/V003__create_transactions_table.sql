CREATE SEQUENCE IF NOT EXISTS invermo.transactions_transaction_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS invermo.transactions
(
    transaction_id bigint NOT NULL DEFAULT nextval('invermo.transactions_transaction_id_seq'::regclass),
    position_id bigint NOT NULL,
    number_of_asset numeric NOT NULL,
    price_per_asset numeric NOT NULL,
    currency_exchange numeric NOT NULL,
    transaction_type text COLLATE pg_catalog."default" NOT NULL,
    date timestamp without time zone NOT NULL,
    CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id)
)