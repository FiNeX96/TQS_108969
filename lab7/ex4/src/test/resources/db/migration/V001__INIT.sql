CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY,
    maker VARCHAR(255),
    model VARCHAR(255)
);

insert into cars (maker, model) values ('Toyota', 'Corolla');
insert into cars (maker, model) values ('Toyota', 'Camry');
insert into cars (maker, model) values ('Mercedes', 'C-Class');
insert into cars (maker, model) values ('Renault', 'Clio');