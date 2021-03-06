;; 最後通牒ゲーム

(define provided-val 100000)

(define-namespace ult "ult")
(define (ult:assign ctx ::Context u1 u2)
  (def:assign ctx u1 'FirstPlayer)
  (def:assign ctx u2 'SecondPlayer))


(define (def:setup-game-builder game-builder ::GameBuilder)
  (def:player 'FirstPlayer 'human SimplePlayer)
  (def:player 'SecondPlayer 'human SimplePlayer)

  (def:round
    (def:stage 'start-of-round
      (def:task 'notify-round))
    (def:stage 'init
      (def:task 'FirstPlayer 'init)
      (def:task 'SecondPlayer 'init))
    (def:stage 'negotiation
      (def:task 'FirstPlayer 'first-player)
      (def:task 'SecondPlayer 'second-player)
      (def:task 'paid-model)))

  (def:rounds 2
    (def:restage 'start-of-round)
    (def:stage 'status
      (def:task 'FirstPlayer 'status)
      (def:task 'SecondPlayer 'status))
    (def:restage 'negotiation))

  (def:round
    (def:restage 'status)))

(define (notify-round ctx ::Context)
  (manager:show-message 'all (to-string "第" ctx:roundnum "ラウンドです．")))

(define (init cxt ::Context self ::Player)
  (self:set 'account 0))

(define (status cxt ::Context self ::Player)
  (self:showMessage (self:history:tabulate)))

(define proposition 0)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 通牒者プレーヤ(FirstPlayer)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (first-player context ::Context self ::Player)
  (self:syncRequestToInput 
    (ui:form  (to-string "あなたは" provided-val "円を受けとりました．いくらを分け与えますか？")
      (ui:number "金額" 'proposition 1000))
    (lambda (prop ::number)
      (self:set 'proposition prop)
      (set! proposition prop)
      (define msg ::Message (self:makeMessage 'proposition (cons 'proposition prop)))
      (manager:send-message 'SecondPlayer msg))))

(define yes-or-no "")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 判断者プレーヤ(SecondPlayer)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (second-player context ::Context self ::Player)
  (define rec-msg (self:msgbox:pop))
  (log:debug rec-msg)
  (self:syncRequestToInput 
    (ui:form (to-string "FirstPlayerさんは" provided-val "円を受け取り，あなたに"
                (rec-msg:get 'proposition) "円を分けると言いました．受けとりますか？")
      (ui:radio "受けとる？" 'yes-or-no "yes" (list "yes" "no") (list "yes" "no")))
    (lambda (y-n)
      (self:set 'yes-or-no y-n)
      (set! yes-or-no y-n))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 金銭の授受の計算モデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (paid-model context ::Context)
  (define FirstPlayer (context:getPlayer 'FirstPlayer))
  (define SecondPlayer (context:getPlayer 'SecondPlayer))

  (if (equal? yes-or-no "yes")
    (money-paid)
    (money-not-paid))

  (define (money-paid)
    (let* ((val-of-p2 proposition)
           (val-of-p1 (- 100000 proposition)))
      (FirstPlayer:setAll    (cons 'account (+ (FirstPlayer:get 'account)  val-of-p1)) (cons 'paid val-of-p1) (cons 'yes-or-no "yes"))
      (SecondPlayer:setAll   (cons 'account (+ (SecondPlayer:get 'account) val-of-p2)) (cons 'paid val-of-p2) (cons 'yes-or-no "yes"))
      (manager:show-message 'all
        (<div-class> "alert alert-success" 
          "交渉が成立しました．"
          (html:ul
            (to-string "FirstPlayerは" val-of-p1 "円を取得しました．")
            (to-string "SecondPlayerは" val-of-p2 "円を取得しました．"))))))

  (define (money-not-paid)
    (log:breakpoint (ln) "")
    (manager:show-message 'all (<div-class> "alert alert-warning" "交渉は成立しませんでした"))
    (FirstPlayer:setAll    (cons 'account (+ (FirstPlayer:get 'account)  0)) (cons 'paid 0) (cons 'yes-or-no "no"))
    (SecondPlayer:setAll   (cons 'account (+ (SecondPlayer:get 'account) 0)) (cons 'paid 0) (cons 'yes-or-no "no")))
)
