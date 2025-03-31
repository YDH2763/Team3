USE OMOK; 

SELECT * FROM omok.user;
SELECT * FROM omok.room;
SELECT * FROM omok.gibo;
SELECT * FROM omok.result order by re_date DESC;
SELECT * FROM omok.score;
SELECT * FROM omok.winning_rate;


SELECT g_count
FROM omok.gibo
where g_ro_id = 2
order by g_ro_id, g_count desc
limit 1;

select g_x
from gibo
where g_count = 8 and g_ro_id = 1;



select * from result join room on re_ro_id=ro_id;

-- 특정유저(dbehdgns2)가 흑팀(U_NAME=RO_B_U_NAME)일때 결과 출력
SELECT U_NAME AS"유저(흑팀)", RO_ID AS"방아이디",RO_NUM AS"방번호",RO_W_U_NAME AS"상대방(백팀)", RE_WINNER AS"게임결과"
FROM RESULT
JOIN ROOM ON RO_ID=RE_RO_ID
JOIN `USER` ON U_NAME = RO_B_U_NAME
WHERE U_NAME="dbehdgns2";	

-- 특정유저(dbehdgns2)가 백팀(U_NAME=RO_W_U_NAME)일때 결과 출력
SELECT U_NAME AS"유저(백팀)", RO_ID AS"방아이디",RO_NUM AS"방번호",RO_B_U_NAME AS"상대방(흑팀)", RE_WINNER AS"게임결과"
FROM RESULT
JOIN ROOM ON RO_ID=RE_RO_ID
JOIN `USER` ON U_NAME = RO_W_U_NAME
WHERE U_NAME="dbehdgns2";

SELECT *
FROM RESULT
JOIN ROOM ON RO_ID = RE_RO_ID;
/* 최종 쿼리문 */
-- 1일 때 흑팀일때 결과
SELECT RO_W_U_NAME AS 상대유저, IF(RE_WINNER = "BLACK", "WIN", "LOSE") AS 승패, RE_DATE AS 일시
FROM RESULT
JOIN ROOM ON RO_ID = RE_RO_ID
WHERE RO_B_U_NAME = "dbehdgns1" ORDER BY RE_DATE DESC;

-- 1일 때 백팀일때 결과
SELECT RO_B_U_NAME AS 상대유저, IF(RE_WINNER = "WHITE", "WIN", "LOSE") AS 승패, RE_DATE AS 일시
FROM RESULT
JOIN ROOM ON RO_ID = RE_RO_ID
WHERE RO_W_U_NAME = "dbehdgns1" ORDER BY RE_DATE DESC;

/*위 결과를 합쳤을 때??=> 틀림*/
SELECT 
	IF(RO_W_U_NAME != "dbehdgns1", RO_W_U_NAME, RO_B_U_NAME)AS 상대유저,
    IF(RE_WINNER = "WHITE", "WIN", 
    ) AS 승패,
    RE_DATE AS 일시
FROM RESULT
JOIN ROOM ON RO_ID = RE_RO_ID
ORDER BY RE_DATE DESC;

SELECT
	IF(RO_W_U_NAME != "dbehdgns1", RO_W_U_NAME, RO_B_U_NAME)AS 상대유저,
	IF(),
    RE_DATE
FROM RESULT
JOIN ROOM ON RE_ID=RE_RO_ID
ORDER BY RE_DATE DESC;

SELECT 
	IF(RO_W_U_NAME != "dbehdgns1", RO_W_U_NAME, RO_B_U_NAME)AS 상대유저,
    
    IF(RO_B_U_NAME = "dbehdgns1" AND RE_WINNER = "BLACK","WIN",
	IF(RO_W_U_NAME = "dbehdgns1" AND RE_WINNER = "WHITE","WIN","LOSE")) AS 승패,
    RE_DATE AS 일시
FROM RESULT
JOIN ROOM ON RO_ID = RE_RO_ID

ORDER BY RE_DATE DESC;

SELECT
	IF(

-- 특정유저(dbehdgns2)가 흑팀(U_NAME=RO_B_U_NAME)일때 해당 유저와 같은 진영으로 플레이한`방아이디`(3)기보 출력
SELECT U_NAME AS"유저(흑팀)", G_RO_ID AS"방 아이디" , G_COUNT AS"수", G_X AS"X좌표", G_Y AS"Y좌표" FROM GIBO
JOIN ROOM ON G_RO_ID=RO_ID
JOIN `USER` ON U_NAME=RO_B_U_NAME
WHERE U_NAME="dbehdgns2" AND G_RO_ID=3;

-- 특정유저(dbehdgns2)가 백팀(U_NAME=RO_W_U_NAME)일때 해당 유저와 같은 진영으로 플레이한 `방아이디`(2)기보 출력
SELECT U_NAME AS"유저(백팀)", G_RO_ID AS"방 아이디" , G_COUNT AS"수", G_X AS"X좌표", G_Y AS"Y좌표" FROM GIBO
JOIN ROOM ON G_RO_ID=RO_ID
JOIN `USER` ON U_NAME=RO_W_U_NAME
WHERE U_NAME="dbehdgns2" AND G_RO_ID=2;
