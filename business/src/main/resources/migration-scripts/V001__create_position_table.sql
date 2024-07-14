CREATE SEQUENCE IF NOT EXISTS invermo.positions_position_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS invermo.positions
(
    position_id bigint NOT NULL DEFAULT nextval('invermo.positions_position_id_seq'::regclass),
    asset_id bigint NOT NULL,
    position_type text NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT positions_pkey PRIMARY KEY (position_id)
)