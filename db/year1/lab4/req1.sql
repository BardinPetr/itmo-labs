EXPLAIN ANALYZE
SELECT peop."���", ved."����", sess."��"
FROM "�_����" peop
         INNER JOIN "�_������" sess on peop."��" = sess."����_��"
         INNER JOIN "�_���������" ved on peop."��" = ved."����_��"
WHERE peop."�������" < '���������'
  AND ved."����_��" > 105590
  AND sess."����_��" > 126631;

EXPLAIN ANALYZE
SELECT peop."���", ved."����", sess."��"
FROM "�_����" peop
         INNER JOIN "�_������" sess on peop."��" = sess."����_��"
         INNER JOIN "�_���������" ved on peop."��" = ved."����_��"
WHERE peop."�������" < '���������'
  AND peop."��" > 126631;
