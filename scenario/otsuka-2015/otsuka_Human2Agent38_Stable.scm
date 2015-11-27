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
  (define pnames    (list '1P '2P '3P '4P '5P '6P '7P '8P '9P '10P '11P '12P '13P '14P '15P '16P '17P '18P '19P '20P '21P '22P '23P '24P '25P '26P '27P '28P '29P '30P '31P '32P '33P '34P '35P '36P '37P '38P '39P '40P))
  (define ptypes    (list 'human 'human 'agent  'agent 'agent 'agent 'agent 'agent 'agent 'agent
  						'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent
  						'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent
  						'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent 'agent))
  (define pclasses  (list PublicGoodsGameAgentPlayerHuman
                          PublicGoodsGameAgentPlayerHuman
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional

                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional

                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional

                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional
                          PublicGoodsGameAgentPlayerConditional))

  (builder:addDefContext
    (def:context PublicGoodsGameContext))

  (builder:addDefPlayers
	(def:player (pnames 0) (ptypes 0) (pclasses 0))
	(def:player (pnames 1) (ptypes 1) (pclasses 1))
	(def:player (pnames 2) (ptypes 2) (pclasses 2))
	(def:player (pnames 3) (ptypes 3) (pclasses 3))
	(def:player (pnames 4) (ptypes 4) (pclasses 4))
	(def:player (pnames 5) (ptypes 5) (pclasses 5))
	(def:player (pnames 6) (ptypes 6) (pclasses 6))
	(def:player (pnames 7) (ptypes 7) (pclasses 7))
	(def:player (pnames 8) (ptypes 8) (pclasses 8))
	(def:player (pnames 9) (ptypes 9) (pclasses 9))
	(def:player (pnames 10) (ptypes 10) (pclasses 10))
	(def:player (pnames 11) (ptypes 11) (pclasses 11))
	(def:player (pnames 12) (ptypes 12) (pclasses 12))
	(def:player (pnames 13) (ptypes 13) (pclasses 13))
	(def:player (pnames 14) (ptypes 14) (pclasses 14))
	(def:player (pnames 15) (ptypes 15) (pclasses 15))
	(def:player (pnames 16) (ptypes 16) (pclasses 16))
	(def:player (pnames 17) (ptypes 17) (pclasses 17))
	(def:player (pnames 18) (ptypes 18) (pclasses 18))
	(def:player (pnames 19) (ptypes 19) (pclasses 19))
	(def:player (pnames 20) (ptypes 20) (pclasses 20))
	(def:player (pnames 21) (ptypes 21) (pclasses 21))
	(def:player (pnames 22) (ptypes 22) (pclasses 22))
	(def:player (pnames 23) (ptypes 23) (pclasses 23))
	(def:player (pnames 24) (ptypes 24) (pclasses 24))
	(def:player (pnames 25) (ptypes 25) (pclasses 25))
	(def:player (pnames 26) (ptypes 26) (pclasses 26))
	(def:player (pnames 27) (ptypes 27) (pclasses 27))
	(def:player (pnames 28) (ptypes 28) (pclasses 28))
	(def:player (pnames 29) (ptypes 29) (pclasses 29))
	(def:player (pnames 30) (ptypes 30) (pclasses 30))
	(def:player (pnames 31) (ptypes 31) (pclasses 31))
	(def:player (pnames 32) (ptypes 32) (pclasses 32))
	(def:player (pnames 33) (ptypes 33) (pclasses 33))
	(def:player (pnames 34) (ptypes 34) (pclasses 34))
	(def:player (pnames 35) (ptypes 35) (pclasses 35))
	(def:player (pnames 36) (ptypes 36) (pclasses 36))
	(def:player (pnames 37) (ptypes 37) (pclasses 37))
	(def:player (pnames 38) (ptypes 38) (pclasses 38))
	(def:player (pnames 39) (ptypes 39) (pclasses 39)))

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

