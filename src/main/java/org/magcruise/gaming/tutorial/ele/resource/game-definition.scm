(define-private-alias CompanyPlayer org.magcruise.gaming.tutorial.ele.actor.CompanyPlayer)
(define-private-alias MarketContext org.magcruise.gaming.tutorial.ele.actor.MarketContext)

(define-private-namespace agent "agent")
(define-private-namespace human "human")

(define (def:setup-game-builder builder ::GameBuilder)
  (define *human-players*  (list 'A ))
  (define *agent-players* (list 'B 'C 'D 'E 'F 'G 'H))
  (define *all-players* (append *human-players* *agent-players*))


  (builder:addDefContext (def:context MarketContext))
  (builder:addDefPlayers
    (def:players *agent-players* 'agent CompanyPlayer)
    (def:players *human-players* 'human CompanyPlayer))
  (builder:addDefRounds
    (def:round
      (def:stage 'init
        (def:task 'init))
      (def:parallel-stage 'vote
        (def:players-task *all-players* 'vote))
      (def:stage 'auction
        (def:task 'auction)))
    (def:round
      (def:parallel-stage 'status
        (def:players-task *all-players* 'status))
      (def:restage 'init)
      (def:restage 'vote)
      (def:restage 'auction))
    (def:round
      (def:restage 'status))))

;; ゲーム実行環境のセットアップ
;; (load framework.csm)                            MAGCruiseフレームワークのロード
;; (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder) 実行環境の準備

;; ゲームのセットアップ
;; (load framework.csm)
;; (load your-game.class)                      ゲームで使われるクラスのロード 【ゲーム開発者がJavaを記述】
;; (load your-game-scenario.scm)               ゲームシナリオで使われる関数とGameBuilder設定の定義．【ゲーム開発者がschemeを記述】
;; game-builder <- (def:setup-game-builder)    GameBuilderの設定．直前に(def:before-setup-game-builder game-builder)が呼ばれる．
;;                                             また直後に(def:after-setup-game-builder game-builder)が呼ばれる．
;; game <- (game-builder:build)                Gameの作成  ．直後に(def:after-build-game game)が呼ばれる．
;; manager <- (create-manager game)            GameManagerの作成
;;
;; ゲームの開始
;; (manager:kickGame)                          Gameの開始 ．
;; (manager:requestToJoin)                     参加者の募集  ．直前に(def:before-start-game context)が呼ばれる
;; (manager:start-round)                       最初のラウンドの実行  ．直前に(def:before-start-first-round cotext)が呼ばれる．


;; (define (def:before-setup-game-builder game-builder ::GameBuilder)
;; (define (def:setup-game-builder game-builder ::GameBuilder)

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


;; ゲームシナリオから呼び出す関数が定義されていない場合，暗黙的に target:methodname が呼び出される．
;; つまり，以下の様な関数は定義しなくても，暗黙的に呼び出される．
;; (define (init ctx ::MarketContext) (ctx:init))
;; (define (auction ctx ::MarketContext) (ctx:auction))
;; (define (status ctx ::MarketContext self ::CompanyPlayer) (self:status ctx))


(define (vote ctx ::MarketContext self ::CompanyPlayer)
  (if (self:isAgent)
      (self:vote ctx)
      (human:vote ctx self)))

(define (agent:vote ctx ::MarketContext self ::CompanyPlayer)
  (self:vote ctx))

(define (human:vote ctx ::MarketContext self ::CompanyPlayer)
  (self:syncRequestToInput
    (ui:form (to-string "あなたは" self:type "です．" "必要量は" self:demand "です．" "留保価格をいくらにしますか？")
      (ui:number "金額(円/kWh)" 'reservation self:reservation))
    (lambda (inputPrice ::integer)
      (set! self:inputPrice inputPrice))))

