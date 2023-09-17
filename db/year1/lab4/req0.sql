EXPLAIN ANALYZE
SELECT peop."ФАМИЛИЯ", sess."ИД"
FROM "Н_ЛЮДИ" peop
         INNER JOIN "Н_СЕССИЯ" sess on peop."ИД" = sess."ЧЛВК_ИД"
WHERE peop."ИМЯ" < 'Роман'
  AND sess."УЧГОД" < '2008/2009';