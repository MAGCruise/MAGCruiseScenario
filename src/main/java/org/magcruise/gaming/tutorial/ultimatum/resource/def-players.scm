(define  (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
   (def:player 'BigBear 'human FirstPlayer (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null ))
    (def:player 'SmallBear 'human SecondPlayer (list #t #t #t #t #t #t #t #t #t #t))))

