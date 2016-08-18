
--
-- Structure for table modulenotifygrumappingmanager_
--

DROP TABLE IF EXISTS directory_mapping_manager;
CREATE TABLE directory_mapping_manager (
id_mappingmanager int(6) NOT NULL,
beankey varchar(255) NOT NULL default '',
guid varchar(255) NOT NULL default '',
reference_demand int(11) NOT NULL default '0',
customer_id int (11) NULL,
mobilephonenumber int(11) NOT NULL default '0',
fixedphonenumber int(11) NOT NULL default '0',
email int(11) NOT NULL default '0',
PRIMARY KEY (id_mappingmanager)
);
