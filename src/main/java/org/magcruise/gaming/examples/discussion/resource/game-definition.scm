(define (def:setup-game-builder builder ::GameBuilder)
    (builder:addDefContext (def:context org.magcruise.gaming.model.game.SimpleContext))

  (builder:addDefPlayers
    (def:player 'Player1 'human org.magcruise.gaming.examples.discussion.actor.Talker)
    (def:player 'Player2 'human org.magcruise.gaming.examples.discussion.actor.Talker))

  (builder:addDefRounds
    (def:round repeat: 2
      (def:stage type: 'parallel name: 'discussion-stage
        (def:scenario-task 'Player1 'talker-scenario)
        (def:scenario-task 'Player2 'talker-scenario)
        (def:task 'start-stage))
      (def:stage
         (def:task 'show-msg)))))

(define (show-msg ctx ::Context)
  (ctx:showMessageToAll (to-string "roundnum. " ctx:roundnum " is finished.")))

(define (start-stage ctx ::Context)
  (ctx:sendGameEvent 'Player1 'start-negotiation)
  (ctx:sendGameEvent 'Player2 'start-negotiation))

(define (talker-scenario)
  (def:player-scenario 'talker-scenario
    (def:scene 'negotiation-scene
      (def:behavior 'isStartNegotiation 'negotiation 'negotiation-scene)
      (def:behavior 'isFinish '!change-scene 'exit)
      (def:default-behavior 'negotiation 'negotiation-scene))))

(define (!change-scene self ::Player ctx ::Context event ::GameEvent)
    #t)

