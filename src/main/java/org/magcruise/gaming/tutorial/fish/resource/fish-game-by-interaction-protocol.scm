(define (def:setup-game-builder builder ::GameBuilder)
    (builder:addDefContext (def:context org.magcruise.gaming.model.game.SimpleContext))

  (builder:addDefPlayers
  (def:player 'Fisherman1 'human org.magcruise.gaming.model.game.SimplePlayer)
  (def:player 'Fisherman2 'human org.magcruise.gaming.model.game.SimplePlayer))

  (builder:addDefRounds
  (def:rounds 2
    (def:interaction-protocol-stage 'fish-game
      (def:scenario-task 'Fisherman1 'fisherman1-scenario)
      (def:scenario-task 'Fisherman2 'fisherman2-scenario)
      (def:task 'start-stage))
    (def:stage 'calc-fishing
      (def:task 'go-to-fishing)
      (def:task 'cleanup-and-recover)))))

(define-namespace man1 "man1")

(define (man1:?start-stage ctx ::Context self ::Player event ::ScenarioEvent)
  (event:isNamed 'start-stage))

(define (man1:?response ctx ::Context self ::Player event ::ScenarioEvent)
  (event:isNamed 'response))

(define (man1:?finish-negotiation ctx ::Context self ::Player event ::ScenarioEvent)
  (event:isNamed 'finish-negotiation))

(define (man1:!change-scene ctx ::Context self ::Player event ::ScenarioEvent)
 (self:sendScenarioEvent 'Fisherman1 'notify))

(define (man1:!negotiation ctx ::Context self ::Player event ::ScenarioEvent)
  (fisher:negotiation ctx self)
    (if (equal? (self:get 'text) "END")
      (begin
        (self:sendScenarioEvent 'Fisherman1 'finish-negotiation)
        (self:sendScenarioEvent 'Fisherman2 'finish-negotiation))
      (begin
        (self:sendScenarioEvent 'Fisherman2 'negotiation)
        (self:sendScenarioEvent 'Fisherman1 'response))))

(define (man1:!decide-target ctx ::Context self ::Player event ::ScenarioEvent)
  (ctx:showMessageToAll (to-string event))
  (fisher:decide-number-of-fish ctx self))


(define (fisherman1-scenario)
  (def:player-scenario 'fisherman1
    (def:scene 'negotiation-scene
      (def:behavior 'man1:?start-stage 'man1:!negotiation 'negotiation-scene)
      (def:behavior 'man1:?finish-negotiation 'man1:!change-scene 'decision-scene)
      (def:behavior 'man1:?response 'man1:!negotiation 'negotiation-scene))
    (def:scene 'decision-scene
      (def:default-behavior 'man1:!decide-target 'end-scene))))

(define-namespace man2 "man2")

(define (man2:?negotiation ctx ::Context self ::Player event ::ScenarioEvent)
  (event:isNamed 'negotiation))

(define (man2:?finish-negotiation ctx ::Context self ::Player event ::ScenarioEvent)
  (event:isNamed 'finish-negotiation))

(define (man2:!change-scene ctx ::Context self ::Player event ::ScenarioEvent)
 (self:sendScenarioEvent 'Fisherman2 'notify))

(define (man2:!response ctx ::Context self ::Player event ::ScenarioEvent)
  (fisher:negotiation ctx self)
  (let* ((event 'response))
    (self:sendScenarioEvent 'Fisherman1 event)
    (self:sendScenarioEvent 'Fisherman2 'negotiation)))

(define (man2:!decide-target ctx ::Context self ::Player event ::ScenarioEvent)
  (ctx:showMessageToAll (to-string event))
  (fisher:decide-number-of-fish ctx self))

(define (fisherman2-scenario)
  (def:player-scenario 'fisherman2
    (def:scene 'negotiation-scene
      (def:behavior 'man1:?start-stage 'man1:!negotiation 'negotiation-scene)
      (def:behavior 'man2:?finish-negotiation 'man2:!change-scene 'decision-scene)
      (def:behavior 'man2:?negotiation 'man2:!response 'negotiation-scene))
    (def:scene 'decision-scene
      (def:default-behavior 'man2:!decide-target 'end-scene))))

(define (start-stage ctx ::Context)
  (ctx:sendScenarioEvent 'Fisherman1 'start-stage)
  (ctx:sendScenarioEvent 'Fisherman2 'start-stage))

(define (fisher:negotiation ctx ::Context self ::Player)
  (self:syncRequestToInput
    (ui:form "相手に伝えたいことを入力して下さい．"
      (ui:text "何も無ければENDと入力して下さい．" 'text "END" (make SimpleSubmit)))
    (lambda (text ::String)
      (self:set 'text text)
      (ctx:showMessageToAll (to-string self:name ": " (html:p text))))))

