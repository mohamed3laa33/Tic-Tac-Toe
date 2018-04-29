select * from game where ((f_P_Pid = 1 AND s_P_Pid = 5) OR (s_P_Pid = 5 AND s_P_Pid = 1 ))
AND winnerId is null;