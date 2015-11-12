(load-relative "fisherman.scm")

(define (def:setup-game-builder game-builder ::GameBuilder)
  (def:player 'Fisherman1 'human SimplePlayer)
  (def:player 'Fisherman2 'human SimplePlayer)

  (def:rounds 2
    (def:interaction-protocol-stage 'fish-game
      (def:scenario-task 'Fisherman1 'fisherman1-scenario)
      (def:scenario-task 'Fisherman2 'fisherman2-scenario)
      (def:task 'start-stage))
    (def:stage 'calc-fishing
      (def:task 'go-to-fishing)
      (def:task 'cleanup-and-recover))))


(define (fish-test ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
  (event:isNamed 'start-stage))

(define (fisherman1-scenario)
  (define (?start-stage ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (event:isNamed 'start-stage))

  (define (?response ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (event:isNamed 'response))

  (define (?finish-negotiation ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (event:isNamed 'finish-negotiation))

  (define (!change-scene ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
   (log:breakpoint event)
   (manager:send-scenario-event 'Fisherman1 (manager:make-scenario-event 'notify)))

  (define (!negotiation ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (fisher:negotiation ctx self)
      (if (equal? (self:get 'text) "END")
        (begin
          (manager:send-scenario-event 'Fisherman1 (self:makeEvent 'finish-negotiation))
          (manager:send-scenario-event 'Fisherman2 (self:makeEvent 'finish-negotiation)))
        (manager:send-scenario-event 'Fisherman2 (self:makeEvent 'negotiation))))

  (define (!decide-target ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (manager:show-message 'all event)
    (fisher:decide-number-of-fish ctx self))

  (def:player-scenario 'fisherman1
    (def:scene 'negotiation-scene
      (def:behavior ?start-stage !negotiation 'negotiation-scene)
      (def:behavior ?response !negotiation 'negotiation-scene)
      (def:behavior ?finish-negotiation !change-scene 'decision-scene))
    (def:scene 'decision-scene
      (def:default-behavior !decide-target 'end-scene))))

(define (fisherman2-scenario)
  (define (?negotiation ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (event:isNamed 'negotiation))

  (define (?finish-negotiation ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (event:isNamed 'finish-negotiation))
    
  (define (!change-scene ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
   (manager:send-scenario-event 'Fisherman2 (manager:make-scenario-event 'notify)))

  (define (!response ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (fisher:negotiation ctx self)
    (let* ((event (self:makeEvent 'response)))
      (manager:send-scenario-event 'Fisherman1 event)))

  (define (!decide-target ctx ::Context self ::Player messenger ::Messenger event ::ScenarioEvent)
    (manager:show-message 'all event)
    (fisher:decide-number-of-fish ctx self))

  (def:player-scenario 'fisherman2 
    (def:scene 'negotiation-scene
      (def:behavior ?finish-negotiation !change-scene 'decision-scene)
      (def:behavior ?negotiation !response 'negotiation-scene))
    (def:scene 'decision-scene
      (def:default-behavior !decide-target 'end-scene))))

(define (start-stage ctx ::Context)
  (manager:send-scenario-event 'Fisherman1 (manager:make-scenario-event 'start-stage)))

(define (fisher:negotiation ctx ::Context self ::Player)
  (self:syncRequestForInput 
    (ui:form "相手に伝えたいことを入力して下さい．"
      (ui:text "何も無ければENDと入力して下さい．" 'text "END"))
    (lambda (text ::string)
      (self:set 'text text)
      (manager:show-message 'all self:name ": " (html:p text)))))

