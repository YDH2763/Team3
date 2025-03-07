USE OMOK; 

INSERT INTO USER(U_NAME, U_PW) VALUES
	("정순형", "1836"), ("박소은", "6974"),
    ("빅토리아", "5877"), ("김가람", "4485");

INSERT ROOM(RO_NUM, RO_B_U_NAME, RO_W_U_NAME) VALUES
-- 	(15, "정순형", "박소은"), (1, "김가람", "빅토리아"),
--     (7, "정순형", "빅토리아"), (9, "김가람", "박소은"),
    (15, "박소은", "정순형"), (1,  "빅토리아","김가람"),
    (7, "빅토리아", "정순형"), (9, "박소은", "김가람");
    
INSERT INTO RESULT(RE_WINNER, RE_RO_ID) VALUES
	("BLACK", 1), ("DRAW", 2), ("BLACK", 3), ("BLACK", 4),
	("WHITE", 5), ("DRAW", 6), ("WHITE", 7), ("WHITE", 8);

SELECT * FROM omok.user;    
SELECT * FROM omok.result;
SELECT * FROM omok.score;