# Users schema

# --- !Ups
CREATE TABLE dashboard (
  id char(36) NOT NULL,
  cagegory smallint(6) UNSIGNED NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  lock_version int(11) UNSIGNED NOT NULL,
  is_deleted boolean NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE issue (
  id char(36) NOT NULL,
  title varchar(255) NOT NULL,
  uri varchar(1024) NOT NULL,
  health_check_uri varchar(1024) DEFAULT NULL,
  description varchar(1024) DEFAULT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  lock_version int(11) UNSIGNED NOT NULL,
  is_deleted boolean NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE seien (
  id char(36) NOT NULL,
  message varchar(258) NOT NULL,
  name varchar(32) DEFAULT NULL,
  link_count int(11) UNSIGNED NOT NULL,
  fault_id char(36) NOT NULL,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  lock_version int(11) UNSIGNED NOT NULL,
  is_deleted boolean NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs

DROP TABLE dashboard;
DROP TABLE issue;
DROP TABLE seien;
