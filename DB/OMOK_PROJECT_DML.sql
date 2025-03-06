USE OMOK; 

INSERT INTO USER(U_NAME, U_PW) VALUES
	("정순형", "1836"), ("박소은", "6974"),
    ("빅토리아", "5877"), ("김가람", "4485");
    
INSERT INTO RESULT(R_U_NUM, R_POSITION, R_OUTCOME) VALUES
	(1, "BLACK", "WIN"), (2, "WHITE", "LOSE"), 
    (1, "WHITE", "WIN"), (3, "BLACK", "LOSE"), 
    (1, "WHITE", "DRAW"), (4, "BLACK", "DRAW"),
    (2, "BLACK", "WIN"), (3, "WHITE", "LOSE"), 
    (2, "WHITE", "WIN"), (4, "BLACK", "LOSE"), 
    (3, "WHITE", "DRAW"), (4, "BLACK", "DRAW");