create table member (
    email varchar(50) not null
                    , encrypted_pwd varchar(200) not null
                    , nick_name varchar(50) not null
                    , member_id varchar(50) not null
                    , primary key (member_id));


alter table member add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email);
alter table member add constraint UK_89xks1ovn69amddfyloxbwbkk unique (nick_name);
alter table member add constraint UK_a9bw6sk85ykh4bacjpu0ju5f6 unique (member_id);