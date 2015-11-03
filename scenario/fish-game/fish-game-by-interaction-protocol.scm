(load-relative "fisherman.scm")

(define (def:game-scenario)
  (def:player 'Fisherman1 'human)
  (def:player 'Fisherman2 'human)

  (def:rounds 2
    (def:interaction-protocol-stage 'fish-game
      (def:assign-scenario 'Fisherman1 'fisherman1-scenario)
      (def:assign-scenario 'Fisherman2 'fisherman2-scenario)
      (def:task 'start-stage))
    (def:stage 'calc-fishing
      (def:task 'go-to-fishing)
      (def:task 'cleanup-and-recover))))

(define (fisherman1-scenario)
  (define (?start-stage ctx ::Context self ::Player event ::Event)
    (event:isNamed 'start-stage))

  (define (?response ctx ::Context self ::Player event ::Event)
    (event:isNamed 'response))

  (define (?finish-negotiation ctx ::Context self ::Player event ::Event)
    (event:isNamed 'finish-negotiation))

  (define (!change-scene ctx ::Context self ::Player event ::Event)
   (log:breakpoint event)
   (manager:send-event 'Fisherman1 (manager:make-event 'notify)))

  (define (!negotiation ctx ::Context self ::Player event ::Event)
    (fisher:negotiation ctx self)
      (if (equal? (self:get 'text) "END")
        (begin
          (manager:send-event 'Fisherman1 (self:makeEvent 'finish-negotiation))
          ;; for swing gui mode (sleep 2)
          (manager:send-event 'Fisherman2 (self:makeEvent 'finish-negotiation)))
        (manager:send-event 'Fisherman2 (self:makeEvent 'negotiation))))

  (define (!decide-target ctx ::Context self ::Player event ::Event)
    (ui:show-message 'all event)
    (fisher:decide-number-of-fish ctx self))

  (def:player-scenario 'fisherman1
    (def:scene 'negotiation-scene
      (def:behavior ?start-stage !negotiation 'negotiation-scene)
      (def:behavior ?response !negotiation 'negotiation-scene)
      (def:behavior ?finish-negotiation !change-scene 'decision-scene))
    (def:scene 'decision-scene
      (def:default-behavior !decide-target 'end-scene))))

(define (fisherman2-scenario)
  (define (?negotiation ctx ::Context self ::Player event ::Event)
    (event:isNamed 'negotiation))

  (define (?finish-negotiation ctx ::Context self ::Player event ::Event)
    (event:isNamed 'finish-negotiation))
    
  (define (!change-scene ctx ::Context self ::Player event ::Event)
   (manager:send-event 'Fisherman2 (manager:make-event 'notify)))

  (define (!response ctx ::Context self ::Player event ::Event)
    (fisher:negotiation ctx self)
    (let* ((event (self:makeEvent 'response)))
      (manager:send-event 'Fisherman1 event)))

  (define (!decide-target ctx ::Context self ::Player event ::Event)
    (ui:show-message 'all event)
    (fisher:decide-number-of-fish ctx self))

  (def:player-scenario 'fisherman2 
    (def:scene 'negotiation-scene
      (def:behavior ?finish-negotiation !change-scene 'decision-scene)
      (def:behavior ?negotiation !response 'negotiation-scene))
    (def:scene 'decision-scene
      (def:default-behavior !decide-target 'end-scene))))

(define (start-stage ctx ::Context)
  (manager:send-event 'Fisherman1 (manager:make-event 'start-stage)))

(define (fisher:negotiation ctx ::Context self ::Player)
  (ui:request-to-input self:name
    (ui:form "相手に伝えたいことを入力して下さい．"
      (ui:text "何も無ければENDと入力して下さい．" 'text "END"))
    (lambda (text ::string)
      (self:set 'text text)
      (ui:show-message 'all self:name ": " (html:p text)))))

