create table if not exists devices (
    id uuid PRIMARY KEY default gen_random_uuid(),
    name varchar(255),
    type varchar(255),
    location varchar(255),
    user_id uuid,

    constraint fk_devices_users foreign key (user_id) references users(id) on delete cascade
);

create index if not exists idx_devices_user_id on devices(user_id);