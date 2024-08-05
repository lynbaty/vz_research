CREATE TABLE IF NOT EXISTS migration
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    create_timestamp timestamp(6) without time zone,
    delete_timestamp timestamp(6) without time zone,
    title character varying(255) COLLATE pg_catalog."default",
    update_timestamp timestamp(6) without time zone,
    CONSTRAINT id_pkey PRIMARY KEY (id)
)