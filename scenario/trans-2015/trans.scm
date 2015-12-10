(define-private-alias TranslationGameContext org.magcruise.gaming.scenario.trans.TranslationGameContext)
(define-private-alias TranslationGamePlayer org.magcruise.gaming.scenario.trans.TranslationGamePlayer)


(define (def:game-classes-dir-zip zip-file ::path) ::DefGameSystemProperty
  (make org.magcruise.gaming.model.def.sys.DefGameClassesDirZip zip-file))

(define (def:game-log-db-path dir ::path relative-path ::string) ::DefGameSystemProperty
  (make org.magcruise.gaming.model.def.sys.DefGameLogDbPath dir relative-path))

(define (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder)
    (builder:setProperties
        ;;(def:game-log-db-path (system-tmpdir) "magcruise-game")
        (def:game-log-db-path (current-path) "magcruise-game")))

(define (def:setup-game-builder builder ::GameBuilder)
  (define pnames    (list 'Player1 'Player2 'Player3 'Player4))
  (define ptypes    (list 'human 'human 'human 'human))
  (define pclasses  (list TranslationGamePlayer TranslationGamePlayer TranslationGamePlayer TranslationGamePlayer))

  (builder:setDefContext
    (def:context TranslationGameContext))

  (builder:addDefPlayers
    (def:player (pnames 0) (ptypes 0) (pclasses 0))
    (def:player (pnames 1) (ptypes 1) (pclasses 1))
    (def:player (pnames 2) (ptypes 2) (pclasses 2))
    (def:player (pnames 3) (ptypes 3) (pclasses 3)))

  (builder:addDefRounds
    (def:round
      (def:stage 
        (def:players-task (builder:getPlayerNames) 'initialize)))
    (def:rounds 25
;;       (def:parallel-stage 
;;         (def:players-task (builder:getPlayerNames) 'decide))
      (def:stage 
        (def:task 'beforeRound))
      (def:parallel-stage 
        (def:task 'Player1 'beforeRound))
      (def:parallel-stage 
        (def:task 'Player1 'decide))
      (def:stage 
        (def:task 'clearing)
      (def:parallel-stage 
        (def:task 'Player1 'afterRound))))))

