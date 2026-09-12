create table if not exists users (
    id uuid PRIMARY KEY default gen_random_uuid(),
    name varchar(100) not null,
    surname varchar(100),
    email varchar(255) not null,
    address text,
    alerting bit not null default 0,
    energy_alerting_threshold double precision not null default 0
);

create unique index if not exists idx_users_email on users(email);