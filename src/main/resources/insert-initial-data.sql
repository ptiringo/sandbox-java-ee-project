INSERT INTO Country SELECT * FROM CSVREAD('classpath:countries.csv', null, 'charset=UTF-8');

COMMIT;