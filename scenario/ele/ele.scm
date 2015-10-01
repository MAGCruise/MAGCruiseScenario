(define-alias CompanyPlayer org.magcruise.gaming.scenario.ele.CompanyPlayer)
(define-alias MarketContext org.magcruise.gaming.scenario.ele.MarketContext)

(define-namespace player "player")
(define-namespace agent "agent")
(define-namespace human "human")
(define-namespace market "market")

(define *human-players*  (list 'A 'B))
(define *agent-players* (list 'C 'D 'E 'F 'G 'H))
(define *all-players* (append *human-players* *agent-players*))

(define (def:game-scenario)
  (def:ext-context MarketContext)
  (def:ext-players *agent-players* 'agent CompanyPlayer)
  (def:ext-players *human-players* 'human CompanyPlayer)
  (def:round
    (def:stage 'init
      (def:task 'market:init))
    (def:parallel-stage 'vote
      (def:players-task *agent-players* 'agent:vote)
      (def:players-task *human-players* 'human:vote))
    (def:stage 'auction
      (def:task 'market:auction)))
  (def:round
    (def:parallel-stage 'status
      (def:players-task *all-players* 'player:status))
    (def:restage 'init)
    (def:restage 'vote)
    (def:restage 'auction))
  (def:round 
    (def:restage 'status)))

(define (market:init ctx ::MarketContext)
  (ctx:init))

(define (market:auction ctx ::MarketContext)
  (ctx:auction))

(define (agent:vote ctx ::MarketContext self ::CompanyPlayer)
  (self:vote ctx))

(define (human:vote ctx ::MarketContext self ::CompanyPlayer)
  (ui:request-to-input self:name
    (ui:form (to-string "あなたは" self:type "です．" "必要量は" self:demand "です．" "留保価格をいくらにしますか？")
      (ui:number "金額(円/kWh)" 'reservation self:reservation))
    (lambda (inputPrice)
      (set! self:inputPrice inputPrice))))

(define (player:status ctx ::MarketContext self ::CompanyPlayer)
  (ui:show-message self:name (self:tabulateHistory)))
