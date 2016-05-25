(define  (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'BigBear 'human FirstPlayer (list 10000 20000 30000 40000 50000 60000 10000 20000 30000 40000))
    (def:player 'SmallBear 'human SecondPlayer (list #t #t #f #t #t #f #t #t #f #t))))

