(define-alias CompanyPlayer org.magcruise.gaming.scenario.ele.CompanyPlayer)
(define-alias MarketContext org.magcruise.gaming.scenario.ele.MarketContext)

(define-namespace player "player")
(define-namespace agent "agent")
(define-namespace human "human")
(define-namespace market "market")

(define *human-players*  (list 'A 'B))
(define *agent-players* (list 'C 'D 'E 'F 'G 'H))
(define *all-players* (append *human-players* *agent-players*))

(define (def:setup-game-builder game-builder ::GameBuilder)
  (game-builder:addContext (def:context MarketContext))

  (game-builder:addPlayers
    (def:players *agent-players* 'agent CompanyPlayer)
    (def:players *human-players* 'human CompanyPlayer))

  (game-builder:addRounds
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
      (def:registered-stage 'init)
      (def:registered-stage 'vote)
      (def:registered-stage 'auction))
    (def:round 
      (def:registered-stage 'status))))

;; (load framework)                            MAGCruiseフレームワークのロード
;;
;; (load your-game.class)                      ゲームで使われるクラスのロード 【ゲーム開発者がJavaを記述】
;; (load your-game-scenario.scm)               ゲームシナリオで使われる関数とGameBuilder設定の定義．【ゲーム開発者がschemeを記述】
;;
;; game-builder <- (def:setup-game-builder)    GameBuilderの設定．
;; game <- (game-builder:build)                Gameの作成  ．直後に(def:after-build-game game)が呼ばれる．
;; manager <- (create-manager game)            GameManagerの作成 
;; (manager:kickGame)                          Gameの開始 ．
;; (manager:requestToJoin)                     参加者の募集  ．直前に(def:before-start-game context)が呼ばれる
;; (manager:start-round)                       最初のラウンドの実行  ．直前に(def:before-start-first-round cotext)が呼ばれる．

;; Gameが作られた直後に呼び出される．この後に，game が GameManagerに渡されて，起動される．
(define (def:after-build-game game ::Game)
  (log:debug (ln) "proc def:after-build-game .."))

;; Gameが始まる直前に呼び出される．この後に，プレーヤの募集が始まる．
(define (def:before-start-game ctx ::MarketContext)
  (log:debug (ln) "proc def:before-start-game .."))

;; 最初のラウンド(通常は0ラウンドだが，再開した場合は0ラウンドとは限らない)が始まる直前に呼び出される．
;; この後に最初のラウンドの実行が始まる．
(define (def:before-start-first-round ctx ::MarketContext)
  (log:debug (ln) "proc def:before-start-first-round .."))


(define (market:init ctx ::MarketContext)
  (ctx:init))

(define (market:auction ctx ::MarketContext)
  (ctx:auction))

(define (agent:vote ctx ::MarketContext self ::CompanyPlayer)
  (self:vote ctx))

(define (human:vote ctx ::MarketContext self ::CompanyPlayer)
  (manager:sync-request-to-input self:name
    (ui:form (to-string "あなたは" self:type "です．" "必要量は" self:demand "です．" "留保価格をいくらにしますか？")
      (ui:number "金額(円/kWh)" 'reservation self:reservation))
    (lambda (inputPrice)
      (set! self:inputPrice inputPrice))))

(define (player:status ctx ::MarketContext self ::CompanyPlayer)
  (manager:show-message self:name (self:tabulateHistory)))
