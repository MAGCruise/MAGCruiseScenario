(define-alias PublicGoodsGameContext org.magcruise.gaming.scenario.otsuka.PublicGoodsGameContext)
(define-alias PublicGoodsGameAgentPlayer org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayer)
(define-alias PublicGoodsGameAgentPlayerHuman org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerHuman)
(define-alias PublicGoodsGameAgentPlayerAlwaysPayAll org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerAlwaysPayAll)
(define-alias PublicGoodsGameAgentPlayerAlwaysNo org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerAlwaysNo)
(define-alias PublicGoodsGameAgentPlayerTFT org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerTFT)
(define-alias PublicGoodsGameAgentPlayerConditional org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerConditional)
(define-alias PublicGoodsGameAgentPlayerRandom org.magcruise.gaming.scenario.otsuka.PublicGoodsGameAgentPlayerRandom)

(define-namespace agent "agent")
(define-namespace human "human")


(define (def:setup-game-builder builder ::GameBuilder)
  (define pnames    (list 'FirstPlayer 'SecondPlayer 'ThirdPlayer 'FourthPlayer))
  (define ptypes    (list 'agent 'agent 'agent 'human))
  (define pclasses  (list PublicGoodsGameAgentPlayerAlwaysPayAll
                          PublicGoodsGameAgentPlayerTFT
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerHuman))

  (builder:addDefContext
    (def:context PublicGoodsGameContext))
    
  (builder:addDefPlayers
    (def:player (pnames 0) (ptypes 0) (pclasses 0))
    (def:player (pnames 1) (ptypes 1) (pclasses 1))
    (def:player (pnames 2) (ptypes 2) (pclasses 2))
    (def:player (pnames 3) (ptypes 3) (pclasses 3)))

  (builder:addDefRounds
    (def:round
      (def:stage 'init-stage
        (def:players-task (builder:getPlayerNames) 'init)))
    (def:rounds 5
      (def:stage 'status-stage
        (def:players-task (builder:getPlayerNames) 'status))
      (def:stage 'decide-stage
        (def:players-task (builder:getPlayerNames) 'decide))
      (def:stage 'pay-stage
        (def:players-task (builder:getPlayerNames) 'pay))
      (def:stage 'clearing-stage
        (def:task 'clearing)))
    (def:round
      (def:restage 'status-stage))))



;;(define (human:decide ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
;;  (self:syncRequestForInput 
;;      (ui:form  (to-string (<h1> "ラウンド" ctx:roundnum ": お金出" "しますか？" ))
;;        (ui:number "共同基金への出資金額" 'money 1000 (make Min 0) (make Max self:account) (make Required)))
;;      (lambda (money ::number)
;;        (set! self:investment money)
;;        (self:showMessage (to-string "あなたの出資金額は" self:investment "円です．")))))


