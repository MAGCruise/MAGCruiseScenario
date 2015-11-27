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
  (define ptypes    (list 'agent 'human 'human 'human))
  (define pclasses  (list PublicGoodsGameAgentPlayerTFT
                          PublicGoodsGameAgentPlayerHuman
                          PublicGoodsGameAgentPlayerHuman
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
    (def:rounds 10
      (def:stage 'status-stage
        (def:players-task (builder:getPlayerNames) 'status))
      (def:stage 'decide-stage
        (def:players-task (builder:getPlayerNames) 'decide))
      (def:stage 'pay-stage
        (def:players-task (builder:getPlayerNames) 'pay))
      (def:stage 'clearing-stage
        (def:task 'clearing)))
    (def:round
      (def:restage 'status-stage)
      (def:stage 'ranking-stage
       (def:players-task (builder:getPlayerNames) 'ranking))
       )))



(define (status ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (self:showMessage (self:tabulateHistory))
  (self:showMessage (to-string ctx:predistribution "円を受け取りました！")))

(define (decide ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (if (self:isAgent)
      (self:decide ctx)
      (human:decide ctx self)))


(define (human:decide ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (self:syncRequestToInput
      (ui:form  (to-string (<h1> "ラウンド" ctx:roundnum ": 出資金額を決定してください" "<p>口座残高 : "self:account "<p>前回の投資額" self:investment "<p>前回の分配金 : " ctx:predistribution "円"))
        (ui:number "共同基金への出資金額" 'money 0 (make Min 0) (make Max self:account)))
      (lambda (money ::number)
        (set! self:investment money)
        (self:showMessage (to-string "あなたの出資金額は" self:investment "円です．")))))

(define (ranking ctx ::PublicGoodsGameContext self ::PublicGoodsGameAgentPlayer)
  (self:showMessage (ctx:ranking)))

