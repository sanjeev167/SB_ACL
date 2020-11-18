-- Table: admin_post

-- DROP TABLE admin_post;

CREATE TABLE admin_post
(
  id bigserial NOT NULL,
  date date,
  message character varying(255),
  CONSTRAINT admin_post_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE admin_post
  OWNER TO postgres;


-- Table: personal_post

-- DROP TABLE personal_post;

CREATE TABLE personal_post
(
  id bigserial NOT NULL,
  date date,
  message character varying(255),
  CONSTRAINT personal_post_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE personal_post
  OWNER TO postgres;


 -- Table: public_post

-- DROP TABLE public_post;

CREATE TABLE public_post
(
  id bigserial NOT NULL,
  date date,
  message character varying(255),
  CONSTRAINT public_post_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public_post
  OWNER TO postgres;
 
  
  
  
  