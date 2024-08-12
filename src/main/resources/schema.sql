CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


create table if not exists exchange_rates (
    id uuid primary key DEFAULT uuid_generate_v4(),
    currency varchar(50),
    value varchar(25),
    date date;
    
);


