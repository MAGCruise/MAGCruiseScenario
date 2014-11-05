(require 'list-lib)

(define-alias Market org.magcruise.gaming.scenario.ele.Market)
(define-alias CompanyPlayer org.magcruise.gaming.scenario.ele.CompanyPlayer)

(define-namespace player "player")
(define-namespace agent "agent")
(define-namespace human "human")
(define-namespace market "market")

(define *human* (list 'X 'Y))
(define *agents* (list 'A 'B 'C 'D 'E 'F 'G 'H))
(define *market* ::Market (make Market))

(define (def:game-scenario)
  (def:ext-players *agents* 'agent CompanyPlayer)
  (def:ext-players *human* 'human CompanyPlayer)
  (def:round
    (def:stage 'init
      (def:task 'market:init))
    (def:concurrent-stage 'vote
      (def:players-task *human* 'human:vote))
    (def:stage 'auction
      (def:task 'market:auction)))
  (def:round
    (def:concurrent-stage 'status
      (def:players-task (append *human* *agents*) 'player:status))
    (def:restage 'init)
    (def:restage 'vote)
    (def:restage 'auction))
  (def:round 
    (def:restage 'status)))

;;(define (def:setup-game ctx ::Context)
;;  (*market*:init ctx))

(define (market:init ctx ::Context)
  (*market*:init ctx))

(define (market:auction ctx ::Context)
  (*market*:auction ctx))

(define (human:vote ctx ::Context self ::CompanyPlayer)
  (ui:request-input self:name
    (ui:form  (to-string "あなたは" self:type "です．" "必要量は" self:demand "です．" "留保価格をいくらにしますか？")
      (ui:val-input "金額(円/kWh)" 'reservation 6))
    (lambda (reservation)
      (set! self:reservation reservation))))

(define (player:status ctx ::Context self ::CompanyPlayer)
  (ui:show-message self:name (self:history:tabulate)))
