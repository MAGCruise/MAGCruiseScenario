(define  (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
   (def:player 'FirstPlayer 'human org.magcruise.gaming.examples.ultimatum.actor.FirstPlayer (list #!null #!null #!null #!null #!null #!null #!null #!null #!null #!null ))
   (def:player 'SecondPlayer 'human org.magcruise.gaming.examples.ultimatum.actor.SecondPlayer (list #t #t #t #t #t #t #t #t #t #t))))

