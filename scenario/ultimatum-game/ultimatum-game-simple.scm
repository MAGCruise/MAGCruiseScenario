;; 最後通牒ゲーム
;; http://www.magcruise.org/jp/developer/tutorial/ultimatum-game/all

(define provided-val 100000)

(define (def:game-scenario)
  (def:player 'FirstPlayer 'human)
  (def:player 'SecondPlayer 'human)

  (def:rounds 2
    (def:stage 'negotiation
      (def:task 'FirstPlayer 'first-player)
      (def:task 'SecondPlayer 'second-player)
      (def:task 'paid-model))))


(define proposition 0)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 通牒者プレーヤ(FirstPlayer)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (first-player ctx ::Context self ::Player)
  (ui:show-message self:name (to-string "第" ctx:roundnum "ラウンドです．"))
  (ui:input-request self:name
    (ui:form  (to-string "あなたは" provided-val "円を受けとりました．いくらを分け与えますか？")
      (ui:val-input "分け与えると提案する金額" 'proposition 10 (make Min 0) (make Max 100000)))
    (lambda (prop)
      (self:set 'proposition prop)
      (set! proposition prop)
      (define msg ::Message (self:makeMessage 'proposition (cons 'proposition prop)))
      (manager:send-message 'SecondPlayer msg))))

(define yes-or-no "")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 判断者プレーヤ(SecondPlayer)のモデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (second-player ctx ::Context self ::Player)
  (ui:show-message self:name (to-string "第" ctx:roundnum "ラウンドです．"))
  (define rec-msg (self:msgbox:pop))
  (log:debug rec-msg)
  (ui:input-request self:name
    (ui:form (to-string "FirstPlayerさんは" provided-val "円を受け取り，あなたに"
                (rec-msg:get 'proposition) "円を分けると言いました．受けとりますか？")
      (ui:radio-input "受けとる？" 'yes-or-no "yes" (list "yes" "no") (list "yes" "no")))
    (lambda (y-n)
      (self:set 'yes-or-no y-n)
      (set! yes-or-no y-n))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 金銭の授受の計算モデル
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (paid-model ctx ::Context)
  (define FirstPlayer (ctx:getPlayer 'FirstPlayer))
  (define SecondPlayer (ctx:getPlayer 'SecondPlayer))

  (if (equal? yes-or-no "yes")
    (money-paid)
    (money-not-paid))

  (define (money-paid)
    (let* ((val-of-p2 proposition)
           (val-of-p1 (- 100000 (to-num proposition))))
      (FirstPlayer:set 'account val-of-p1)
      (SecondPlayer:set 'account val-of-p2)
      (ui:show-message 'all
        "交渉が成立しました．"
        (<ul>
          (<li> (to-string "FirstPlayerは" val-of-p1 "円を取得しました．"))
          (<li> (to-string "SecondPlayerは" val-of-p2 "円を取得しました．"))))))

  (define (money-not-paid)
    (ui:show-message 'all "交渉は成立しませんでした")
    (FirstPlayer:set 'account 0)
    (SecondPlayer:set 'account 0))
)
