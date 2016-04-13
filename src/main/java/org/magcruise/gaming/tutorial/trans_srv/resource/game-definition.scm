(define (def:setup-game-builder builder ::GameBuilder)

  (define-alias TranslationGameContext org.magcruise.gaming.tutorial.trans_srv.actor.TranslationServiceGameContext)
  (define-alias TranslationGamePlayer org.magcruise.gaming.tutorial.trans_srv.actor.TranslationServiceGamePlayer)

  (builder:setDefContext
    (def:context TranslationGameContext))

  (builder:addDefPlayers
    (def:players
      (list 'Player1 'Player2 'Player3 'Player4)
      ;;(list 'Player1)
      'human
       TranslationGamePlayer))

  (builder:addDefRounds
    (def:round
      (def:stage
        (def:players-task (builder:getPlayerNames) 'initialize)))
    (def:rounds 25
      (def:stage
        (def:task 'beforeRound))
      (def:parallel-stage
        (def:players-task (builder:getPlayerNames) 'beforeRound))
      (def:parallel-stage
        (def:players-task (builder:getPlayerNames) 'decide))
      (def:stage
        (def:task 'clearing))
      (def:parallel-stage
        (def:players-task (builder:getPlayerNames) 'afterRound)))
    (def:round
      (def:stage
        (def:players-task (builder:getPlayerNames) 'end)))))
