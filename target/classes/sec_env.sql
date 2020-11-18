-- Table: department_master

-- DROP TABLE department_master;

CREATE TABLE department_master
(
  id serial NOT NULL,
  name character varying(50),
  CONSTRAINT department_master_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE department_master
  OWNER TO postgres;

-- Table: module_master

-- DROP TABLE module_master;

CREATE TABLE module_master
(
  id serial NOT NULL,
  name character varying(50),
  department_id integer,
  CONSTRAINT module_master_pkey PRIMARY KEY (id),
  CONSTRAINT module_master_department_id_fkey FOREIGN KEY (department_id)
      REFERENCES department_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE module_master
  OWNER TO postgres;


-- Table: page_master

-- DROP TABLE page_master;

CREATE TABLE page_master
(
  id serial NOT NULL,
  name character varying(50),
  module_id integer,
  baseurl character varying(100),
  CONSTRAINT page_master_pkey PRIMARY KEY (id),
  CONSTRAINT page_master_module_id_fkey FOREIGN KEY (module_id)
      REFERENCES module_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE page_master
  OWNER TO postgres;

-- Table: tree_menu_type_master

-- DROP TABLE tree_menu_type_master;

CREATE TABLE tree_menu_type_master
(
  id serial NOT NULL,
  name character varying(50),
  created_by integer,
  created_on timestamp with time zone,
  updated_by integer,
  updated_on timestamp with time zone,
  active_c character(1),
  CONSTRAINT tree_menu_type_master_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tree_menu_type_master
  OWNER TO postgres;


-- Table: menu_manager

-- DROP TABLE menu_manager;

CREATE TABLE menu_manager
(
  id serial NOT NULL,
  parent_node_id integer,
  node_name character varying(50),
  node_type character(1),
  tree_menu_type_id integer,
  img_url character varying(50),
  page_id integer,
  created_by integer,
  created_on time with time zone,
  updated_by integer,
  updated_on time with time zone,
  active_c character(1),
  department_id integer,
  CONSTRAINT menu_manager_pkey PRIMARY KEY (id),
  CONSTRAINT menu_manager_department_id_fkey FOREIGN KEY (department_id)
      REFERENCES department_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT menu_manager_page_id_fkey FOREIGN KEY (page_id)
      REFERENCES page_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT menu_manager_parent_node_id_fkey FOREIGN KEY (parent_node_id)
      REFERENCES menu_manager (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT menu_manager_tree_menu_type_id_fkey FOREIGN KEY (tree_menu_type_id)
      REFERENCES tree_menu_type_master (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE menu_manager
  OWNER TO postgres;


