(define (def:setup-game-builder builder ::GameBuilder)

  (define-alias TranslationGameContext org.magcruise.gaming.tutorial.scenario.trans.TranslationGameContext)
  (define-alias TranslationGamePlayer org.magcruise.gaming.tutorial.scenario.trans.TranslationGamePlayer)

  (builder:setDefContext
    (def:context TranslationGameContext))

  (builder:addDefPlayers
    (def:players
      (list 'Player1 'Player2 'Player3 'Player4)
      ;;(list 'Player1)
      'human
       TranslationGamePlayer))

  (builder:addDefAssignRequests
    (def:assign-request 'Player1 "nkjm")
    (def:assign-request 'Player2 "admin"))

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

