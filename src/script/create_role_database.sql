DROP ROLE IF EXISTS prog_admin;
CREATE ROLE prog_admin LOGIN PASSWORD '123456';
DROP DATABASE IF EXISTS library_management;
CREATE DATABASE library_management OWNER = prog_admin;
CREATE DATABASE prog_admin OWNER = prog_admin;
CREATE DATABASE library_management;
\c library_management;
psql -U prog_admin -h localhost
-- password: 123456