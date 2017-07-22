CREATE TABLE sakila.users (
  user_id smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (user_id));
  
CREATE TABLE sakila.user_roles (
  role_id int(11) NOT NULL AUTO_INCREMENT,
  user_id smallint(5) unsigned NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (role_id),
  UNIQUE KEY uni_user_role (role_id,user_id),
  KEY fk_userid_idx (user_id),
  CONSTRAINT fk_userid FOREIGN KEY (user_id) REFERENCES sakila.users (user_id));