(define-private-alias TranslationGameContext org.magcruise.gaming.scenario.trans.TranslationGameContext)
(define-private-alias TranslationGamePlayer org.magcruise.gaming.scenario.trans.TranslationGamePlayer)

;;(define (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder)
;;    (builder:setProperties
;;        (def:game-classes-dir (path "https://www.dropbox.com/sh/x8275gvqnqm42oj/AABTMkXhjlOEKmQu-MjEJcD8a?dl=1"))))

(define (def:setup-game-builder builder ::GameBuilder)

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
        (def:task 'Player1 'decide)
        (def:players-task (builder:getPlayerNames) 'decide))
      (def:stage 
        (def:task 'clearing)
      (def:parallel-stage 
        (def:players-task (builder:getPlayerNames) 'afterRound))))))

