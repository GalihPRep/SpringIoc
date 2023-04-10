CREATE TABLE big5(
	id BIGSERIAL PRIMARY KEY,
	openness FLOAT8,
    conscientiousness FLOAT8,
    extroversion FLOAT8,
    agreeableness FLOAT8,
    neuroticism FLOAT8
);

CREATE TABLE participants(
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(60),
	birthdate DATE,
	big5_id BIGINT REFERENCES big5(id) ON DELETE CASCADE
);

DROP SCHEMA public CASCADE; CREATE SCHEMA public;
TRUNCATE TABLE big5 RESTART IDENTITY CASCADE; TRUNCATE TABLE participants RESTART IDENTITY CASCADE;

SELECT * FROM big5;
SELECT * FROM participants;

SELECT id, openness, conscientiousness, extroversion, agreeableness, neuroticism, 
CASE WHEN openness > 1/3 THEN 'O' WHEN openness < 1/3 THEN 'F' ELSE 'S' END ||
CASE WHEN conscientiousness > 1/3 THEN 'C' WHEN conscientiousness < 1/3 THEN 'L' ELSE 'M' END ||
CASE WHEN extroversion > 1/3 THEN 'E' WHEN extroversion < 1/3 THEN 'I' ELSE 'U' END ||
CASE WHEN agreeableness > 1/3 THEN 'A' WHEN agreeableness < 1/3 THEN 'R' ELSE 'G' END ||
CASE WHEN neuroticism > 1/3 THEN 'N' WHEN neuroticism < 1/3 THEN 'T' ELSE 'Y' END AS type
FROM big5;

SELECT a.id, a.name, a.birthdate, b.type FROM participants a
INNER JOIN (
SELECT id, 
CASE WHEN openness > 1/3 THEN 'O' WHEN openness < 1/3 THEN 'F' ELSE 'S' END ||
CASE WHEN conscientiousness > 1/3 THEN 'C' WHEN conscientiousness < 1/3 THEN 'L' ELSE 'M' END ||
CASE WHEN extroversion > 1/3 THEN 'E' WHEN extroversion < 1/3 THEN 'I' ELSE 'U' END ||
CASE WHEN agreeableness > 1/3 THEN 'A' WHEN agreeableness < 1/3 THEN 'R' ELSE 'G' END ||
CASE WHEN neuroticism > 1/3 THEN 'N' WHEN neuroticism < 1/3 THEN 'T' ELSE 'Y' END AS type
FROM big5
) AS b ON a.big5_id = b.id 
WHERE a.name LIKE '%w%';