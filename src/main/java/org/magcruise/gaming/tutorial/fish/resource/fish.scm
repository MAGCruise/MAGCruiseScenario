
(define (def:setup-game-builder builder ::GameBuilder)
  (builder:addDefContext (def:context org.magcruise.gaming.model.game.SimpleContext))

  (builder:addDefPlayers
   (def:player 'Fisherman1 'human org.magcruise.gaming.model.game.SimplePlayer)
   (def:player 'Fisherman2 'human org.magcruise.gaming.model.game.SimplePlayer))

  (define fishers (list 'Fisherman1 'Fisherman2))

  (builder:addDefRounds
    (def:rounds 3
      (def:parallel-stage 'negotiation
        (def:task  'notify-status)
        (def:players-task fishers 'fisher:comment))
      (def:parallel-stage 'fishing
        (def:players-task fishers 'fisher:decide-number-of-fish))
      (def:stage 'calc-fishing
        (def:task 'go-to-fishing)
        (def:task 'cleanup-and-recover)))))

