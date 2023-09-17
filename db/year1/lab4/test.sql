-- create table t(a integer, b text, c boolean);
--
-- insert into t(a,b,c)
--   select s.id, chr((32+random()*94)::integer), random() < 0.01
--   from generate_series(1,100000) as s(id)
--   order by random();
--
-- create index on t(a);
-- create index on t(b);
--
-- explain select * from t where a <= 10000 order by b


drop table if exists tmp0, tmp1, ttmp cascade ;
create table tmp0(
    id int generated always as identity primary key,
    a text
);

create table tmp1(
    id int generated always as identity primary key,
    b text
);

create table ttmp(
    id0 int references tmp0(id),
    id1 int references tmp1(id),
    primary key(id0, id1)
);

create index on tmp0 using hash(a);
create index on tmp1 using btree(b);

insert into tmp0 (a)
  select (s.id)::text
  from generate_series(1,10000) as s(id)
  order by random();

insert into tmp1 (b)
  select (s.id)::text
  from generate_series(1,10000) as s(id)
  order by random();

insert into ttmp
  select s.id, 10001-s.id
  from generate_series(1,10000) as s(id);


explain analyse
select b, t2.a from tmp1
    join ttmp t on tmp1.id = t.id1
    join tmp0 t2 on t2.id = t.id0;
