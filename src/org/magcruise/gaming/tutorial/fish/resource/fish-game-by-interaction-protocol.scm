(define-alias ScenarioEvent org.magcruise.gaming.model.game.message.ScenarioEvent)

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


(define (fish-test ctx ::Context self ::Player event ::ScenarioEvent)
  (event:isNamed 'start-stage))

(define (fisherman1-scenario)
  (define (?start-stage ctx ::Context self ::Player event ::ScenarioEvent)
    (event:isNamed 'start-stage))

  (define (?response ctx ::Context self ::Player event ::ScenarioEvent)
    (event:isNamed 'response))

  (define (?finish-negotiation ctx ::Context self ::Player event ::ScenarioEvent)
    (event:isNamed 'finish-negotiation))

  (define (!change-scene ctx ::Context self ::Player event ::ScenarioEvent)
   (self:sendScenarioEvent 'Fisherman1 'notify))

  (define (!negotiation ctx ::Context self ::Player event ::ScenarioEvent)
    (fisher:negotiation ctx self)
      (if (equal? (self:get 'text) "END")
        (begin
          (self:sendScenarioEvent 'Fisherman1 'finish-negotiation)
          (self:sendScenarioEvent 'Fisherman2 'finish-negotiation))
        (self:sendScenarioEvent 'Fisherman2 'negotiation)))

  (define (!decide-target ctx ::Context self ::Player event ::ScenarioEvent)
    (ctx:showMessageToAll (to-string event))
    (fisher:decide-number-of-fish ctx self))

  (def:player-scenario 'fisherman1
    (def:scene 'negotiation-scene
      (def:behavior ?start-stage !negotiation 'negotiation-scene)
      (def:behavior ?response !negotiation 'negotiation-scene)
      (def:behavior ?finish-negotiation !change-scene 'decision-scene))
    (def:scene 'decision-scene
      (def:default-behavior !decide-target 'end-scene))))

(define (fisherman2-scenario)
  (define (?negotiation ctx ::Context self ::Player event ::ScenarioEvent)
    (event:isNamed 'negotiation))

  (define (?finish-negotiation ctx ::Context self ::Player event ::ScenarioEvent)
    (event:isNamed 'finish-negotiation))

  (define (!change-scene ctx ::Context self ::Player event ::ScenarioEvent)
   (self:sendScenarioEvent 'Fisherman2 'notify))

  (define (!response ctx ::Context self ::Player event ::ScenarioEvent)
    (fisher:negotiation ctx self)
    (let* ((event 'response))
      (self:sendScenarioEvent 'Fisherman1 event)))

  (define (!decide-target ctx ::Context self ::Player event ::ScenarioEvent)
    (ctx:showMessageToAll (to-string event))
    (fisher:decide-number-of-fish ctx self))

  (def:player-scenario 'fisherman2
    (def:scene 'negotiation-scene
      (def:behavior ?finish-negotiation !change-scene 'decision-scene)
      (def:behavior ?negotiation !response 'negotiation-scene))
    (def:scene 'decision-scene
      (def:default-behavior !decide-target 'end-scene))))

(define (start-stage ctx ::Context)
  (ctx:sendScenarioEvent 'Fisherman1 'start-stage))

(define (fisher:negotiation ctx ::Context self ::Player)
  (self:syncRequestToInput
    (ui:form "相手に伝えたいことを入力して下さい．"
      (ui:text "何も無ければENDと入力して下さい．" 'text "END"))
    (lambda (text ::String)
      (self:set 'text text)
      (ctx:showMessageToAll (to-string self:name ": " (html:p text))))))

