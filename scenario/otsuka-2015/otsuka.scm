(define-alias PublicGoodsGameContext org.magcruise.gaming.scenario.otsuka.PublicGoodsGameContext)
(define-alias PublicGoodsGameAgentPlayer org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayer)
(define-alias PublicGoodsGameAgentPlayerHuman org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerHuman)
(define-alias PublicGoodsGameAgentPlayerAlwaysPayAll org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerAlwaysPayAll)
(define-alias PublicGoodsGameAgentPlayerAlwaysNo org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerAlwaysNo)


(define-namespace agent "agent")
(define-namespace human "human")

(define *players* '(FirstPlayer SecondPlayer ThirdPlayer))

;;(define *human-players*  (list 'A 'B))
;;(define *agent-players* (list 'C 'D 'E 'F 'G 'H))

(define (def:game-scenario)
    (def:ext-context PublicGoodsGameContext)
    (def:ext-player 'FirstPlayer  'agent PublicGoodsGameAgentPlayerAlwaysPayAll)
    (def:ext-player 'SecondPlayer 'human PublicGoodsGameAgentPlayerHuman)
    (def:ext-player 'ThirdPlayer  'agent PublicGoodsGameAgentPlayerAlwaysNo)

    ;; (def:ext-players *agent-players* 'agent CompanyPlayer)
    ;; (def:ext-players *human-players* 'human CompanyPlayer)


  (def:round
    (def:stage 'init-stage
      (def:task 'FirstPlayer 'init)
      (def:task 'SecondPlayer 'init))
    (def:stage 'decide-stage
      (def:task 'FirstPlayer 'agent:decide)
      (def:task 'SecondPlayer 'human:decide)
      (def:task 'ThirdPlayer 'agent:decide))
    (def:stage 'pay-stage
      (def:players-task *players* 'pay))
    (def:stage 'clearing-stage
      (def:task 'clearing)))
      
  (def:rounds 5
    (def:stage 'status-stage
      (def:task 'SecondPlayer 'status))
    (def:restage 'decide-stage)
    (def:restage 'pay-stage)
    (def:restage 'clearing-stage)))
      


(define (pay ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (self:payToFunds ctx))

(define (init ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (self:init ctx))

(define (status ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (ui:show-message self:name (self:history:tabulate)))

(define (agent:decide ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (self:decide ctx))

(define (human:decide ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (ui:request-to-input self:name
      (ui:form  (to-string (<h1> "ラウンド" ctx:roundnum ": お金出" "しますか？" ))
        (ui:number "共同基金への出資金額" 'money 1000 (make Min 0) (make Max self:account)))
      (lambda (money ::number)
        (set! self:investment money)
        (ui:show-message self:name (to-string "あなたの出資金額は" self:investment "円です．")))))


(define (clearing ctx ::PublicGoodsGameContext)
  (ctx:clearing))
