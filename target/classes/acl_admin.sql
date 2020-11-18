-- Table: domain_class

-- DROP TABLE domain_class;

CREATE TABLE domain_class
(
  id bigserial NOT NULL,
  class_name character varying(200),
  CONSTRAINT domain_class_pkey PRIMARY KEY (id),
  CONSTRAINT domain_class_class_name_key UNIQUE (class_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE domain_class
  OWNER TO postgres;



-- Table: permission_context

-- DROP TABLE permission_context;

CREATE TABLE permission_context
(
  id serial NOT NULL,
  name character varying(50),
  is_base_context boolean,
  CONSTRAINT permission_context_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE permission_context
  OWNER TO postgres;

  
  
 -- Table: permission_on_domain

-- DROP TABLE permission_on_domain;

CREATE TABLE permission_on_domain
(
  id serial NOT NULL,
  permission_mask_collection character varying(100),
  domain_id integer,
  CONSTRAINT permission_on_domain_pkey PRIMARY KEY (id),
  CONSTRAINT permission_on_domain_domain_id_fkey FOREIGN KEY (domain_id)
      REFERENCES domain_class (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE permission_on_domain
  OWNER TO postgres;



  -- Table: permission_type

-- DROP TABLE permission_type;

CREATE TABLE permission_type
(
  name character varying(50) NOT NULL,
  mask integer NOT NULL,
  id serial NOT NULL,
  is_base_permission boolean,
  permission_context_id integer,
  CONSTRAINT permission_type_pkey PRIMARY KEY (id),
  CONSTRAINT permission_type_permission_context_id_fkey FOREIGN KEY (permission_context_id)
      REFERENCES permission_context (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT permission_type_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE permission_type
  OWNER TO postgres;


-- Table: acl_policy_defined_on_domain

-- DROP TABLE acl_policy_defined_on_domain;

CREATE TABLE acl_policy_defined_on_domain
(
  id serial NOT NULL,
  domain_id integer,
  is_principal boolean,
  permission_assigned_mask_collection character varying(100),
  role_id integer,
  CONSTRAINT acl_policy_defined_on_domain_pkey PRIMARY KEY (id),
  CONSTRAINT acl_policy_defined_on_domain_domain_id_fkey FOREIGN KEY (domain_id)
      REFERENCES domain_class (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acl_policy_defined_on_domain_role_id_fkey FOREIGN KEY (role_id)
      REFERENCES app_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE acl_policy_defined_on_domain
  OWNER TO postgres;



  
  