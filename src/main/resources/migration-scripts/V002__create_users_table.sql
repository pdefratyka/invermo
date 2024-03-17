CREATE TABLE IF NOT EXISTS invermo.users
(
    user_id bigint NOT NULL,
    login text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    user_name text COLLATE pg_catalog."default" NOT NULL,
    user_email text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
)