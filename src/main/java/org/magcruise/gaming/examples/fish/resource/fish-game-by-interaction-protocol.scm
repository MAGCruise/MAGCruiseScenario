(define-alias FishGamePlayer org.magcruise.gaming.examples.fish.actor.FishGamePlayer)

(define (def:setup-game-builder builder ::GameBuilder)
    (builder:addDefContext (def:context org.magcruise.gaming.model.game.SimpleContext))

  (builder:addDefPlayers
    (def:player 'Player1 'human org.magcruise.gaming.examples.fish.actor.FishGamePlayer)
    (def:player 'Player2 'human org.magcruise.gaming.examples.fish.actor.FishGamePlayer))

  (builder:addDefRounds
  (def:rounds 2
    (def:interaction-protocol-stage 'fish-game
      (def:scenario-task 'Player1 'Player1-scenario)
      (def:scenario-task 'Player2 'Player2-scenario)
      (def:task 'start-stage))
    (def:stage 'calc-fishing
      (def:task 'go-to-fishing)
      (def:task 'cleanup-and-recover)))))

(define-namespace man1 "man1")

(define (?negotiation self ::FishGamePlayer ctx ::Context event ::ScenarioEvent)
  (event:isNamed 'negotiation))

(define (?finish-negotiation self ::FishGamePlayer ctx ::Context event ::ScenarioEvent)
  (event:isNamed 'finish-negotiation))

(define (!change-scene self ::FishGamePlayer ctx ::Context event ::ScenarioEvent) #t)

(define (man1:!negotiation self ::FishGamePlayer ctx ::Context event ::ScenarioEvent)
  (self:negotiation ctx)
    (if (equal? (self:get 'text) "END")
      (begin
        (self:sendScenarioEvent 'Player1 'finish-negotiation)
        (self:sendScenarioEvent 'Player2 'finish-negotiation))
      (begin
        (self:sendScenarioEvent 'Player1 'negotiation))))

(define (man1:!decide-target self ::FishGamePlayer ctx ::Context event ::ScenarioEvent)
  (ctx:showMessageToAll (to-string event))
  (fisher:decide-number-of-fish ctx self))


(define (Player1-scenario)
  (def:player-scenario 'Player1
    (def:scene 'negotiation-scene
      (def:behavior '?negotiation 'man1:!negotiation 'negotiation-scene)
      (def:behavior '?finish-negotiation '!change-scene 'decision-scene))
    (def:scene 'decision-scene
      (def:default-behavior 'man1:!decide-target 'end-scene))))

(define-namespace man2 "man2")

(define (man2:!negotiation self ::FishGamePlayer ctx ::Context event ::ScenarioEvent)
  (self:negotiation ctx)
    (if (equal? (self:get 'text) "END")
      (begin
        (self:sendScenarioEvent 'Player1 'finish-negotiation)
        (self:sendScenarioEvent 'Player2 'finish-negotiation))
      (begin
        (self:sendScenarioEvent 'Player2 'negotiation))))

(define (man2:!decide-target self ::FishGamePlayer ctx ::Context event ::ScenarioEvent)
  (ctx:showMessageToAll (to-string event))
  (fisher:decide-number-of-fish ctx self))

(define (Player2-scenario)
  (def:player-scenario 'Player2
    (def:scene 'negotiation-scene
      (def:behavior '?negotiation 'man2:!negotiation 'negotiation-scene)
      (def:behavior '?finish-negotiation '!change-scene 'decision-scene))
    (def:scene 'decision-scene
      (def:default-behavior 'man2:!decide-target 'end-scene))))

(define (start-stage ctx ::Context)
  (ctx:sendScenarioEvent 'Player1 'negotiation)
  (ctx:sendScenarioEvent 'Player2 'negotiation))


