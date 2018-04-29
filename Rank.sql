select winnerId as id, userName , 
count(winnerId)*10 as score from game, 
user where winnerId <> -1 
and user.id = winnerId group by winnerId 
order by score desc limit 5