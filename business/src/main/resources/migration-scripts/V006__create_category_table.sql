CREATE TABLE IF NOT EXISTS invermo.category
(
    category_id bigint NOT NULL,
    name text NOT NULL,
    parent_id bigint,
    CONSTRAINT category_pkey PRIMARY KEY (category_id)
)