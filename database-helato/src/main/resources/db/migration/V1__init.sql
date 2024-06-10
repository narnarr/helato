create table member
(
    id               bigint primary key auto_increment,
    username         varchar(20)     not null,
    password         text            not null,
    mobile_phone_num varchar(10)     not null,
    extra_phone_num  varchar(10)     null,
    birth_date       date            null,
    gender           enum ('M', 'F') null,
    inserted_at      datetime        not null,
    updated_at       datetime        not null
)