CREATE SCHEMA IF NOT EXISTS restaurant;

DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'restaurant_user') THEN
        CREATE USER restaurant_user WITH PASSWORD 'restaurant_password';
END IF;
END $$;

GRANT USAGE ON SCHEMA restaurant TO restaurant_user;
GRANT CREATE, CONNECT ON DATABASE postgres_db TO restaurant_user;
GRANT ALL PRIVILEGES ON SCHEMA restaurant TO restaurant_user;
ALTER SCHEMA restaurant OWNER TO restaurant_user;

CREATE SEQUENCE IF NOT EXISTS restaurant.address_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS restaurant.address (
    address_id BIGINT PRIMARY KEY DEFAULT nextval('restaurant.address_id_seq'),
    address_public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    street VARCHAR(200) NOT NULL,
    number_address VARCHAR(20) NOT NULL,
    city_address VARCHAR(50) NOT NULL,
    state_address VARCHAR(50) NOT NULL,
    zip_code VARCHAR(9) NOT NULL,
    last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
ALTER SEQUENCE restaurant.address_id_seq OWNED BY restaurant.address.address_id;

CREATE SEQUENCE IF NOT EXISTS restaurant.users_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS restaurant.users (
    users_id BIGINT PRIMARY KEY DEFAULT nextval('restaurant.users_id_seq'),
    address_id BIGINT NOT NULL REFERENCES restaurant.address(address_id) ON DELETE CASCADE,
    user_public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    name_users VARCHAR(200) NOT NULL UNIQUE,
    login VARCHAR(200) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    type_users CHAR(1) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
ALTER SEQUENCE restaurant.users_id_seq OWNED BY restaurant.users.users_id;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA restaurant TO restaurant_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA restaurant TO restaurant_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA restaurant
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO restaurant_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA restaurant
  GRANT USAGE, SELECT ON SEQUENCES TO restaurant_user;

insert into restaurant.address(address_id, address_public_id, street, number_address, city_address, state_address, zip_code)
values(5555, '5df95d0b-d95b-464b-ab86-9ef65b7e4427','Rua um', '1A', 'são paulo', 'SP','03814-000');

--initial load password admin123
insert into restaurant.users(address_id, user_public_id, name_users, login, email, password, type_users)
values(5555, '8ff8663d-8379-4089-b756-48fa3a100959','admin', 'adminlogin', 'admin@gmail.com', '$2a$10$hWCUXzdW2f2k9utLNjAQp.V0DRzHiZ8NzvDhNDOJjWpCmiIPad4Ui', 'O');
