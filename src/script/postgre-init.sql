-- Drop the database if it exists
DROP DATABASE IF EXISTS restdb;

-- Drop the user if it exists
DROP USER IF EXISTS restadmin;

-- Create the database
CREATE DATABASE restdb;

-- Create the user
CREATE USER restadmin WITH ENCRYPTED PASSWORD 'password';

-- Grant privileges to the user on the database
GRANT CONNECT ON DATABASE restdb TO restadmin;
GRANT USAGE ON SCHEMA public TO restadmin;
GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES, TRIGGER ON ALL TABLES IN SCHEMA public TO restadmin;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO restadmin;