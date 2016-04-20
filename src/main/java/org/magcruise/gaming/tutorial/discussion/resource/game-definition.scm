(define (def:setup-game-builder builder ::GameBuilder)
    (builder:addDefContext (def:context org.magcruise.gaming.model.game.SimpleContext))

  (builder:addDefPlayers
    (def:player 'Talker1 'human org.magcruise.gaming.tutorial.discussion.actor.Talker)
    (def:player 'Talker2 'human org.magcruise.gaming.tutorial.discussion.actor.Talker))

  (builder:addDefRounds
    (def:rounds 2
      (def:interaction-protocol-stage 'discussion-stage
        (def:scenario-task 'Talker1 'talker-scenario)
        (def:scenario-task 'Talker2 'talker-scenario)
        (def:task 'start-stage))
      (def:stage
         (def:task 'show-msg)))))

(define (show-msg ctx ::Context)
  (ctx:showMessageToAll (to-string ctx:roundnum " is finished.")))

(define (start-stage ctx ::Context)
  (ctx:sendScenarioEvent 'Talker1 'start-negotiation)
  (ctx:sendScenarioEvent 'Talker2 'start-negotiation))

(define (talker-scenario)
  (def:player-scenario 'talker-scenario
    (def:scene 'negotiation-scene
      (def:behavior 'isStartNegotiation 'negotiation 'negotiation-scene)
      (def:behavior 'isFinish '!change-scene 'end-scene)
      (def:default-behavior 'negotiation 'negotiation-scene))))

(define (!change-scene self ::Player ctx ::Context event ::ScenarioEvent)
    #t)

