EXPLAIN ANALYZE
SELECT peop."хлъ", ved."дюрю", sess."хд"
FROM "м_кчдх" peop
         INNER JOIN "м_яеяяхъ" sess on peop."хд" = sess."вкбй_хд"
         INNER JOIN "м_беднлнярх" ved on peop."хд" = ved."вкбй_хд"
WHERE peop."тюлхкхъ" < 'юТЮМЮЯЭЕБ'
  AND ved."вкбй_хд" > 105590
  AND sess."вкбй_хд" > 126631;

EXPLAIN ANALYZE
SELECT peop."хлъ", ved."дюрю", sess."хд"
FROM "м_кчдх" peop
         INNER JOIN "м_яеяяхъ" sess on peop."хд" = sess."вкбй_хд"
         INNER JOIN "м_беднлнярх" ved on peop."хд" = ved."вкбй_хд"
WHERE peop."тюлхкхъ" < 'юТЮМЮЯЭЕБ'
  AND peop."хд" > 126631;
