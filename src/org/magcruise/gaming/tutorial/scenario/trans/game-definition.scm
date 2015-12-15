(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
        ;;(def:game-classes-path "https://www.dropbox.com/s/xq3kx1oxrwo7apx/MAGCruiseScenario.jar?dl=1")
        (def:game-classes-path "bin/")
        (def:game-launcher "org.magcruise.gaming.tutorial.scenario.trans.TranslationGameLauncher")))

(define (def:setup-game-builder builder ::GameBuilder)

  (define-private-alias TranslationGameContext org.magcruise.gaming.tutorial.scenario.trans.TranslationGameContext)
  (define-private-alias TranslationGamePlayer org.magcruise.gaming.tutorial.scenario.trans.TranslationGamePlayer)

  (builder:setDefContext
    (def:context TranslationGameContext))

  (builder:addDefPlayers
    (def:players
      (list 'Player1 'Player2 'Player3 'Player4)
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
        (def:task 'clearing)
      (def:parallel-stage
        (def:players-task (builder:getPlayerNames) 'afterRound))))))

