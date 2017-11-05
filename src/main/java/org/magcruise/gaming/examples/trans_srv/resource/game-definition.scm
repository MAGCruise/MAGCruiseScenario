(define (def:setup-game-builder builder ::GameBuilder)
  (define *players* (list 'Player1 'Player2 'Player3 'Player4))

  (define-alias TranslationGameContext org.magcruise.gaming.examples.trans_srv.actor.TranslationServiceGameContext)
  (define-alias TranslationGamePlayer org.magcruise.gaming.examples.trans_srv.actor.TranslationServiceGamePlayer)

  (builder:addDefContext
    (def:context TranslationGameContext))

  (builder:addDefPlayers
     (def:player 'Player1 'human TranslationGamePlayer)
     (def:player 'Player2 'human TranslationGamePlayer)
     (def:player 'Player3 'human TranslationGamePlayer)
     (def:player 'Player4 'human TranslationGamePlayer))

  (builder:addDefRounds
    (def:round
      (def:stage
        (def:players-task *players* 'initialize)))
    (def:rounds 25
      (def:stage
        (def:task 'beforeRound))
      (def:parallel-stage
        (def:players-task *players* 'beforeRound))
      (def:parallel-stage
        (def:players-task *players* 'decide))
      (def:stage
        (def:task 'clearing))
      (def:parallel-stage
        (def:players-task *players* 'afterRound)))
    (def:round
      (def:stage
        (def:players-task *players* 'finishGame)))))

