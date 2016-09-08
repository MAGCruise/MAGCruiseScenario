(define (setup-players builder ::GameBuilder)
  (builder:addDefPlayers
    (def:player 'Player1 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'Player2 'human org.magcruise.gaming.examples.discussion.actor.Talker)))
