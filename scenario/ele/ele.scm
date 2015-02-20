(define-alias CompanyPlayer org.magcruise.gaming.scenario.ele.CompanyPlayer)
(define-alias MarketContext org.magcruise.gaming.scenario.ele.MarketContext)

(define-namespace player "player")
(define-namespace agent "agent")
(define-namespace human "human")
(define-namespace market "market")

(define *human* (list 'X 'Y))
(define *agents* (list 'A 'B 'C 'D 'E 'F 'G 'H))

(define (def:game-scenario)
  (def:ext-context MarketContext)
  (def:ext-players *agents* 'agent CompanyPlayer)
  (def:ext-players *human* 'human CompanyPlayer)
  (def:round
    (def:stage 'init
      (def:task 'market:init))
    (def:parallel-stage 'vote
      (def:players-task *human* 'human:vote))
    (def:stage 'auction
      (def:task 'market:auction)))
  (def:round
    (def:parallel-stage 'status
      (def:players-task (append *human* *agents*) 'player:status))
    (def:restage 'init)
    (def:restage 'vote)
    (def:restage 'auction))
  (def:round 
    (def:restage 'status)))

;;(define (def:before-game ctx ::Context))

(define (market:init ctx ::MarketContext)
  (ctx:init))

(define (market:auction ctx ::MarketContext)
  (ctx:auction))

(define (human:vote ctx ::MarketContext self ::CompanyPlayer)
  (ui:request-input self:name
    (ui:form  (to-string "あなたは" self:type "です．" "必要量は" self:demand "です．" "留保価格をいくらにしますか？")
      (ui:val-input "金額(円/kWh)" 'reservation 6))
    (lambda (reservation)
      (set! self:reservation reservation))))

(define (player:status ctx ::MarketContext self ::CompanyPlayer)
  (ui:show-message self:name (self:history:tabulate)))
