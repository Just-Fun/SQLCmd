-- Table: public.user_actions

-- DROP TABLE public.user_actions;

CREATE TABLE public.user_actions
(
  id integer NOT NULL DEFAULT nextval('user_actions_id_seq'::regclass),
  action character varying(225) NOT NULL,
  database_connection_id integer,
  date text,
  CONSTRAINT user_actions_pkey PRIMARY KEY (id),
  CONSTRAINT user_action_database_connection_fkey FOREIGN KEY (database_connection_id)
      REFERENCES public.database_connection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.user_actions
  OWNER TO postgres;

-- Index: public.fki_user_action_database_connection_fkey

-- DROP INDEX public.fki_user_action_database_connection_fkey;

CREATE INDEX fki_user_action_database_connection_fkey
  ON public.user_actions
  USING btree
  (database_connection_id);


-- Table: public.database_connection

-- DROP TABLE public.database_connection;

CREATE TABLE public.database_connection
(
  id integer NOT NULL DEFAULT nextval('database_connection_seq'::regclass),
  db_name text,
  user_name text,
  CONSTRAINT database_connection_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.database_connection
  OWNER TO postgres;