-- Table: app_role

-- DROP TABLE app_role;

CREATE TABLE app_role
(
  id serial NOT NULL,
  role_name character varying(50),
  department_id integer,
  CONSTRAINT app_role_pkey PRIMARY KEY (id),
  CONSTRAINT app_role_department_id_fkey FOREIGN KEY (department_id)
      REFERENCES department_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_role
  OWNER TO postgres;



-- Table: app_group

-- DROP TABLE app_group;

CREATE TABLE app_group
(
  id serial NOT NULL,
  group_name character varying(50),
  department_id integer,
  CONSTRAINT app_group_pkey PRIMARY KEY (id),
  CONSTRAINT app_group_department_id_fkey FOREIGN KEY (department_id)
      REFERENCES department_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_group
  OWNER TO postgres;

-- Table: app_group_role

-- DROP TABLE app_group_role;

CREATE TABLE app_group_role
(
  id serial NOT NULL,
  app_group_id integer,
  app_role_id integer,
  CONSTRAINT app_group_role_pkey PRIMARY KEY (id),
  CONSTRAINT app_group_role_app_group_id_fkey FOREIGN KEY (app_group_id)
      REFERENCES app_group (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT app_group_role_app_role_id_fkey FOREIGN KEY (app_role_id)
      REFERENCES app_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE app_group_role
  OWNER TO postgres;


  

-- Table: user_category

-- DROP TABLE user_category;

CREATE TABLE user_category
(
  id serial NOT NULL,
  name character varying(50),
  department_id integer,
  CONSTRAINT user_category_pkey PRIMARY KEY (id),
  CONSTRAINT user_category_department_id_fkey FOREIGN KEY (department_id)
      REFERENCES department_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_category
  OWNER TO postgres;


-- Table: user_reg

-- DROP TABLE user_reg;

CREATE TABLE user_reg
(
  id serial NOT NULL,
  email character varying(50),
  password character varying(100),
  user_category_id integer,
  name character varying(50),
  CONSTRAINT user_reg_pkey PRIMARY KEY (id),
  CONSTRAINT user_reg_user_category_id_fkey FOREIGN KEY (user_category_id)
      REFERENCES user_category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_reg
  OWNER TO postgres;


-- DROP TABLE web_user;

CREATE TABLE web_user
(
  id bigserial NOT NULL,
  name character varying(50),
  email character varying(50),
  user_category_id integer,
  created_on time with time zone NOT NULL DEFAULT now(),
  active_c character(1) NOT NULL DEFAULT 'Y'::bpchar,
  password character varying(100),
  authprovider character varying(50),
  CONSTRAINT web_user_pkey PRIMARY KEY (id),
  CONSTRAINT web_user_user_category_id_fkey FOREIGN KEY (user_category_id)
      REFERENCES user_category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE web_user
  OWNER TO postgres;


-- Table: user_group

-- DROP TABLE user_group;

CREATE TABLE user_group
(
  id serial NOT NULL,
  user_id integer,
  app_group_id integer,
  web_user_id bigint,
  CONSTRAINT user_group_pkey PRIMARY KEY (id),
  CONSTRAINT user_group_app_group_id_fkey FOREIGN KEY (app_group_id)
      REFERENCES app_group (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_group_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES user_reg (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_group_web_user_id_fkey FOREIGN KEY (web_user_id)
      REFERENCES web_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_group
  OWNER TO postgres;



-- Table: web_user



-- Table: page_access

-- DROP TABLE page_access;

CREATE TABLE page_access
(
  id serial NOT NULL,
  role_id integer,
  page_id integer,
  CONSTRAINT page_access_pkey PRIMARY KEY (id),
  CONSTRAINT page_access_page_id_fkey FOREIGN KEY (page_id)
      REFERENCES page_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT page_access_role_id_fkey FOREIGN KEY (role_id)
      REFERENCES app_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE page_access
  OWNER TO postgres;



-- Table: role_hierarchy

-- DROP TABLE role_hierarchy;

CREATE TABLE role_hierarchy
(
  id serial NOT NULL,
  head character varying(50),
  contents character varying(100),
  app_role_id integer,
  parent_in_hierarchy_id integer,
  app_context_id integer,
  created_by integer,
  created_on time with time zone,
  updated_by integer,
  updated_on time with time zone,
  active_c character(1),
  CONSTRAINT role_hierarchy_pkey PRIMARY KEY (id),
  CONSTRAINT role_hierarchy_app_context_id_fkey FOREIGN KEY (app_context_id)
      REFERENCES department_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT role_hierarchy_app_role_id_fkey FOREIGN KEY (app_role_id)
      REFERENCES app_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT role_hierarchy_parent_in_hierarchy_id_fkey FOREIGN KEY (parent_in_hierarchy_id)
      REFERENCES role_hierarchy (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role_hierarchy
  OWNER TO postgres;


-- Table: web_access_rights

-- DROP TABLE web_access_rights;

CREATE TABLE web_access_rights
(
  id serial NOT NULL,
  role_id integer,
  page_id integer,
  CONSTRAINT web_access_rights_pkey PRIMARY KEY (id),
  CONSTRAINT web_access_rights_page_id_fkey FOREIGN KEY (page_id)
      REFERENCES page_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT web_access_rights_role_id_fkey FOREIGN KEY (role_id)
      REFERENCES app_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE web_access_rights
  OWNER TO postgres;


